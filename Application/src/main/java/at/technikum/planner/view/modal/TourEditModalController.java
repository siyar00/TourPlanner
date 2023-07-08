package at.technikum.planner.view.modal;

import at.technikum.planner.TourPlannerApplication;
import at.technikum.planner.model.Tour;
import at.technikum.planner.viewmodel.TourEditModalViewModel;
import at.technikum.planner.viewmodel.TourModalViewModel;
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
    private TextField tourName;
    @FXML
    private TextField tourStartAddress;
    @FXML
    private TextField tourEndAddress;
    @FXML
    private Button editButton;
    @FXML
    private Button exitButton;
    private final Tour tour;
    private final ResourceBundle bundle;
    private final TourEditModalViewModel viewModel;

    public TourEditModalController(TourEditModalViewModel tourEditModalViewModel, ResourceBundle resourceBundle) {
        tour = Tour.builder().build();
        this.viewModel = tourEditModalViewModel;
        this.bundle = resourceBundle;
    }

    @FXML
    void initialize() {
        this.transportComboBox.getItems().addAll(bundle.getString("Car"), bundle.getString("Pedestrian"));
        tourName.setText(tour.getName());
        tourStartAddress.setText(tour.getStartAddress());
        tourEndAddress.setText(tour.getStartAddress());
        transportComboBox.getSelectionModel().select(tour.getTransportation().getType());
    }

    public void enterKey(KeyEvent keyEvent) {
        if (tourName.getText().trim().isEmpty() && keyEvent.getCode().isDigitKey() && keyEvent.getCode().isLetterKey()) {
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
        if (tourName.getText().trim().isEmpty()) {
            System.out.println("Please enter a name for the tour.");
        } else {
            Tour tour = Tour.builder().name(tourName.getText().trim())
                    .startAddress(tourStartAddress.getText().trim())
                    .endAddress(tourEndAddress.getText().trim())
                    .transportation(new TourModalViewModel().getRouteType(transportComboBox.getSelectionModel().getSelectedItem(), bundle))
                    .build();
            exitButton.getScene().getWindow().setUserData(tour);
            onCloseWindow();
        }

    }

    public void onDeleteButton() {
        tourName.clear();
        tourStartAddress.clear();
        tourEndAddress.clear();
        transportComboBox.getSelectionModel().clearSelection();
        transportComboBox.setPromptText(bundle.getString("TourModal_Transportation"));
        editButton.setDisable(true);
    }

    public void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public Tour stage(TourEditModalController controller) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(TourEditModalController.class.getResource("TourEditModal.fxml")),
                bundle, new JavaFXBuilderFactory());
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
