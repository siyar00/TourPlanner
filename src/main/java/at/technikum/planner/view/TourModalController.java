package at.technikum.planner.view;

import at.technikum.planner.MediaLibApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TourModalController {
    @FXML private Button exitButton;
    @FXML private TextField TourName;

    public void enterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            okayButton();
        }
    }

    public void okayButton() {
        if(TourName.getCharacters().isEmpty()) {
            System.out.println("Please enter a name for the tour.");
        } else {
            closeWindow();
        }
    }

    public void deleteButton() {
        TourName.clear();
    }

    public void closeWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setUserData(TourName.getText());
        stage.close();
    }

    public String stage() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TourModal.fxml")),
                ResourceBundle.getBundle("at.technikum.planner.view.gui_strings_de"), new JavaFXBuilderFactory()));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(Objects.requireNonNull(MediaLibApplication.class.getResourceAsStream("images/dora.png"))));
        stage.showAndWait();
        return (String) stage.getUserData();
    }
}
