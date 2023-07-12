package at.technikum.planner.transformer;

import at.technikum.dal.dao.TourDao;
import at.technikum.dal.dao.TourLogsDao;
import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class TourDaoToTourTransformerTest {

    @Test
    public void testApply() {
        TourDao input = getTourDao();
        Tour tour = new TourDaoToTourTransformer().apply(input);

        Assertions.assertEquals(input.getId(), tour.getId());
        Assertions.assertEquals(input.getName(), tour.getName());
        Assertions.assertEquals(input.getDescription(), tour.getTourDescription());
        Assertions.assertEquals(input.getStart(), tour.getStartAddress());
        Assertions.assertEquals(input.getDestination(), tour.getEndAddress());
        Assertions.assertEquals(RouteType.fromType(input.getTransportation()), tour.getTransportation());
        Assertions.assertEquals(input.getTime(), tour.getTime());
        Assertions.assertEquals(input.getDistance(), tour.getDistance());
        Assertions.assertEquals(input.getHasTollRoad(), tour.getToll());
        Assertions.assertEquals(input.getHasHighway(), tour.getHighway());
        Assertions.assertEquals(input.getImage(), tour.getImageBytes());
        Assertions.assertEquals(input.getStartLat(), tour.getStartLat());
        Assertions.assertEquals(input.getStartLng(), tour.getStartLng());
        Assertions.assertEquals(input.getEndLat(), tour.getEndLat());
        Assertions.assertEquals(input.getEndLng(), tour.getEndLng());
        Assertions.assertEquals(input.getPopularity(), tour.getPopularity());
        Assertions.assertEquals(input.getChildFriendly(), tour.getChildFriendly());
        Assertions.assertEquals(input.getTourLogsDao().get(0).getLogId(), tour.getTourLog().get(0).getLogId());
        Assertions.assertEquals(input.getTourLogsDao().get(0).getComment(), tour.getTourLog().get(0).getComment());
        Assertions.assertEquals(input.getTourLogsDao().get(0).getRating(), tour.getTourLog().get(0).getRating());
        Assertions.assertEquals(input.getTourLogsDao().get(0).getDuration(), tour.getTourLog().get(0).getDuration());
        Assertions.assertEquals(input.getTourLogsDao().get(0).getDifficulty(), tour.getTourLog().get(0).getDifficulty());
    }

    private TourDao getTourDao(){
        return TourDao.builder()
                .id(1L)
                .transportation(RouteType.BICYCLE.getType())
                .description("Tour description")
                .name("Tour name")
                .start("Start address")
                .destination("Destination address")
                .time("03:20")
                .distance("10 km")
                .hasTollRoad(String.valueOf(false))
                .hasHighway(String.valueOf(true))
                .image(new byte[0])
                .startLat(1.0)
                .startLng(2.0)
                .endLat(3.0)
                .endLng(4.0)
                .popularity("High")
                .childFriendly("Child friendly")
                .tourLogsDao(Collections.singletonList(TourLogsDao.builder()
                                .comment("Comment")
                                .logId(1L)
                                .rating(5F)
                                .duration("1h20min")
                                .tourDao(TourDao.builder().id(1L).build())
                                .difficulty(3).build())).build();
    }
}
