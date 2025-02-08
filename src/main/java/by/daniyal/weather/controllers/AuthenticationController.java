package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Session;
import by.daniyal.weather.models.User;
import by.daniyal.weather.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static by.daniyal.weather.controllers.CookieConfiguration.COOKIE_NAME;

@Controller
@RequestMapping("/sign-in")
@AllArgsConstructor
public class AuthenticationController {

    private final CookieConfiguration cookieConfiguration;
    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping
    public String signInPage() {
        return "sign-in";
    }

    @PostMapping
    public String signIn(final @CookieValue(value = COOKIE_NAME, required = false) String sessionId,
                         final @RequestParam("username") String username,
                         final HttpServletResponse response) {
        if (sessionId != null) {
            final Optional<Session> session = sessionService.findBySessionId(sessionId);

            if (session.isPresent()) {
                return "redirect:/";
            }
        }

        final Optional<User> user = userService.findByLogin(username);

        if (user.isPresent()) {
            final String uuid = UUID.randomUUID().toString();
            sessionService.save(buildSession(uuid, user.get()));
            final Cookie cookie = setCookie(uuid);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/sign-in";
        }
    }

    private Cookie setCookie(String uuid) {
        Cookie cookie = new Cookie(COOKIE_NAME, uuid);
        cookie.setMaxAge(cookieConfiguration.getExpiryTime());
        cookie.setPath(cookieConfiguration.getPath());
        return cookie;
    }

    private static Session buildSession(String uuid, User user) {
        return Session.builder()
                .id(uuid)
                .userId(Math.toIntExact(user.getId()))
                .expireSet(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();
    }
}


