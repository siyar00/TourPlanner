package at.technikum.planner.view.dialog;

import at.technikum.planner.model.Tour;
import at.technikum.planner.transformer.RouteTypeTransformer;
import at.technikum.planner.viewmodel.dialog.TourListDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.ResourceBundle;

@Setter
@Getter
@NoArgsConstructor
public class TourListDialogController {
    @FXML
    ComboBox<String> transportComboBox;
    @FXML
    TextField tourDescription;
    @FXML
    TextField tourName;
    @FXML
    TextField tourStartAddress;
    @FXML
    TextField tourEndAddress;
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

    public void initialize(Tour tour, ResourceBundle bundle) {
        this.tour = tour;
        this.bundle = bundle;
        transportComboBox.getItems().addAll(bundle.getString("RouteType_CarFastest"),
                bundle.getString("RouteType_CarShortest"), bundle.getString("RouteType_Pedestrian"),
                bundle.getString("RouteType_Bicycle"));
    }

    public void enterKey(KeyEvent keyEvent) {
        if (tourName.getText().trim().isEmpty() || tourStartAddress.getText().isEmpty() || tourEndAddress.getText().isEmpty()) {
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
            alert(bundle.getString("TourModal_NameError"));
        } else if (transportComboBox.getValue() == null) {
            alert(bundle.getString("TourModal_TransportError"));
        } else if (tourStartAddress.getText().trim().isEmpty()) {
            alert(bundle.getString("TourModal_StartAddressError"));
        } else if (tourEndAddress.getText().trim().isEmpty()) {
            alert(bundle.getString("TourModal_EndAddressError"));
        } else {
            this.tour = Tour.builder().name(tourName.getText().trim())
                    .tourDescription(tourDescription.getText().trim())
                    .startAddress(tourStartAddress.getText().trim())
                    .endAddress(tourEndAddress.getText().trim())
                    .transportation(new RouteTypeTransformer().getRouteTypeFromBundle(transportComboBox.getSelectionModel().getSelectedItem(), bundle))
                    .tourLog(new ArrayList<>()).build();
            onCloseWindow();
        }
    }

    public void onDeleteButton() {
        tourName.clear();
        tourDescription.clear();
        tourStartAddress.clear();
        tourEndAddress.clear();
        transportComboBox.getSelectionModel().clearSelection();
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

    public void setAll() {
        tourDescription.setText(tour.getTourDescription());
        tourName.setText(tour.getName());
        tourStartAddress.setText(tour.getStartAddress());
        tourEndAddress.setText(tour.getEndAddress());
        transportComboBox.getSelectionModel().select(new RouteTypeTransformer().getBundleFromRouteType(tour.getTransportation(), bundle));
    }
}
