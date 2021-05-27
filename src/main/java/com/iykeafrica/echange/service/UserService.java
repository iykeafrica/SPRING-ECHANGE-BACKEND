package com.iykeafrica.echange.service;

import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.ui.model.request.UserUpdatePersonalRecordRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String userName);
    UserDto getUserByWalletId(String walletId);
    UserDto creditMoney(String requesterWalletId, UserDto user);
    UserDto debitMoney(String senderWalletId, UserDto user);
    UserDto updateUser(String walletId, UserDto user);
    UserDto updateUserFCM(String walletId, UserDto user);
    void deleteUser(String walletId);
    void deleteAllUser();
    List<UserDto> getUsers(int page, int limit);

//    UserDto createExtras(String walletID, UserDto userDto);

//    UserDto createExtra(String walletID, UserDto userDto);

}
