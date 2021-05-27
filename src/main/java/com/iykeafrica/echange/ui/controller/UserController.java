package com.iykeafrica.echange.ui.controller;

import com.iykeafrica.echange.service.TransactionService;
import com.iykeafrica.echange.service.UserService;
import com.iykeafrica.echange.shared.dto.TransactionDTO;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.ui.model.request.*;
import com.iykeafrica.echange.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionServices;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionService transactionsService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserSignUpRequest userSignUpRequest) {
        UserRest returnValue = new UserRest();


        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userSignUpRequest, UserDto.class);

        UserDto registeredUser = userService.createUser(userDto);

        returnValue = modelMapper.map(registeredUser, UserRest.class);

        return returnValue;
    }

    @PostMapping(path = "/{walletID}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TransactionRest postTransaction(@PathVariable String walletID, @RequestBody TransactionRequestModel addUserExtrasRequest) {
        TransactionRest returnValue = new TransactionRest();

        ModelMapper modelMapper = new ModelMapper();
        TransactionDTO transactionDTO = modelMapper.map(addUserExtrasRequest, TransactionDTO.class);

        TransactionDTO addedExtra = transactionsService.postTransaction(walletID, transactionDTO);

        returnValue = modelMapper.map(addedExtra, TransactionRest.class);

        return returnValue;
    }

    @GetMapping(path = "/{userName}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable String userName) { //for admin user only; with phone, email or walletId
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUser(userName);

        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @GetMapping(path = "1/{walletId}")
    public UserVerifyWalletIdResponse getUserByWalletId(@PathVariable String walletId) {
        UserVerifyWalletIdResponse returnValue = new UserVerifyWalletIdResponse();
        UserDto userDto = userService.getUserByWalletId(walletId);

        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    //http://localhost:8081/echange-app-ws/users/NllBt6fcMB7Rk71AYJvVvU6mjIlHdo/send-money/gUFGXOrRo4Wtfc1DDPXBzTMCevNe0t
    @PutMapping(path = "{senderWalletId}/send-money/{requesterWalletId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserSendMoneyResponse sendMoney(@PathVariable String senderWalletId, @PathVariable String requesterWalletId, @RequestBody UserSendMoneyRequest userSendMoneyRequest) {
        UserSendMoneyResponse returnValue = new UserSendMoneyResponse();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userSendMoneyRequest, userDto);

        UserDto moneyDebited = userService.debitMoney(senderWalletId, userDto);

        UserDto moneyCredited = userService.creditMoney(requesterWalletId, userDto);
        BeanUtils.copyProperties(moneyCredited, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{walletId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String walletId, @RequestBody UserUpdatePersonalRecordRequest userUpdatePersonalRecordRequest) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userUpdatePersonalRecordRequest, userDto);

        UserDto updatedUser = userService.updateUser(walletId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{walletId}/fcm", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUserFCM(@PathVariable String walletId, @RequestBody UserUpdateFCMRequest userUpdateFCMRequest) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userUpdateFCMRequest, userDto);

        UserDto updatedUser = userService.updateUserFCM(walletId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }


    @DeleteMapping(path = "/{walletId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String walletId) {
        OperationStatusModel returnValue = new OperationStatusModel();

        userService.deleteUser(walletId);

        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteAllUser() {
        OperationStatusModel returnValue = new OperationStatusModel();

        userService.deleteAllUser();

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

        return returnValue;
    }

    //http:localhost:8080/walletId/extras
    @GetMapping(path = "/{walletId}/extras", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<TransactionRest> getUserExtras(@PathVariable String walletId) {
        ModelMapper modelMapper = new ModelMapper();

        List<TransactionRest> returnValue = new ArrayList<>();
        List<TransactionDTO> transactionDTO = transactionServices.getTransactions(walletId);

        if (transactionDTO != null && !transactionDTO.isEmpty()) {
            Type listType = new TypeToken<List<TransactionRest>>() {
            }.getType();
            returnValue = modelMapper.map(transactionDTO, listType);
        }

        return returnValue;
    }

    //http:localhost:8080/walletId/extras/addressId
    @GetMapping(path = "/{walletId}/extras/{extrasId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TransactionRest getUserExtra(@PathVariable String walletId, @PathVariable String extrasId) {
        ModelMapper modelMapper = new ModelMapper();

        TransactionRest returnValue = new TransactionRest();
        TransactionDTO transactionDTO = transactionService.getTransaction(extrasId);

        if (transactionDTO != null) {
            returnValue = modelMapper.map(transactionDTO, TransactionRest.class);
        }

        return returnValue;
    }

}
