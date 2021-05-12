package com.iykeafrica.echange.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iykeafrica.echange.SpringApplicationContext;
import com.iykeafrica.echange.service.UserService;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.shared.dto.utils.Utils;
import com.iykeafrica.echange.ui.model.request.UserSignInRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    Utils utils;


    //loadUserByUsername method implemented in UserServiceImpl from the UserService interface that extended UserDetailsService
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserSignInRequest creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserSignInRequest.class);

            String userLogin = "";
            if (creds.getPhoneNo() == null && creds.getEmail() == null) {
                userLogin = creds.getWalletId();
            } else if (creds.getPhoneNo() == null && creds.getWalletId() == null) {
                userLogin = creds.getEmail();
            } else if (creds.getEmail() == null && creds.getWalletId() == null) {
                userLogin = creds.getPhoneNo();
            }

                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLogin,
                                creds.getPassword(),
                                new ArrayList<>()
                        )
                );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //once request is successful
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String userName = ((User) authResult.getPrincipal()).getUsername(); //phoneNo,email or userId used to sign in successfully
//        String tokenSecret = new SecurityConstants.getTokenSecret();

        //To be used in the authorization filter
        // it will be included in the header information of our request
        // and the client that receives this response it will extract it and store it (IOS kitchen in ios)
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();

        //to get walletId
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUser(userName);
        String walletId = userDto.getWalletId();

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader(SecurityConstants.USER_ID, walletId);
    }

}