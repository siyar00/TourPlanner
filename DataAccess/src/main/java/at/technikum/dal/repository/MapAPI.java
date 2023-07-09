package at.technikum.dal.repository;

import at.technikum.dal.dto.RouteResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MapAPI {

    @GET("directions/v2/route")
    Call<RouteResponse> getRoute(@Query("from") String start, @Query("to") String end, @Query("routeType") String routeType, @Query("unit") String unit, @Query("key") String key);

    @GET("staticmap/v5/map")
    Call<ResponseBody> getMap(@Query("session") String session, @Query("boundingBox") String boundingBox, @Query("key") String key);

    @GET("staticmap/v5/map")
    Call<ResponseBody> getMapBySize(@Query("session") String session, @Query("size") String size, @Query("zoom") String zoom, @Query("key") String key);
}
