package org.pharmacy.controller;

import org.pharmacy.dto.TokenInfoDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface CredentialsController {

    @GetMapping("/api/v1/credentials/{pharmacyId}/get-token")
    TokenInfoDto getToken(@PathVariable UUID pharmacyId);

    @DeleteMapping("/api/v1/credentials/{pharmacyId}/revoke-token")
    void revokeToken(@PathVariable UUID pharmacyId);
}
