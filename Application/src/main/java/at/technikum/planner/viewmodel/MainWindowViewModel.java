package at.technikum.planner.viewmodel;

import at.technikum.planner.model.Tour;

public class MainWindowViewModel {
    private final TourListViewModel tourListViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourLogsViewModel tourLogsViewModel;
    private final RouteMapViewModel routeMapViewModel;

    public MainWindowViewModel(TourListViewModel tourListViewModel, SearchBarViewModel searchBarViewModel, TourLogsViewModel tourLogsViewModel, RouteMapViewModel routeMapViewModel) {
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
        this.tourLogsViewModel = tourLogsViewModel;
        this.routeMapViewModel = routeMapViewModel;
        
        this.tourListViewModel.addSelectionChangedListener(this::selectTour);
    }

    private void selectTour(Tour selectedTour) {
        routeMapViewModel.setTour(selectedTour);
        tourLogsViewModel.setTourLog(selectedTour);
    }
}
