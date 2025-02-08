package by.daniyal.weather.services;

import by.daniyal.weather.models.City;
import by.daniyal.weather.models.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WeatherService {
    private static final String BY_NAME =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=800c82f878876e1bb151ad38e99af65a&units=metric&lang=en";

    private static final String BY_COORDINATES =
            "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=800c82f878876e1bb151ad38e99af65a&lang=en&units=metric";

    private static final String BY_ALL_STATES_CHOICES = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=c646e1486eb8bee3ad2bb7bf9a4d451a";

    private final RestTemplate restTemplate;

    public City[] getByAllStatesChoices(String name) {
        return restTemplate.getForObject(BY_ALL_STATES_CHOICES.formatted(name), City[].class);
    }

    public Optional<WeatherResponse> getByName(String name) {
        WeatherResponse weatherResponse = null;

        try {
            weatherResponse = restTemplate.getForObject(BY_NAME.formatted(name), WeatherResponse.class);
            System.out.println(weatherResponse);
        } catch (Exception e) {
            // TODO
        }

        return Optional.ofNullable(weatherResponse);
    }

    public Optional<WeatherResponse> getByCoordinate(BigDecimal latitude, BigDecimal longitude) {
        WeatherResponse weatherResponse = null;

        try {
            weatherResponse = restTemplate.getForObject(BY_COORDINATES.formatted(latitude.toString(), longitude.toString()), WeatherResponse.class);
        } catch (Exception e) {
            // TODO
        }

        return Optional.ofNullable(weatherResponse);
    }
}
