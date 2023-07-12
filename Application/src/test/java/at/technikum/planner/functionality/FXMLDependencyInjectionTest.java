package at.technikum.planner.functionality;

import at.technikum.planner.FXMLDependencyInjection;
import at.technikum.planner.config.ApplicationConfigProperties;
import at.technikum.planner.view.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FXMLDependencyInjectionTest {
    @Mock
    private ConfigurableApplicationContext context;

    private ResourceBundle bundle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bundle = ResourceBundle.getBundle("at.technikum.planner.gui_strings", Locale.GERMAN);
        when(context.getBean(ApplicationConfigProperties.class)).thenReturn(new ApplicationConfigProperties());
    }

//    @Test
//    void loadFXMLFile() throws IOException {
//        Parent expectedParent = new ParentStub();
//
//        FXMLLoader loaderMock = mock(FXMLLoader.class);
//        when(loaderMock.load()).thenReturn(expectedParent);
//
//        FXMLDependencyInjection fxmlDependencyInjectionMock = mock(FXMLDependencyInjection.class);
//        when(fxmlDependencyInjectionMock.getLoader("MainWindow.fxml", Locale.GERMAN, context)).thenReturn(loaderMock);
//
//        Parent actualParent = FXMLDependencyInjection.load("MainWindow.fxml", Locale.GERMAN, context);
//
//        assertNotNull(actualParent);
//        assertSame(expectedParent, actualParent);
//    }


    @Test
    void getLoader() {
        FXMLLoader loader = FXMLDependencyInjection.getLoader("MainWindow.fxml", Locale.GERMAN, context);

        assertNotNull(loader);
        assertNotNull(loader.getResources());
        assertNotNull(loader.getControllerFactory());
        assertNotNull(loader.getBuilderFactory());

        // Set the bundle directly on the FXMLLoader
        loader.setResources(bundle);

        //assertSame(bundle, loader.getResources().getBundle());
        assertNotNull(loader.getControllerFactory().call(MainWindowController.class));
    }

    // Helper class for testing
    private static class ParentStub extends Parent {
    }
}
