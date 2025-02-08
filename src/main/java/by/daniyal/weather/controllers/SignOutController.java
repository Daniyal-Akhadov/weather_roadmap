package by.daniyal.weather.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import static by.daniyal.weather.controllers.CookieConstraints.*;

@Controller
@AllArgsConstructor
public class SignOutController {
    private final SessionService sessionService;

    @GetMapping("/sign-out")
    public String signOut(@CookieValue(SESSION_ID) String sessionId,
                          HttpServletResponse response) {
        Cookie cookie = new Cookie(SESSION_ID, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        sessionService.deleteBySessionId(sessionId);
        return "sign-in";
    }
}
