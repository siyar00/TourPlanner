package at.technikum.planner.viewmodel;

import at.technikum.dal.dao.TourDao;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.dal.repository.TourLogsDaoRepository;
import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourLog;
import at.technikum.planner.transformer.RouteTypeTransformer;
import at.technikum.planner.transformer.TourDaoToTourTransformer;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import lombok.Data;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@Data
public class RouteMapViewModel {
    Logger LOGGER = Logger.getLogger(RouteMapViewModel.class.getName());

    private volatile boolean isInitValue = false;
    private final StringProperty startingAddress = new SimpleStringProperty();
    private final StringProperty destinationAddress = new SimpleStringProperty();
    private final StringProperty toll = new SimpleStringProperty();
    private final StringProperty highway = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty transportation = new SimpleStringProperty();
    private final StringProperty tourDescription = new SimpleStringProperty();
    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty childFriendly = new SimpleStringProperty();

    private final BooleanProperty tourDescriptionLabel = new SimpleBooleanProperty();
    private final BooleanProperty startingAddressLabel = new SimpleBooleanProperty();
    private final BooleanProperty destinationAddressLabel = new SimpleBooleanProperty();
    private final BooleanProperty tollLabel = new SimpleBooleanProperty();
    private final BooleanProperty highwayLabel = new SimpleBooleanProperty();
    private final BooleanProperty timeLabel = new SimpleBooleanProperty();
    private final BooleanProperty distanceLabel = new SimpleBooleanProperty();
    private final BooleanProperty transportationLabel = new SimpleBooleanProperty();
    private final BooleanProperty childFriendlyLabel = new SimpleBooleanProperty();
    private final BooleanProperty popularityLabel = new SimpleBooleanProperty();
    private final Property<Image> mapImageProperty = new SimpleObjectProperty<>();

    private final TourLogsDaoRepository logsRepository;
    private final TourDaoRepository tourDaoRepository;
    private Tour tour;
    private ResourceBundle bundle;

    public RouteMapViewModel (TourLogsDaoRepository tourLogsDaoRepository, TourDaoRepository tourDaoRepository, ResourceBundle bundle) {
        this.bundle = bundle;
        this.logsRepository = tourLogsDaoRepository;
        this.tourDaoRepository = tourDaoRepository;
        startingAddress.addListener((arg, oldVal, newVal) -> updateTour());
        destinationAddress.addListener((arg, oldVal, newVal) -> updateTour());
        toll.addListener((arg, oldVal, newVal) -> updateTour());
        highway.addListener((arg, oldVal, newVal) -> updateTour());
        time.addListener((arg, oldVal, newVal) -> updateTour());
        distance.addListener((arg, oldVal, newVal) -> updateTour());
        transportation.addListener((arg, oldVal, newVal) -> updateTour());
        tourDescription.addListener((arg, oldVal, newVal) -> updateTour());
        popularity.addListener((arg, oldVal, newVal) -> updateTour());
        childFriendly.addListener((arg, oldVal, newVal) -> updateTour());
        mapImageProperty.addListener((arg, oldVal, newVal) -> updateTour());
    }

    public void setTour(Tour tour) {
        isInitValue = true;
        if (tour == null) {
            startingAddressLabel.set(false);
            destinationAddressLabel.set(false);
            tollLabel.set(false);
            highwayLabel.set(false);
            timeLabel.set(false);
            distanceLabel.set(false);
            transportationLabel.set(false);
            tourDescriptionLabel.set(false);
            childFriendlyLabel.set(false);
            popularityLabel.set(false);
            tourDescription.set("");
            startingAddress.set("");
            destinationAddress.set("");
            toll.set("");
            highway.set("");
            time.set("");
            distance.set("");
            transportation.set("");
            mapImageProperty.setValue(null);
            childFriendly.set("");
            popularity.set("");
            LOGGER.fine("Tour set to null");
            return;
        }
        this.tour = tour;
        startingAddressLabel.set(true);
        destinationAddressLabel.set(true);
        tollLabel.set(true);
        highwayLabel.set(true);
        timeLabel.set(true);
        distanceLabel.set(true);
        transportationLabel.set(true);
        tourDescriptionLabel.set(true);
        childFriendlyLabel.set(true);
        popularityLabel.set(true);
        tourDescription.set(tour.getTourDescription());
        startingAddress.setValue(tour.getStartAddress());
        destinationAddress.setValue(tour.getEndAddress());
        toll.setValue(tour.getToll().equals("false") ? bundle.getString("No") : bundle.getString("Yes"));
        highway.setValue(tour.getHighway().equals("false") ? bundle.getString("No") : bundle.getString("Yes"));
        time.setValue(tour.getTime());
        distance.setValue(tour.getDistance().contains("km") ? tour.getDistance() : tour.getDistance() + " km");
        transportation.setValue(RouteTypeTransformer.getBundleFromRouteType(tour.getTransportation(), bundle));
        mapImageProperty.setValue(tour.getMap());
        LOGGER.fine("Tour set to " + tour.getName());
        isInitValue = false;
    }

    private void updateTour() {
        if( !isInitValue ) {
            tour.setStartAddress(startingAddress.get());
            tour.setEndAddress(destinationAddress.get());
            tour.setTransportation(RouteType.BICYCLE);
            tour.setMap(mapImageProperty.getValue());
            tour.setTourDescription(tourDescription.get());
            tour.setToll(toll.get());
            tour.setHighway(highway.get());
            tour.setTime(time.get());
            tour.setDistance(distance.get());
            tour.setTransportation(RouteTypeTransformer.getRouteTypeFromBundle(transportation.get(), bundle));
            LOGGER.info("Tour updated to " + tour.getName());
        }
    }

    public Property<Image> mapImageProperty() {
        return mapImageProperty;
    }

    public void logAdded(List<TourLog> tourLog) {
        List<TourDao> tourDaos = tourDaoRepository.findAll();
        if (tourDaos.isEmpty()) return;
        tourDaos.forEach(tourDao -> tourDao.setTourLogsDao(logsRepository.findByTourId(tourDao.getId())));
        List<Tour> allTours = tourDaos.stream().map(tourDao -> new TourDaoToTourTransformer().apply(tourDao)).toList();

        findPopularity(tourLog, allTours);
        findChildFriendliness(tourLog);
    }

    private void findChildFriendliness(List<TourLog> tourLogs) {
        if(tourLogs.isEmpty()) {
            childFriendly.set("");
            tourDaoRepository.updateChildFriendlyById("", tour.getId());
            return;
        }
        double childFriendliness = 0;
        double totalTime = 0;
        for (TourLog log : tourLogs) {
            String[] durationParts = log.getDuration().split("h|min");
            double hours = Double.parseDouble(durationParts[0]);
            double minutes = Double.parseDouble(durationParts[1]) + (hours * 60);
            totalTime += minutes;
        }
        double averageTotalTime = totalTime / tourLogs.size();
        for (TourLog log : tourLogs) {
            String[] distance = tour.getDistance().split("km");
            double km = Double.parseDouble(distance[0]);
            childFriendliness += ((averageTotalTime * 0.3) + (km * 0.2) + (log.getDifficulty()*5)) / 1000;
        }

        childFriendliness = childFriendliness / tourLogs.size();
        if(1-childFriendliness >= 0.9) {
            childFriendly.set(bundle.getString("VeryChildFriendly"));
        } else if (1-childFriendliness >= 0.75) {
            childFriendly.set(bundle.getString("ChildFriendly"));
        } else {
            childFriendly.set(bundle.getString("NotChildFriendly"));
        }
        tourDaoRepository.updateChildFriendlyById(childFriendly.get(), tour.getId());
        LOGGER.info("ChildFriendliness: " + childFriendliness);
    }

    private void findPopularity(List<TourLog> tourLog, List<Tour> allTours) {
        if(tourLog.isEmpty()) {
            popularity.set("");
            tourDaoRepository.updatePopularityById(null, tour.getId());
            return;
        }
        List<Integer> tourLogSizes = allTours.stream().map(tour -> tour.getTourLog().size()).toList();
        int max = tourLogSizes.stream().max(Integer::compareTo).orElse(0);
        int min = tourLogSizes.stream().min(Integer::compareTo).orElse(0);
        int range = max - min;
        if(tourLog.size()   >=  max - (range/4)){
            popularity.set(bundle.getString("VeryPopular"));
        } else if (tourLog.size() >= max - (range/2)) {
            popularity.set(bundle.getString("Popular"));
        } else if (tourLog.size() >= max - ((range/4)*3)) {
            popularity.set(bundle.getString("Average"));
        } else {
            popularity.set(bundle.getString("Unpopular"));
        }
        tour.setPopularity(popularity.get());
        tourDaoRepository.updatePopularityById(popularity.get(), tour.getId());
    }
}
