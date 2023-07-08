package at.technikum.planner.view;

import at.technikum.planner.model.TourLog;
import at.technikum.planner.view.dialog.TourDialogController;
import at.technikum.planner.viewmodel.TourLogsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class TourLogsController {
    @FXML
    private TableView<TourLog> logTable;
    @FXML
    private Button addButton;
    @FXML
    private Button okayButton;

    private final ResourceBundle bundle;

    @FXML
    private TextField durationField;
    @FXML
    private TextField distanceField;
    @FXML
    private TextArea commentArea;
    @FXML
    private TableView<TourLog> tourLogsTableView;
    @FXML
    private TableColumn<TourLog, Integer> idColumn;
    @FXML
    private TableColumn<TourLog, Date> dateColumn;
    @FXML
    private TableColumn<TourLog, Double> durationColumn;
    @FXML
    private TableColumn<TourLog, Double> distanceColumn;
    @FXML
    private TableColumn<TourLog, String> commentColumn;

    private final TourLogsViewModel tourLogsViewModel;

    public TourLogsController(TourLogsViewModel tourLogsViewModel, ResourceBundle bundle) {
        this.tourLogsViewModel = tourLogsViewModel;
        this.bundle = bundle;
    }

    @FXML
    void initialize() {
        createTable();
    }

    @FXML
    void onButtonAdd(ActionEvent event) throws IOException {
        Button addButton = (Button) event.getSource();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog/TourDialog.fxml"), bundle);
        Parent dialogPane = fxmlLoader.load();
        TourDialogController dialogController = fxmlLoader.getController();
        dialogController.setTourLogsViewModel(tourLogsViewModel);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(addButton.getScene().getWindow());
        dialog.getDialogPane().setContent(dialogPane);
        dialog.setTitle("Add TourLog");


        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Der OK-Button wurde gedrückt
            TourLog newTourLog = dialogController.createTourLogFromInput();

            // Setze das aktuelle Datum ohne Zeitzone und Jahr
            setDateToCurrent(newTourLog);

            //tourLogsViewModel.getObservableTourLogs().add(newTourLog);
            tourLogsViewModel.addTourLog(newTourLog);
        }
    }


    private void createTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tourLogsTableView.setItems(tourLogsViewModel.getObservableTourLogs());
    }


    public void onButtonRemove(ActionEvent actionEvent) {
        TourLog selectedTourLog = tourLogsTableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            tourLogsViewModel.getObservableTourLogs().remove(selectedTourLog);
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



    public void onEditButton(ActionEvent actionEvent) {
        TourLog selectedTourLog = tourLogsTableView.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            // Öffne den Bearbeitungsdialog für den ausgewählten TourLog
            // Hier kannst du den Code hinzufügen, um den Bearbeitungsdialog zu öffnen und die entsprechenden Daten zu übergeben
        }
    }
}
