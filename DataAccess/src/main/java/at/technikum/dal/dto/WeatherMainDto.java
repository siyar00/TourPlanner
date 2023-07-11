package at.technikum.dal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMainDto {
    private String temp;
    private String feels_like;
    private String temp_min;
    private String temp_max;
    private String pressure;
    private String humidity;
}
