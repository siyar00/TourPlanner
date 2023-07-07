package at.technikum.planner.viewmodel;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.dao.Tour;
import at.technikum.dal.repository.TourRepository;
import at.technikum.dal.repository.UserRepository;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TourListViewModel {
    public interface SelectionChangedListener {
        void tourChanged(at.technikum.planner.model.Tour tour);
    }

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<at.technikum.planner.model.Tour> observableTours = FXCollections.observableArrayList();
    private final RouteServiceImpl routeService;

    public TourListViewModel(RouteServiceImpl routeService, UserRepository userRepository, TourRepository tourRepository) {
        this.routeService = routeService;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }

    public ObservableList<at.technikum.planner.model.Tour> getObservableTours() {
        return observableTours;
    }

    public ChangeListener<at.technikum.planner.model.Tour> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    private void notifyListeners(at.technikum.planner.model.Tour newValue) {
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

    public void addNewTour(at.technikum.planner.model.Tour tour) {
        ScheduledService<Void> task = new ScheduledService<>() {
            @Override
            protected Task<Void> createTask() {
                try {
                    String sessionId = routeService.getRoute(tour.getStartAddress().toString(), tour.getEndAddress().toString());
                    File file = new File("downloads/" + routeService.getImage(sessionId) + ".png");
                    tour.setMap(new Image(new FileInputStream(file)));
                    observableTours.add(tour);
                    System.out.println(userRepository.findAll());
                    System.out.println(tourRepository.findAll());
                    //tourRepository.save(Tour.builder().name(tour.getName()).build());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Platform.runLater(task::start);
    }

    public void updateTour(at.technikum.planner.model.Tour tour) {
        observableTours.set(observableTours.indexOf(tour), tour);
    }

    public void removeTour(at.technikum.planner.model.Tour tour) {
        observableTours.remove(tour);
    }


}
