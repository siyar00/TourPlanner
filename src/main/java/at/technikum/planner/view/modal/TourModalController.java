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
import javafx.scene.control.Alert;
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
    private ComboBox<String> tourStartCountryComboBox;
    @FXML
    private ComboBox<String> tourEndCountryComboBox;
    @FXML
    private TextField TourName;
    @FXML
    private TextField TourStartStreet;
    @FXML
    private TextField TourStartZip;
    @FXML
    private TextField TourStartCity;
    @FXML
    private TextField TourEndStreet;
    @FXML
    private TextField TourEndZip;
    @FXML
    private TextField TourEndCity;
    @FXML
    private Button okayButton;
    @FXML
    private Button exitButton;
    private ResourceBundle resourceBundle;

    public TourModalController(TourModalViewModel tourModalViewModel) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        transportComboBox.getItems().addAll(resourceBundle.getString("Car"), resourceBundle.getString("Pedestrian"));
        tourStartCountryComboBox.getItems().addAll(resourceBundle.getString("Austria"), resourceBundle.getString("Germany"), resourceBundle.getString("Switzerland"));
        tourEndCountryComboBox.getItems().addAll(resourceBundle.getString("Austria"), resourceBundle.getString("Germany"), resourceBundle.getString("Switzerland"));
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
                                .country(tourStartCountryComboBox.getValue()).build())
                        .endAddress(Address.builder()
                                .street(TourEndStreet.getText().trim())
                                .zip(Integer.valueOf(TourEndZip.getText().trim()))
                                .city(TourEndCity.getText().trim())
                                .country(tourEndCountryComboBox.getValue()).build())
                        .transportation(transportComboBox.getValue())
                        .route(null)
                        .misc(null)
                        .build();
                exitButton.getScene().getWindow().setUserData(tour);
                onCloseWindow();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(exitButton.getScene().getWindow());
            alert.setTitle(resourceBundle.getString("TourEditModal_ZipErrorTitle"));
            alert.setHeaderText(null);
            alert.setContentText(resourceBundle.getString("TourEditModal_ZipErrorText"));
            alert.showAndWait();
        }
    }

    public void onDeleteButton() {
        TourName.clear();
        TourStartStreet.clear();
        TourStartZip.clear();
        TourStartCity.clear();
        TourEndStreet.clear();
        TourEndZip.clear();
        TourEndCity.clear();
        transportComboBox.setPromptText(resourceBundle.getString("TourModal_Transportation"));
        tourStartCountryComboBox.getSelectionModel().clearSelection();
        tourStartCountryComboBox.setPromptText(resourceBundle.getString("TourModal_Country"));
        tourEndCountryComboBox.getSelectionModel().clearSelection();
        tourEndCountryComboBox.setPromptText(resourceBundle.getString("TourModal_Country"));
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
