package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.LocationsRepository;
import by.daniyal.weather.services.WeatherService;
import by.daniyal.weather.services.weather.Coord;
import by.daniyal.weather.services.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        List<Coord> coords = locations.stream()
                .map(l -> new Coord(l.getLatitude(), l.getLongitude()))
                .toList();

        List<WeatherResponse> weathers = new ArrayList<>();

        for (Coord coord : coords) {
            Optional<WeatherResponse> weatherByName = weatherService.getByCoordinate(coord.getLat(), coord.getLon());
            weatherByName.ifPresent(weathers::add);
        }

        model.addAttribute("weathers", weathers);
        return "index";
    }


}

