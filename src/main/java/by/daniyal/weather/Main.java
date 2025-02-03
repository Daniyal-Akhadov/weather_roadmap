package by.daniyal.weather;

import by.daniyal.weather.services.WeatherService;
import by.daniyal.weather.services.weather.WeatherResponse;
import org.springframework.web.client.RestTemplate;

class Main {
    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService(new RestTemplate());
//        WeatherResponse london = weatherService.getWeatherByName("London");
//        System.out.println(london);
    }
}
