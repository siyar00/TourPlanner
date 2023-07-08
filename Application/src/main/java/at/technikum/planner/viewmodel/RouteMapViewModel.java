package at.technikum.planner.viewmodel;

import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import at.technikum.planner.transformer.RouteTypeTransformer;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
        mapImageProperty.addListener((arg, oldVal, newVal) -> updateTour());
    }

    public void setTour(Tour tour) {
        isInitValue = true;
        if (tour == null) {
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
        startingAddress.setValue(tour.getStartAddress());
        destinationAddress.setValue(tour.getEndAddress());
        toll.setValue(tour.getToll().equals("false") ? bundle.getString("No") : bundle.getString("Yes"));
        highway.setValue(tour.getHighway().equals("false") ? bundle.getString("No") : bundle.getString("Yes"));
        time.setValue(tour.getTime());
        distance.setValue(tour.getDistance() + " km");
        transportation.setValue(new RouteTypeTransformer().getBundleFromRouteType(tour.getTransportation(), bundle));
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

    public StringProperty contenteProperty() {
        return startingAddress;
    }

    public Property<Image> mapImageProperty() {
        return mapImageProperty;
    }
}
