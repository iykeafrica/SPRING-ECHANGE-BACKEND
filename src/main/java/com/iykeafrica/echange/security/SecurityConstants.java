package com.iykeafrica.echange.security;

//import com.example.mobile.app.ws.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; //10 days milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_ID = "User ID";
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_SECRET = "jf9i4jgu8nfl0";


//    public static String getTokenSecret(){
//        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
//        return appProperties.getTokenSecret();
//    }
}
