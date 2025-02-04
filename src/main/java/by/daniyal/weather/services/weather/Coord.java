package by.daniyal.weather.services.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coord {
    private BigDecimal lon;
    private BigDecimal lat;
}
