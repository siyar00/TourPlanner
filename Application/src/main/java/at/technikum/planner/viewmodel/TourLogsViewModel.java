package at.technikum.planner.viewmodel;

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
    private final PropertyValueFactory<TourLog, Integer> idColumn = new PropertyValueFactory<>("id");
    private int currentId = 1;

    public TourLogsViewModel() {

    }

    public void setTourLog(List<TourLog> tourLog) {
        if(tourLog == null) return;

        observableTourLogs.clear();
        observableTourLogs.addAll(tourLog);
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
        tourLog.setId(currentId);
        currentId++;
        observableTourLogs.add(tourLog);
    }

    public void updateTourLog(TourLog tourLog) {
        int index = observableTourLogs.indexOf(tourLog);
        if (index != -1) {
            observableTourLogs.set(index, tourLog);
        }
    }

    public void removeTourLog(TourLog tourLog) {
        observableTourLogs.remove(tourLog);
    }
}
