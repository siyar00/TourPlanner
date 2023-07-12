//package at.technikum.planner.functionality;
//
//import at.technikum.planner.FXMLDependencyInjection;
//import at.technikum.planner.TourPlannerApplication;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import jfxtras.styles.jmetro.JMetro;
//import jfxtras.styles.jmetro.Style;
//import org.apache.commons.compress.compressors.lz77support.Parameters;
//import org.junit.jupiter.api.*;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import java.awt.*;
//import java.util.Collections;
//import java.util.Locale;
//
//import static org.mockito.Mockito.*;
//
//public class TourPlannerTest {
//
//    private TourPlannerApplication application;
//    private ConfigurableApplicationContext applicationContext;
//
//    @BeforeEach
//    public void setUp() {
//        application = new TourPlannerApplication();
//        applicationContext = mock(ConfigurableApplicationContext.class);
//        application.applicationContext = applicationContext;
//    }
//
//    @Test
//    public void testInit() {
//        // Arrange
//        SpringApplicationBuilder builder = mock(SpringApplicationBuilder.class);
//        when(builder.run()).thenReturn(mock(ConfigurableApplicationContext.class));
//
//        // Create a mock Parameters object and set up its behavior
//        Parameters parameters = mock(Parameters.class);
//        when(parameters.getRaw()).thenReturn(Collections.emptyList());
//
//        // Set the mock Parameters object in the Application instance
//        TourPlannerApplication application = new TourPlannerApplication();
//        application.setParameters(parameters);
//
//        // Act
//        application.init();
//
//        // Assert
//        verify(builder).run();
//        verify(applicationContext).stop();
//    }
//
//
//
//    @Test
//    public void testStop() throws Exception {
//        // Act
//        application.stop();
//
//        // Assert
//        verify(applicationContext).stop();
//    }
//
//    @Test
//    public void testStart() throws Exception {
//        // Arrange
//        Stage stage = mock(Stage.class);
//        Parent root = mock(Parent.class);
//        Scene scene = mock(Scene.class);
//        JMetro jMetro = mock(JMetro.class);
//
//        TourPlannerApplication application = new TourPlannerApplication();
//        application.applicationContext = mock(ConfigurableApplicationContext.class);
//
//        when(FXMLDependencyInjection.load(anyString(), any(Locale.class), any(ConfigurableApplicationContext.class))).thenReturn(root);
//
//        // Act
//        application.start(stage);
//
//        // Assert
//        verify(stage).setScene(scene);
//        verify(stage).setTitle("Dora the Explorer");
//        verify(stage, times(2)).setScene(scene);
//        verify(stage).show();
//        verify(application.applicationContext).stop();
//        verify(jMetro).setScene(scene);
//        verify(scene).setRoot(root);
//    }
//
//}