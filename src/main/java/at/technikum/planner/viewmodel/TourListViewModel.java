package at.technikum.planner.viewmodel;

import at.technikum.planner.businessLayer.RouteServiceImpl;
import at.technikum.planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import retrofit2.Retrofit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    public void addNewTour(Tour tour) throws FileNotFoundException {
        String sessionId = routeService.getRoute(tour.getStartAddress().toString(), tour.getEndAddress().toString());

        tour.setMap(new Image(new FileInputStream("src/main/resources/at/technikum/planner/downloads/" + routeService.getImage(sessionId) + ".png")));
        observableTours.add(tour);
    }

    public void removeTour(Tour tour){
        observableTours.remove(tour);
    }

}
