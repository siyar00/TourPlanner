package at.technikum.planner.model;

import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class Tour implements Serializable {
    private Long id;
    private String name;
    private String tourDescription;
    private String startAddress;
    private String endAddress;
    private RouteType transportation;
    private String time;
    private String distance;
    private String toll;
    private String highway;
    private Image map;
    private byte[] imageBytes;
    private List<TourLog> tourLog;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Tour tour)) return false;
        return Objects.equals(this.id, tour.getId()) && Objects.equals(this.name, tour.getName()) && Objects.equals(this.startAddress, tour.getStartAddress()) && Objects.equals(this.endAddress, tour.getEndAddress()) && Objects.equals(this.transportation, tour.getTransportation()) && Objects.equals(this.tourDescription, tour.getTourDescription());
    }
}
