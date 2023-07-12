package at.technikum.planner.transformer;

import at.technikum.bl.model.TourBl;
import at.technikum.bl.model.TourLogBl;
import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TourPlannerToTourBlTransformerTest {
    @Test
    void testApply() {
        Tour tour = getTour();
        TourBl tourBl = new TourPlannerToTourBlTransformer().apply(tour);

        assertEquals("Test Tour", tourBl.getName());
        assertEquals("Test Description", tourBl.getTourDescription());
        assertEquals("Start Address", tourBl.getStartAddress());
        assertEquals("End Address", tourBl.getEndAddress());
        assertEquals("FASTEST", tourBl.getTransportation());
        assertEquals("1h", tourBl.getTime());
        assertEquals("10 km", tourBl.getDistance());
        assertEquals("true", tourBl.getToll());
        assertEquals("false", tourBl.getHighway());
        assertNull(tourBl.getMap());
        assertArrayEquals(new byte[0], tourBl.getImageBytes());
        List<TourLogBl> tourLogBl = tourBl.getTourLogBl();
        assertNotNull(tourLogBl);
        assertEquals(1, tourLogBl.size());

        TourLogBl tourLog = tourLogBl.get(0);
        assertEquals(String.valueOf(LocalDate.now()), tourLog.getDate());
        assertEquals("1h", tourLog.getDuration());
        assertEquals("Test Comment", tourLog.getComment());
        assertEquals(3, tourLog.getDifficulty());
        assertEquals(4, tourLog.getRating());
    }

    private Tour getTour(){
        return Tour.builder()
                .name("Test Tour")
                .tourDescription("Test Description")
                .startAddress("Start Address")
                .endAddress("End Address")
                .transportation(RouteType.FASTEST)
                .time("1h")
                .distance("10 km")
                .toll(String.valueOf(true))
                .highway(String.valueOf(false))
                .imageBytes(new byte[0])
                .tourLog(Collections.singletonList(
                        TourLog.builder()
                                .date(String.valueOf(LocalDate.now()))
                                .duration("1h")
                                .comment("Test Comment")
                                .difficulty(3)
                                .rating(4F)
                                .build())).build();
    }
}
