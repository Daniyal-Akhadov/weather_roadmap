package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.LocationsRepository;
import by.daniyal.weather.services.WeatherService;
import by.daniyal.weather.services.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/index")
public class WeatherController {
    private final WeatherService weatherService;
    private final LocationsRepository locationsRepository;
    private final SessionService sessionService;

    @GetMapping
    public String index(@CookieValue("SESSION_ID") String sessionId, Model model) {
        Optional<Session> session = sessionService.findBySessionId(sessionId);
        List<Location> locations = locationsRepository.findByUserId(session.get().getUserId());
        List<String> names = locations.stream().map(Location::getName).toList();
        List<WeatherResponse> weathers = new ArrayList<>();

        for (String name : names) {
            Optional<WeatherResponse> weatherByName = weatherService.getWeatherByName(name);
            weatherByName.ifPresent(weathers::add);
        }

        model.addAttribute("weathers", weathers);
        return "index";
    }

    @GetMapping("/find")
    public String find(@CookieValue("SESSION_ID") String sessionId,
                       @RequestParam(value = "name", required = false) String name) {

        Optional<Session> session = sessionService.findBySessionId(sessionId);
        Optional<WeatherResponse> weather = weatherService.getWeatherByName(name);

        if (weather.isPresent()) {
            Location location = Location.builder()
                    .name(name)
                    .longitude(BigDecimal.valueOf(0.1f))
                    .latitude(BigDecimal.valueOf(0.1f))
                    .userId(session.get().getUserId())
                    .build();

            locationsRepository.save(location);
        }

        return "redirect:/index";
    }
}