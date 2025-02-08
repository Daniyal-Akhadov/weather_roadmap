package by.daniyal.weather.controllers.services;

import by.daniyal.weather.controllers.SessionService;
import by.daniyal.weather.repositories.LocationsRepository;
import by.daniyal.weather.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/delete")
@AllArgsConstructor
public class DeleteController {

    private final SessionService sessionService;
    private final LocationService locationService;

    @PostMapping
    public String delete(@RequestParam("lat") BigDecimal latitude,
                         @RequestParam("lon") BigDecimal longitude,
                         @CookieValue("SESSION_ID") String sessionId) {
        sessionService.findBySessionId(sessionId).ifPresent(session -> {
            locationService.deleteByLatitudeAndLongitude(latitude, longitude);
        });

        return "redirect:/";
    }
}
