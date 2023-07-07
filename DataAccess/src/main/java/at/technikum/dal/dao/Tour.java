package at.technikum.dal.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private String description;
    @Column
    private String start;
    @Column
    private String destination;
    @Column
    private String distance;
    @Column
    private String duration;
    @Column
    private String route;
    @Column
    private String image;
    @Column
    private String transportation;
}
