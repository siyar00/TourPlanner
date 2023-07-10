package at.technikum.planner.transformer;

import at.technikum.dal.dao.TourDao;
import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourLog;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TourDaoToTourTransformer implements Function<TourDao, Tour> {
    @Override
    public Tour apply(TourDao tourDao) {
        List<TourLog> tourLogs = tourDao.getTourLogsDao().stream().map(t -> new TourLogDaoToTourLogTransformer().apply(t)).collect(Collectors.toList());
        return Tour.builder()
                .id(tourDao.getId())
                .tourDescription(tourDao.getDescription())
                .name(tourDao.getName())
                .startAddress(tourDao.getStart())
                .endAddress(tourDao.getDestination())
                .transportation(RouteType.fromType(tourDao.getTransportation()))
                .time(tourDao.getTime())
                .distance(tourDao.getDistance())
                .toll(tourDao.getHasTollRoad())
                .highway(tourDao.getHasHighway())
                .map(new Image(new ByteArrayInputStream(tourDao.getImage())))
                .imageBytes(tourDao.getImage())
                .tourLog(tourLogs).build();
    }
}
