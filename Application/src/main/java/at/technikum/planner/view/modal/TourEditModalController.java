package at.technikum.planner.view.modal;

import at.technikum.planner.TourPlannerApplication;
import at.technikum.planner.model.Address;
import at.technikum.planner.model.Tour;
import at.technikum.planner.viewmodel.TourEditModalViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TourEditModalController {
    @FXML
    private ComboBox<String> transportComboBox;
    @FXML
    private ComboBox<String> tourStartComboBox;
    @FXML
    private ComboBox<String> tourEndComboBox;
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
    private Button editButton;
    @FXML
    private Button exitButton;
    private final Tour tour;
    private ResourceBundle resourceBundle;

    public TourEditModalController(Tour tour) {
        this.tour = tour;
    }

    public TourEditModalController(TourEditModalViewModel tourEditModalViewModel) {
        tour = Tour.builder().build();
    }

    @FXML
    void initialize() {
        resourceBundle = ResourceBundle.getBundle("at.technikum.planner.view.gui_strings_de");
        this.transportComboBox.getItems().addAll(resourceBundle.getString("Car"), resourceBundle.getString("Pedestrian"));
        this.tourStartComboBox.getItems().addAll(resourceBundle.getString("Austria"), resourceBundle.getString("Germany"), resourceBundle.getString("Switzerland"));
        this.tourEndComboBox.getItems().addAll(resourceBundle.getString("Austria"), resourceBundle.getString("Germany"), resourceBundle.getString("Switzerland"));
        TourName.setText(tour.getName());
        TourStartStreet.setText(tour.getStartAddress().getStreet());
        TourStartCity.setText(tour.getStartAddress().getCity());
        TourEndStreet.setText(tour.getEndAddress().getStreet());
        TourEndCity.setText(tour.getEndAddress().getCity());
        transportComboBox.getSelectionModel().select(tour.getTransportation());
        tourStartComboBox.getSelectionModel().select(tour.getStartAddress().getCountry());
        tourEndComboBox.getSelectionModel().select(tour.getEndAddress().getCountry());
        if (tour.getStartAddress().getZip() != null)
            TourStartZip.setText(String.valueOf(tour.getStartAddress().getZip()));
        if (tour.getEndAddress().getZip() != null)
            TourEndZip.setText(String.valueOf(tour.getEndAddress().getZip()));
    }

    public void enterKey(KeyEvent keyEvent) {
        if (TourName.getText().trim().isEmpty() && keyEvent.getCode().isDigitKey() && keyEvent.getCode().isLetterKey()) {
            editButton.setDisable(true);
            return;
        }
        editButton.setDisable(false);
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onEditButton();
        } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            onCloseWindow();
        }
    }

    public void onEditButton() {
        try {
            if (TourName.getText().trim().isEmpty()) {
                System.out.println("Please enter a name for the tour.");
            } else {
                Tour tour = Tour.builder().name(TourName.getText().trim())
                        .startAddress(Address.builder()
                                .street(TourStartStreet.getText().trim())
                                .zip(Integer.valueOf(TourStartZip.getText().trim()))
                                .city(TourStartCity.getText().trim())
                                .country(tourStartComboBox.getValue()).build())
                        .endAddress(Address.builder()
                                .street(TourEndStreet.getText().trim())
                                .zip(Integer.valueOf(TourEndZip.getText().trim()))
                                .city(TourEndCity.getText().trim())
                                .country(tourEndComboBox.getValue()).build())
                        .transportation(transportComboBox.getValue())
                        .misc(null)
                        .build();
                exitButton.getScene().getWindow().setUserData(tour);
                onCloseWindow();
            }
        } catch (NumberFormatException ignored) {
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
        transportComboBox.getSelectionModel().clearSelection();
        transportComboBox.setPromptText(resourceBundle.getString("TourModal_Transportation"));
        tourStartComboBox.getSelectionModel().clearSelection();
        tourStartComboBox.setPromptText(resourceBundle.getString("TourModal_Country"));
        tourEndComboBox.getSelectionModel().clearSelection();
        tourEndComboBox.setPromptText(resourceBundle.getString("TourModal_Country"));
        editButton.setDisable(true);
    }

    public void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public Tour stage(TourEditModalController controller) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(TourEditModalController.class.getResource("TourEditModal.fxml")),
                ResourceBundle.getBundle("at.technikum.planner.view.gui_strings_de"), new JavaFXBuilderFactory());
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(Objects.requireNonNull(TourPlannerApplication.class.getResourceAsStream("images/dora.png"))));
        stage.showAndWait();
        return (Tour) stage.getUserData();
    }
}
