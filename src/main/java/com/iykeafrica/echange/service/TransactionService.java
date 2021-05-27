package com.iykeafrica.echange.service;

import com.iykeafrica.echange.shared.dto.TransactionDTO;
import com.iykeafrica.echange.shared.dto.UserDto;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getTransactions(String walletId);

    TransactionDTO getTransaction(String extrasId);

    TransactionDTO postTransaction(String walletId, TransactionDTO transactionDTO);

    TransactionDTO postTransactions(String walletId, UserDto userDto);

}
