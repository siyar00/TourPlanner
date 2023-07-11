package at.technikum.dal.repository;

import at.technikum.dal.dto.GeoCodingResponse;
import at.technikum.dal.dto.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface WeatherAPI {

    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeather(@Query("lat") Double lat, @Query("lon") Double lon, @Query("units") String units, @Query("lang") String lang, @Query("appid") String key);

    @GET("geo/1.0/reverse")
    Call<List<GeoCodingResponse>> getGeocoding(@Query("lat") Double lat, @Query("lon") Double lon, @Query("limit") int limit, @Query("appid") String key);
}
