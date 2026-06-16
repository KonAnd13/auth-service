package org.pharmacy.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pharmacy.repository.CredentialsRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class TokenExpirationScheduler {

    private final CredentialsRepository credentialsRepository;

    @Scheduled(fixedDelayString = "${scheduler.token.expiration.delay}", timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void revokeExpiredTokens() {
        log.info("Running token expiration check");
        int revokedTokens = credentialsRepository.revokeExpiredTokens(LocalDate.now());

        if (revokedTokens > 0) {
            log.info("Revoked {} expired tokens", revokedTokens);
        } else {
            log.debug("No expired tokens found");
        }
    }

}
