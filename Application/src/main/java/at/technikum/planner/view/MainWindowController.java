package at.technikum.planner.view;

import at.technikum.planner.viewmodel.MainWindowViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import jfxtras.styles.jmetro.Style;
import lombok.Data;

@Data
public class MainWindowController {
    @FXML
    VBox scene;
    @FXML
    SearchBarController searchBarController;
    @FXML
    TourListController tourListController;
    @FXML
    RouteMapController routeMapController;
    private double x, y;
    private final MainWindowViewModel mainWindowViewModel;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MainWindowController(MainWindowViewModel mainWindowViewModel) {
        this.mainWindowViewModel = mainWindowViewModel;
    }

    @FXML
    void initialize() {
    }

    public void onMenuFileQuitClicked() {
        Platform.exit();
    }

    public void onMenuHelpAboutClicked() {
        Alert aboutBox = new Alert(Alert.AlertType.INFORMATION, "Semesterproject BIF4-SWE2\nby Siyar Y. and Joben P. B.");
        aboutBox.initOwner(scene.getScene().getWindow());
        aboutBox.setTitle("About TourPlanner");
        aboutBox.setHeaderText(null);
        aboutBox.showAndWait();
    }

    @FXML
    void changeTheme(ActionEvent actionEvent) {
        scene.getScene().getStylesheets().clear();
        if (((MenuItem) actionEvent.getSource()).getText().contains("Dark")) {
            scene.getScene().getStylesheets().add(Style.DARK.getStyleStylesheetURL());
        } else {
            scene.getScene().getStylesheets().add(Style.LIGHT.getStyleStylesheetURL());
        }
    }

    @FXML
    void importFile() {

    }

    @FXML
    void exportFile() {
    }

    @FXML
    void onDragged(MouseEvent mouseEvent) {
//        Stage stage = (Stage) scene.getScene().getWindow();
//        stage.setX(mouseEvent.getScreenX() - x);
//        stage.setY(mouseEvent.getScreenY() - y);
    }

    @FXML
    void onMousePressed(MouseEvent mouseEvent) {
//        x = mouseEvent.getSceneX();
//        y = mouseEvent.getSceneY();
    }
}
