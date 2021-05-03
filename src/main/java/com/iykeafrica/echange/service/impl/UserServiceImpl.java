package com.iykeafrica.echange.service.impl;

import com.iykeafrica.echange.io.entity.UserEntity;
import com.iykeafrica.echange.io.repositories.UserRepository;
import com.iykeafrica.echange.service.UserService;
import com.iykeafrica.echange.shared.dto.utils.Utils;
import com.iykeafrica.echange.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private static final int LENGTH_GENERATED_USER_ID = 30;
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        UserEntity signUpEmail = userRepository.findByEmail(user.getEmail());
        UserEntity signUpPhone = userRepository.findByPhoneNo(user.getPhoneNo());

        if (signUpEmail != null && signUpPhone != null)
            throw new RuntimeException("Record already exists");

        UserDto returnValue = new UserDto();
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(LENGTH_GENERATED_USER_ID);
        userEntity.setWalletId(publicUserId);
        userEntity.setEncryptedTransactionPin(bCryptPasswordEncoder.encode(user.getTransactionPin()));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userEntity.setEncryptedPassword("123456");
        userEntity.setWalletBalance(0.00);
        userEntity.setLastSentReceivedAmount(0.00);
        userEntity.setFcmMessageToken("fGLoX3ybRPadWVOkbDUwmV:APA91bFWYD1-VPr_K6WtDWwy5Kt4CQptDGSzsJKPQn94hOjxu006AblSuOzsr7M5rS9SzO_rhS54gOYjAa6DbUo2lH5LFX_LJEq51BzjhyXjJAJPyF1Ld7d2k_gSo3F8OYoKf8hTzZD5");
        userEntity.setFcmAuthToken("Authorization: key=AAAASVplkPY:APA91bFeGQNCMRWSEalDDD64-nAmt2MDKBrCtB_WJzU2NMdUM1QzB6ef6CtZmmLcC4F8iipMOP1a6vnR215udpRC8zLYO7g68TLnJLaAprqwXqJkysFgoWFLQBDVYHFVl48ARcBrkxaz");

        UserEntity savedUser = userRepository.save(userEntity);
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String userName) { //phoneNo, email or userId used to sign in successfully
        UserDto returnValue = new UserDto();
        UserEntity userEntity = new UserEntity();

        if (utils.isNumber(userName)) {
            userEntity = userRepository.findByPhoneNo(userName);
            if (userEntity == null)
                throw new UsernameNotFoundException("No user with phone number: " + userName);

        } else if (userName.contains(Character.toString('@'))) {
            userEntity = userRepository.findByEmail(userName);

            if (userEntity == null)
                throw new UsernameNotFoundException("No user with email: " + userName);

        } else {
            userEntity = userRepository.findByWalletId(userName);
            if (userEntity == null)
                throw new UsernameNotFoundException("No user with walletId: " + userName);
        }

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByWalletId(String walletId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (userEntity == null)
            throw new UsernameNotFoundException("No user with walletId: " + walletId);

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto sendMoney(String requesterWalletId, UserDto user) {
        UserDto returnValue = new UserDto();
        UserEntity requesterEntity = userRepository.findByWalletId(requesterWalletId);
        UserEntity senderEntity = userRepository.findByWalletId(user.getWalletId());

        requesterEntity.setLastSentReceivedAmount(user.getLastSentReceivedAmount());
        requesterEntity.setWalletBalance(user.getLastSentReceivedAmount());

        senderEntity.setWalletBalance(user.getLastSentReceivedAmount());
        senderEntity.setLastSentReceivedAmount(user.getLastSentReceivedAmount());

//        if (senderEntity.getWalletBalance() < user.getLastSentReceivedAmount())
//            throw new RuntimeException("Transaction declined, incorrect pin");

//        String senderPin = bCryptPasswordEncoder.encode(user.getTransactionPin());
//        if (senderEntity.getEncryptedTransactionPin() != senderPin)
//            throw new RuntimeException("Transaction declined, incorrect pin");


        UserEntity savedRequesterEntity = userRepository.save(requesterEntity);
        UserEntity savedSenderEntity = userRepository.save(senderEntity);

        BeanUtils.copyProperties(savedRequesterEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email_PhoneNo_WalletId) throws UsernameNotFoundException {
        UserEntity userEntity = new UserEntity();
        String signInByEmail_PhoneNo_WalletId = "";


        if (utils.isNumber(email_PhoneNo_WalletId)) {
            userEntity = userRepository.findByPhoneNo(email_PhoneNo_WalletId);
            if (userEntity == null)
                throw new UsernameNotFoundException("No user with phone number: " + email_PhoneNo_WalletId);

            signInByEmail_PhoneNo_WalletId = userEntity.getPhoneNo();

        } else if (email_PhoneNo_WalletId.contains(Character.toString('@'))) {
            userEntity = userRepository.findByEmail(email_PhoneNo_WalletId);

            if (userEntity == null)
                throw new UsernameNotFoundException("No user with email: " + email_PhoneNo_WalletId);
            signInByEmail_PhoneNo_WalletId = userEntity.getEmail();

        } else {
            userEntity = userRepository.findByWalletId(email_PhoneNo_WalletId);
            if (userEntity == null)
                throw new UsernameNotFoundException("No user with " + email_PhoneNo_WalletId);
            signInByEmail_PhoneNo_WalletId = userEntity.getWalletId();
        }

        return new User(signInByEmail_PhoneNo_WalletId, userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
