package com.mokhovav.goodcare_moex_info.logging;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("Slf4jLogger")
@Primary
public class Slf4jLogger implements Logger {
    private final org.slf4j.Logger logger;

    public Slf4jLogger() {
        logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("Slf4jLogger initialization");
    }

    @Override
    public void info(String str) {
        logger.info(str);
    }

    @Override
    public void debug(String str) {
        logger.debug(str);
    }

    @Override
    public void error(String str) {
        logger.error(str);
    }

    @Override
    public void warning(String str) {
        logger.warn(str);
    }
}
