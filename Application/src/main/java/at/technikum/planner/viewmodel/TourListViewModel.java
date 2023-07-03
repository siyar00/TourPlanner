package at.technikum.planner.viewmodel;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TourListViewModel {
    public interface SelectionChangedListener {
        void tourChanged(Tour tour);
    }

    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<Tour> observableTours = FXCollections.observableArrayList();
    private final RouteServiceImpl routeService;

    public TourListViewModel(RouteServiceImpl routeService) {
        this.routeService = routeService;
    }

    public ObservableList<Tour> getObservableTours() {
        return observableTours;
    }

    public ChangeListener<Tour> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    private void notifyListeners(Tour newValue) {
        for (var listener : listeners) {
            listener.tourChanged(newValue);
        }
    }

    public void addSelectionChangedListener(SelectionChangedListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        listeners.remove(listener);
    }

    public void addNewTour(Tour tour) {
        //Executors.newFixedThreadPool(2).submit(() -> {
            try {
                String sessionId = routeService.getRoute(tour.getStartAddress().toString(), tour.getEndAddress().toString());
                File file = new File("../downloads/" + routeService.getImage(sessionId) + ".png");
                tour.setMap(new Image(new FileInputStream(file)));
                observableTours.add(tour);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
       // });
    }

    public void removeTour(Tour tour) {
        observableTours.remove(tour);
    }

}
