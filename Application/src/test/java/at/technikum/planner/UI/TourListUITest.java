package at.technikum.planner.UI;

import at.technikum.planner.FXMLDependencyInjection;
import at.technikum.planner.model.Tour;
import at.technikum.planner.view.TourListController;
import at.technikum.planner.viewmodel.TourListViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ListViewMatchers;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class TourListUITest extends ApplicationTest {

    private TourListViewModel tourListViewModel;
    private ResourceBundle bundle;

    @Start
    public void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load("Application/src/main/resources/at/technikum/planner/view/MainWindow.fxml", Locale.GERMAN, null);
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }


    @Test
    void addTour(FxRobot robot) {
        int sizebefore  = robot.lookup("#tourList").queryListView().getItems().size();
        robot.clickOn("#addButton");
        assertEquals(robot.lookup("#tourList").queryListView().getItems().size(), sizebefore+1);
    }


//    @Test
//    void deleteTour(FxRobot robot) {
//        robot.clickOn("#addTour");
//        int sizebefore  = robot.lookup("#listView").queryListView().getItems().size();
//        robot.lookup("#listView").queryListView().getSelectionModel().selectLast();
//        robot.clickOn("#deleteTour");
//        assertEquals(robot.lookup("#listView").queryListView().getItems().size(), sizebefore-1);
//    }
}
