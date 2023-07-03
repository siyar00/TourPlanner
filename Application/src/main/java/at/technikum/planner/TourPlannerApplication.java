package at.technikum.planner;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Objects;

import static java.util.Locale.GERMAN;

@SpringBootApplication
public class TourPlannerApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TourPlannerApplication.class);
        builder.application().setWebApplicationType(WebApplicationType.NONE);
        List<String> args = getParameters().getRaw(); // passed from command line
        applicationContext = builder.run(args.toArray(String[]::new));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.stop();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLDependencyInjection.load("MainWindow.fxml", GERMAN, applicationContext);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheets/style.css")).toExternalForm());
        stage.setTitle("Dora the Explorer");
        stage.getIcons().add(new Image(Objects.requireNonNull(TourPlannerApplication.class.getResourceAsStream("images/dora.png"))));
        stage.setScene(scene);
        stage.show();
    }
}
