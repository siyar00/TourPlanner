package at.technikum.dal.dao;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tours")
public class TourDao {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String start;
    @Column
    private String destination;
    @Column
    private String distance;
    @Column
    private String time;
    @Column
    private String hasTollRoad;
    @Column
    private String hasHighway;
    @Column
    private String transportation;
    @Column
    private byte[] image;
}
