package at.technikum.planner;

import at.technikum.planner.config.ApplicationConfigProperties;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.h2.store.fs.FileUtils;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfigProperties.class)
public class TourPlannerApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Logger LOGGER = Logger.getLogger(TourPlannerApplication.class.getName());
    ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TourPlannerApplication.class);
        builder.application().setWebApplicationType(WebApplicationType.NONE);
        List<String> args = getParameters().getRaw();
        applicationContext = builder.run(args.toArray(String[]::new));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.stop();
        LOGGER.info("Application stopped");
    }

    @Override
    public void start(Stage stage) throws Exception {
        FileUtils.createDirectories(System.getProperty("user.dir").concat("/downloads"));
        FileUtils.createDirectories(System.getProperty("user.dir").concat("/logs"));
        FileUtils.createDirectories(System.getProperty("user.dir").concat("/PDF"));

        Parent root = FXMLDependencyInjection.load("MainWindow.fxml", Locale.forLanguageTag(System.getProperties().getProperty("user.language")), applicationContext);
        Scene scene = new Scene(root);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        stage.setScene(jMetro.getScene());
        stage.setTitle("Dora the Explorer");
        stage.getIcons().add(new Image(Objects.requireNonNull(TourPlannerApplication.class.getResourceAsStream("images/dora.png"))));
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        LOGGER.info("Application started");
    }
}
