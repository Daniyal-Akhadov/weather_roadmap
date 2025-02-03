package by.daniyal.weather.services;

import by.daniyal.weather.services.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@AllArgsConstructor
@Service
public class WeatherService {
    private static final String URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=c646e1486eb8bee3ad2bb7bf9a4d451a&units=metric";

    private final RestTemplate restTemplate;

    public Optional<WeatherResponse> getWeatherByName(String name) {
        WeatherResponse weatherResponse = null;

        try {
            weatherResponse = restTemplate.getForObject(URL.formatted(name), WeatherResponse.class);
        } catch (Exception e) {
            // TODO
        }

        return Optional.ofNullable(weatherResponse);
    }
}
