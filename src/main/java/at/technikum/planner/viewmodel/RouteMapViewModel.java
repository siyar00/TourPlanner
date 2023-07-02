package at.technikum.planner.viewmodel;

import at.technikum.planner.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

public class RouteMapViewModel {

    private volatile boolean isInitValue = false;
    private final StringProperty contentProperty = new SimpleStringProperty();
    private Tour tour;

    public RouteMapViewModel () {
        contentProperty.addListener((arg, oldVal, newVal) -> updateTour());
    }

    public void setTour(Tour tour) {
        isInitValue = true;
        if (tour == null) {
            // select the first in the list
            contentProperty.set("Hier wird die Route angezeigt");
            System.out.println("1setTourModel name=" + contentProperty.get());
            return;
        }
        System.out.println("setTourModel name=" + tour.getName());
        this.tour = tour;
        contentProperty.setValue(tour.getName());
        isInitValue = false;
    }

    private void updateTour() {
        if( !isInitValue ) {
            tour.setName(contentProperty.get());
        }
    }

    public StringProperty contenteProperty() {
        return contentProperty;
    }
}
