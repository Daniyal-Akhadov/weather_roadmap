package by.daniyal.weather.services;

import by.daniyal.weather.models.User;
import by.daniyal.weather.repositories.AuthorizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorizationService {
    private AuthorizationRepository authorizationRepository;

    public User register(String login, String password) {
        User user = User.builder()
                .login(login)
                .password(password)
                .build();

        return authorizationRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        return authorizationRepository.findByLogin(login);
    }
}
