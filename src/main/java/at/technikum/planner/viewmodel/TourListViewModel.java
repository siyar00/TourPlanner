package at.technikum.planner.viewmodel;

import at.technikum.planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {
    public interface SelectionChangedListener {
        void tourChanged(Tour tour);
    }

    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<Tour> observableTours = FXCollections.observableArrayList();

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
        observableTours.add(tour);
    }

    public void removeTour(Tour tour){
        observableTours.remove(tour);
    }

}
