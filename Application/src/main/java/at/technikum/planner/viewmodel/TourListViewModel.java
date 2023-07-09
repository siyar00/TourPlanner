package at.technikum.planner.viewmodel;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.dao.TourDao;
import at.technikum.dal.dto.RouteDto;
import at.technikum.dal.repository.TourRepository;
import at.technikum.planner.model.TourLog;
import at.technikum.planner.transformer.TourDaoToTourTransformer;
import at.technikum.planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class TourListViewModel {

    public interface SelectionChangedListener {
        void tourChanged(Tour tour);
    }

    private final TourRepository tourRepository;
    private final RouteServiceImpl routeService;
    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<Tour> observableTours = FXCollections.observableArrayList();

    public TourListViewModel(RouteServiceImpl routeService, TourRepository tourRepository) {
        this.routeService = routeService;
        this.tourRepository = tourRepository;
    }

    public ObservableList<Tour> getObservableTours() {
        List<TourDao> tourDaoList = tourRepository.findAll();
        List<Tour> tourList = tourDaoList.stream().map(t -> new TourDaoToTourTransformer().apply(t)).toList();
        observableTours.addAll(tourList);
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
                    String path = "downloads/" + routeService.getImage(route.getSessionId(), route.getBoundingBox()) + ".png";
                    tourRepository.save(getTourDao(tour, route, path));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> observableTours.add(tour));
        task.setOnFailed(event -> System.out.println("Failed"+ event.getSource().getException()));
        new Thread(task).start();
    }

    public void updateTour(Tour oldTour, Tour tour) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    RouteDto route = routeService.getRoute(tour.getStartAddress(), tour.getEndAddress(), tour.getTransportation().getType());
                    String path = "downloads/" + routeService.getImage(route.getSessionId(), route.getBoundingBox()) + ".png";
                    TourDao tourDao = getTourDao(tour, route, path);
                    tourRepository.updateTourDaoByName(tourDao.getName(), tourDao.getStart(), tourDao.getDestination(), tourDao.getDistance(), tourDao.getTime(), tourDao.getHasTollRoad(), tourDao.getHasHighway(),tourDao.getTransportation(),  tourDao.getImage(),  tourDao.getDescription(), oldTour.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> observableTours.set(observableTours.indexOf(oldTour), tour));
        task.setOnFailed(event -> System.out.println("Failed"+ event.getSource().getException()));
        new Thread(task).start();
    }

    public void removeTour(Tour tour) {
        observableTours.remove(tour);
        tourRepository.deleteByName(tour.getName());
    }

    public void addLog(Tour tour, TourLog log) {
        int index = observableTours.indexOf(tour);
        List<TourLog> list = observableTours.get(index).getTourLog();
        list.add(log);
        observableTours.get(index).setTourLog(list);
    }

    public void updateLog(Tour tour, TourLog newLog, TourLog oldLog) {
        int index = observableTours.indexOf(tour);
        List<TourLog> list = observableTours.get(index).getTourLog();
        list.set(list.indexOf(oldLog), newLog);
        observableTours.get(index).setTourLog(list);
    }

    public void removeLog(Tour tour, TourLog tourLog) {
        int index = observableTours.indexOf(tour);
        List<TourLog> list = observableTours.get(index).getTourLog();
        list.remove(tourLog);
        observableTours.get(index).setTourLog(list);
    }

    @SuppressWarnings("resource")
    private TourDao getTourDao(Tour tour, RouteDto route, String path) throws IOException{
        tour.setMap(new Image(new ByteArrayInputStream(new FileInputStream(path).readAllBytes())));
        tour.setDistance(route.getDistance());
        tour.setTime(route.getFormattedTime());
        tour.setHighway(route.getHasHighway());
        tour.setToll(route.getHasTollRoad());
        tour.setStartAddress(String.valueOf(route.getLocations().get(0)));
        tour.setEndAddress(String.valueOf(route.getLocations().get(1)));
        return TourDao.builder().name(tour.getName())
                .description(tour.getTourDescription())
                .start(tour.getStartAddress())
                .destination(tour.getEndAddress())
                .distance(tour.getDistance())
                .time(tour.getTime())
                .transportation(tour.getTransportation().getType())
                .hasHighway(tour.getHighway())
                .hasTollRoad(tour.getToll())
                .image(new FileInputStream(path).readAllBytes()).build();
    }

}