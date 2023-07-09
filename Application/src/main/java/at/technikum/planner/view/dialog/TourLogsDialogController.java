package at.technikum.planner.view.dialog;

import at.technikum.planner.model.TourLog;
import at.technikum.planner.viewmodel.dialog.TourLogsDialogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.controlsfx.control.Rating;
import org.controlsfx.validation.ValidationSupport;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Data
@NoArgsConstructor
public class TourLogsDialogController {
    @FXML
    Spinner<Integer> hour;
    @FXML
    Spinner<Integer> minute;
    @FXML
    DateTimePicker date;
    @FXML
    TextArea comment;
    @FXML
    Slider difficulty;
    @FXML
    Rating rating;
    @FXML
    Button addButton;
    @FXML
    Button editButton;
    @FXML
    Button exitButton;
    TourLog tourLog;
    ResourceBundle bundle;
    ValidationSupport validationSupport = new ValidationSupport();

    public TourLogsDialogController(TourLogsDialogViewModel tourLogsDialogViewModel){}

    @FXML
    void initialize() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        hour.setValueFactory(hourValueFactory);
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minute.setValueFactory(minuteValueFactory);
    }

    @FXML
    void onAddButton() {
        if(date.getDateTimeValue() == null) {
            showAlert(bundle.getString("TourLogModal_DateError"));
            return;
        } else if (hour.getValue() == 0 && minute.getValue() == 0) {
            showAlert(bundle.getString("TourLogModal_DurationError"));
            return;
        } else if (comment.getText() == null) {
            showAlert(bundle.getString("TourLogModal_CommentError"));
            return;
        }
        tourLog = TourLog.builder()
                .date(date.getDateTimeValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .duration(String.format("%02dh%02dmin",hour.getValue(), minute.getValue()))
                .comment(comment.getText())
                .difficulty((int) difficulty.getValue())
                .rating((double) Math.round(rating.getRating()*10)/10)
                .build();
        onCloseWindow();
    }

    @FXML
    void onDeleteButton() {
        date.setDateTimeValue(null);
        hour.getValueFactory().setValue(0);
        minute.getValueFactory().setValue(0);
        comment.setText("");
        difficulty.setValue(0);
        rating.setRating(0);
    }

    @FXML
    void onCloseWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.initOwner(editButton.getScene().getWindow());
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        confirmationAlert.getButtonTypes().setAll(ButtonType.OK);
        confirmationAlert.showAndWait();
    }

    public void setTourLog(TourLog tourLog){
        this.tourLog = tourLog;
        date.setDateTimeValue(LocalDateTime.parse(tourLog.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        comment.setText(tourLog.getComment());
        difficulty.setValue(tourLog.getDifficulty());
        rating.setRating(tourLog.getRating());
        String[] time = tourLog.getDuration().split("h");
        hour.getValueFactory().setValue(Integer.parseInt(time[0]));
        minute.getValueFactory().setValue(Integer.parseInt(time[1].substring(0,2)));
    }
}