package at.technikum.bl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourLogBl implements Serializable {
    private String date;
    private String duration;
    private String comment;
    private Integer difficulty;
    private Float rating;
}
