package by.daniyal.weather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizationController {

    @GetMapping("/hello")
    public String rest() {
        return "hello";
    }


}
