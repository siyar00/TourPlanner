package at.technikum.planner.view;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.planner.view.modal.TourEditModalController;
import at.technikum.planner.view.modal.TourModalController;
import at.technikum.planner.viewmodel.*;
import org.springframework.context.ConfigurableApplicationContext;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final RouteMapViewModel routeMapViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourLogsViewModel tourLogsViewModel;
    private final TourModalViewModel tourModalViewModel;
    private final TourEditModalViewModel tourEditModalViewModel;

    public ControllerFactory(ConfigurableApplicationContext applicationContext) {
        searchBarViewModel = new SearchBarViewModel();
        routeMapViewModel = new RouteMapViewModel();
        RouteServiceImpl routeService = new RouteServiceImpl();
        tourListViewModel = new TourListViewModel(routeService);
        tourLogsViewModel = new TourLogsViewModel();
        tourModalViewModel = new TourModalViewModel();
        tourEditModalViewModel = new TourEditModalViewModel();
        mainWindowViewModel = new MainWindowViewModel(tourListViewModel, searchBarViewModel, tourLogsViewModel, routeMapViewModel);
    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
        if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel);
        } else if (controllerClass == RouteMapController.class) {
            return new RouteMapController(routeMapViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(tourListViewModel);
        } else if (controllerClass == TourLogsController.class) {
            return new TourLogsController(tourLogsViewModel);
        } else if (controllerClass == TourModalController.class) {
            return new TourModalController(tourModalViewModel);
        } else if (controllerClass == TourEditModalController.class) {
            return new TourEditModalController(tourEditModalViewModel);
        } else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }
    }


    //
    // Singleton-Pattern with lazy-binding
    //
    private static ControllerFactory instance;

    public static ControllerFactory getInstance(ConfigurableApplicationContext applicationContext) {
        if (instance == null) instance = new ControllerFactory(applicationContext);
        return instance;
    }

}
