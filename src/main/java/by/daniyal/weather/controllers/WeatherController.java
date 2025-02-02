package by.daniyal.weather.controllers;

import by.daniyal.weather.services.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/index")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public String index(Model model,
                        HttpServletRequest request) {
        model.addAttribute("weather", weatherService.getWeatherByName("Moscow"));
        return "index";
    }
}
