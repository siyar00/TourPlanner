package at.technikum.dal.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@SuppressWarnings("all")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tours")
public class TourDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    private String start;
    private String destination;
    private String distance;
    private String time;
    private String hasTollRoad;
    private String hasHighway;
    private String transportation;
    private byte[] image;

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, orphanRemoval = true, targetEntity = TourLogsDao.class, fetch = FetchType.LAZY, mappedBy = "tourDao")
    private List<TourLogsDao> tourLogsDao;

}
