package at.technikum.planner.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class TourListController {
    @FXML
    private ListView<String> tourNameListView = new ListView<>();

    public void onButtonAdd() throws IOException {
        TourModalController tourModalController = new TourModalController();
        String tourName = tourModalController.stage();
        if(tourName != null)
            tourNameListView.getItems().add(tourName);
    }

    public void onButtonRemove() {
        int selectedIndex = tourNameListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            tourNameListView.getItems().remove(selectedIndex);
        }
    }

    public void onShowMore() {
    }
}
