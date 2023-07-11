package at.technikum.planner.view;

import at.technikum.bl.RouteServiceImpl;
import at.technikum.bl.WeatherServiceImpl;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.dal.repository.TourLogsDaoRepository;
import at.technikum.planner.view.dialog.TourListDialogController;
import at.technikum.planner.view.dialog.TourLogsDialogController;
import at.technikum.planner.viewmodel.*;
import at.technikum.planner.viewmodel.dialog.TourListDialogViewModel;
import at.technikum.planner.viewmodel.dialog.TourLogsDialogViewModel;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ControllerFactory {
    Logger LOGGER = Logger.getLogger(ControllerFactory.class.getName());
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
        RouteServiceImpl routeService = new RouteServiceImpl();
        WeatherServiceImpl weatherService = new WeatherServiceImpl();
        TourDaoRepository tourDaoRepository = applicationContext.getBean(TourDaoRepository.class);
        TourLogsDaoRepository tourLogsDaoRepository = applicationContext.getBean(TourLogsDaoRepository.class);
        routeMapViewModel = new RouteMapViewModel(bundle);
        tourListViewModel = new TourListViewModel(routeService, tourDaoRepository, tourLogsDaoRepository);
        tourLogsViewModel = new TourLogsViewModel(tourListViewModel, tourLogsDaoRepository, tourDaoRepository, weatherService);
        searchBarViewModel = new SearchBarViewModel(tourListViewModel, tourLogsDaoRepository, tourDaoRepository, bundle);
        tourListDialogViewModel = new TourListDialogViewModel();
        tourLogsDialogViewModel= new TourLogsDialogViewModel();
        mainWindowViewModel = new MainWindowViewModel(tourListViewModel, searchBarViewModel, tourLogsViewModel, routeMapViewModel);
    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
        if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel, tourListViewModel);
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
            LOGGER.warning("Unknown controller class: " + controllerClass);
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
