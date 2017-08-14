package com.cct.service.api;

import com.cct.model.dto.MakeDTO;

import java.util.Collection;

public interface MakeService {
    MakeDTO getMake(String id);

    Collection<MakeDTO> getMakes();
}
