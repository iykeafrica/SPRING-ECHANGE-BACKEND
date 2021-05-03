package com.iykeafrica.echange.service;

import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.ui.model.response.UserSignUpResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
}
