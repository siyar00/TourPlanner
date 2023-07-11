package at.technikum.planner.view;

import at.technikum.planner.model.Tour;
import at.technikum.planner.view.dialog.TourListDialogController;
import at.technikum.planner.viewmodel.TourListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import lombok.Getter;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@Getter
public class TourListController {
    @FXML
    private Button addButton;
    @FXML
    private ListView<Tour> tourNameListView = new ListView<>();
    final ResourceBundle bundle;
    final TourListViewModel viewModel;
    Logger LOGGER = Logger.getLogger(TourListController.class.getName());

    public TourListController(TourListViewModel tourListViewModell, ResourceBundle bundle) {
        this.viewModel = tourListViewModell;
        this.bundle = bundle;
    }

    @FXML
    void initialize() {
        tourNameListView.setItems(viewModel.getObservableToursFromDatabase());
        tourNameListView.getSelectionModel().selectedItemProperty().addListener(viewModel.getChangeListener());
        updateListView();
        LOGGER.info("TourListController initialized");
    }

    public void onButtonAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourListDialog.fxml"), bundle);
        DialogPane dialogPane = fxmlLoader.load();
        TourListDialogController controller = fxmlLoader.getController();
        controller.initialize(null, bundle);
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
                LOGGER.info("Tour already exists");
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
            ButtonType deleteButton = new ButtonType(bundle.getString("Delete"));
            ButtonType cancelButton = new ButtonType(bundle.getString("Cancel"));
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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourListDialog.fxml"), bundle);
        DialogPane dialogPane = fxmlLoader.load();
        TourListDialogController tourListDialogController = fxmlLoader.getController();
        tourListDialogController.initialize(tour, bundle);
        tourListDialogController.setAll();
        showDialog(dialogPane);

        tour = tourListDialogController.getTour();
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

    public void showDialog(DialogPane dialogPane) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.initOwner(addButton.getScene().getWindow());
        dialog.initStyle(StageStyle.UNIFIED);
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.showAndWait();
    }
}
