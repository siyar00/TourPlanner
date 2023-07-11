package at.technikum.planner.transformer;

import at.technikum.bl.model.TourBl;
import at.technikum.bl.model.TourLogBl;
import at.technikum.planner.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TourPlannerToTourBlTransformer implements Function<Tour, TourBl> {
    @Override
    public TourBl apply(Tour tour) {
        List<TourLogBl> tourLogBl = new ArrayList<>();
        if(tour.getTourLog() != null && tour.getTourLog().size() > 0)
             tourLogBl = tour.getTourLog().stream().map(tourLog -> at.technikum.bl.model.TourLogBl.builder()
                .date(tourLog.getDate())
                .duration(tourLog.getDuration())
                .comment(tourLog.getComment())
                .difficulty(tourLog.getDifficulty())
                .rating(tourLog.getRating())
                .build()).toList();
        return TourBl.builder()
                .name(tour.getName())
                .tourDescription(tour.getTourDescription())
                .startAddress(tour.getStartAddress())
                .endAddress(tour.getEndAddress())
                .transportation(String.valueOf(tour.getTransportation()))
                .time(tour.getTime())
                .distance(tour.getDistance())
                .toll(tour.getToll())
                .highway(tour.getHighway())
                .map(tour.getMap())
                .imageBytes(tour.getImageBytes())
                .tourLogBl(tourLogBl)
                .build();
    }
}
