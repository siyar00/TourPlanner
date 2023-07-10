package at.technikum.planner.view;

import at.technikum.planner.viewmodel.RouteMapViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class RouteMapController {

    @FXML
    Label tourDescriptionLabel;
    @FXML
    Label startingAddressLabel;
    @FXML
    Label destinationAddressLabel;
    @FXML
    Label tollLabel;
    @FXML
    Label highwayLabel;
    @FXML
    Label timeLabel;
    @FXML
    Label distanceLabel;
    @FXML
    Label transportationLabel;
    @FXML
    Text transportation;
    @FXML
    Text toll;
    @FXML
    Text highway;
    @FXML
    Text time;
    @FXML
    Text distance;
    @FXML
    Text destinationAddress;
    @FXML
    Text startingAddress;
    @FXML
    Text tourDescription;
    @FXML
    ImageView mapImage;
    @FXML
    BorderPane borderPane;
    @SuppressWarnings("unused")
    @FXML
    TourLogsController tourLogsController;
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
