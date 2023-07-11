package at.technikum.bl;


import at.technikum.dal.dto.CoordinatesDto;

public interface WeatherService {
    String getWeatherData(CoordinatesDto coordinatesDto);
}
