package by.daniyal.weather.controllers;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:cookie.properties")
public class CookieConfiguration {

    public static final String COOKIE_NAME = "SESSION_ID";

    @Value("${cookie.session_id.expiry}")
    private int expiryTime;

    @Value("${cookie.session_id.path}")
    private String path;
}
