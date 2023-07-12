package at.technikum.planner.functionality;

import at.technikum.dal.dto.RouteResponse;
import at.technikum.dal.repository.MapAPI;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MapAPITest {

    @Mock
    private MapAPI mapAPI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoute_Successful() throws Exception {
        // Arrange
        String start = "Start Location";
        String end = "End Location";
        String routeType = "car";
        String unit = "km";
        String key = "API_KEY";

        // Create a mock Call object and set up its behavior
        Call<RouteResponse> mockCall = mock(Call.class);
        when(mapAPI.getRoute(start, end, routeType, unit, key)).thenReturn(mockCall);

        // Create a mock response
        RouteResponse routeResponse = new RouteResponse();
        Response<RouteResponse> response = Response.success(routeResponse);
        when(mockCall.execute()).thenReturn(response);

        // Act
        RouteResponse result = mapAPI.getRoute(start, end, routeType, unit, key).execute().body();

        // Assert
        assertNotNull(result);
        assertEquals(routeResponse, result);
        verify(mapAPI).getRoute(start, end, routeType, unit, key);
        verify(mockCall).execute();
    }

    @Test
    public void testGetMap_Successful() throws Exception {
        // Arrange
        String session = "SessionID";
        String boundingBox = "10,20,30,40";
        String key = "API_KEY";

        // Create a mock Call object and set up its behavior
        Call<ResponseBody> mockCall = mock(Call.class);
        when(mapAPI.getMap(session, boundingBox, key)).thenReturn(mockCall);

        // Create a mock response
        ResponseBody responseBody = mock(ResponseBody.class);
        Response<ResponseBody> response = Response.success(responseBody);
        when(mockCall.execute()).thenReturn(response);

        // Act
        ResponseBody result = mapAPI.getMap(session, boundingBox, key).execute().body();

        // Assert
        assertNotNull(result);
        assertEquals(responseBody, result);
        verify(mapAPI).getMap(session, boundingBox, key);
        verify(mockCall).execute();
    }

    @Test
    public void testGetMapBySize_Successful() throws Exception {
        // Arrange
        String session = "SessionID";
        String size = "500x500";
        String zoom = "10";
        String key = "API_KEY";

        // Create a mock Call object and set up its behavior
        Call<ResponseBody> mockCall = mock(Call.class);
        when(mapAPI.getMapBySize(session, size, zoom, key)).thenReturn(mockCall);

        // Create a mock response
        ResponseBody responseBody = mock(ResponseBody.class);
        Response<ResponseBody> response = Response.success(responseBody);
        when(mockCall.execute()).thenReturn(response);

        // Act
        ResponseBody result = mapAPI.getMapBySize(session, size, zoom, key).execute().body();

        // Assert
        assertNotNull(result);
        assertEquals(responseBody, result);
        verify(mapAPI).getMapBySize(session, size, zoom, key);
        verify(mockCall).execute();
    }

    // Additional tests can be added here

}
