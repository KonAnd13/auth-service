package org.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import org.pharmacy.service.CredentialsService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CredentialControllerImpl implements CredentialsController {

    private final CredentialsService credentialsService;

    @Override
    public String getToken(UUID pharmacyId) {
        return credentialsService.getToken(pharmacyId);
    }

    @Override
    public void revokeToken(UUID pharmacyId) {
        credentialsService.revokeToken(pharmacyId);
    }
}
