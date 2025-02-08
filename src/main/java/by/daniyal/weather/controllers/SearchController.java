package by.daniyal.weather.controllers;

import by.daniyal.weather.City;
import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.LocationsRepository;
import by.daniyal.weather.services.WeatherService;
import by.daniyal.weather.services.LocationService;
import by.daniyal.weather.services.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

import static by.daniyal.weather.controllers.CookieConstraints.SESSION_ID;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {
    private final WeatherService weatherService;
    private final LocationsRepository locationsRepository;
    private final SessionService sessionService;
    private final LocationService locationService;

    @GetMapping
    public String find(@RequestParam(value = "name", required = false) String name,
                       Model model) {
        City[] cities = weatherService.getByAllStatesChoices(name);
        model.addAttribute("cities", cities);
        return "search-results";
    }

    @PostMapping
    public String find(@CookieValue(value = SESSION_ID, required = false) String sessionId,
                       @RequestParam(value = "name") String name,
                       @RequestParam(value = "lat") BigDecimal lat,
                       @RequestParam(value = "lon") BigDecimal lon,
                       @RequestParam(value = "country") String country) {

        Optional<Session> session = sessionService.findBySessionId(sessionId);
        Optional<WeatherResponse> weather = weatherService.getByCoordinate(lat, lon);

        if (session.isEmpty() || weather.isEmpty()) {
            return "redirect:search?name=" + name;
        }

        return handleLocation(session.get(), weather.get(), name, lat, lon, country);
    }

    private String handleLocation(Session session, WeatherResponse weather, String name, BigDecimal lat, BigDecimal lon, String country) {
        Location location = locationBuilder(name, country, weather, session);
        boolean hasLocation = locationService.hasUserLocation(lat, lon, session);

        if (!hasLocation) {
            locationsRepository.save(location);
            return "redirect:/";
        }

        return "redirect:search?name=" + name;
    }


    private static Location locationBuilder(String name, String country, WeatherResponse weather, Session session) {
        return Location.builder()
                .name(name)
                .name(name + ", " + country)
                .longitude(weather.getCoord().getLon())
                .latitude(weather.getCoord().getLat())
                .userId(session.getUserId())
                .build();
    }
}
