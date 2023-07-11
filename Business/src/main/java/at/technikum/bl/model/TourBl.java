package at.technikum.bl.model;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TourBl implements Serializable {
    private String name;
    private String tourDescription;
    private String startAddress;
    private String endAddress;
    private String transportation;
    private String time;
    private String distance;
    private String toll;
    private String highway;
    private Image map;
    private byte[] imageBytes;
    private List<TourLogBl> tourLogBl;
}
