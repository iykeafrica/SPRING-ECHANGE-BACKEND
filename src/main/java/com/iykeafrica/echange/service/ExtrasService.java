package com.iykeafrica.echange.service;

import com.iykeafrica.echange.shared.dto.ExtrasDTO;

import java.util.List;

public interface ExtrasService {

    List<ExtrasDTO> getExtras(String walletId);

    ExtrasDTO getExtra(String extrasId);

}
