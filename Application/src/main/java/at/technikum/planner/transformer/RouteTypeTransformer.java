package at.technikum.planner.transformer;

import at.technikum.planner.model.RouteType;
import at.technikum.planner.viewmodel.TourModalViewModel;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RouteTypeTransformer {
    Logger LOGGER = Logger.getLogger(TourModalViewModel.class.getName());

    public RouteType getRouteTypeFromBundle(String value, ResourceBundle bundle) {
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

    public String getBundleFromRouteType(RouteType value, ResourceBundle bundle) {
        if (value.equals(RouteType.FASTEST)) {
            return bundle.getString("RouteType_CarFastest");
        } else if (value.equals(RouteType.SHORTEST)) {
            return bundle.getString("RouteType_CarShortest");
        } else if (value.equals(RouteType.BICYCLE)) {
            return bundle.getString("RouteType_Bicycle");
        } else if (value.equals(RouteType.PEDESTRIAN)) {
            return bundle.getString("RouteType_Pedestrian");
        } else {
            LOGGER.log(Level.SEVERE, "RouteType not found");
            throw new IllegalArgumentException("RouteType not found");
        }
    }
}
