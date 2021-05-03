package com.iykeafrica.echange.io.repositories;

import com.iykeafrica.echange.io.entity.UserEntity;
import com.iykeafrica.echange.service.UserService;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByPhoneNo(String phoneNo);
    UserEntity findByWalletId(String walletId);
}
