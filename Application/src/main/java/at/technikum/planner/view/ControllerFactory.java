package at.technikum.planner.view;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.repository.TourRepository;
import at.technikum.planner.view.dialog.TourDialogController;
import at.technikum.planner.view.modal.TourEditModalController;
import at.technikum.planner.view.modal.TourModalController;
import at.technikum.planner.viewmodel.*;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ResourceBundle;

public class ControllerFactory {
    private final ResourceBundle bundle;
    private final MainWindowViewModel mainWindowViewModel;
    private final RouteMapViewModel routeMapViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourLogsViewModel tourLogsViewModel;
    private final TourModalViewModel tourModalViewModel;
    private final TourEditModalViewModel tourEditModalViewModel;

    public ControllerFactory(ConfigurableApplicationContext applicationContext, ResourceBundle bundle) {
        this.bundle = bundle;
        searchBarViewModel = new SearchBarViewModel();
        routeMapViewModel = new RouteMapViewModel(bundle);
        RouteServiceImpl routeService = new RouteServiceImpl();
        TourRepository tourRepository = applicationContext.getBean(TourRepository.class);
        tourListViewModel = new TourListViewModel(routeService, tourRepository);
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
            return new TourListController(tourListViewModel, bundle);
        } else if (controllerClass == TourLogsController.class) {
            return new TourLogsController(tourLogsViewModel, bundle);
        } else if (controllerClass == TourModalController.class) {
            return new TourModalController(tourModalViewModel, bundle);
        } else if (controllerClass == TourEditModalController.class) {
            return new TourEditModalController(tourEditModalViewModel, bundle);
        } else if (controllerClass == TourDialogController.class) {
            return new TourDialogController();
        } else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }
    }


    //
    // Singleton-Pattern with lazy-binding
    //
    private static ControllerFactory instance;

    public static ControllerFactory getInstance(ConfigurableApplicationContext applicationContext, ResourceBundle bundle) {
        if (instance == null) instance = new ControllerFactory(applicationContext, bundle);
        return instance;
    }

}
