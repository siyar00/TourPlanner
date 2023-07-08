package at.technikum.planner.model;

public enum RouteType {
    FASTEST("fastest"),
    SHORTEST("shortest"),
    PEDESTRIAN("pedestrian"),
    BICYCLE("bicycle");

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
        throw new IllegalArgumentException("No matching RouteType for type: " + type);
    }
}
