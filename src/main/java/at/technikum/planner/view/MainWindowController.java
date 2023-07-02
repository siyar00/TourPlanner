package at.technikum.planner.view;

import at.technikum.planner.viewmodel.MainWindowViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

public class MainWindowController {
    @FXML
    private VBox scene;
    @FXML
    private SearchBarController searchBarController;
    @FXML
    private TourListController tourListController;
    @FXML
    private TourLogsController tourLogsController;
    @FXML
    private RouteMapController routeMapController;

    private final MainWindowViewModel mainWindowViewModel;

    public MainWindowController(MainWindowViewModel mainWindowViewModel) {
        this.mainWindowViewModel = mainWindowViewModel;
    }

    public MainWindowViewModel getMainWindowViewModel() {
        return mainWindowViewModel;
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
}
