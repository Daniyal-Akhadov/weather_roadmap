package by.daniyal.weather.services;

import by.daniyal.weather.models.User;
import by.daniyal.weather.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService {
    private UserRepository userRepository;

    public User register(String login, String password) {
        User user = User.builder()
                .login(login)
                .password(password)
                .build();

        return userRepository.save(user);
    }

}

