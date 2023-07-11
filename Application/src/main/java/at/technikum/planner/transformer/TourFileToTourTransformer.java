package at.technikum.planner.transformer;

import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourFile;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.function.Function;

public class TourFileToTourTransformer implements Function<TourFile, Tour> {
    @Override
    public Tour apply(TourFile tourFile) {
        return Tour.builder()
                .id(tourFile.getId())
                .tourDescription(tourFile.getTourDescription())
                .name(tourFile.getName())
                .startAddress(tourFile.getStartAddress())
                .endAddress(tourFile.getEndAddress())
                .transportation(tourFile.getTransportation())
                .time(tourFile.getTime())
                .distance(tourFile.getDistance())
                .toll(tourFile.getToll())
                .highway(tourFile.getHighway())
                .map(new Image(new ByteArrayInputStream(tourFile.getImageBytes())))
                .imageBytes(tourFile.getImageBytes())
                .startLat(tourFile.getStartLat())
                .startLng(tourFile.getStartLng())
                .endLat(tourFile.getEndLat())
                .endLng(tourFile.getEndLng())
                .tourLog(tourFile.getTourLog()).build();
    }
}
