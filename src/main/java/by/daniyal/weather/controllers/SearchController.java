package by.daniyal.weather.controllers;

import by.daniyal.weather.City;
import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.LocationsRepository;
import by.daniyal.weather.services.WeatherService;
import by.daniyal.weather.services.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {
    private final WeatherService weatherService;
    private final LocationsRepository locationsRepository;
    private final SessionService sessionService;

    @GetMapping
    public String find(@CookieValue("SESSION_ID") String sessionId,
                       @RequestParam(value = "name", required = false) String name,
                       Model model) {
        Optional<Session> session = sessionService.findBySessionId(sessionId);
        City[] cities = weatherService.getByAllStatesChoices(name);
        model.addAttribute("cities", cities);
        return "search-results";
    }

    @PostMapping
    public String find(@CookieValue("SESSION_ID") String sessionId,
                       @RequestParam(value = "name") String name,
                       @RequestParam(value = "lat") BigDecimal lat,
                       @RequestParam(value = "lon") BigDecimal lon,
                       @RequestParam(value = "country") String country
    ) {
        Optional<Session> session = sessionService.findBySessionId(sessionId);
        Optional<WeatherResponse> weather = weatherService.getByCoordinate(lat, lon);

        if (weather.isPresent()) {
            Location location = Location.builder()
                    .name(name)
                    .longitude(weather.get().getCoord().getLon())
                    .latitude(weather.get().getCoord().getLat())
                    .userId(session.get().getUserId())
                    .build();

            locationsRepository.save(location);
        }

        return "redirect:/index";
    }
}
