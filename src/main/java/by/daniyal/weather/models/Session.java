package by.daniyal.weather.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sessions")
@Builder
@AllArgsConstructor
@ToString
public class Session {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "expiresat")
    private LocalDateTime expireSet;
}


