package at.technikum.dal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.geometry.BoundingBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class RouteDto {
    String sessionId;
    List<LocationDto> locations;
    String distance;
    String formattedTime;
    String hasTollRoad;
    String hasHighway;
    BoundingBoxDto boundingBox;
}
