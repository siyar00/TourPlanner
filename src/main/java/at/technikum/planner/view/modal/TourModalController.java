package at.technikum.planner.view.modal;

import at.technikum.planner.MediaLibApplication;
import at.technikum.planner.model.Address;
import at.technikum.planner.model.Tour;
import at.technikum.planner.viewmodel.TourModalViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TourModalController implements Initializable {
    @FXML
    private ComboBox<String> transportComboBox;
    @FXML
    private TextField TourName;
    @FXML
    private TextField TourStartStreet;
    @FXML
    private TextField TourStartZip;
    @FXML
    private TextField TourStartCity;
    @FXML
    private TextField TourStartCountry;
    @FXML
    private TextField TourEndStreet;
    @FXML
    private TextField TourEndZip;
    @FXML
    private TextField TourEndCity;
    @FXML
    private TextField TourEndCountry;
    @FXML
    private Button okayButton;
    @FXML
    private Button exitButton;

    public TourModalController(TourModalViewModel tourModalViewModel) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transportComboBox.getItems().addAll(resourceBundle.getString("Car"), resourceBundle.getString("Pedestrian"));
    }

    public void enterKey(KeyEvent keyEvent) {
        if (TourName.getText().trim().isEmpty() && keyEvent.getCode().isDigitKey() && keyEvent.getCode().isLetterKey()) {
            okayButton.setDisable(true);
            return;
        }
        okayButton.setDisable(false);
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onOkayButton();
        } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            onCloseWindow();
        }
    }

    public void onOkayButton() {
        try {
            if (TourName.getText().trim().isEmpty()) {
                System.out.println("Please enter a name for the tour.");
            } else {
                Tour tour = Tour.builder().name(TourName.getText().trim())
                        .startAddress(Address.builder()
                                .street(TourStartStreet.getText().trim())
                                .zip(Integer.valueOf(TourStartZip.getText().trim()))
                                .city(TourStartCity.getText().trim())
                                .country(TourStartCountry.getText().trim()).build())
                        .endAddress(Address.builder()
                                .street(TourEndStreet.getText().trim())
                                .zip(Integer.valueOf(TourEndZip.getText().trim()))
                                .city(TourEndCity.getText().trim())
                                .country(TourEndCountry.getText().trim()).build())
                        .transportation(transportComboBox.getValue())
                        .route(null)
                        .misc(null)
                        .build();
                exitButton.getScene().getWindow().setUserData(tour);
                onCloseWindow();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid zip code.");
        }
    }

    public void onDeleteButton() {
        TourName.clear();
        TourStartStreet.clear();
        TourStartZip.clear();
        TourStartCity.clear();
        TourStartCountry.clear();
        TourEndStreet.clear();
        TourEndZip.clear();
        TourEndCity.clear();
        TourEndCountry.clear();
        transportComboBox.getSelectionModel().clearSelection();
        okayButton.setDisable(true);
    }

    public void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public Tour stage(TourModalController controller) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("TourModal.fxml")),
                ResourceBundle.getBundle("at.technikum.planner.view.gui_strings_de"), new JavaFXBuilderFactory());
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(Objects.requireNonNull(MediaLibApplication.class.getResourceAsStream("images/dora.png"))));
        stage.showAndWait();
        return (Tour) stage.getUserData();
    }
}
