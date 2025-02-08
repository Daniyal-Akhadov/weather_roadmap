package by.daniyal.weather.services;

import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.LocationsRepository;
import by.daniyal.weather.models.weather.WeatherResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor

public class LocationService {
    private static final BigDecimal PRECISION = new BigDecimal("0.0001"); // Заданная точность

    private final LocationsRepository locationRepository;

    @Transactional
    public void deleteByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude) {
        locationRepository.deleteByLatitudeAndLongitude(latitude, longitude);
    }

    public boolean hasUserLocation(BigDecimal lat, BigDecimal lon, Session session) {
        return locationRepository.findLocationsByUserId(session.getUserId()).stream()
                .anyMatch(location -> isLocationClose(location, lat, lon));
    }

    public Location locationBuilder(String name, WeatherResponse weather, Session session) {
        return Location.builder()
                .name(name)
                .longitude(weather.getCoord().getLon())
                .latitude(weather.getCoord().getLat())
                .userId(session.getUserId())
                .build();
    }

    public void save(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location must not be null");
        }

        locationRepository.save(location);
    }

    private boolean isLocationClose(Location location, BigDecimal lat, BigDecimal lon) {
        BigDecimal latDifference = location.getLatitude().subtract(lat);
        BigDecimal lonDifference = location.getLongitude().subtract(lon);

        return latDifference.abs().compareTo(PRECISION) < 0
                && lonDifference.abs().compareTo(PRECISION) < 0;
    }

    public List<Location> findByUserId(int userId) {
        return locationRepository.findLocationsByUserId(userId);
    }
}