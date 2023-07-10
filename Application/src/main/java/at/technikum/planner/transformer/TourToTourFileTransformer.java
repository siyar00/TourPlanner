package at.technikum.planner.transformer;

import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourFile;

import java.util.function.Function;

public class TourToTourFileTransformer implements Function<Tour, TourFile> {
    @Override
    public TourFile apply(Tour tour) {
        return TourFile.builder()
                .id(tour.getId())
                .tourDescription(tour.getTourDescription())
                .name(tour.getName())
                .startAddress(tour.getStartAddress())
                .endAddress(tour.getEndAddress())
                .transportation(tour.getTransportation())
                .time(tour.getTime())
                .distance(tour.getDistance())
                .toll(tour.getToll())
                .highway(tour.getHighway())
                .imageBytes(tour.getImageBytes())
                .tourLog(tour.getTourLog()).build();
    }
}
