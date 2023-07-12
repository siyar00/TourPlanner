package at.technikum.planner.UI;

import at.technikum.dal.repository.TourDaoRepository;
import at.technikum.planner.FXMLDependencyInjection;
import at.technikum.planner.TourPlannerApplication;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.h2.store.fs.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Test should be manually executed")
@ExtendWith(ApplicationExtension.class)
public class AddTest {

    private final ConfigurableApplicationContext context = SpringApplication.run(TourPlannerApplication.class);
    private final UUID testName = UUID.randomUUID();

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        FileUtils.createDirectories(System.getProperty("user.dir").concat("/downloads"));
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
    void addTour(FxRobot robot) {
        int sizeBefore = robot.lookup("#tourNameListView").queryListView().getItems().size();
        robot.clickOn("#addButton");

        robot.lookup("#tourName").queryTextInputControl().setText(testName.toString());
        robot.lookup("#tourStartAddress").queryTextInputControl().setText("Wien");
        robot.lookup("#tourEndAddress").queryTextInputControl().setText("Graz");
        robot.interact(() -> {
            ComboBox<String> transportComboBox = robot.lookup("#transportComboBox").queryComboBox();
            transportComboBox.getSelectionModel().selectFirst();
        });
        robot.interact(() -> {
            TextArea tourDescriptionTextArea = robot.lookup("#tourDescription").queryAs(TextArea.class);
            if (tourDescriptionTextArea != null) {
                tourDescriptionTextArea.setText("TestDescription");
            }
        });

        robot.lookup("#okayButton").queryButton().setDisable(false);
        robot.clickOn("#okayButton");

        synchronized (robot) {
            try {
                robot.wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        assertNotNull(robot.lookup("#startingAddress"));
        assertNotNull(robot.lookup("#destinationAddress"));
        assertEquals(sizeBefore + 1, robot.lookup("#tourNameListView").queryListView().getItems().size());

        TourDaoRepository tourDaoRepository = context.getBean(TourDaoRepository.class);
        tourDaoRepository.deleteByName(testName.toString());
        FileUtils.deleteRecursive(System.getProperty("user.dir").concat("/downloads"), true);
    }

    @AfterTestClass
    public void tearDown() {
        FileUtils.deleteRecursive(System.getProperty("user.dir").concat("/downloads"), true);
        context.close();
    }
}
