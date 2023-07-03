package at.technikum.planner.view;

import at.technikum.planner.viewmodel.RouteMapViewModel;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class RouteMapController {

    @FXML
    private ImageView mapImage;
    @FXML
    private Text generalContentText;
    private final RouteMapViewModel viewModel;

    public RouteMapController(RouteMapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    void initialize() {
        generalContentText.textProperty().bindBidirectional(viewModel.contenteProperty());
        mapImage.imageProperty().bindBidirectional(viewModel.mapImageProperty());
    }

    public RouteMapViewModel getViewModel() {
        return viewModel;
    }

}
