package at.technikum.planner.view;

import at.technikum.planner.viewmodel.RouteMapViewModel;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class RouteMapController {

    @FXML
    private Text transportation;
    @FXML
    private Text toll;
    @FXML
    private Text highway;
    @FXML
    private Text time;
    @FXML
    private Text distance;
    @FXML
    private Text destinationAddress;
    @FXML
    private Text startingAddress;
    @FXML
    private ImageView mapImage;
    private final RouteMapViewModel viewModel;

    public RouteMapController(RouteMapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    void initialize() {
        startingAddress.textProperty().bindBidirectional(viewModel.getStartingAddress());
        destinationAddress.textProperty().bindBidirectional(viewModel.getDestinationAddress());
        distance.textProperty().bindBidirectional(viewModel.getDistance());
        time.textProperty().bindBidirectional(viewModel.getTime());
        highway.textProperty().bindBidirectional(viewModel.getHighway());
        toll.textProperty().bindBidirectional(viewModel.getToll());
        transportation.textProperty().bindBidirectional(viewModel.getTransportation());
        mapImage.imageProperty().bindBidirectional(viewModel.mapImageProperty());
    }
}
