module at.technikum.planner {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires lombok;
    requires com.fasterxml.jackson.annotation;


    opens at.technikum.planner.view to javafx.fxml;
    opens at.technikum.planner to javafx.fxml;
    exports at.technikum.planner;
    exports at.technikum.planner.view;
}