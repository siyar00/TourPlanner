package at.technikum.planner.businessLayer;

import at.technikum.planner.dataAccessLayer.dataTransferObject.RouteResponse;
import at.technikum.planner.dataAccessLayer.repository.MapAPI;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class RouteServiceImpl implements RouteService {

    private final MapAPI api;

    public RouteServiceImpl() {
        this.api = new Retrofit.Builder().baseUrl("https://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(MapAPI.class);
    }

    @Override
    public String getRoute(String start, String end) {
        try {
            Response<RouteResponse> response = api.getRoute(start, end, "k", "v2wMZ8xEjaxfK77wG7lijMRNCEH47yxz").execute();
            assert response.body() != null;
            return response.body().getRoute().getSessionId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getImage(String sessionId) {
        try {
            Response<ResponseBody> response = api.getMap(sessionId, "600,400", "v2wMZ8xEjaxfK77wG7lijMRNCEH47yxz").execute();
            return storeResponseInFile(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String storeResponseInFile(Response<ResponseBody> response) {
        try (ResponseBody body = response.body()) {
            byte[] bytes = body.bytes();
            String uuid = UUID.randomUUID().toString();
            FileOutputStream fos = new FileOutputStream("src/main/resources/at/technikum/planner/downloads/" + uuid + ".png");
            fos.write(bytes);
            fos.close();
            return uuid;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
