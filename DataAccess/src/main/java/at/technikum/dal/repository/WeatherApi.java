package at.technikum.dal.repository;

import at.technikum.dal.dto.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeather(@Query("lat") Double lat, @Query("lon") Double lon, @Query("units") String units, @Query("lang") String lang, @Query("appid") String key);

}
