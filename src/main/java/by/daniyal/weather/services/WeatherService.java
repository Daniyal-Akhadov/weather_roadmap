package by.daniyal.weather.services;

import by.daniyal.weather.services.weather.WeatherResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class WeatherService {
    private static final String URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=c646e1486eb8bee3ad2bb7bf9a4d451a&units=metric";

    private final RestTemplate restTemplate;

    public WeatherResponse getWeatherByName(String name) {
        return restTemplate.getForObject(URL.formatted(name), WeatherResponse.class);
    }
}
