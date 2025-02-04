package by.daniyal.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class City {
    private String name;
    private String country;
    private BigDecimal lat;
    private BigDecimal lon;
}
