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
}
