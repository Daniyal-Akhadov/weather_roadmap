package by.daniyal.weather.repositories;

import by.daniyal.weather.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationRepository extends JpaRepository<User, Integer> {
    void save(String username);

    Optional<User> findByLogin(String login);
}
