package org.pharmacy.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping("api/v1/credentials")
public interface CredentialsController {

    @PostMapping("/{pharmacyId}/get-token")
    String getToken(@PathVariable UUID pharmacyId);

    @DeleteMapping("/{pharmacyId}/revoke-token")
    void revokeToken(@PathVariable UUID pharmacyId);
}
