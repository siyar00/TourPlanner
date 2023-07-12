package at.technikum.planner;

import at.technikum.planner.config.ApplicationConfigProperties;
import at.technikum.planner.view.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * FXMLLoader with Dependency-Injection
 * based on <a href="https://edencoding.com/dependency-injection/">https://edencoding.com/dependency-injection/</a>
 */
public class FXMLDependencyInjection {
    public static Parent load(String location, Locale locale, ConfigurableApplicationContext context) throws IOException {
        FXMLLoader loader = getLoader(location, locale, context);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location, Locale locale, ConfigurableApplicationContext context) {
        ResourceBundle bundle = ResourceBundle.getBundle("at.technikum.planner.gui_strings", locale);
        context.getBean(ApplicationConfigProperties.class).setBundle(bundle);
        return new FXMLLoader(
                FXMLDependencyInjection.class.getResource("/at/technikum/planner/view/" + location), bundle,
                new JavaFXBuilderFactory(),
                controllerClass -> ControllerFactory.getInstance(context, bundle).create(controllerClass));
    }
}
