package at.technikum.planner.view;

import at.technikum.planner.model.Tour;
import at.technikum.planner.view.dialog.TourEditDialogController;
import at.technikum.planner.view.dialog.TourListDialogController;
import at.technikum.planner.viewmodel.TourListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ResourceBundle;

public class TourListController {
    @FXML
    Button addButton;
    @FXML
    ListView<Tour> tourNameListView = new ListView<>();
    final ResourceBundle bundle;
    final TourListViewModel viewModel;

    public TourListController(TourListViewModel tourListViewModell, ResourceBundle bundle) {
        this.viewModel = tourListViewModell;
        this.bundle = bundle;
    }

    @FXML
    void initialize() {
        tourNameListView.setItems(viewModel.getObservableTours());
        tourNameListView.getSelectionModel().selectedItemProperty().addListener(viewModel.getChangeListener());
        updateListView();
    }

    public void onButtonAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourListDialog.fxml"), bundle);
        DialogPane dialogPane = fxmlLoader.load();
        TourListDialogController controller = fxmlLoader.getController();
        controller.initialize(bundle);
        showDialog(dialogPane);
        Tour tour = controller.getTour();
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
            viewModel.addNewTour(tour);
            updateListView();
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
                    viewModel.removeTour(tourNameListView.getSelectionModel().getSelectedItem());
                }
            });
        }
    }

    public void onEditButton() throws IOException {
        Tour tour = tourNameListView.getSelectionModel().getSelectedItem();
        Tour oldTour = tour;
        if (tour == null) return;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourEditDialog.fxml"), bundle);
        DialogPane dialogPane = fxmlLoader.load();
        TourEditDialogController tourEditDialogController = fxmlLoader.getController();
        tourEditDialogController.init(tour, bundle);
        showDialog(dialogPane);

        tour = tourEditDialogController.getTour();
        if (!tour.equals(oldTour))
            viewModel.updateTour(oldTour, tour);
    }

    private void updateListView() {
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

    private void showDialog(DialogPane dialogPane) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.initOwner(addButton.getScene().getWindow());
        dialog.initStyle(StageStyle.UNIFIED);
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.showAndWait();
    }
}
