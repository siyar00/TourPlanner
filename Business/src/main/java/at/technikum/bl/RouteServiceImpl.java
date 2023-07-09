package at.technikum.bl;

import at.technikum.dal.dto.BoundingBoxDto;
import at.technikum.dal.dto.RouteDto;
import at.technikum.dal.dto.RouteResponse;
import at.technikum.dal.repository.MapAPI;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class RouteServiceImpl implements RouteService {

    private final MapAPI api;

    public RouteServiceImpl() {
        this.api = new Retrofit.Builder().baseUrl("https://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(MapAPI.class);
    }

    @Override
    public RouteDto getRoute(String start, String end, String routeType) {
        try {
            Response<RouteResponse> response = api.getRoute(start, end, routeType, "k", "v2wMZ8xEjaxfK77wG7lijMRNCEH47yxz").execute();
            assert Objects.requireNonNull(response.body()).getRoute() != null;
            return response.body().getRoute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getImage(String sessionId, BoundingBoxDto boundingBox) {
        try {
            Response<ResponseBody> response;
            if (boundingBox.getLr().equals(boundingBox.getUl()))
                response = api.getMapBySize(sessionId, "600,600", "12", "v2wMZ8xEjaxfK77wG7lijMRNCEH47yxz").execute();
            else
                response = api.getMap(sessionId, boundingBox.toString(), "v2wMZ8xEjaxfK77wG7lijMRNCEH47yxz").execute();
            return storeResponseInFile(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String storeResponseInFile(Response<ResponseBody> response) {
        try (ResponseBody body = response.body()) {
            byte[] bytes = body.bytes();
            String uuid = UUID.randomUUID().toString();
            FileOutputStream fos = new FileOutputStream("downloads/" + uuid + ".png");
            fos.write(bytes);
            fos.close();
            return uuid;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
