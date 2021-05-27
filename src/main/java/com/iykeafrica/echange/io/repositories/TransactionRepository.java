package com.iykeafrica.echange.io.repositories;

import com.iykeafrica.echange.io.entity.TransactionEntity;
import com.iykeafrica.echange.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByUserDetails(UserEntity userEntity);

    TransactionEntity findByTransactionId(String extrasId);
}
