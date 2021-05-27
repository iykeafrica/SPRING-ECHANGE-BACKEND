package com.iykeafrica.echange.service;

import com.iykeafrica.echange.shared.dto.TransactionDTO;
import com.iykeafrica.echange.shared.dto.UserDto;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getTransactions(String walletId);

//    List<TransactionDTO> getTransactions(int page, int limit, long date, String walletId);

    TransactionDTO getTransaction(String transactionId);

    TransactionDTO postTransaction(String walletId, TransactionDTO transactionDTO);
}