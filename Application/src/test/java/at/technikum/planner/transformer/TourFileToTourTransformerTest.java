package at.technikum.planner.transformer;

import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourFile;
import at.technikum.planner.model.TourLog;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourFileToTourTransformerTest {

    @Test
    public void testApply() {
        TourFile input = getTourFile();
        Tour tour = new TourFileToTourTransformer().apply(input);

        assertEquals(input.getId(), tour.getId());
        assertEquals(input.getName(), tour.getName());
        assertEquals(input.getTourDescription(), tour.getTourDescription());
        assertEquals(input.getStartAddress(), tour.getStartAddress());
        assertEquals(input.getEndAddress(), tour.getEndAddress());
        assertEquals(input.getTransportation(), tour.getTransportation());
        assertEquals(input.getTime(), tour.getTime());
        assertEquals(input.getDistance(), tour.getDistance());
        assertEquals(input.getToll(), tour.getToll());
        assertEquals(input.getHighway(), tour.getHighway());
        assertEquals(input.getImageBytes(), tour.getImageBytes());
        assertEquals(input.getStartLat(), tour.getStartLat());
        assertEquals(input.getStartLng(), tour.getStartLng());
        assertEquals(input.getEndLat(), tour.getEndLat());
        assertEquals(input.getEndLng(), tour.getEndLng());
        assertEquals(input.getTourLog(), tour.getTourLog());
    }

    private TourFile getTourFile() {
        return TourFile.builder()
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
