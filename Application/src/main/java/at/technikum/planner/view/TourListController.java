package at.technikum.planner.view;

import at.technikum.planner.TourPlannerApplication;
import at.technikum.planner.model.Tour;
import at.technikum.planner.view.modal.TourEditModalController;
import at.technikum.planner.view.modal.TourModalController;
import at.technikum.planner.viewmodel.TourListViewModel;
import at.technikum.planner.viewmodel.TourModalViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TourListController {
    @FXML
    private Button addButton;
    @FXML
    private ListView<Tour> tourNameListView = new ListView<>();
    private final ResourceBundle bundle;
    private final TourListViewModel viewModel;

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
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("modal/TourModal.fxml")), bundle);
        fxmlLoader.setController(new TourModalController(new TourModalViewModel(), bundle));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(Objects.requireNonNull(TourPlannerApplication.class.getResourceAsStream("images/dora.png"))));
        stage.showAndWait();
        Tour tour = (Tour) stage.getUserData();
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
        if (tour == null) return;
        Tour oldTour = tour;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modal/TourEditModal.fxml"), bundle);
        DialogPane dialogPane = fxmlLoader.load();
        TourEditModalController tourEditModalController = fxmlLoader.getController();
        tourEditModalController.init(tour, bundle);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.initOwner(addButton.getScene().getWindow());
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.showAndWait();

        tour = tourEditModalController.getTour();
        if (tour != null)
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
}
