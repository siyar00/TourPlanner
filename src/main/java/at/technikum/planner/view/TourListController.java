package at.technikum.planner.view;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;

public class TourListController {
    @FXML
    private ListView<String> tourNameListView = new ListView<>();

    public void onButtonAdd() throws IOException {
        TourModalController tourModalController = new TourModalController();
        String tourName = tourModalController.stage();

        if (tourName != null) {
            if (tourNameListView.getItems().contains(tourName)) {
                // Show modal with existing tour name message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tour Name Exists");
                alert.setHeaderText(null);
                alert.setContentText("Eine Tour mit diesem Namen existiert bereits.");
                alert.showAndWait();
            } else {
                tourNameListView.getItems().add(tourName);
            }
        }
    }

    public void onButtonRemove() {
        int selectedIndex = tourNameListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            // Show confirmation modal
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Tour löschen");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Möchten Sie diese Tour wirklich löschen?");
            ButtonType deleteButton = new ButtonType("Löschen");
            ButtonType cancelButton = new ButtonType("Abbrechen");
            confirmationAlert.getButtonTypes().setAll(deleteButton, cancelButton);

            // Handle user's response
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == deleteButton) {
                    tourNameListView.getItems().remove(selectedIndex);
                }
            });
        }
    }

    public void onShowMore() {
    }
}
