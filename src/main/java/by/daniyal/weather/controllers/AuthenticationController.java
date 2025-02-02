package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Session;
import by.daniyal.weather.models.User;
import by.daniyal.weather.services.AuthenticationService;
import by.daniyal.weather.services.AuthorizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Controller
@RequestMapping("/sign-in")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final SessionService sessionService;

    @GetMapping
    public String signIn() {
        return "sign-in";
    }

    @PostMapping
    public String signInPost(@RequestParam String username,
                             @RequestParam String password,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        if (true)
            return "redirect:/index";

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Optional<Cookie> loggedIn = Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals("SESSION_ID"))
                    .findFirst();

            if (loggedIn.isPresent()) {
                Optional<Session> session = sessionService.findBySessionId(loggedIn.get().getValue());

                if (session.isPresent()) {
                    return "redirect:/index";
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
            return "redirect:/index";
        } else {
            return "redirect:/sign-in";
        }
    }
}