package at.technikum.planner.view.dialog;

import at.technikum.planner.model.TourLog;
import at.technikum.planner.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TourDialogController {
    @FXML
    DatePicker date;
    @FXML
    TextField duration;
    @FXML
    TextField comment;
    @FXML
    Slider difficulty;
    @FXML
    Slider rating;
    @FXML
    Button addButton;
    @FXML
    Button editButton;
    @FXML
    Button exitButton;
    TourLog tourLog;
    TourLogsViewModel tourLogsViewModel;

    public TourDialogController(TourLogsViewModel tourLogsViewModel) {
        this.tourLogsViewModel = tourLogsViewModel;
    }

    @FXML
    void enterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onAddButton();
        }
    }

    @FXML
    void onAddButton() {
//
//        if (!isValidNumber(durationText) || !isValidNumber(distanceText)) {
//            showAlert("Invalid Input", "Dauer und Entfernung müssen numerische Werte sein.");
//            return;
//        }



//        tourLog = TourLog.builder().duration(duration).comment(comment).build();
//        onCloseWindow();
    }

    @FXML
    void onDeleteButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void setTourLogsViewModel(TourLogsViewModel tourLogsViewModel) {
        this.tourLogsViewModel = tourLogsViewModel;
    }

    private boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
