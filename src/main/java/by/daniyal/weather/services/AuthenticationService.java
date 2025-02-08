package by.daniyal.weather.services;

import by.daniyal.weather.models.User;
import by.daniyal.weather.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private UserRepository userRepository;

    public boolean login(String username, String password) {
        Optional<User> user = userRepository.findByLogin(username);
        return user.map(value -> value.getPassword().equals(password)).orElse(false);
    }
}
