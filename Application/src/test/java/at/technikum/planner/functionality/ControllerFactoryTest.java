package at.technikum.planner.functionality;

import at.technikum.planner.view.*;
import at.technikum.planner.view.dialog.TourListDialogController;
import at.technikum.planner.view.dialog.TourLogsDialogController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerFactoryTest {
    @Mock
    private ConfigurableApplicationContext applicationContext;

    @Mock
    private ResourceBundle bundle;

    private ControllerFactory factory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        factory = new ControllerFactory(applicationContext, bundle);
    }

    @Test
    void createMainWindowController() {
        Object controller = factory.create(MainWindowController.class);
        assertNotNull(controller);
        assertTrue(controller instanceof MainWindowController);
    }

    @Test
    void createRouteMapController() {
        Object controller = factory.create(RouteMapController.class);
        assertNotNull(controller);
        assertTrue(controller instanceof RouteMapController);
    }

    @Test
    void createSearchBarController() {
        Object controller = factory.create(SearchBarController.class);
        assertNotNull(controller);
        assertTrue(controller instanceof SearchBarController);
    }

    @Test
    void createTourLogsController() {
        Object controller = factory.create(TourLogsController.class);
        assertNotNull(controller);
        assertTrue(controller instanceof TourLogsController);
    }

    @Test
    void createTourListDialogController() {
        Object controller = factory.create(TourListDialogController.class);
        assertNotNull(controller);
        assertTrue(controller instanceof TourListDialogController);
    }

    @Test
    void createTourLogsDialogController() {
        Object controller = factory.create(TourLogsDialogController.class);
        assertNotNull(controller);
        assertTrue(controller instanceof TourLogsDialogController);
    }

    @Test
    void createUnknownController() {
        assertThrows(IllegalArgumentException.class, () -> factory.create(Object.class));
    }
}
