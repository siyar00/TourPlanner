package at.technikum.planner.view;

import at.technikum.planner.model.TourLog;
import at.technikum.planner.view.dialog.TourLogsDialogController;
import at.technikum.planner.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class TourLogsController {

    @FXML
    private Button addButton;
    @FXML
    private TableView<TourLog> tourLogsTableView;
    @FXML
    private TableColumn<TourLog, String> dateColumn;
    @FXML
    private TableColumn<TourLog, String> durationColumn;
    @FXML
    private TableColumn<TourLog, String> commentColumn;
    @FXML
    private TableColumn<TourLog, Integer> difficultyColumn;
    @FXML
    private TableColumn<TourLog, Double> ratingColumn;
    final ResourceBundle bundle;
    final TourLogsViewModel viewModel;
    Logger LOGGER = Logger.getLogger(TourLogsController.class.getName());

    public TourLogsController(TourLogsViewModel tourLogsViewModel, ResourceBundle bundle) {
        this.viewModel = tourLogsViewModel;
        this.bundle = bundle;
    }

    @FXML
    void initialize() {
        dateColumn.setCellValueFactory(viewModel.getDateColumnProperty());
        durationColumn.setCellValueFactory(viewModel.getDurationColumnProperty());
        commentColumn.setCellValueFactory(viewModel.getCommentColumnProperty());
        difficultyColumn.setCellValueFactory(viewModel.getDifficultyColumnProperty());
        ratingColumn.setCellValueFactory(viewModel.getRatingColumnProperty());
        tourLogsTableView.setItems(viewModel.getObservableTourLogs());
        LOGGER.info("TourLogsController initialized");
    }

    @FXML
    void onButtonAdd() throws IOException {
        if(viewModel.getTour() == null) return;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourLogsDialog.fxml"), bundle);
        DialogPane dialogPane = fxmlLoader.load();
        TourLogsDialogController dialogController = fxmlLoader.getController();
        dialogController.setBundle(bundle);

        TourLog tourLog = showDialog(dialogPane, dialogController);
        if (tourLog != null) {
            viewModel.addTourLog(tourLog);
        }
    }

    @FXML
    void onButtonRemove() {
        if(viewModel.getTour() == null) return;
        TourLog selectedTourLog = tourLogsTableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            viewModel.removeTourLog(selectedTourLog);
        }
    }

    @FXML
    public void onEditButton() throws IOException {
        if(viewModel.getTour() == null) return;
        TourLog selectedTourLog = tourLogsTableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourLogsDialog.fxml"), bundle);
            DialogPane dialogPane = fxmlLoader.load();
            TourLogsDialogController dialogController = fxmlLoader.getController();
            dialogController.setBundle(bundle);
            dialogController.setTourLog(selectedTourLog);

            TourLog tourLog = showDialog(dialogPane, dialogController);
            if (tourLog != null) {
                viewModel.updateTourLog(tourLog, selectedTourLog);
            }
        }
    }

    private TourLog showDialog(DialogPane dialogPane, TourLogsDialogController dialogController) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.initOwner(addButton.getScene().getWindow());
        dialog.initStyle(StageStyle.UNIFIED);
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.showAndWait();
        return dialogController.getTourLog();
    }
}
