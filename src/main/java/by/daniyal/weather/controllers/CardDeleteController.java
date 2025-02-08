package by.daniyal.weather.controllers;

import by.daniyal.weather.services.LocationService;
import by.daniyal.weather.services.SessionService;
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
public class CardDeleteController {

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
