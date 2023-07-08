package at.technikum.planner.model;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Tour implements Serializable {
    private String id;
    private String name;
    private String startAddress;
    private String endAddress;
    private RouteType transportation;
    private String time;
    private String distance;
    private String toll;
    private String highway;
    private Image map;
}
