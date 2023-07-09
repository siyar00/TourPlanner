package at.technikum.planner.viewmodel;

import at.technikum.planner.model.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourLogsViewModel {
    private final ObservableList<TourLog> observableTourLogs = FXCollections.observableArrayList();
    private int currentId = 1;

    public ObservableList<TourLog> getObservableTourLogs() {
        return observableTourLogs;
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
