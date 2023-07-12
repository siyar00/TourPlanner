package at.technikum.planner.functionality;

import at.technikum.bl.WeatherServiceImpl;
import at.technikum.dal.dto.CoordinatesDto;
import at.technikum.dal.dto.GeoCodingResponse;
import at.technikum.dal.dto.WeatherResponse;
import at.technikum.dal.repository.WeatherAPI;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class WeatherServiceImplTest {

    @Mock
    private WeatherAPI weatherAPI;

    private WeatherServiceImpl weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherServiceImpl();
        weatherService.api = weatherAPI;
    }

//    @Test
//    public void testGetWeatherData_Successful() throws IOException {
//        // Arrange
//        double lat = 48.2082;
//        double lng = 16.3738;
//        CoordinatesDto coordinatesDto = new CoordinatesDto(lat, lng);
//
//        // Create a mock WeatherResponse
//        WeatherResponse weatherResponseMock = new WeatherResponse();
//        weatherResponseMock.setName("Vienna");
//
//        // Create a mock GeoCodingResponse
//        GeoCodingResponse geoCodingResponse = new GeoCodingResponse();
//        geoCodingResponse.setName("Austria");
//        List<GeoCodingResponse> geoResponses = new ArrayList<>();
//        geoResponses.add(geoCodingResponse);
//
//        // Create a mock Call object for weather and set up its behavior
//        Call<WeatherResponse> weatherCall = mock(Call.class);
//        Response<WeatherResponse> weatherResponse = Response.success(weatherResponseMock);
//        when(weatherCall.execute()).thenReturn(weatherResponse);
//
//        // Create a mock Call object for geocoding and set up its behavior
//        Call<List<GeoCodingResponse>> geoCall = mock(Call.class);
//        Response<List<GeoCodingResponse>> geoResponse = Response.success(geoResponses);
//        when(geoCall.execute()).thenReturn(geoResponse);
//
//        // Set up the mock behavior for the weatherAPI
//        when(weatherAPI.getWeather(lat, lng, "metric", System.getProperty("user.language"), "API_KEY")).thenReturn(weatherCall);
//        when(weatherAPI.getGeocoding(lat, lng, 1, "API_KEY")).thenReturn(geoCall);
//
//        // Act
//        WeatherResponse result = weatherService.getWeatherData(coordinatesDto);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(weatherResponseMock, result);
//        assertEquals("Vienna, Austria", result.getName());
//        verify(weatherAPI).getWeather(lat, lng, "metric", System.getProperty("user.language"), "API_KEY");
//        verify(weatherAPI).getGeocoding(lat, lng, 1, "API_KEY");
//        verify(weatherCall).execute();
//        verify(geoCall).execute();
//    }



    @Test
    public void testGetWeatherData_IOException() throws IOException {
        // Arrange
        double lat = 48.2082;
        double lng = 16.3738;
        CoordinatesDto coordinatesDto = new CoordinatesDto(lat, lng);

        // Create a mock Call object and throw an IOException when executed
        Call<WeatherResponse> weatherCall = mock(Call.class);
        when(weatherAPI.getWeather(lat, lng, "metric", System.getProperty("user.language"), "38d517f493c8bf2614d070dd381502ef")).thenReturn(weatherCall);
        when(weatherCall.execute()).thenThrow(new IOException("Network error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> weatherService.getWeatherData(coordinatesDto));
        verify(weatherAPI).getWeather(lat, lng, "metric", System.getProperty("user.language"), "38d517f493c8bf2614d070dd381502ef");
        verify(weatherCall).execute();
    }

    // Additional tests can be added here

}
