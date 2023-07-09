package at.technikum.planner.view;

import at.technikum.planner.model.TourLog;
import at.technikum.planner.view.dialog.TourDialogController;
import at.technikum.planner.viewmodel.TourLogsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;


public class TourLogsController {

    @FXML
    Button addButton;
    @FXML
    TableView<TourLog> tourLogsTableView;
    @FXML
    TableColumn<TourLog, Integer> idColumn;
    @FXML
    TableColumn<TourLog, Date> dateColumn;
    @FXML
    TableColumn<TourLog, Double> durationColumn;
    @FXML
    TableColumn<TourLog, String> commentColumn;
    @FXML
    TableColumn<TourLog, String> difficultyColumn;
    @FXML
    TableColumn<TourLog, String> ratingColumn;
    final ResourceBundle bundle;
    final TourLogsViewModel viewModel;

    public TourLogsController(TourLogsViewModel tourLogsViewModel, ResourceBundle bundle) {
        this.viewModel = tourLogsViewModel;
        this.bundle = bundle;
    }

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        tourLogsTableView.setItems(viewModel.getObservableTourLogs());
        this.tourLogsTableView.setItems(viewModel.getObservableTourLogs());
    }

    @FXML
    void onButtonAdd(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourDialog.fxml"), bundle);
        DialogPane dialogPane = fxmlLoader.load();
        TourDialogController dialogController = fxmlLoader.getController();
        dialogController.setTourLogsViewModel(viewModel);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.initOwner(addButton.getScene().getWindow());
        dialog.initStyle(StageStyle.UNIFIED);
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.showAndWait();

        TourLog tourLog = dialogController.getTourLog();
//
//        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//        Optional<ButtonType> result = dialog.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            TourLog newTourLog = dialogController.createTourLogFromInput();
//            setDateToCurrent(newTourLog);
//
//            //tourLogsViewModel.getObservableTourLogs().add(newTourLog);
//            viewModel.addTourLog(newTourLog);
//        }
    }


    @FXML
    void onButtonRemove() {
        TourLog selectedTourLog = tourLogsTableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            viewModel.getObservableTourLogs().remove(selectedTourLog);
        }
    }

    @FXML
    public void onEditButton(ActionEvent actionEvent) {
        TourLog selectedTourLog = tourLogsTableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            // Öffne den Bearbeitungsdialog für den ausgewählten TourLog
            // Hier kannst du den Code hinzufügen, um den Bearbeitungsdialog zu öffnen und die entsprechenden Daten zu übergeben
        }
    }

    /*
    @FXML
    void onOkayButton(ActionEvent actionEvent) {
        double duration = Double.parseDouble(durationField.getText());
        double distance = Double.parseDouble(distanceField.getText());
        String comment = commentArea.getText();

        // Erstelle ein neues TourLog-Objekt mit den eingelesenen Werten
        TourLog tourLog = new TourLog(duration, distance, comment);
        setDateToCurrent(tourLog);

        // Füge das TourLog-Objekt der ViewModel-Liste hinzu
        tourLogsViewModel.addTourLog(tourLog);

        // Schließe das Dialogfenster
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }
     */

    private void setDateToCurrent(TourLog tourLog) {
        // Setze das aktuelle Datum ohne Zeitzone und Jahr
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date currentDate = calendar.getTime();
        tourLog.setDate(currentDate);
    }
}
