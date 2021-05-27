package com.iykeafrica.echange.service.impl;

import com.iykeafrica.echange.exceptions.UserServiceException;
import com.iykeafrica.echange.io.entity.UserEntity;
import com.iykeafrica.echange.io.repositories.TransactionRepository;
import com.iykeafrica.echange.io.repositories.UserRepository;
import com.iykeafrica.echange.service.UserService;
import com.iykeafrica.echange.shared.dto.TransactionDTO;
import com.iykeafrica.echange.shared.dto.utils.Utils;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final int LENGTH_GENERATED_USER_ID = 30;
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        UserEntity signUpEmail = userRepository.findByEmail(user.getEmail());
        UserEntity signUpPhone = userRepository.findByPhoneNo(user.getPhoneNo());

        if (signUpEmail != null && signUpPhone != null)
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage()
                    + " for:\n" + user.getEmail() + " and:\n" + user.getPhoneNo());

        for (int i = 0; i < user.getTransactions().size(); i++) {
            TransactionDTO transactionDTO = user.getTransactions().get(i);
            transactionDTO.setUserDetails(user);
            transactionDTO.setTransactionId(utils.generateExtrasId(30));
            user.getTransactions().set(i, transactionDTO);
        }

        UserDto returnValue = new UserDto();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(LENGTH_GENERATED_USER_ID);
        userEntity.setWalletId(publicUserId);
        userEntity.setEncryptedTransactionPin(bCryptPasswordEncoder.encode(user.getTransactionPin()));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setWalletBalance(1000.00);
        userEntity.setLastSentReceivedAmount(0.00);
        userEntity.setFcmMessageToken("");
        userEntity.setFcmAuthToken("");

        UserEntity savedUser = userRepository.save(userEntity);
        returnValue = modelMapper.map(savedUser, UserDto.class);

        return returnValue;
    }

//    @Override
//    public UserDto createExtras(String walletId, UserDto user) {
//        UserEntity userEntity = userRepository.findByWalletId(walletId);
//
//        if (walletId == null)
//            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
//
//        UserEntity setUserEntity = new UserEntity();
//
//        ModelMapper modelMapper = new ModelMapper();
//
//        for (int i = 0; i < user.getExtras().size(); i++) {
//            ExtrasEntity extrasEntity = modelMapper.map(user, ExtrasEntity.class);
//
//            ExtrasDTO extrasDTO = user.getExtras().get(i);
//
//            extrasEntity.setExtrasId(utils.generateExtrasId(30));
//            extrasEntity.setAddress(extrasDTO.getAddress());
//            extrasEntity.setType(extrasDTO.getType());
//            extrasEntity.setUserDetails(userEntity);
//
//            ExtrasEntity savedExtra = extrasRepository.save(extrasEntity);
//        }
//
//        UserEntity updatedExtras = userRepository.findByWalletId(walletId);
//
//        UserDto returnValue = new UserDto();
//        returnValue = modelMapper.map(updatedExtras, UserDto.class);
//
//        return returnValue;
//    }

    @Override
    public UserDto getUser(String userName) { //phoneNo, email or userId used to sign in successfully
        UserDto returnValue = new UserDto();
        UserEntity userEntity = new UserEntity();

        if (utils.isNumber(userName)) {
            userEntity = userRepository.findByPhoneNo(userName);
            if (userEntity == null)
                throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + userName);

        } else if (userName.contains(Character.toString('@'))) {
            userEntity = userRepository.findByEmail(userName);

            if (userEntity == null)
                throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + userName);

        } else {
            userEntity = userRepository.findByWalletId(userName);
            if (userEntity == null)
                throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + userName);
        }

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByWalletId(String walletId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + walletId);

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto creditMoney(String requesterWalletId, UserDto user) {
        UserDto returnValue = new UserDto();

        UserEntity requesterEntity = userRepository.findByWalletId(requesterWalletId);
        if (requesterEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + requesterWalletId);

        requesterEntity.setLastSentReceivedAmount(user.getLastSentReceivedAmount());
        requesterEntity.setWalletBalance(requesterEntity.getWalletBalance() + user.getLastSentReceivedAmount());

        UserEntity savedRequesterEntity = userRepository.save(requesterEntity);

        BeanUtils.copyProperties(savedRequesterEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto debitMoney(String senderWalletId, UserDto user) {
        UserDto returnValue = new UserDto();

        UserEntity senderEntity = userRepository.findByWalletId(senderWalletId);
        if (senderEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + senderWalletId);

        if (!bCryptPasswordEncoder.matches(user.getTransactionPin(), senderEntity.getEncryptedTransactionPin()))
            throw new UserServiceException(ErrorMessages.WRONG_TRANSACTION_PIN.getErrorMessage());

        if (senderEntity.getWalletBalance() < user.getLastSentReceivedAmount())
            throw new UserServiceException(ErrorMessages.INSUFFICIENT_BALANCE.getErrorMessage());

        if (user.getLastSentReceivedAmount() <= 1)
            throw new UserServiceException(ErrorMessages.LOW_AMOUNT.getErrorMessage());

        senderEntity.setLastSentReceivedAmount(user.getLastSentReceivedAmount());
        senderEntity.setWalletBalance(senderEntity.getWalletBalance() - user.getLastSentReceivedAmount());

        UserEntity savedSenderEntity = userRepository.save(senderEntity);

        BeanUtils.copyProperties(savedSenderEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String walletId, UserDto user) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByWalletId(walletId);
        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + walletId);
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity userUpdated = userRepository.save(userEntity);

        BeanUtils.copyProperties(userUpdated, returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUserFCM(String walletId, UserDto user) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByWalletId(walletId);
        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + walletId);
        userEntity.setFcmMessageToken(user.getFcmMessageToken());

        UserEntity userUpdated = userRepository.save(userEntity);

        BeanUtils.copyProperties(userUpdated, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String walletId) {
        UserEntity userEntity = userRepository.findByWalletId(walletId);
        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + walletId);

        userRepository.delete(userEntity);
    }

    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if (page > 0)
            page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);

        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email_PhoneNo_WalletId) throws UsernameNotFoundException {
        UserEntity userEntity = new UserEntity();
        String signInByEmail_PhoneNo_WalletId = "";


        if (utils.isNumber(email_PhoneNo_WalletId)) {
            userEntity = userRepository.findByPhoneNo(email_PhoneNo_WalletId);
            if (userEntity == null)
                throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + email_PhoneNo_WalletId);

            signInByEmail_PhoneNo_WalletId = userEntity.getPhoneNo();

        } else if (email_PhoneNo_WalletId.contains(Character.toString('@'))) {
            userEntity = userRepository.findByEmail(email_PhoneNo_WalletId);

            if (userEntity == null)
                throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + email_PhoneNo_WalletId);
            signInByEmail_PhoneNo_WalletId = userEntity.getEmail();

        } else {
            userEntity = userRepository.findByWalletId(email_PhoneNo_WalletId);
            if (userEntity == null)
                throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for:\n" + email_PhoneNo_WalletId);
            signInByEmail_PhoneNo_WalletId = userEntity.getWalletId();
        }

        return new User(signInByEmail_PhoneNo_WalletId, userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
