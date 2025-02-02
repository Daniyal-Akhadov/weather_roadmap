package by.daniyal.weather.services;

import by.daniyal.weather.models.User;
import by.daniyal.weather.repositories.AuthorizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthorizationRepository authorizationRepository;

    public boolean login(String username, String password) {
        Optional<User> user = authorizationRepository.findByLogin(username);
        return user.map(value -> value.getPassword().equals(password)).orElse(false);
    }
}
