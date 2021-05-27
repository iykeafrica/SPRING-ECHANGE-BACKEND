package com.iykeafrica.echange.io.repositories;

import com.iykeafrica.echange.io.entity.TransactionEntity;
import com.iykeafrica.echange.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByUserDetailsOrderByDateDesc(UserEntity userEntity);

    TransactionEntity findByTransactionId(String transactionId);
}