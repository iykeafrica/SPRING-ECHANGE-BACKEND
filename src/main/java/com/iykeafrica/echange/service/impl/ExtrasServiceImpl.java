package com.iykeafrica.echange.service.impl;

import com.iykeafrica.echange.exceptions.UserServiceException;
import com.iykeafrica.echange.io.entity.ExtrasEntity;
import com.iykeafrica.echange.io.entity.UserEntity;
import com.iykeafrica.echange.io.repositories.ExtrasRepository;
import com.iykeafrica.echange.io.repositories.UserRepository;
import com.iykeafrica.echange.service.ExtrasService;
import com.iykeafrica.echange.shared.dto.ExtrasDTO;
import com.iykeafrica.echange.shared.dto.UserDto;
import com.iykeafrica.echange.shared.dto.utils.Utils;
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

    @Autowired
    Utils utils;

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

    @Override
    public ExtrasDTO createExtra(String walletId, ExtrasDTO userExtras) {
        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (walletId == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ExtrasDTO returnValue = new ExtrasDTO();
        ModelMapper modelMapper = new ModelMapper();

        ExtrasEntity extrasEntity = modelMapper.map(userExtras, ExtrasEntity.class);

        String publicExtraId = utils.generateExtrasId(30);

        extrasEntity.setExtrasId(publicExtraId);
        extrasEntity.setAddress(userExtras.getAddress());
        extrasEntity.setType(userExtras.getType());
        extrasEntity.setUserDetails(userEntity);

        ExtrasEntity savedExtra = extrasRepository.save(extrasEntity);
        returnValue = modelMapper.map(savedExtra, ExtrasDTO.class);

        return returnValue;
    }

    @Override
    public ExtrasDTO createExtras(String walletId, UserDto user) {
        UserEntity userEntity = userRepository.findByWalletId(walletId);

        if (walletId == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ExtrasDTO returnValue = new ExtrasDTO();
        ModelMapper modelMapper = new ModelMapper();

        ExtrasEntity extrasEntity = new ExtrasEntity();

        for (int i = 0; i < user.getExtras().size(); i++){
            returnValue = user.getExtras().get(i);
            returnValue.setUserDetails(user);
            returnValue.setExtrasId(utils.generateExtrasId(30));
            user.getExtras().set(i, returnValue);
            extrasEntity = modelMapper.map(user, ExtrasEntity.class);

            ExtrasEntity savedExtra = extrasRepository.save(extrasEntity);
            returnValue = modelMapper.map(savedExtra, ExtrasDTO.class);
        }

        return returnValue;
    }
}
