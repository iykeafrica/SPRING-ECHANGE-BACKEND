package com.iykeafrica.echange.ui.controller;

import com.iykeafrica.echange.io.repositories.UserRepository;
import com.iykeafrica.echange.service.UserService;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.ui.model.request.UserSignUpRequest;
import com.iykeafrica.echange.ui.model.response.UserSignUpResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public UserSignUpResponse createUser(@RequestBody UserSignUpRequest userSignUpRequest){
        UserSignUpResponse returnValue = new UserSignUpResponse();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userSignUpRequest, userDto);
        UserDto registeredUser = userService.createUser(userDto);

        BeanUtils.copyProperties(registeredUser, returnValue);

        return returnValue;
    }

    @GetMapping
    public String getUser(){
        return "user gotten successfully";
    }

    @PutMapping
    public String updateUser(){
        return "user updated successfully";
    }

    @DeleteMapping
    public String deleteUser(){
        return "user deleted successfully";
    }
}
