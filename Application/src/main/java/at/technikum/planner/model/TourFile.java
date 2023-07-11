package at.technikum.planner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourFile implements Serializable {
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
    private byte[] imageBytes;
    private List<TourLog> tourLog;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
}
