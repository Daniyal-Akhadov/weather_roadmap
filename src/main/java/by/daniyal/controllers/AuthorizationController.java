package by.daniyal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizationController {

    @GetMapping("/hello")
    public void hello() {
        System.out.println("Hello World");
    }
}
