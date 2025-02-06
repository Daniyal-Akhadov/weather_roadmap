package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Session;
import by.daniyal.weather.models.User;
import by.daniyal.weather.services.AuthorizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Controller
@RequestMapping("/sign-in")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthorizationService authorizationService;
    private final SessionService sessionService;

    @GetMapping
    public String signInPage() {
        return "sign-in";
    }

    @PostMapping
    public String signIn(@RequestParam String username,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Optional<Cookie> loggedIn = Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals("SESSION_ID"))
                    .findFirst();

            if (loggedIn.isPresent()) {
                Optional<Session> session = sessionService.findBySessionId(loggedIn.get().getValue());

                if (session.isPresent()) {
                    return "redirect:/";
                }
            }
        }

        Optional<User> user = authorizationService.findByLogin(username);

        if (user.isPresent()) {
            String uuid = UUID.randomUUID().toString();
            Session session = Session.builder()
                    .id(uuid)
                    .userId(Math.toIntExact(user.get().getId()))
                    .expireSet(LocalDateTime.of(2020, 1, 1, 0, 0))
                    .build();

            System.out.println(session.toString());
            sessionService.save(session);
            Cookie cookie = new Cookie("SESSION_ID", uuid);
            cookie.setMaxAge(10_000);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/sign-in";
        }
    }
}