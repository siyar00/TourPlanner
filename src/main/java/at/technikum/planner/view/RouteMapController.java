package at.technikum.planner.view;

import at.technikum.planner.viewmodel.RouteMapViewModel;
import javafx.fxml.FXML;

public class RouteMapController {

    private final RouteMapViewModel viewModel;

    public RouteMapController(RouteMapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public RouteMapViewModel getViewModel() {
        return viewModel;
    }

    @FXML
    void initialize() {
    }
}
