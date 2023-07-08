package at.technikum.planner.viewmodel;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.dto.RouteDto;
import at.technikum.dal.repository.TourRepository;
import at.technikum.planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {

    public interface SelectionChangedListener {
        void tourChanged(Tour tour);
    }

    private final TourRepository tourRepository;
    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<Tour> observableTours = FXCollections.observableArrayList();
    private final RouteServiceImpl routeService;

    public TourListViewModel(RouteServiceImpl routeService, TourRepository tourRepository) {
        this.routeService = routeService;
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
                    RouteDto route = routeService.getRoute(tour.getStartAddress(), tour.getEndAddress(), tour.getTransportation().getType());
                    File file = new File("downloads/" + routeService.getImage(route.getSessionId(), route.getBoundingBox()) + ".png");
                    tour.setMap(new Image(new FileInputStream(file)));
                    tour.setDistance(route.getDistance());
                    tour.setTime(route.getFormattedTime());
                    tour.setHighway(route.getHasHighway());
                    tour.setToll(route.getHasTollRoad());
                    tour.setStartAddress(String.valueOf(route.getLocations().get(0)));
                    tour.setEndAddress(String.valueOf(route.getLocations().get(1)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            observableTours.add(tour);
            //tourRepository.save(tour);
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