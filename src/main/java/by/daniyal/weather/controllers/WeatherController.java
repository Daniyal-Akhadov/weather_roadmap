package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.services.LocationService;
import by.daniyal.weather.services.SessionService;
import by.daniyal.weather.services.WeatherService;
import by.daniyal.weather.models.weather.Coord;
import by.daniyal.weather.models.weather.WeatherResponse;
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
    private final WeatherService weatherService;
    private final SessionService sessionService;
    private final LocationService locationService;

    @GetMapping
    public String index(@CookieValue(value = "SESSION_ID", required = false) final String sessionId,
                        final Model model) {
        try {
            final Optional<Session> session = sessionService.findBySessionId(sessionId);
            final List<Location> locations = locationService.findByUserId(session.get().getUserId());
            final List<Coord> coords = locations.stream()
                    .map(l -> new Coord(l.getLatitude(), l.getLongitude()))
                    .toList();

            final List<WeatherResponse> weathers = new ArrayList<>();

            for (Coord coord : coords) {
                Optional<WeatherResponse> weatherByName = weatherService.getByCoordinate(coord.getLon(), coord.getLat());
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