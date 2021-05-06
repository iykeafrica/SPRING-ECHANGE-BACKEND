package com.iykeafrica.echange.service.impl;

import com.iykeafrica.echange.exceptions.UserServiceException;
import com.iykeafrica.echange.io.entity.ExtrasEntity;
import com.iykeafrica.echange.io.entity.UserEntity;
import com.iykeafrica.echange.io.repositories.ExtrasRepository;
import com.iykeafrica.echange.io.repositories.UserRepository;
import com.iykeafrica.echange.service.ExtrasService;
import com.iykeafrica.echange.shared.dto.ExtrasDTO;
import com.iykeafrica.echange.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtrasServiceImpl implements ExtrasService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExtrasRepository extrasRepository;

    @Override
    public List<ExtrasDTO> getExtras(String walletId) {
        ModelMapper modelMapper = new ModelMapper();
        List<ExtrasDTO> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (userEntity == null) return returnValue;

        Iterable<ExtrasEntity> extrasEntities = extrasRepository.findAllByUserDetails(userEntity);
        for (ExtrasEntity extrasEntity : extrasEntities){
            returnValue.add(modelMapper.map(extrasEntity, ExtrasDTO.class));
        }

        return returnValue;
    }

    @Override
    public ExtrasDTO getExtra(String extrasId) {
        ExtrasDTO returnValue = new ExtrasDTO();

        ExtrasEntity extrasEntity = extrasRepository.findByExtrasId(extrasId);

        if (extrasEntity == null) return  returnValue;

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(extrasEntity, ExtrasDTO.class);
    }
}
