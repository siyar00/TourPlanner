package at.technikum.planner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;
import java.util.ResourceBundle;

@SpringBootApplication
public class MediaLibApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/MainWindow.fxml")),
                ResourceBundle.getBundle("at.technikum.planner.view.gui_strings_de"), new JavaFXBuilderFactory());
        stage.setTitle("Dora the Explorer");
        stage.getIcons().add(new Image(Objects.requireNonNull(MediaLibApplication.class.getResourceAsStream("images/dora.png"))));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
