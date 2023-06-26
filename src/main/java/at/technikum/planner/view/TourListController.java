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
    }

    public void onShowMore() {
    }
}
