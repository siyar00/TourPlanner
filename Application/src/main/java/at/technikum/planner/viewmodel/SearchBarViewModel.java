package at.technikum.planner.viewmodel;

import at.technikum.dal.dao.TourDao;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.dal.repository.TourLogsDaoRepository;
import at.technikum.planner.model.Tour;
import at.technikum.planner.transformer.TourDaoToTourTransformer;
import javafx.scene.control.TextField;

import java.util.List;

public class SearchBarViewModel {
    private final TourListViewModel viewModel;
    private final TourLogsDaoRepository logsDaoRepository;
    private final TourDaoRepository tourRepository;

    public SearchBarViewModel(TourListViewModel tourListViewModel, TourLogsDaoRepository tourLogsDaoRepository, TourDaoRepository tourDaoRepository) {
        this.viewModel = tourListViewModel;
        this.logsDaoRepository = tourLogsDaoRepository;
        this.tourRepository = tourDaoRepository;
    }

    public void search(TextField text) {
        List<TourDao> tourDaos = tourRepository.findTourIdsByKeyword(text.getText());
        tourDaos.forEach(tourDao -> tourDao.setTourLogsDao(logsDaoRepository.findByTourId(tourDao.getId())));
        viewModel.getObservableTours().clear();
        if(tourDaos.isEmpty()) return;
        List<Tour> tours = tourDaos.stream().map(tourDao -> new TourDaoToTourTransformer().apply(tourDao)).toList();
        viewModel.getObservableTours().addAll(tours);
    }
}