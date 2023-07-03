package at.technikum.planner.view;

import at.technikum.planner.model.Tour;
import at.technikum.planner.view.modal.TourEditModalController;
import at.technikum.planner.view.modal.TourModalController;
import at.technikum.planner.viewmodel.TourListViewModel;
import at.technikum.planner.viewmodel.TourModalViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ResourceBundle;

public class TourListController {
    @FXML
    private Button addButton;
    @FXML
    private ListView<Tour> tourNameListView = new ListView<>();
    private final ResourceBundle bundle = ResourceBundle.getBundle("at.technikum.planner.view.gui_strings_de");
    private final TourListViewModel tourListViewModel;

    public TourListController(TourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }

    @FXML
    void initialize(){
        tourNameListView.setItems(tourListViewModel.getObservableTours());
        tourNameListView.getSelectionModel().selectedItemProperty().addListener(tourListViewModel.getChangeListener());
    }

    public void onButtonAdd() throws IOException {
        TourModalController tourModalController = new TourModalController(new TourModalViewModel());
        Tour tour = tourModalController.stage(tourModalController);
        if (tour != null) {
            if (tourNameListView.getItems().stream().anyMatch(t -> t.getName().equals(tour.getName()))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(addButton.getScene().getWindow());
                alert.setTitle(bundle.getString("TourList_ExistingTitle"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("TourList_ExistingText"));
                alert.showAndWait();
                return;
            }
            tourListViewModel.addNewTour(tour);
            tourNameListView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Tour item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getName() == null) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            });
        }
    }

    public void onButtonRemove() {
        int selectedIndex = tourNameListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.initOwner(addButton.getScene().getWindow());
            confirmationAlert.setTitle(bundle.getString("TourList_DeleteTitle"));
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText(bundle.getString("TourList_DeleteText"));
            ButtonType deleteButton = new ButtonType(bundle.getString("TourModal_Delete"));
            ButtonType cancelButton = new ButtonType(bundle.getString("TourModal_Cancel"));
            confirmationAlert.getButtonTypes().setAll(deleteButton, cancelButton);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == deleteButton) {
                    tourListViewModel.removeTour(tourNameListView.getSelectionModel().getSelectedItem());
                }
            });
        }
    }

    public void onEditButton() throws IOException {
        Tour tour;
        if ((tour = tourNameListView.getSelectionModel().getSelectedItem()) == null)
            return;
        TourEditModalController tourEditModalController = new TourEditModalController(tour);
        tour = tourEditModalController.stage(tourEditModalController);
        if (tour != null)
            tourNameListView.getItems().set(tourNameListView.getSelectionModel().getSelectedIndex(), tour);
    }
}
