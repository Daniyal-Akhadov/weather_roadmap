package by.daniyal.weather.services;

import by.daniyal.weather.models.User;
import by.daniyal.weather.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User register(String login, String password) {
        User user = User.builder()
                .login(login)
                .password(password)
                .build();

        return userRepository.save(user);
    }
}
