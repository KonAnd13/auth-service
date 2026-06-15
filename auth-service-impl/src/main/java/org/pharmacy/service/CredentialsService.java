package org.pharmacy.service;

import org.pharmacy.dto.TokenInfoDto;

import java.util.UUID;

public interface CredentialsService {

    TokenInfoDto getToken(UUID pharmacyId);

    void revokeToken(UUID pharmacyID);
}
