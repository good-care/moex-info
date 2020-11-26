package com.mokhovav.goodcare_moex_info.logging;

import org.springframework.stereotype.Component;

@Component
public interface Logger {
    void info(String str);
    void debug(String str);
    void error(String str);
    void warning(String str);
}
