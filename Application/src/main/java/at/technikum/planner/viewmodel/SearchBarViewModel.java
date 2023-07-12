package at.technikum.planner.viewmodel;

import at.technikum.dal.dao.TourDao;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.dal.repository.TourLogsDaoRepository;
import at.technikum.planner.model.Tour;
import at.technikum.planner.transformer.RouteTypeTransformer;
import at.technikum.planner.transformer.TourDaoToTourTransformer;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SearchBarViewModel {
    Logger LOGGER = Logger.getLogger(SearchBarViewModel.class.getName());
    private final TourListViewModel viewModel;
    private final TourLogsDaoRepository logsDaoRepository;
    private final TourDaoRepository tourRepository;
    private final ResourceBundle bundle;

    public SearchBarViewModel(TourListViewModel tourListViewModel, TourLogsDaoRepository tourLogsDaoRepository, TourDaoRepository tourDaoRepository, ResourceBundle bundle) {
        this.viewModel = tourListViewModel;
        this.logsDaoRepository = tourLogsDaoRepository;
        this.tourRepository = tourDaoRepository;
        this.bundle = bundle;
    }

    public void search(TextField text) {
        String filter = text.getText().toLowerCase();
        List<TourDao> tourDaos = tourRepository.findAll();
        viewModel.getObservableTours().clear();
        if (tourDaos.isEmpty()) return;
        tourDaos.forEach(tourDao -> tourDao.setTourLogsDao(logsDaoRepository.findByTourId(tourDao.getId())));
        List<Tour> filteredTours = tourDaos.stream()
                .map(tourDao -> new TourDaoToTourTransformer().apply(tourDao))
                .filter(tour -> {
                    if (filter.isBlank()) return true;
                    String tourName = tour.getName().toLowerCase();
                    String tourDescription = tour.getTourDescription().toLowerCase();
                    String startAddress = tour.getStartAddress().toLowerCase();
                    String endAddress = tour.getEndAddress().toLowerCase();
                    String transportation = RouteTypeTransformer.getBundleFromRouteType(tour.getTransportation(), bundle).toLowerCase();
                    String time = tour.getTime().toLowerCase();
                    String distance = tour.getDistance().toLowerCase();
                    String popularity = tour.getPopularity().toLowerCase();
                    String childFriendly = tour.getChildFriendly().toLowerCase();
                    return popularity.contains(filter) || childFriendly.contains(filter) || tourName.contains(filter) || tourDescription.contains(filter) || startAddress.contains(filter) || endAddress.contains(filter) || transportation.contains(filter) || time.contains(filter) || distance.contains(filter)
                            || tour.getTourLog().stream().anyMatch(tourLog -> {
                        String date = tourLog.getDate().toLowerCase();
                        String duration = tourLog.getDuration().toLowerCase();
                        String comment = tourLog.getComment().toLowerCase();
                        String difficulty = tourLog.getDifficulty().toString().toLowerCase();
                        String rating = tourLog.getRating().toString().toLowerCase();
                        return date.contains(filter) || duration.contains(filter) || comment.contains(filter)
                                || difficulty.contains(filter) || rating.contains(filter);
                    });
                }).toList();
        viewModel.getObservableTours().addAll(filteredTours);
        LOGGER.fine("Search successfully executed.");
    }
}