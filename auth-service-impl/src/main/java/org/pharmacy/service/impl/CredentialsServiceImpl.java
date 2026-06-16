package org.pharmacy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.dto.TokenInfoDto;
import org.pharmacy.entity.Credentials;
import org.pharmacy.mapper.TokenInfoMapper;
import org.pharmacy.repository.CredentialsRepository;
import org.pharmacy.service.CredentialsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private final TokenInfoMapper tokenInfoMapper;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final int TOKEN_VALIDITY_DAYS = 7;

    @Override
    @Transactional
    public TokenInfoDto getToken(UUID pharmacyId) {
        log.info("Getting token for pharmacy id: {}", pharmacyId);
        Credentials credentials = credentialsRepository.findByPharmacyId(pharmacyId)
                .map(this::refreshIfInactive)
                .orElseGet(() -> createCredentials(pharmacyId));

        log.debug("Token issued for pharmacy id: {}", pharmacyId);
        return tokenInfoMapper.toTokenInfoDto(credentials);
    }

    private Credentials refreshIfInactive(Credentials credentials) {
        if (!credentials.getActive()) {
            fillTokenData(credentials);
            credentialsRepository.save(credentials);
            log.debug("Token updated for pharmacy id: {}", credentials.getPharmacyId());
        }

        return credentials;
    }

    private Credentials createCredentials(UUID pharmacyId) {
        Credentials credentials = new Credentials();
        credentials.setPharmacyId(pharmacyId);
        fillTokenData(credentials);
        credentials = credentialsRepository.save(credentials);
        log.debug("Token created for pharmacy id: {}", credentials.getPharmacyId());
        return credentials;
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
        log.info("Revoking token for pharmacy id: {}", pharmacyId);
        Credentials credentials = credentialsRepository.findByPharmacyId(pharmacyId)
                .orElseThrow(() -> new RuntimeException("Не найден токен для аптеки с id = " + pharmacyId));

        if (credentials.getActive()) {
            credentials.setActive(false);
            credentials.setUpdatedDate(LocalDate.now());
            credentialsRepository.save(credentials);
            log.debug("Token revoked for pharmacy id: {}", pharmacyId);
        } else {
            log.warn("Token already inactive for pharmacy id: {}", pharmacyId);
        }
    }

    @Override
    public TokenInfoDto getUserToken(String inn) {
        log.info("Getting user token for inn: {}", inn);
        TokenInfoDto tokenInfoDto = TokenInfoDto.builder()
                .token(generateToken())
                .expiredDate(LocalDate.now().plusDays(TOKEN_VALIDITY_DAYS))
                .build();

        log.debug("User token issued for inn: {}", inn);
        return tokenInfoDto;
    }

}
