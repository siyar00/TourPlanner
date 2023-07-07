package at.technikum.planner.viewmodel;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.repository.TourRepository;
import at.technikum.dal.repository.UserRepository;
import at.technikum.planner.model.Tour;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TourListViewModel {
    public interface SelectionChangedListener {
        void tourChanged(Tour tour);
    }

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<Tour> observableTours = FXCollections.observableArrayList();
    private final RouteServiceImpl routeService;

    public TourListViewModel(RouteServiceImpl routeService, UserRepository userRepository, TourRepository tourRepository) {
        this.routeService = routeService;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
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
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    String sessionId = routeService.getRoute(tour.getStartAddress().toString(), tour.getEndAddress().toString());
                    File file = new File("downloads/" + routeService.getImage(sessionId) + ".png");
                    tour.setMap(new Image(new FileInputStream(file)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            observableTours.add(tour);
            System.out.println(userRepository.findAll());
            System.out.println(tourRepository.findAll());
        });
        new Thread(task).start();
    }

    public void updateTour(Tour tour) {
        observableTours.set(observableTours.indexOf(tour), tour);
    }

    public void removeTour(Tour tour) {
        observableTours.remove(tour);
    }

}