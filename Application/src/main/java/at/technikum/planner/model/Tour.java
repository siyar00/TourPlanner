package at.technikum.planner.model;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Tour implements Serializable {

    private String name;
    private Address startAddress;
    private Address endAddress;
    private String transportation;
    private Image map;
    private String misc;

}
