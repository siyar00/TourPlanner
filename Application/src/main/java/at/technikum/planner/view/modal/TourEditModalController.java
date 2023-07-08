package at.technikum.planner.view.modal;

import at.technikum.planner.model.Tour;
import at.technikum.planner.transformer.RouteTypeTransformer;
import at.technikum.planner.viewmodel.TourEditModalViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ResourceBundle;

@Setter
@Getter
@NoArgsConstructor
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
    private Tour tour;
    private ResourceBundle bundle;
    private TourEditModalViewModel viewModel;

    public TourEditModalController(TourEditModalViewModel tourEditModalViewModel) {
        this.viewModel = tourEditModalViewModel;
    }

    public void enterKey(KeyEvent keyEvent) {
        if (tourName.getText().trim().isEmpty() || tourStartAddress.getText().isEmpty() || tourEndAddress.getText().isEmpty()) {
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
            alert("Please enter a name for the tour.");
        } else if (transportComboBox.getValue() == null) {
            alert("Select a transportation.");
        } else if (tourStartAddress.getText().trim().isEmpty()) {
            alert("Please enter a start address.");
        } else if (tourEndAddress.getText().trim().isEmpty()) {
            alert("Please enter an end address.");
        } else {
            this.tour = Tour.builder().name(tourName.getText().trim())
                    .startAddress(tourStartAddress.getText().trim())
                    .endAddress(tourEndAddress.getText().trim())
                    .transportation(new RouteTypeTransformer().getRouteTypeFromBundle(transportComboBox.getSelectionModel().getSelectedItem(), bundle))
                    .build();
            onCloseWindow();
        }
    }

    public void onDeleteButton() {
        tourName.clear();
        tourStartAddress.clear();
        tourEndAddress.clear();
        transportComboBox.getSelectionModel().clearSelection();
        transportComboBox.setPromptText("h"+bundle.getString("TourModal_Transportation"));
        editButton.setDisable(true);
    }

    public void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void alert(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.initOwner(editButton.getScene().getWindow());
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        confirmationAlert.getButtonTypes().setAll(ButtonType.OK);
        confirmationAlert.showAndWait();
    }

    public void init(Tour tour, ResourceBundle bundle) {
        this.tour = tour;
        this.bundle = bundle;
        transportComboBox.getItems().addAll(bundle.getString("RouteType_CarFastest"),
                bundle.getString("RouteType_CarShortest"), bundle.getString("RouteType_Pedestrian"),
                bundle.getString("RouteType_Bicycle"));
        tourName.setText(tour.getName());
        tourStartAddress.setText(tour.getStartAddress());
        tourEndAddress.setText(tour.getEndAddress());
        transportComboBox.getSelectionModel().select(new RouteTypeTransformer().getBundleFromRouteType(tour.getTransportation(), bundle));
    }

}
