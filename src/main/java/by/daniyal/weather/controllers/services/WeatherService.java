package by.daniyal.weather.controllers.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public class WeatherService {

    private RestTemplate restTemplate = new RestTemplate();

    public String getWeather(String city) {
        return restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather", String.class);
    }
}
