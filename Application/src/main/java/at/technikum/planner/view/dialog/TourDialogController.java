package at.technikum.planner.view.dialog;

import at.technikum.planner.model.TourLog;
import at.technikum.planner.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TourDialogController {
    @FXML
    TextField durationField;
    @FXML
    TextField distanceField;
    @FXML
    TextArea commentArea;
    @FXML
    Button okayButton;
    @FXML
    Button exitButton;
    TourLogsViewModel tourLogsViewModel;

    public TourDialogController(TourLogsViewModel tourLogsViewModel) {
        this.tourLogsViewModel = tourLogsViewModel;
    }

    @FXML
    void enterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onOkayButton();
        }
    }

    @FXML
    void onOkayButton() {
        String durationText = durationField.getText();
        String distanceText = distanceField.getText();
        String comment = commentArea.getText();

        if (!isValidNumber(durationText) || !isValidNumber(distanceText)) {
            showAlert("Invalid Input", "Dauer und Entfernung müssen numerische Werte sein.");
            return;
        }

        double duration = Double.parseDouble(durationText);
        double distance = Double.parseDouble(distanceText);

        tourLogsViewModel.getObservableTourLogs().add(TourLog.builder().duration(duration).distance(distance).comment(comment).build());
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
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

    public TourLog createTourLogFromInput() {
        String durationText = durationField.getText();
        String distanceText = distanceField.getText();
        String comment = commentArea.getText();

        if (!isValidNumber(durationText) || !isValidNumber(distanceText)) {
            showAlert("Invalid Input", "Dauer und Entfernung müssen numerische Werte sein.");
            return null;
        }

        double duration = Double.parseDouble(durationText);
        double distance = Double.parseDouble(distanceText);

        return TourLog.builder().duration(duration).distance(distance).comment(comment).build();
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
