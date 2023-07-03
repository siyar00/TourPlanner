package at.technikum.planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourLogs {

        private int id;

        private String date;

        private double duration;

        private double distance;

        private String comment;
}
