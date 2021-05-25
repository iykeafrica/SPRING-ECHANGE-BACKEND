package com.iykeafrica.echange.service;

import com.iykeafrica.echange.shared.dto.ExtrasDTO;
import com.iykeafrica.echange.shared.dto.UserDto;

import java.util.List;

public interface ExtrasService {

    List<ExtrasDTO> getExtras(String walletId);

    ExtrasDTO getExtra(String extrasId);

    ExtrasDTO createExtra(String walletId, ExtrasDTO extrasDTO);

    ExtrasDTO createExtras(String walletId, UserDto userDto);

}
