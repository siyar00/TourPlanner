module at.technikum.planner {
    requires javafx.controls;
    requires javafx.fxml;
            
                        
    opens at.technikum.planner to javafx.fxml;
    exports at.technikum.planner;
}