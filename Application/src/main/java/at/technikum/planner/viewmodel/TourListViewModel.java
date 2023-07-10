package at.technikum.planner.viewmodel;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.dao.TourDao;
import at.technikum.dal.dto.RouteDto;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.dal.repository.TourLogsDaoRepository;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourLog;
import at.technikum.planner.transformer.TourDaoToTourTransformer;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class TourListViewModel {

    public interface SelectionChangedListener {
        void tourChanged(Tour tour);
    }

    private final TourDaoRepository tourDaoRepository;
    private final TourLogsDaoRepository tourLogsDaoRepository;
    private final RouteServiceImpl routeService;
    private final List<SelectionChangedListener> listeners = new ArrayList<>();
    private final ObservableList<Tour> observableTours = FXCollections.observableArrayList();

    public TourListViewModel(RouteServiceImpl routeService, TourDaoRepository tourDaoRepository, TourLogsDaoRepository tourLogsDaoRepository) {
        this.routeService = routeService;
        this.tourDaoRepository = tourDaoRepository;
        this.tourLogsDaoRepository = tourLogsDaoRepository;
    }

    public ObservableList<Tour> getObservableTours() {
        List<TourDao> tourDaoList = tourDaoRepository.findAll();
        tourDaoList.forEach(tourDao -> tourDao.setTourLogsDao(tourLogsDaoRepository.findByTourId(tourDao.getId())));
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

    @SuppressWarnings("unused")
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
                    tourDaoRepository.save(getTourDao(tour, route, path));
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
                    tourDaoRepository.updateTourDaoByName(tourDao.getName(), tourDao.getStart(), tourDao.getDestination(), tourDao.getDistance(), tourDao.getTime(), tourDao.getHasTollRoad(), tourDao.getHasHighway(),tourDao.getTransportation(),  tourDao.getImage(),  tourDao.getDescription(), oldTour.getName());
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
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                tourDaoRepository.deleteByName(tour.getName());
                return null;
            }
        };
        task.setOnSucceeded(event -> observableTours.remove(tour));
        task.setOnFailed(event -> System.out.println("Failed"+ event.getSource().getException()));
        new Thread(task).start();
    }

    public void addLog(Tour tour, TourLog log) {
        observableTours.get(observableTours.indexOf(tour)).getTourLog().add(log);
    }

    public void updateLog(Tour tour, TourLog newLog, TourLog oldLog) {
        int index = observableTours.indexOf(tour);
        observableTours.get(index).getTourLog().set(observableTours.get(index).getTourLog().indexOf(oldLog), newLog);
    }

    public void removeLog(Tour tour, TourLog tourLog) {
        observableTours.get(observableTours.indexOf(tour)).getTourLog().remove(tourLog);
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