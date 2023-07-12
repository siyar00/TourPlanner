package at.technikum.planner.UI;

import at.technikum.dal.dao.TourDao;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.planner.FXMLDependencyInjection;
import at.technikum.planner.TourPlannerApplication;
import at.technikum.planner.model.RouteType;
import at.technikum.planner.model.Tour;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Test should be manually executed")
@ExtendWith(ApplicationExtension.class)
public class RemoveTest {
    private static final ConfigurableApplicationContext context = SpringApplication.run(TourPlannerApplication.class);
    private static final UUID testTour = UUID.randomUUID();

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        TourDaoRepository tourDaoRepository = context.getBean(TourDaoRepository.class);
        tourDaoRepository.save(getTourDao());
        Parent root = FXMLDependencyInjection.load("MainWindow.fxml", Locale.forLanguageTag(System.getProperties().getProperty("user.language")), context);
        Scene scene = new Scene(root);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        stage.setScene(jMetro.getScene());
        stage.setTitle("Dora the Explorer");
        stage.getIcons().add(new Image(Objects.requireNonNull(TourPlannerApplication.class.getResourceAsStream("images/dora.png"))));
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    @Test
    public void removeTest(FxRobot robot) {
        int sizeBefore = robot.lookup("#tourNameListView").queryListView().getItems().size();
        ObservableList<Object> selectedItems = robot.lookup("#tourNameListView").queryListView().getItems();

        List<Tour> selectedTours = new ArrayList<>();
        for (Object item : selectedItems) {
            selectedTours.add((Tour) item);
        }

        int index = -1;
        for (int i = 0; i < selectedTours.size(); i++) {
            Tour tour = selectedTours.get(i);
            if (tour.getName().contains(testTour.toString())) {
                index = i;
                break;
            }
        }

        robot.lookup("#tourNameListView").queryListView().getSelectionModel().select(index);
        robot.clickOn("#removeButton");

        robot.clickOn("OK");
        int sizeAfter = robot.lookup("#tourNameListView").queryListView().getItems().size();
        assertEquals(sizeBefore - 1, sizeAfter);

    }

    private TourDao getTourDao() {
        return TourDao.builder()
                .name(testTour.toString())
                .image(new byte[0])
                .start("Vienna")
                .destination("Graz")
                .distance("200 km")
                .time("01:20")
                .popularity("Very popular")
                .childFriendly("Very child friendly")
                .description("Test")
                .description("Test")
                .hasHighway(String.valueOf(false))
                .hasTollRoad(String.valueOf(false))
                .transportation(RouteType.BICYCLE.getType())
                .build();
    }
}
