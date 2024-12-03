package com.SegundasHuellas.backend.shared.infrastructure.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LoggerManager {
    private final LoggerContext loggerContext;

    public LoggerManager() {
        this.loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    public void executeWithSuppressedLogs(List<String> loggerNames, Level temporaryLevel, Runnable action) {
        var loggerStates = loggerNames.stream()
                                      .map(name -> new LoggerState(loggerContext.getLogger(name),
                                              loggerContext.getLogger(name).getLevel()))
                                      .toList();

        try {
            loggerStates.forEach(state -> state.setLevel(temporaryLevel));
            action.run();
        } finally {
            loggerStates.forEach(LoggerState::restoreLevel);
        }
    }

    public void executeWithSuppressedSQLLogs(Runnable action) {
        var loggersToDisable = List.of(
                "org.hibernate.SQL",
                "org.hibernate.type",
                "org.hibernate.stat"
        );

        executeWithSuppressedLogs(loggersToDisable, Level.ERROR, action);
    }

    private record LoggerState(Logger logger, Level originalLevel) {
        void setLevel(Level level) {
            logger.setLevel(level);
        }

        void restoreLevel() {
            logger.setLevel(originalLevel);
        }
    }
}
