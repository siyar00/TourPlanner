package at.technikum.planner.viewmodel;

import at.technikum.bl.WeatherServiceImpl;
import at.technikum.dal.dto.CoordinatesDto;
import at.technikum.dal.dto.WeatherResponse;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.dal.repository.TourLogsDaoRepository;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class TourLogsViewModel {
    public interface AddedTourLogListener {
        void logAdded(List<TourLog> log);
    }

    Logger LOGGER = Logger.getLogger(TourLogsViewModel.class.getName());

    private final List<AddedTourLogListener> listeners = new ArrayList<>();
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
    private final WeatherServiceImpl weatherService;
    private Tour tour;

    public TourLogsViewModel(TourListViewModel viewModel, TourLogsDaoRepository logsDaoRepository, TourDaoRepository tourDaoRepository, WeatherServiceImpl weatherService) {
        this.viewModel = viewModel;
        this.logsDaoRepository = logsDaoRepository;
        this.tourRepository = tourDaoRepository;
        this.weatherService = weatherService;
    }

    public void setTourLog(Tour tour) {
        observableTourLogs.clear();
        if (tour == null) return;
        this.tour = tour;
        List<TourLog> tourLogs = tour.getTourLog();
        if (tourLogs.isEmpty()) return;
        observableTourLogs.addAll(tourLogs);
    }

    public ObservableList<TourLog> getObservableTourLogs() {
        return observableTourLogs;
    }

    public ListChangeListener<TourLog> getChangeListener() {
        return this::notifyListeners;
    }

    private void notifyListeners(ListChangeListener.Change<? extends TourLog> newValue) {
        for (var listener : listeners) {
            if(newValue.getList().isEmpty()) return;
            List<TourLog> test = new ArrayList<>(newValue.getList());
            listener.logAdded(test);
            LOGGER.info("Notify listener: " + listener.getClass().getName());
        }
    }

    public void addListener(AddedTourLogListener listener) {
        listeners.add(listener);
    }

    @SuppressWarnings("unused")
    public void removeListener(AddedTourLogListener listener) {
        listeners.remove(listener);
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
            tourLog.setTourId(tour.getId());
            viewModel.addLog(tour, tourLog);
            observableTourLogs.add(tourLog);
            LOGGER.log(Level.FINEST, "Insert TourLog successful");
        });
        task.setOnFailed(event -> LOGGER.warning("Insert TourLog failed: " + event.getSource().getException().getMessage()));
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
        task.setOnFailed(event -> LOGGER.warning("Update TourLog failed: " + event.getSource().getException().getMessage()));
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
        task.setOnFailed(event -> LOGGER.warning("Delete TourLog failed: " + event.getSource().getException().getMessage()));
        new Thread(task).start();
    }

    public List<WeatherResponse> getWeatherReport() {
        WeatherResponse responseStart = weatherService.getWeatherData(new CoordinatesDto(tour.getStartLat(), tour.getStartLng()));
        WeatherResponse responseDestination = weatherService.getWeatherData(new CoordinatesDto(tour.getEndLat(), tour.getEndLng()));
        return List.of(responseStart, responseDestination);
    }
}
