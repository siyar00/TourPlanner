package at.technikum.planner.viewmodel;

import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourLog;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TourLogsViewModel {
    public interface SelectionChangedListener {
        void logChanged(TourLog log);
    }

    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<TourLog> observableTourLogs = FXCollections.observableArrayList();
    private final PropertyValueFactory<TourLog, Integer> idColumnProperty = new PropertyValueFactory<>("id");
    private final PropertyValueFactory<TourLog, String> dateColumnProperty = new PropertyValueFactory<>("date");
    private final PropertyValueFactory<TourLog, String> durationColumnProperty = new PropertyValueFactory<>("duration");
    private final PropertyValueFactory<TourLog, String> commentColumnProperty = new PropertyValueFactory<>("comment");
    private final PropertyValueFactory<TourLog, Integer> difficultyColumnProperty = new PropertyValueFactory<>("difficulty");
    private final PropertyValueFactory<TourLog, Double> ratingColumnProperty = new PropertyValueFactory<>("rating");
    private final TourListViewModel tourListViewModel;
    private Tour tour;

    public TourLogsViewModel(TourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }

    public void setTourLog(Tour tour) {
        observableTourLogs.clear();
        this.tour = tour;
        List<TourLog> tourLogs = tour.getTourLog();
        if (tourLogs.isEmpty()) return;
        System.out.println("setTourLogs name=" + tour.getName());
        observableTourLogs.addAll(tourLogs);
    }

    public ObservableList<TourLog> getObservableTourLogs() {
        return observableTourLogs;
    }

    public ChangeListener<TourLog> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    private void notifyListeners(TourLog newValue) {
        for (var listener : listeners) {
            listener.logChanged(newValue);
        }
    }

    public void addTourLog(TourLog tourLog) {
        tourListViewModel.addLog(tour, tourLog);
        observableTourLogs.add(tourLog);
    }

    public void updateTourLog(TourLog newLog, TourLog oldLog) {
        tourListViewModel.updateLog(tour, newLog, oldLog);
        observableTourLogs.set(observableTourLogs.indexOf(oldLog), newLog);
    }

    public void removeTourLog(TourLog tourLog) {
        tourListViewModel.removeLog(tour, tourLog);
        observableTourLogs.remove(tourLog);
    }
}
