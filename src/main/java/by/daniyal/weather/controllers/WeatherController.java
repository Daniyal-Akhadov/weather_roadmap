package by.daniyal.weather.controllers;

import by.daniyal.weather.services.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/index")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("weather", weatherService.getWeatherByName("Москва"));
        return "index";
    }

    @GetMapping("/find")
    public String find(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("weather", weatherService.getWeatherByName(name));
        return "index";
    }
}
