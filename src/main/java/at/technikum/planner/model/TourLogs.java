package at.technikum.planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourLogs {

        @JsonProperty("id")
        private int id;

        @JsonProperty("date")
        private String date;

        // not sure if data type is correct
        @JsonProperty("duration")
        private double duration;

        @JsonProperty("distance")
        private double distance;

        @JsonProperty("comment")
        private String comment;
}
