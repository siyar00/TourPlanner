package at.technikum.bl;


import at.technikum.dal.dto.CoordinatesDto;
import at.technikum.dal.dto.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeatherData(CoordinatesDto coordinatesDto);
}
