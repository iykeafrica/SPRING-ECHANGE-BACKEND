package com.iykeafrica.echange.io.repositories;

import com.iykeafrica.echange.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByPhoneNo(String phoneNo);
    UserEntity findByWalletId(String walletId);
}
