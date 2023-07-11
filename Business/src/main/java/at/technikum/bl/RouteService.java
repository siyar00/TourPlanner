package at.technikum.bl;

import at.technikum.dal.dto.BoundingBoxDto;
import at.technikum.dal.dto.RouteDto;

public interface RouteService {
    RouteDto getRoute(String start, String end, String routeType);

    String getImage(String sessionId, BoundingBoxDto boundingBox);
}
