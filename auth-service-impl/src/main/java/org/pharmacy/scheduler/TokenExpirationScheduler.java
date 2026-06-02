package org.pharmacy.scheduler;

import lombok.RequiredArgsConstructor;
import org.pharmacy.repository.CredentialsRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class TokenExpirationScheduler {

    private final CredentialsRepository credentialsRepository;

    @Scheduled(fixedDelayString = "${scheduler.token.expiration.delay}", timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void revokeExpiredTokens() {
        int revokedTokens = credentialsRepository.revokeExpiredTokens(LocalDate.now());

        if (revokedTokens > 0) {
            System.out.println("Revoked " + revokedTokens + " expired tokens");
        }
    }

}
