package at.technikum.planner.viewmodel;

import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.dal.repository.TourLogsDaoRepository;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourLog;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
    private final TourListViewModel viewModel;
    private final TourLogsDaoRepository logsDaoRepository;
    private final TourDaoRepository tourRepository;
    private Tour tour;

    public TourLogsViewModel(TourListViewModel viewModel, TourLogsDaoRepository logsDaoRepository, TourDaoRepository tourDaoRepository) {
        this.viewModel = viewModel;
        this.logsDaoRepository = logsDaoRepository;
        this.tourRepository = tourDaoRepository;
    }

    public void setTourLog(Tour tour) {
        observableTourLogs.clear();
        if(tour == null) return;
        this.tour = tour;
        List<TourLog> tourLogs = tour.getTourLog();
        if (tourLogs.isEmpty()) return;
        observableTourLogs.addAll(tourLogs);
    }

    public ObservableList<TourLog> getObservableTourLogs() {
        return observableTourLogs;
    }

    @SuppressWarnings("unused")
    public ChangeListener<TourLog> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    private void notifyListeners(TourLog newValue) {
        for (var listener : listeners) {
            listener.logChanged(newValue);
        }
    }

    public void addTourLog(TourLog tourLog) {
        Task<Long> task = new Task<>() {
            @Override
            protected Long call() {
                return logsDaoRepository.insertTourLog(tour.getId(), tourLog.getDate(), tourLog.getDuration(), tourLog.getComment(), tourLog.getDifficulty(), tourLog.getRating()).orElse(0L);
            }
        };
        task.setOnSucceeded(event -> {
            tourLog.setLogId(task.getValue());
            viewModel.addLog(tour, tourLog);
            observableTourLogs.add(tourLog);
        });
        task.setOnFailed(event -> System.out.println("Add TourLog failed: " + event.getSource().getException().getMessage()));
        new Thread(task).start();
    }

    public void updateTourLog(TourLog newLog, TourLog oldLog) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                logsDaoRepository.updateById(newLog.getDate(), newLog.getDuration(), newLog.getComment(), newLog.getDifficulty(), newLog.getRating(), oldLog.getLogId());
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            viewModel.updateLog(tour, newLog, oldLog);
            observableTourLogs.set(observableTourLogs.indexOf(oldLog), newLog);
        });
        task.setOnFailed(event -> System.out.println("Update TourLog failed: " + event.getSource().getException().getMessage()));
        new Thread(task).start();
    }

    public void removeTourLog(TourLog tourLog) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                logsDaoRepository.deleteById(tourLog.getLogId());
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            viewModel.removeLog(tour, tourLog);
            observableTourLogs.remove(tourLog);
        });
        task.setOnFailed(event -> System.out.println("Delete TourLog failed: " + event.getSource().getException().getMessage()));
        new Thread(task).start();
    }
}
