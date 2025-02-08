package by.daniyal.weather.controllers;

import by.daniyal.weather.services.AuthorizationService;
import by.daniyal.weather.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpPost(final @RequestParam("name") String username,
                             final @RequestParam("password") String password,
                             final @RequestParam("repeat-password") String repeatPassword) {
        if (!isPasswordValid(password, repeatPassword) || isUsernameTaken(username)) {
            return redirectToSignUp();
        }

        registerUser(username, password);
        return redirectToSignIn();
    }

    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isUsernameTaken(String username) {
        return userService.findByLogin(username).isPresent();
    }

    private void registerUser(String username, String password) {
        authorizationService.register(username, password);
    }

    private String redirectToSignIn() {
        return "redirect:/sign-in";
    }

    private String redirectToSignUp() {
        return "redirect:/sign-up";
    }
}



