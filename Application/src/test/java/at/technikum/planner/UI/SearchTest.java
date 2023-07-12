package at.technikum.planner.UI;

import at.technikum.dal.dao.TourDao;
import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.planner.FXMLDependencyInjection;
import at.technikum.planner.TourPlannerApplication;
import at.technikum.planner.model.RouteType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Test should be manually executed")
@ExtendWith(ApplicationExtension.class)
public class SearchTest {

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

    @AfterEach
    void tearDown() {
        TourDaoRepository tourDaoRepository = context.getBean(TourDaoRepository.class);
        tourDaoRepository.deleteByName(testTour.toString());
    }

    @Test
    void searchTour(FxRobot robot) {
        robot.lookup("#searchTextField").queryTextInputControl().setText(testTour.toString());
        robot.clickOn("#searchButton");

        assertEquals(1, robot.lookup("#tourNameListView").queryListView().getItems().size());
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
