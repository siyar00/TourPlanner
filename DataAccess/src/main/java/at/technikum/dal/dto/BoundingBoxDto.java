package at.technikum.dal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class BoundingBoxDto {
    private CoordinatesDto ul;
    private CoordinatesDto lr;

    public String toString() {
        return String.format("%s,%s,%s,%s", ul.getLat(), ul.getLng(), lr.getLat(), lr.getLng());
    }
}
