package at.technikum.planner.businessLayer;

public interface RouteService {
    String getRoute(String start, String end);
    String getImage(String sessionId);
}
