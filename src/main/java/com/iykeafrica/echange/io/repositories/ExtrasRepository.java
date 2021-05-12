package com.iykeafrica.echange.io.repositories;

import com.iykeafrica.echange.io.entity.ExtrasEntity;
import com.iykeafrica.echange.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtrasRepository extends CrudRepository<ExtrasEntity, Long> {

    List<ExtrasEntity> findAllByUserDetails(UserEntity userEntity);

    ExtrasEntity findByExtrasId(String extrasId);
}
