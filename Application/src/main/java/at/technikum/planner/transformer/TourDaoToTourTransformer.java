package at.technikum.planner.transformer;

import at.technikum.dal.dao.TourDao;
import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.function.Function;

public class TourDaoToTourTransformer implements Function<TourDao, Tour> {
    @Override
    public Tour apply(TourDao tourDao) {
        return Tour.builder()
                .name(tourDao.getName())
                .startAddress(tourDao.getStart())
                .endAddress(tourDao.getDestination())
                .transportation(RouteType.fromType(tourDao.getTransportation()))
                .time(tourDao.getTime())
                .distance(tourDao.getDistance())
                .toll(tourDao.getHasTollRoad())
                .highway(tourDao.getHasHighway())
                .map(new Image(new ByteArrayInputStream(tourDao.getImage()))).build();
    }
}
