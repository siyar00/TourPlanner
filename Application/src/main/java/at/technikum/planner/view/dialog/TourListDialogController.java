package at.technikum.planner.view.dialog;

import at.technikum.planner.model.Tour;
import at.technikum.planner.transformer.RouteTypeTransformer;
import at.technikum.planner.viewmodel.dialog.TourListDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ResourceBundle;

@Data
@NoArgsConstructor
public class TourListDialogController {
    @FXML
    ComboBox<String> transportComboBox;
    @FXML
    TextField tourName;
    @FXML
    TextField tourStartAddress;
    @FXML
    TextField tourEndAddress;
    @FXML
    TextField tourDescription;
    @FXML
    Button okayButton;
    @FXML
    Button exitButton;
    Tour tour;
    ResourceBundle bundle;
    TourListDialogViewModel viewModel;

    public TourListDialogController(TourListDialogViewModel tourListDialogViewModel) {
        this.viewModel = tourListDialogViewModel;
    }

    public void initialize(ResourceBundle bundle) {
        this.bundle = bundle;
        transportComboBox.getItems().addAll(bundle.getString("RouteType_CarFastest"),
                bundle.getString("RouteType_CarShortest"), bundle.getString("RouteType_Pedestrian"),
                bundle.getString("RouteType_Bicycle"));
    }

    public void enterKey(KeyEvent keyEvent) {
        if (tourName.getText().trim().isEmpty() && tourStartAddress.getText().isEmpty() && tourEndAddress.getText().isEmpty() &&
                transportComboBox.getSelectionModel().isEmpty() && keyEvent.getCode().isDigitKey() && keyEvent.getCode().isLetterKey()) {
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
                    .tourDescription(tourDescription.getText().trim())
                    .startAddress(tourStartAddress.getText().trim())
                    .endAddress(tourEndAddress.getText().trim())
                    .transportation(new RouteTypeTransformer().getRouteTypeFromBundle(transportComboBox.getValue(), bundle))
                    .build();
            onCloseWindow();
        }
    }

    public void onDeleteButton() {
        tourName.clear();
        tourDescription.clear();
        tourStartAddress.clear();
        tourEndAddress.clear();
        transportComboBox.setPromptText(bundle.getString("TourModal_Transportation"));
        okayButton.setDisable(true);
    }

    public void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void alert(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.initOwner(okayButton.getScene().getWindow());
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        confirmationAlert.getButtonTypes().setAll(ButtonType.OK);
        confirmationAlert.showAndWait();
    }
}
