package at.technikum.planner.view;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.repository.TourRepository;
import at.technikum.planner.view.dialog.TourLogsDialogController;
import at.technikum.planner.view.dialog.TourListDialogController;
import at.technikum.planner.viewmodel.*;
import at.technikum.planner.viewmodel.dialog.TourListDialogViewModel;
import at.technikum.planner.viewmodel.dialog.TourLogsDialogViewModel;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ResourceBundle;

public class ControllerFactory {
    private final ResourceBundle bundle;
    private final MainWindowViewModel mainWindowViewModel;
    private final RouteMapViewModel routeMapViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourLogsViewModel tourLogsViewModel;
    private final TourListDialogViewModel tourListDialogViewModel;
    private final TourLogsDialogViewModel tourLogsDialogViewModel;

    public ControllerFactory(ConfigurableApplicationContext applicationContext, ResourceBundle bundle) {
        this.bundle = bundle;
        searchBarViewModel = new SearchBarViewModel();
        routeMapViewModel = new RouteMapViewModel(bundle);
        RouteServiceImpl routeService = new RouteServiceImpl();
        TourRepository tourRepository = applicationContext.getBean(TourRepository.class);
        tourListViewModel = new TourListViewModel(routeService, tourRepository);
        tourLogsViewModel = new TourLogsViewModel(tourListViewModel);
        tourListDialogViewModel = new TourListDialogViewModel();
        tourLogsDialogViewModel= new TourLogsDialogViewModel();
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
        } else if (controllerClass == TourListDialogController.class) {
            return new TourListDialogController(tourListDialogViewModel);
        } else if (controllerClass == TourLogsDialogController.class) {
            return new TourLogsDialogController(tourLogsDialogViewModel);
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
