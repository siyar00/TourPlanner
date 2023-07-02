package at.technikum.planner.view;

import at.technikum.planner.viewmodel.RouteMapViewModel;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


public class RouteMapController {

    @FXML
    private Text generalContentText;

    private final RouteMapViewModel viewModel;

    public RouteMapController(RouteMapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public RouteMapViewModel getViewModel() {
        return viewModel;
    }

    @FXML
    void initialize() {
        generalContentText.textProperty().bindBidirectional(viewModel.contenteProperty());
    }
}
