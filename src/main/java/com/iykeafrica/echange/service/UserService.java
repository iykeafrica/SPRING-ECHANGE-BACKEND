package com.iykeafrica.echange.service;

import com.iykeafrica.echange.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String userName);
    UserDto getUserByWalletId(String walletId);
    UserDto sendMoney(String requesterWalletId, UserDto user);
}
