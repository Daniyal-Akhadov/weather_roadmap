package by.daniyal.weather.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SignOutController {
    private final SessionService sessionService;

    @GetMapping("/sign-out")
    public String signOut(@CookieValue("SESSION_ID") String sessionId) {
        sessionService.deleteBySessionId(sessionId);
        return "sign-in";
    }
}
