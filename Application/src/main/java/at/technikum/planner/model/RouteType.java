package at.technikum.planner.model;

import java.util.logging.Logger;

public enum RouteType {
    FASTEST("fastest"),
    SHORTEST("shortest"),
    PEDESTRIAN("pedestrian"),
    BICYCLE("bicycle");

    private static final Logger LOGGER = Logger.getLogger(RouteType.class.getName());

    private final String type;

    RouteType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RouteType fromType(String type) {
        for (RouteType routeType : RouteType.values()) {
            if (routeType.getType().equalsIgnoreCase(type)) {
                return routeType;
            }
        }
        LOGGER.severe("No matching RouteType for type: " + type);
        throw new IllegalArgumentException("No matching RouteType for type: " + type);
    }
}
