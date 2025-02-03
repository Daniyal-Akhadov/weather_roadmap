package by.daniyal.weather.controllers;

import by.daniyal.weather.models.User;
import by.daniyal.weather.services.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @GetMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpPost(@RequestParam String username,
                             @RequestParam String password) {
        String[] passwords = password.split(",");

        if (passwords[0].equals(passwords[1])) {
            Optional<User> user = authorizationService.findByLogin(username);

            if (user.isEmpty()) {
                authorizationService.register(username, passwords[0]);
                return "redirect:/sign-in";
            }
        }

        return "redirect:/sign-up";
    }
}



