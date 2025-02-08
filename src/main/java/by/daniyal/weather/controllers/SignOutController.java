package by.daniyal.weather.controllers;

import by.daniyal.weather.services.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import static by.daniyal.weather.config.CookieConfiguration.COOKIE_NAME;

@Controller
@AllArgsConstructor
public class SignOutController {
    private final SessionService sessionService;

    @GetMapping("/sign-out")
    public String signOut(@CookieValue(COOKIE_NAME) String sessionId,
                          HttpServletResponse response) {
        Cookie cookie = resetedCookie();
        response.addCookie(cookie);
        sessionService.deleteBySessionId(sessionId);
        return "sign-in";
    }

    private static Cookie resetedCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}
