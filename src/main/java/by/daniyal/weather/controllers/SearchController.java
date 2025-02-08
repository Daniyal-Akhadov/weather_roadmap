package by.daniyal.weather.controllers;

import by.daniyal.weather.models.City;
import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.services.LocationService;
import by.daniyal.weather.services.SessionService;
import by.daniyal.weather.services.WeatherService;
import by.daniyal.weather.models.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

import static by.daniyal.weather.config.CookieConfiguration.COOKIE_NAME;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {
    private final WeatherService weatherService;
    private final SessionService sessionService;
    private final LocationService locationService;

    @GetMapping
    public String find(@RequestParam(value = "name", required = false) String name,
                       Model model) {
        final City[] cities = weatherService.getByAllStatesChoices(name);
        model.addAttribute("cities", cities);
        return "search-results";
    }

    @PostMapping
    public String find(@CookieValue(value = COOKIE_NAME, required = false) final String sessionId,
                       @RequestParam(value = "name") final String name,
                       @RequestParam(value = "lat") final BigDecimal lat,
                       @RequestParam(value = "lon") final BigDecimal lon,
                       @RequestParam(value = "country") final String country) {
        final Optional<Session> session = sessionService.findBySessionId(sessionId);
        final Optional<WeatherResponse> weather = weatherService.getByCoordinate(lat, lon);

        if (session.isEmpty() || weather.isEmpty()) {
            return "redirect:search?name=" + name;
        }

        return handleLocation(session.get(), weather.get(), name, lat, lon, country);
    }

    private String handleLocation(final Session session,
                                  final WeatherResponse weather,
                                  final String name,
                                  final BigDecimal lat,
                                  final BigDecimal lon,
                                  final String country) {
        final Location location = locationService.locationBuilder(name + ", " + country, weather, session);
        final boolean hasLocation = locationService.hasUserLocation(lat, lon, session);

        if (!hasLocation) {
            locationService.save(location);
            return "redirect:/";
        }

        return "redirect:search?name=" + name;
    }
}
