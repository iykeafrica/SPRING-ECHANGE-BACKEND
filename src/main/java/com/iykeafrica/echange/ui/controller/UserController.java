package com.iykeafrica.echange.ui.controller;

import com.iykeafrica.echange.service.UserService;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.ui.model.request.UserSendMoneyRequest;
import com.iykeafrica.echange.ui.model.request.UserSignUpRequest;
import com.iykeafrica.echange.ui.model.request.UserUpdatePersonalRecordRequest;
import com.iykeafrica.echange.ui.model.response.UserRest;
import com.iykeafrica.echange.ui.model.response.UserSendMoneyResponse;
import com.iykeafrica.echange.ui.model.response.UserVerifyWalletIdResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public UserRest createUser(@RequestBody UserSignUpRequest userSignUpRequest){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userSignUpRequest, userDto);
        UserDto registeredUser = userService.createUser(userDto);

        BeanUtils.copyProperties(registeredUser, returnValue);

        return returnValue;
    }

    @GetMapping(path = "/{userName}")
    public UserRest getUser(@PathVariable String userName){ //for admin user only; with phone, email or walletId
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUser(userName);

        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @GetMapping(path = "1/{walletId}")
    public UserVerifyWalletIdResponse getUserByWalletId(@PathVariable String walletId){
        UserVerifyWalletIdResponse returnValue = new UserVerifyWalletIdResponse();
        UserDto userDto = userService.getUserByWalletId(walletId);

        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{requesterWalletId}")
    public UserSendMoneyResponse sendMoney(@PathVariable String requesterWalletId, @RequestBody UserSendMoneyRequest userSendMoneyRequest){
        UserSendMoneyResponse returnValue = new UserSendMoneyResponse();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userSendMoneyRequest, userDto);

        UserDto moneySent = userService.sendMoney(requesterWalletId, userDto);

        BeanUtils.copyProperties(moneySent, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{walletId}")
    public UserRest  updateUser(@PathVariable String walletId, @RequestBody UserUpdatePersonalRecordRequest userUpdatePersonalRecordRequest){
        UserRest returnValue = new UserRest();

        return returnValue;
    }


    @DeleteMapping
    public String deleteUser(){
        return "user deleted successfully";
    }
}
