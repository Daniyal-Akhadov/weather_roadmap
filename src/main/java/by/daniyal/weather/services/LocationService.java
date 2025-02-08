package by.daniyal.weather.services;

import by.daniyal.weather.models.Location;
import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.LocationsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {
    private final LocationsRepository locationRepository;

    @Transactional
    public void deleteByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude) {
        locationRepository.deleteByLatitudeAndLongitude(latitude, longitude);
    }

    public boolean hasUserLocation(BigDecimal lat, BigDecimal lon, Session session) {
        List<Location> locationsByLatitudeAndLongitude = locationRepository.findLocationsByUserId(session.getUserId());
        boolean hasLocation = false;

        for (Location temp : locationsByLatitudeAndLongitude) {
            BigDecimal precision = new BigDecimal("0.0001"); // Заданная точность

            hasLocation = temp.getLatitude().subtract(lat).abs().compareTo(precision) < 0 && temp.getLongitude().subtract(lon).abs().compareTo(precision) < 0;

            if (hasLocation) {
                break;
            }
        }
        return hasLocation;
    }
}
