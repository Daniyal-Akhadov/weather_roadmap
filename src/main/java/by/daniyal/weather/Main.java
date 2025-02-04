package by.daniyal.weather;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

class Main {
    private static final String BY_ALL_STATES_CHOICES = "https://api.openweathermap.org/geo/1.0/direct?q=Piter&limit=5&appid=c646e1486eb8bee3ad2bb7bf9a4d451a";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        City[] cities = restTemplate.getForObject(BY_ALL_STATES_CHOICES, City[].class);

        System.out.println(Arrays.toString(cities));
    }
}



