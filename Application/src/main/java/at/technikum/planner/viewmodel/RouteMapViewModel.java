package at.technikum.planner.viewmodel;

import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import at.technikum.planner.transformer.RouteTypeTransformer;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import lombok.Data;

import java.util.ResourceBundle;

@Data
public class RouteMapViewModel {

    private volatile boolean isInitValue = false;
    private final StringProperty startingAddress = new SimpleStringProperty();
    private final StringProperty destinationAddress = new SimpleStringProperty();
    private final StringProperty toll = new SimpleStringProperty();
    private final StringProperty highway = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty transportation = new SimpleStringProperty();
    private final StringProperty tourDescription = new SimpleStringProperty();
    private final BooleanProperty tourDescriptionLabel = new SimpleBooleanProperty();
    private final BooleanProperty startingAddressLabel = new SimpleBooleanProperty();
    private final BooleanProperty destinationAddressLabel = new SimpleBooleanProperty();
    private final BooleanProperty tollLabel = new SimpleBooleanProperty();
    private final BooleanProperty highwayLabel = new SimpleBooleanProperty();
    private final BooleanProperty timeLabel = new SimpleBooleanProperty();
    private final BooleanProperty distanceLabel = new SimpleBooleanProperty();
    private final BooleanProperty transportationLabel = new SimpleBooleanProperty();
    private final Property<Image> mapImageProperty = new SimpleObjectProperty<>();
    private Tour tour;
    private ResourceBundle bundle;

    public RouteMapViewModel (ResourceBundle bundle) {
        this.bundle = bundle;
        startingAddress.addListener((arg, oldVal, newVal) -> updateTour());
        destinationAddress.addListener((arg, oldVal, newVal) -> updateTour());
        toll.addListener((arg, oldVal, newVal) -> updateTour());
        highway.addListener((arg, oldVal, newVal) -> updateTour());
        time.addListener((arg, oldVal, newVal) -> updateTour());
        distance.addListener((arg, oldVal, newVal) -> updateTour());
        transportation.addListener((arg, oldVal, newVal) -> updateTour());
        tourDescription.addListener((arg, oldVal, newVal) -> updateTour());
        mapImageProperty.addListener((arg, oldVal, newVal) -> updateTour());
    }

    public void setTour(Tour tour) {
        isInitValue = true;
        if (tour == null) {
            startingAddressLabel.set(false);
            destinationAddressLabel.set(false);
            tollLabel.set(false);
            highwayLabel.set(false);
            timeLabel.set(false);
            distanceLabel.set(false);
            transportationLabel.set(false);
            tourDescriptionLabel.set(false);
            tourDescription.set("");
            startingAddress.set("");
            destinationAddress.set("");
            toll.set("");
            highway.set("");
            time.set("");
            distance.set("");
            transportation.set("");
            mapImageProperty.setValue(null);
            System.out.println("setTourModel name=" + startingAddress.get());
            return;
        }
        System.out.println("setTourModel name=" + tour.getName());
        this.tour = tour;
        startingAddressLabel.set(true);
        destinationAddressLabel.set(true);
        tollLabel.set(true);
        highwayLabel.set(true);
        timeLabel.set(true);
        distanceLabel.set(true);
        transportationLabel.set(true);
        tourDescriptionLabel.set(true);
        tourDescription.set(tour.getTourDescription());
        startingAddress.setValue(tour.getStartAddress());
        destinationAddress.setValue(tour.getEndAddress());
        toll.setValue(tour.getToll().equals("false") ? bundle.getString("No") : bundle.getString("Yes"));
        highway.setValue(tour.getHighway().equals("false") ? bundle.getString("No") : bundle.getString("Yes"));
        time.setValue(tour.getTime());
        distance.setValue(tour.getDistance() + " km");
        transportation.setValue(RouteTypeTransformer.getBundleFromRouteType(tour.getTransportation(), bundle));
        mapImageProperty.setValue(tour.getMap());
        isInitValue = false;
    }

    private void updateTour() {
        if( !isInitValue ) {
            tour.setName(startingAddress.get());
            tour.setStartAddress(startingAddress.get());
            tour.setEndAddress(destinationAddress.get());
            tour.setTransportation(RouteType.BICYCLE);
            tour.setMap(mapImageProperty.getValue());
        }
    }

    public Property<Image> mapImageProperty() {
        return mapImageProperty;
    }
}
