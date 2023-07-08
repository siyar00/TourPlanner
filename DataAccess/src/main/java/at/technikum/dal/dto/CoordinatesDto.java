package at.technikum.dal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesDto {
    double lat;
    double lng;

    public boolean equals(CoordinatesDto o) {
        return o.getLat() == this.getLat() && o.getLng() == this.getLng();
    }
}
