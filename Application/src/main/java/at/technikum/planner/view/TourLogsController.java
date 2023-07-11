package at.technikum.planner.view;

import at.technikum.dal.dto.WeatherResponse;
import at.technikum.planner.model.TourLog;
import at.technikum.planner.view.dialog.TourLogsDialogController;
import at.technikum.planner.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;
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
            dialogController.setTourLog(selectedTourLog);

            TourLog tourLog = showDialog(dialogPane, dialogController);
            if (tourLog != null) {
                viewModel.updateTourLog(tourLog, selectedTourLog);
            }
        }
    }

    @FXML
    void onWeatherReport() {
        if(viewModel.getTour() == null) return;
        List<WeatherResponse> responses =viewModel.getWeatherReport();
        Alert weatherAlert = new Alert(Alert.AlertType.CONFIRMATION);
        weatherAlert.getDialogPane().setMinWidth(800);
        weatherAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        weatherAlert.initOwner(addButton.getScene().getWindow());
        weatherAlert.setTitle(bundle.getString("TourLogs_WeatherReport"));
        weatherAlert.setHeaderText(null);
        WeatherResponse start = responses.get(0);
        WeatherResponse end = responses.get(1);
        String weather = String.format("In %s: %s mit einer Temperatur von %s°C und gefühlten %s°C.\n\nIn %s: %s mit einer Temperatur von %s°C und gefühlten %s°C.\n", start.getName(), start.getWeather().get(0).getDescription(), start.getMain().getTemp(), start.getMain().getFeels_like(), end.getName(), end.getWeather().get(0).getDescription(), end.getMain().getTemp(), end.getMain().getFeels_like());

        weatherAlert.setContentText(weather);
        weatherAlert.getButtonTypes().setAll(ButtonType.OK);
        weatherAlert.showAndWait();
    }

    private TourLog showDialog(DialogPane dialogPane, TourLogsDialogController dialogController) {
        dialogController.setBundle(bundle);
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
