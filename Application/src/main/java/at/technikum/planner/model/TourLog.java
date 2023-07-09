package at.technikum.planner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourLog implements Serializable {
    private String date;
    private String duration;
    private String comment;
    private Integer difficulty;
    private Double rating;
}
