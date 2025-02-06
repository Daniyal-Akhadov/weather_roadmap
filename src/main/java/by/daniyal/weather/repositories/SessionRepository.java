package by.daniyal.weather.repositories;

import by.daniyal.weather.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    @Override
    Optional<Session> findById(String s);
}
