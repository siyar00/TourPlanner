package at.technikum.planner.dataAccessLayer.dataTransferObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class RouteResponse {
    Route route;
}
