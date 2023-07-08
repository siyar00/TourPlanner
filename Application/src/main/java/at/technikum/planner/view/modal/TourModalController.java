package at.technikum.planner.view.modal;

import at.technikum.planner.model.Tour;
import at.technikum.planner.viewmodel.TourModalViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class TourModalController {
    @FXML
    private ComboBox<String> transportComboBox;
    @FXML
    private TextField tourName;
    @FXML
    private TextField tourStartAddress;
    @FXML
    private TextField tourEndAddress;
    @FXML
    private Button okayButton;
    @FXML
    private Button exitButton;
    private final ResourceBundle bundle;
    private final TourModalViewModel viewModel;

    public TourModalController(TourModalViewModel tourModalViewModel, ResourceBundle resourceBundle) {
        this.viewModel = tourModalViewModel;
        this.bundle = resourceBundle;
    }

    @FXML
    void initialize() {
        transportComboBox.getItems().addAll(bundle.getString("RouteType_CarFastest"),
                bundle.getString("RouteType_CarShortest"), bundle.getString("RouteType_Pedestrian"),
                bundle.getString("RouteType_Bicycle"));
    }

    public void enterKey(KeyEvent keyEvent) {
        if (tourName.getText().trim().isEmpty() && keyEvent.getCode().isDigitKey() && keyEvent.getCode().isLetterKey()) {
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
            System.out.println("Please enter a name for the tour.");
        } else if (transportComboBox.getValue() == null) {
            System.out.println("Select a transportation.");
        } else {
            Tour tour = Tour.builder().name(tourName.getText().trim())
                    .startAddress(tourStartAddress.getText().trim())
                    .endAddress(tourEndAddress.getText().trim())
                    .transportation(viewModel.getRouteType(transportComboBox.getValue(), bundle))
                    .build();
            exitButton.getScene().getWindow().setUserData(tour);
            onCloseWindow();
        }
    }

    public void onDeleteButton() {
        tourName.clear();
        transportComboBox.setPromptText(bundle.getString("TourModal_Transportation"));
        okayButton.setDisable(true);
    }

    public void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

//    public Tour stage(TourModalController controller, ResourceBundle bundle) throws IOException {
//        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TourPlannerApplication.class.getResource("view/modal/TourModal.fxml")), bundle);
//        loader.setController(controller);
//        Stage stage = new Stage();
//        stage.setScene(new Scene(loader.load()));
//        stage.setTitle(bundle.getString("TourModal_Title"));
//        stage.getIcons().add(new Image(Objects.requireNonNull(TourPlannerApplication.class.getResourceAsStream("view/images/icon.png"))));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.showAndWait();
//        return (Tour) stage.getScene().getWindow().getUserData();
//    }
}
