package at.technikum.planner.transformer;

import at.technikum.dal.dao.TourDao;
import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourToTourDaoTransformerTest {

    @Test
    public void testApply() {
        Tour input = getTour();
        TourDao tourDao = new TourToTourDaoTransformer().apply(input);

        assertEquals(input.getId(), tourDao.getId());
        assertEquals(input.getTourDescription(), tourDao.getDescription());
        assertEquals(input.getName(), tourDao.getName());
        assertEquals(input.getStartAddress(), tourDao.getStart());
        assertEquals(input.getEndAddress(), tourDao.getDestination());
        assertEquals(input.getTransportation().getType(), tourDao.getTransportation());
        assertEquals(input.getTime(), tourDao.getTime());
        assertEquals(input.getDistance(), tourDao.getDistance());
        assertEquals(input.getToll(), tourDao.getHasTollRoad());
        assertEquals(input.getHighway(), tourDao.getHasHighway());
        assertEquals(input.getImageBytes(), tourDao.getImage());
        assertEquals(input.getStartLat(), tourDao.getStartLat());
        assertEquals(input.getStartLng(), tourDao.getStartLng());
        assertEquals(input.getEndLat(), tourDao.getEndLat());
        assertEquals(input.getEndLng(), tourDao.getEndLng());
    }

    private Tour getTour() {
        return Tour.builder()
                        .id(1L)
                        .tourDescription("Tour Description")
                        .name("Tour Name")
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
                        .build();
    }

}
