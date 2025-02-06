package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.LocationsRepository;
import by.daniyal.weather.services.ApiWeatherService;
import by.daniyal.weather.services.weather.Coord;
import by.daniyal.weather.services.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class WeatherController {
    private final ApiWeatherService apiWeatherService;
    private final LocationsRepository locationsRepository;
    private final SessionService sessionService;

    @GetMapping
    public String index(@CookieValue(value = "SESSION_ID", required = false) String sessionId, Model model) {
        try {
            Optional<Session> session = sessionService.findBySessionId(sessionId);
            List<Location> locations = locationsRepository.findByUserId(session.get().getUserId());

            List<Coord> coords = locations.stream()
                    .map(l -> new Coord(l.getLatitude(), l.getLongitude()))
                    .toList();

            List<WeatherResponse> weathers = new ArrayList<>();

            for (Coord coord : coords) {
                Optional<WeatherResponse> weatherByName = apiWeatherService.getByCoordinate(coord.getLon(), coord.getLat());
                weatherByName.ifPresent(weathers::add);
            }

            Collections.reverse(weathers);
            model.addAttribute("weathers", weathers);
            return "index";

        } catch (Exception e) {
            return "redirect:/sign-in";
        }
    }
}