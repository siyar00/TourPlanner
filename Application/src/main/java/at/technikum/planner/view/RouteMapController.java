package at.technikum.planner.view;

import at.technikum.planner.viewmodel.RouteMapViewModel;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class RouteMapController {

    @FXML
    private Label tourDescriptionLabel;
    @FXML
    private Label startingAddressLabel;
    @FXML
    private Label destinationAddressLabel;
    @FXML
    private Label tollLabel;
    @FXML
    private Label highwayLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label transportationLabel;
    @FXML
    private  Text transportation;
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
    private Text tourDescription;
    @FXML
    private ImageView mapImage;
    @FXML
    private BorderPane borderPane;
    @SuppressWarnings("unused")
    @FXML
    private TourLogsController tourLogsController;
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
        tourDescription.textProperty().bindBidirectional(viewModel.getTourDescription());
        tourDescriptionLabel.visibleProperty().bindBidirectional(viewModel.getTourDescriptionLabel());
        startingAddressLabel.visibleProperty().bindBidirectional(viewModel.getStartingAddressLabel());
        destinationAddressLabel.visibleProperty().bindBidirectional(viewModel.getDestinationAddressLabel());
        distanceLabel.visibleProperty().bindBidirectional(viewModel.getDistanceLabel());
        timeLabel.visibleProperty().bindBidirectional(viewModel.getTimeLabel());
        highwayLabel.visibleProperty().bindBidirectional(viewModel.getHighwayLabel());
        tollLabel.visibleProperty().bindBidirectional(viewModel.getTollLabel());
        transportationLabel.visibleProperty().bindBidirectional(viewModel.getTransportationLabel());

        ChangeListener<Number> sizeChangeListener = (observable, oldValue, newValue) -> {
            mapImage.setFitWidth(borderPane.getScene().getWidth()-200);
            mapImage.setFitHeight(borderPane.getScene().getHeight()-150);
        };
        borderPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener(sizeChangeListener);
                newScene.heightProperty().addListener(sizeChangeListener);
            }
        });
    }

}
