package by.daniyal.weather.services;

import by.daniyal.weather.repositories.LocationsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class LocationService {
    private final LocationsRepository locationRepository;

    @Transactional
    public void deleteByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude) {
        locationRepository.deleteByLatitudeAndLongitude(latitude, longitude);
    }
}
