package com.iykeafrica.echange.ui.controller;

import com.iykeafrica.echange.service.UserService;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.ui.model.request.UserSendMoneyRequest;
import com.iykeafrica.echange.ui.model.request.UserSignUpRequest;
import com.iykeafrica.echange.ui.model.request.UserUpdatePersonalRecordRequest;
import com.iykeafrica.echange.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserSignUpRequest userSignUpRequest){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userSignUpRequest, userDto);
        UserDto registeredUser = userService.createUser(userDto);

        BeanUtils.copyProperties(registeredUser, returnValue);

        return returnValue;
    }

    @GetMapping(path = "/{userName}" , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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

    @PutMapping(path = "send-money/{requesterWalletId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserSendMoneyResponse sendMoney(@PathVariable String requesterWalletId, @RequestBody UserSendMoneyRequest userSendMoneyRequest){
        UserSendMoneyResponse returnValue = new UserSendMoneyResponse();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userSendMoneyRequest, userDto);

        UserDto moneySent = userService.sendMoney(requesterWalletId, userDto);

        BeanUtils.copyProperties(moneySent, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{walletId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest  updateUser(@PathVariable String walletId, @RequestBody UserUpdatePersonalRecordRequest userUpdatePersonalRecordRequest){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userUpdatePersonalRecordRequest, userDto);

        UserDto updatedUser = userService.updateUser(walletId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }


    @DeleteMapping(path = "/{walletId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String walletId){
        OperationStatusModel returnValue = new OperationStatusModel();

        userService.deleteUser(walletId);

        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "12") int limit) {

        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);

        for (UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return  returnValue;
    }
}
