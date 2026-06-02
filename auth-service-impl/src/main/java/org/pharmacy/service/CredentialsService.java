package org.pharmacy.service;

import java.util.UUID;

public interface CredentialsService {

    String getToken(UUID pharmacyId);

    void revokeToken(UUID pharmacyID);
}
