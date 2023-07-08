package at.technikum.dal.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue
    private Long id;
    @Column
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
    private Blob image;
}
