package at.technikum.planner.functionality;

import at.technikum.bl.RouteService;
import at.technikum.bl.RouteServiceImpl;
import at.technikum.dal.dto.BoundingBoxDto;
import at.technikum.dal.dto.CoordinatesDto;
import at.technikum.dal.dto.RouteDto;
import at.technikum.dal.dto.RouteResponse;
import at.technikum.dal.repository.MapAPI;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RouteServiceImplTest {

    private RouteService routeService;
    private MapAPI mapApiMock;

    @BeforeEach
    void setup() {
        mapApiMock = mock(MapAPI.class);
        routeService = new RouteServiceImpl();
        ((RouteServiceImpl) routeService).api = mapApiMock;
    }


    @Test
    void getRoute_Success() throws IOException {
        // Prepare test data
        String start = "Start Address";
        String end = "End Address";
        String routeType = "car";
        String apiKey = "v2wMZ8xEjaxfK77wG7lijMRNCEH47yxz";
        RouteDto routeDto = new RouteDto(/* Route data */);

        // Create a mock Response with the desired routeDto
        Response<RouteResponse> responseMock = Response.success(new RouteResponse(routeDto));

        // Set up the mock API call and response
        Call<RouteResponse> apiCallMock = mock(Call.class);
        when(apiCallMock.execute()).thenReturn(responseMock);
        when(mapApiMock.getRoute(start, end, routeType, "k", apiKey)).thenReturn(apiCallMock);

        // Invoke the method to be tested
        RouteDto result = routeService.getRoute(start, end, routeType);

        // Verify the expected behavior and outcome
        assertNotNull(result);
        assertEquals(routeDto, result);
        verify(apiCallMock).execute();
        verify(mapApiMock).getRoute(start, end, routeType, "k", apiKey);
    }

    @Test
    void getRoute_APIError() throws IOException {
        // Prepare test data
        String start = "Start Address";
        String end = "End Address";
        String routeType = "car";
        String apiKey = "v2wMZ8xEjaxfK77wG7lijMRNCEH47yxz";

        // Set up the mock API call to throw an IOException
        Call<RouteResponse> apiCallMock = mock(Call.class);
        when(apiCallMock.execute()).thenThrow(new IOException());
        when(mapApiMock.getRoute(start, end, routeType, "k", apiKey)).thenReturn(apiCallMock);

        // Invoke the method to be tested and verify the exception is propagated
        assertThrows(RuntimeException.class, () -> routeService.getRoute(start, end, routeType));
        verify(apiCallMock).execute();
        verify(mapApiMock).getRoute(start, end, routeType, "k", apiKey);
    }
}