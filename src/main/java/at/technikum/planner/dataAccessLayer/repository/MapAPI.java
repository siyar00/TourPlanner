package at.technikum.planner.dataAccessLayer.repository;

import at.technikum.planner.dataAccessLayer.dataTransferObject.RouteResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MapAPI {

    @GET("directions/v2/route")
    Call<RouteResponse> getRoute(@Query("from") String start, @Query("to") String end, @Query("unit") String unit, @Query("key") String key);

    @GET("staticmap/v5/map")
    Call<ResponseBody> getMap(@Query("session") String session, @Query("size") String size, @Query("key") String key);
}
