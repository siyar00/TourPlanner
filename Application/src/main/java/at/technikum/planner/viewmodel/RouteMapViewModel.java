package at.technikum.planner.viewmodel;

import at.technikum.planner.model.Tour;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class RouteMapViewModel {

    private volatile boolean isInitValue = false;
    private final StringProperty contentProperty = new SimpleStringProperty();
    private final Property<Image> mapImageProperty = new SimpleObjectProperty<>();
    private Tour tour;

    public RouteMapViewModel () {
        contentProperty.addListener((arg, oldVal, newVal) -> updateTour());
        mapImageProperty.addListener((arg, oldVal, newVal) -> updateTour());
    }

    public void setTour(Tour tour) {
        isInitValue = true;
        if (tour == null) {
            // select the first in the list
            contentProperty.set("");
            mapImageProperty.setValue(null);
            System.out.println("setTourModel name=" + contentProperty.get());
            return;
        }
        System.out.println("setTourModel name=" + tour.getName());
        this.tour = tour;
        contentProperty.setValue(tour.getName());
        mapImageProperty.setValue(tour.getMap());
        isInitValue = false;
    }

    private void updateTour() {
        if( !isInitValue ) {
            tour.setName(contentProperty.get());
            tour.setMap(mapImageProperty.getValue());
        }
    }

    public StringProperty contenteProperty() {
        return contentProperty;
    }

    public Property<Image> mapImageProperty() {
        return mapImageProperty;
    }
}
