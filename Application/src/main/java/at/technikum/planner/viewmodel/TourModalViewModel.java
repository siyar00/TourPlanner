package at.technikum.planner.viewmodel;

import at.technikum.planner.model.RouteType;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TourModalViewModel {
    Logger LOGGER = Logger.getLogger(TourModalViewModel.class.getName());

    public RouteType getRouteType(String value, ResourceBundle bundle) {
        if (value.equals(bundle.getString("RouteType_CarFastest"))) {
            return RouteType.FASTEST;
        } else if (value.equals(bundle.getString("RouteType_CarShortest"))) {
            return RouteType.SHORTEST;
        } else if (value.equals(bundle.getString("RouteType_Bicycle"))) {
            return RouteType.BICYCLE;
        } else if (value.equals(bundle.getString("RouteType_Pedestrian"))) {
            return RouteType.PEDESTRIAN;
        } else {
            LOGGER.log(Level.SEVERE, "RouteType not found");
            throw new IllegalArgumentException("RouteType not found");
        }
    }
}
