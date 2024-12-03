package com.SegundasHuellas.backend.auth.internal.application.service;

import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupService {
    private final TokenRepository tokenRepository;

    @Value("${app.security.token.retention-days:7}")
    private int retentionDays;


    @Scheduled(cron = "${app.security.token.cleanup-cron:0 0 2 * * *}")
    @Transactional
    public void cleanupExpiredTokens() {
        LocalDateTime limitDate = LocalDateTime.now().minusDays(retentionDays);
        List<Token> expiredTokens = tokenRepository.findExpiredTokensCreatedBefore(limitDate);

        if (!expiredTokens.isEmpty()) {
            tokenRepository.deleteAll(expiredTokens);
            log.info("🧹 Cleaned up {} expired tokens", expiredTokens.size());
        }
    }
}
