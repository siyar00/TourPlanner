package at.technikum.planner.view.dialog;

import at.technikum.planner.model.TourLog;
import at.technikum.planner.viewmodel.TourLogsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class TourDialogController {
    @FXML
    private TextField durationField;
    @FXML
    private TextField distanceField;
    @FXML
    private TextArea commentArea;
    @FXML
    private Button okayButton;
    @FXML
    private Button exitButton;

    private TourLogsViewModel tourLogsViewModel;

    public TourDialogController() {
    }

    @FXML
    void initialize() {

    }

    @FXML
    void enterKey(KeyEvent keyEvent) {
        // Überprüfe, ob die gedrückte Taste die Eingabetaste ist
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            // Rufe die Methode onOkayButton auf, um die Eingabe zu bestätigen
            onOkayButton(new ActionEvent());
        }
    }

    @FXML
    void onOkayButton(ActionEvent actionEvent) {
        String durationText = durationField.getText();
        String distanceText = distanceField.getText();
        String comment = commentArea.getText();

        if (!isValidNumber(durationText) || !isValidNumber(distanceText)) {
            showAlert("Invalid Input", "Dauer und Entfernung müssen numerische Werte sein.");
            return;
        }

        double duration = Double.parseDouble(durationText);
        double distance = Double.parseDouble(distanceText);

        // Erstelle ein neues TourLog-Objekt mit den eingelesenen Werten
        TourLog tourLog = new TourLog(duration, distance, comment);

        // Füge das TourLog-Objekt der ViewModel-Liste hinzu
        tourLogsViewModel.getObservableTourLogs().add(tourLog);

        // Schließe das Dialogfenster
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onDeleteButton(ActionEvent actionEvent) {
        // Schließe das Dialogfenster ohne weitere Aktionen
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onCloseWindow(ActionEvent actionEvent) {
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

        // Erstelle ein neues TourLog-Objekt mit den eingelesenen Werten
        TourLog tourLog = new TourLog(duration, distance, comment);

        return tourLog;
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
