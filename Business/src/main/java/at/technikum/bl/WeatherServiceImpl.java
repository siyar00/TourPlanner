package at.technikum.bl;

import at.technikum.dal.dto.CoordinatesDto;
import at.technikum.dal.dto.WeatherResponse;
import at.technikum.dal.repository.WeatherApi;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.logging.Logger;

@Log4j2
public class WeatherServiceImpl implements WeatherService {
    Logger LOGGER = Logger.getLogger(WeatherServiceImpl.class.getName());

    private final WeatherApi api;

    public WeatherServiceImpl() {
        this.api = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(WeatherApi.class);
    }

    @Override
    public WeatherResponse getWeatherData(CoordinatesDto coordinatesDto) {
        try {
            Response<WeatherResponse> response = api.getWeather(coordinatesDto.getLat(), coordinatesDto.getLng(), "metric", System.getProperty("user.language"), "38d517f493c8bf2614d070dd381502ef").execute();
            assert response.body() != null;
            LOGGER.info(response.body().toString());
            return response.body();
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        WeatherService weatherService = new WeatherServiceImpl();
        CoordinatesDto coordinatesDto = new CoordinatesDto();
        coordinatesDto.setLat(69.65139);
        coordinatesDto.setLng(18.95606);
        WeatherResponse response = weatherService.getWeatherData(coordinatesDto);
        System.out.println(response.getWeather().get(0).getDescription() + " mit gefühlten " + response.getMain().getFeels_like() + " Grad Celsius");
    }
}
