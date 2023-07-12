package at.technikum.planner.transformer;

import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourFile;
import at.technikum.planner.model.TourLog;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourToTourFileTransformerTest {
    @Test
    public void testApply() {
        Tour input = getTour();
        TourFile tourFile = new TourToTourFileTransformer().apply(input);

        assertEquals(input.getId(), tourFile.getId());
        assertEquals(input.getName(), tourFile.getName());
        assertEquals(input.getTourDescription(), tourFile.getTourDescription());
        assertEquals(input.getStartAddress(), tourFile.getStartAddress());
        assertEquals(input.getEndAddress(), tourFile.getEndAddress());
        assertEquals(input.getTransportation(), tourFile.getTransportation());
        assertEquals(input.getTime(), tourFile.getTime());
        assertEquals(input.getDistance(), tourFile.getDistance());
        assertEquals(input.getToll(), tourFile.getToll());
        assertEquals(input.getHighway(), tourFile.getHighway());
        assertEquals(input.getImageBytes(), tourFile.getImageBytes());
        assertEquals(input.getStartLat(), tourFile.getStartLat());
        assertEquals(input.getStartLng(), tourFile.getStartLng());
        assertEquals(input.getEndLat(), tourFile.getEndLat());
        assertEquals(input.getEndLng(), tourFile.getEndLng());
        assertEquals(input.getTourLog(), tourFile.getTourLog());
    }

    private Tour getTour() {
        return Tour.builder()
                .id(1L)
                .name("Tour Name")
                .tourDescription("Tour Description")
                .startAddress("Start Address")
                .endAddress("End Address")
                .transportation(RouteType.BICYCLE)
                .time("03:20")
                .distance("10 km")
                .toll(String.valueOf(false))
                .highway(String.valueOf(true))
                .imageBytes(new byte[0])
                .startLat(1.0)
                .startLng(2.0)
                .endLat(3.0)
                .endLng(4.0)
                .tourLog(Collections.singletonList(new TourLog()))
                .build();
    }
}
