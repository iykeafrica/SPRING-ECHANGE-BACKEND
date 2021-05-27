package com.iykeafrica.echange.service.impl;

import com.iykeafrica.echange.exceptions.UserServiceException;
import com.iykeafrica.echange.io.entity.TransactionEntity;
import com.iykeafrica.echange.io.entity.UserEntity;
import com.iykeafrica.echange.io.repositories.TransactionRepository;
import com.iykeafrica.echange.io.repositories.UserRepository;
import com.iykeafrica.echange.service.TransactionService;
import com.iykeafrica.echange.shared.dto.TransactionDTO;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.shared.dto.utils.Utils;
import com.iykeafrica.echange.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    Utils utils;

    @Override
    public List<TransactionDTO> getTransactions(String walletId) {
        ModelMapper modelMapper = new ModelMapper();
        List<TransactionDTO> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (userEntity == null) return returnValue;

        Iterable<TransactionEntity> extrasEntities = transactionRepository.findAllByUserDetails(userEntity);
        for (TransactionEntity transactionEntity : extrasEntities) {
            returnValue.add(modelMapper.map(transactionEntity, TransactionDTO.class));
        }

        return returnValue;
    }

    @Override
    public TransactionDTO getTransaction(String extrasId) {
        TransactionDTO returnValue = new TransactionDTO();

        TransactionEntity transactionEntity = transactionRepository.findByTransactionId(extrasId);

        if (transactionEntity == null) return returnValue;

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(transactionEntity, TransactionDTO.class);
    }

    @Override
    public TransactionDTO postTransaction(String walletId, TransactionDTO transactions) {
        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (walletId == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        TransactionDTO returnValue = new TransactionDTO();
        ModelMapper modelMapper = new ModelMapper();

        TransactionEntity transactionEntity = modelMapper.map(transactions, TransactionEntity.class);

        transactionEntity.setTransactionId(utils.generateExtrasId(30));
        transactionEntity.setAlert(transactions.getAlert());
        transactionEntity.setName(transactions.getName());
        transactionEntity.setDescription(transactions.getDescription());
        transactionEntity.setPreviousBalance(transactions.getPreviousBalance());
        transactionEntity.setAvailableBalance(transactions.getAvailableBalance());
        transactionEntity.setDate(transactions.getDate());
        transactionEntity.setUserDetails(userEntity);

        TransactionEntity savedExtra = transactionRepository.save(transactionEntity);
        returnValue = modelMapper.map(savedExtra, TransactionDTO.class);

        return returnValue;
    }

    @Override
    public TransactionDTO postTransactions(String walletId, UserDto user) {
        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (walletId == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
//
//        UserDto dto = new UserDto();
//        dto.setId(userEntity.getId());
//        dto.setWalletId(userEntity.getWalletId());
//        dto.setFirstName(userEntity.getFirstName());
//        dto.setLastName(userEntity.getLastName());
//        dto.setEmail(userEntity.getEmail());
//        dto.setPhoneNo(userEntity.getPhoneNo());
//        dto.setFcmMessageToken(userEntity.getFcmMessageToken());
//        dto.setWalletBalance(userEntity.getWalletBalance());
//        dto.setLastSentReceivedAmount(userEntity.getLastSentReceivedAmount());
//        dto.setFcmAuthToken(userEntity.getFcmAuthToken());
//        dto.setTransactionPin(userEntity.getEncryptedTransactionPin());
//        private String transactionPin;
//        private String encryptedTransactionPin;
//        private String password;
//        private String encryptedPassword;
//        private String emailVerificationToken;
//        private Boolean emailVerificationStatus = false;

        TransactionDTO returnValue = new TransactionDTO();

        ModelMapper modelMapper = new ModelMapper();

        for (int i = 0; i < user.getTransactions().size(); i++) {
            TransactionEntity transactionEntity = new TransactionEntity();

            TransactionDTO transactions = user.getTransactions().get(i);

            transactionEntity.setTransactionId(utils.generateExtrasId(30));

            transactionEntity.setUserDetails(userEntity);
            transactionEntity.setAlert(transactions.getAlert());
            transactionEntity.setName(transactions.getName());
            transactionEntity.setDescription(transactions.getDescription());
            transactionEntity.setPreviousBalance(transactions.getPreviousBalance());
            transactionEntity.setAvailableBalance(transactions.getAvailableBalance());
            transactionEntity.setDate(transactions.getDate());
            TransactionEntity savedExtra = transactionRepository.save(transactionEntity);
        }

        UserEntity updatedExtras = userRepository.findByWalletId(walletId);

        returnValue = modelMapper.map(updatedExtras, TransactionDTO.class);

        return returnValue;
    }
}
