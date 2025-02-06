package by.daniyal.weather.controllers;

import by.daniyal.weather.models.Session;
import by.daniyal.weather.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public void save(Session session) {
        sessionRepository.save(session);
    }

    public Optional<Session> findBySessionId(String sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public void deleteBySessionId(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
