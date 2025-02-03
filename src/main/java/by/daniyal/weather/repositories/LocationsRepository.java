package by.daniyal.weather.repositories;

import by.daniyal.weather.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationsRepository extends JpaRepository<Location, Integer> {
    Location save(Location location);

    List<Location> findByUserId(int userId);
}
