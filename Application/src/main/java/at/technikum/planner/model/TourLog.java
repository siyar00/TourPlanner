package at.technikum.planner.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourLog implements Serializable {
    private int id;
    private Date date;
    private double duration;
    private String comment;
    private String difficulty;
    private String rating;
}
