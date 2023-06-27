package at.technikum.planner.view;

import at.technikum.planner.model.Tour;
import at.technikum.planner.view.modal.TourEditModalController;
import at.technikum.planner.view.modal.TourModalController;
import at.technikum.planner.viewmodel.TourListViewModel;
import at.technikum.planner.viewmodel.TourModalViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class TourListController {
    @FXML
    private ListView<Tour> tourNameListView = new ListView<>();

    public TourListController(TourListViewModel tourListViewModel) {
    }

    public void onButtonAdd() throws IOException {
        TourModalController tourModalController = new TourModalController(new TourModalViewModel());
        Tour tour = tourModalController.stage(tourModalController);
        if(tour != null)
            tourNameListView.getItems().add(tour);
    }

    public void onButtonRemove() {
        tourNameListView.getItems().remove(tourNameListView.getSelectionModel().getSelectedItem());
    }

    public void onEditButton() throws IOException {
        Tour tour;
        if((tour = tourNameListView.getSelectionModel().getSelectedItem()) == null)
            return;
        TourEditModalController tourEditModalController = new TourEditModalController(tour);
        tour = tourEditModalController.stage(tourEditModalController);
        if(tour != null)
            tourNameListView.getItems().set(tourNameListView.getSelectionModel().getSelectedIndex(), tour);
    }
}
