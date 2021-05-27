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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.iykeafrica.echange.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

        Iterable<TransactionEntity> transactionEntities = transactionRepository.findAllByUserDetailsOrderByDateDesc(userEntity);

        for (TransactionEntity transactionEntity : transactionEntities) {
            returnValue.add(modelMapper.map(transactionEntity, TransactionDTO.class));
        }

        return returnValue;
    }


//    @Override
//    public List<TransactionDTO> getTransactions(int page, int limit, long date, String walletId) {
//        ModelMapper modelMapper = new ModelMapper();
//        List<TransactionDTO> returnValue = new ArrayList<>();
//
//        if (page > 0)
//            page = page - 1;
//
//        UserEntity userEntity = userRepository.findByWalletId(walletId);
//
//        if (userEntity == null) return returnValue;
//
//        Iterable<TransactionEntity> transactionEntities = transactionRepository.findAllByUserDetailsOrderByDate(userEntity);
//
//        Pageable pageableRequest = PageRequest.of(page, limit, Sort.by(String.valueOf(date)));
//        Page<TransactionEntity> transactionSortedPage = transactionRepository.findAll(pageableRequest);
//        List<TransactionEntity> transactions = transactionSortedPage.getContent();
//
//        for (TransactionEntity transactionEntity : transactions) {
//            returnValue.add(modelMapper.map(transactionEntity, TransactionDTO.class));
//        }
//
//        return returnValue;
//    }

    @Override
    public TransactionDTO getTransaction(String transactionId) {
        TransactionDTO returnValue = new TransactionDTO();

        TransactionEntity transactionEntity = transactionRepository.findByTransactionId(transactionId);

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

        TransactionEntity savedTransaction = transactionRepository.save(transactionEntity);
        returnValue = modelMapper.map(savedTransaction, TransactionDTO.class);

        return returnValue;
    }
}