package at.technikum.planner.view;

import at.technikum.bl.PDFServiceImpl;
import at.technikum.planner.transformer.TourPlannerToTourBlTransformer;
import at.technikum.planner.viewmodel.MainWindowViewModel;
import at.technikum.planner.viewmodel.TourListViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import jfxtras.styles.jmetro.Style;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

@Data
public class MainWindowController {
    @FXML
    private VBox scene;
    @FXML
    private SearchBarController searchBarController;
    @FXML
    private TourListController tourListController;
    @FXML
    private RouteMapController routeMapController;
    private double x, y;
    private final MainWindowViewModel mainWindowViewModel;
    private final TourListViewModel tourListViewModel;
    Logger LOGGER = Logger.getLogger(MainWindowController.class.getName());

    public MainWindowController(MainWindowViewModel mainWindowViewModel, TourListViewModel tourListViewModel) {
        this.mainWindowViewModel = mainWindowViewModel;
        this.tourListViewModel = tourListViewModel;
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import TourBl");
        try {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), File.separator + "Downloads"));
        } catch (IllegalArgumentException exception) {
            LOGGER.warning("Could not set initial directory to downloads folder");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
        var file = fileChooser.showOpenDialog(scene.getScene().getWindow());
        if (file != null) {
            alert(mainWindowViewModel.importTour(file));
            LOGGER.info("Imported file successfully");
        }
    }

    private void alert(List<String> strings) {
        if (strings.isEmpty()) return;
        LOGGER.info("Alerting user about already existing tours");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.initOwner(scene.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setContentText("Die folgenden Touren sind schon vorhanden:\n " + String.join("\n", strings) + "\n\nFürs importieren dieser Touren bitte die vorhandenen Touren löschen oder umbenennen.");
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void exportFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Export TourBl");
        try {
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home"), File.separator + "Downloads"));
        } catch (IllegalArgumentException exception) {
            LOGGER.warning("Could not set initial directory to downloads folder");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        var directory = directoryChooser.showDialog(scene.getScene().getWindow());
        if (directory != null) {
            mainWindowViewModel.exportTour(directory);
        }
    }

    @SuppressWarnings("unused")
    @FXML
    void onDragged(MouseEvent mouseEvent) {
//        Stage stage = (Stage) scene.getScene().getWindow();
//        stage.setX(mouseEvent.getScreenX() - x);
//        stage.setY(mouseEvent.getScreenY() - y);
    }

    @SuppressWarnings("unused")
    @FXML
    void onMousePressed(MouseEvent mouseEvent) {
//        x = mouseEvent.getSceneX();
//        y = mouseEvent.getSceneY();
    }

    @FXML
    void onGenerateTourReportClicked() {
        if (tourListController.getTourNameListView().getSelectionModel().getSelectedItem() == null) return;
        PDFServiceImpl reportService = new PDFServiceImpl(new TourPlannerToTourBlTransformer().apply(tourListController.getTourNameListView().getSelectionModel().getSelectedItem()));
        reportAlert(reportService.generateReport());
    }

    @FXML
    void onGenerateSummarizeReport() {
        PDFServiceImpl reportService = new PDFServiceImpl(tourListViewModel.getObservableTours().stream().map(new TourPlannerToTourBlTransformer()).toList());
        reportAlert(reportService.generateSummarizeReport());
    }

    private void reportAlert(boolean success) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(scene.getScene().getWindow());
        alert.setHeaderText(null);
        if (success) {
            alert.setContentText("Report wurde erfolgreich erstellt.");
            LOGGER.info("Report created successfully");
        } else {
            LOGGER.warning("Could not create report");
            alert.setContentText("Report konnte nicht erstellt werden.");
        }
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
}
