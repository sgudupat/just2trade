package com.j2t.app.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

public class MessageConstants {

    private static final Logger log = LoggerFactory.getLogger(MessageConstants.class);
    private static final Properties prop = new Properties();

    static {
        try {
            Resource res = new ClassPathResource("i18n/messages.properties");
            prop.load(res.getInputStream());
        } catch (Exception e) {
            log.error("InCyyteConstants : messages_en.properties file not found");
        }
    }

    private static String getProperty(String key) {
        String value = null;
        try {
            value = prop.getProperty(key).trim();
        } catch (Exception e) {
            log.error("Missing property entry for " + key, e);
            throw new RuntimeException("Missing property entry for " + key);
        }
        return value;
    }

    //User validation messages
    public final static String EMAIL_FROM = getProperty("email.from");
}
