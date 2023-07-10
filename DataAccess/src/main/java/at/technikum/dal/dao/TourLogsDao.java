package at.technikum.dal.dao;

import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("all")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")
public class TourLogsDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @MapsId("tourId")
    @JoinColumn(name = "tour_id")
    private TourDao tourDao;

    private String date;
    private String duration;
    private String comment;
    private Integer difficulty;
    private Float rating;
}
