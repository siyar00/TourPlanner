package at.technikum.planner.view.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TourDialogController {
    public Button exitButton;

    public TourDialogController() {
    }

    @FXML
    void initialize() {

    }

    public void enterKey(KeyEvent keyEvent) {
    }

    public void onOkayButton(ActionEvent actionEvent) {
    }

    public void onDeleteButton(ActionEvent actionEvent) {
    }

    public void onCloseWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
