package org.pharmacy.service;

import lombok.RequiredArgsConstructor;
import org.pharmacy.entity.Credentials;
import org.pharmacy.repository.CredentialsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final int TOKEN_VALIDITY_DAYS = 7;

    @Override
    @Transactional
    public String getToken(UUID pharmacyId) {
        return credentialsRepository.findByPharmacyId(pharmacyId)
                .map(this::refreshIfInactive)
                .orElseGet(() -> createCredentials(pharmacyId))
                .getToken();
    }

    private Credentials refreshIfInactive(Credentials credentials) {
        if (!credentials.getActive()) {
            fillTokenData(credentials);
            credentialsRepository.save(credentials);
        }

        return credentials;
    }

    private Credentials createCredentials(UUID pharmacyId) {
        Credentials credentials = new Credentials();
        credentials.setPharmacyId(pharmacyId);
        fillTokenData(credentials);
        return credentialsRepository.save(credentials);
    }

    private void fillTokenData(Credentials credentials) {
        credentials.setToken(generateToken());
        credentials.setActive(true);
        credentials.setExpiredDate(LocalDate.now().plusDays(TOKEN_VALIDITY_DAYS));
        credentials.setUpdatedDate(LocalDate.now());
    }

    private String generateToken() {
        byte[] randomBytes = new byte[24];
        SECURE_RANDOM.nextBytes(randomBytes);
        return ENCODER.encodeToString(randomBytes);
    }

    @Override
    @Transactional
    public void revokeToken(UUID pharmacyId) {
        Credentials credentials = credentialsRepository.findByPharmacyId(pharmacyId)
                .orElseThrow(() -> new RuntimeException("Не найден токен для аптеки с id = " + pharmacyId));

        if (credentials.getActive()) {
            credentials.setActive(false);
            credentials.setUpdatedDate(LocalDate.now());
            credentialsRepository.save(credentials);
            System.out.println("Отозван токен для аптеки с id = " + pharmacyId);
        }
    }

}
