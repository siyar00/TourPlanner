package at.technikum.planner.transformer;

import at.technikum.dal.dao.TourDao;
import at.technikum.planner.model.Tour;

import java.util.function.Function;

public class TourToTourDaoTransformer implements Function<Tour, TourDao>{
    @Override
    public TourDao apply(Tour tour) {
        return TourDao.builder()
                .id(tour.getId())
                .description(tour.getTourDescription())
                .name(tour.getName())
                .start(tour.getStartAddress())
                .destination(tour.getEndAddress())
                .transportation(tour.getTransportation().getType())
                .time(tour.getTime())
                .distance(tour.getDistance())
                .hasTollRoad(tour.getToll())
                .hasHighway(tour.getHighway())
                .image(tour.getImageBytes())
                .build();
    }
}
