package at.technikum.planner.transformer;

import at.technikum.planner.model.RouteType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

public class RouteTypeTransformerTest {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("at/technikum/planner/gui_strings_de");

    @Test
    public void testGetRouteTypeFromBundle() {
        RouteType routeType1 = RouteTypeTransformer.getRouteTypeFromBundle("Schnellste Auto-Route", bundle);
        Assertions.assertEquals(RouteType.FASTEST, routeType1);

        RouteType routeType2 = RouteTypeTransformer.getRouteTypeFromBundle("Kürzeste Auto-Route", bundle);
        Assertions.assertEquals(RouteType.SHORTEST, routeType2);

        RouteType routeType3 = RouteTypeTransformer.getRouteTypeFromBundle("Fahrrad", bundle);
        Assertions.assertEquals(RouteType.BICYCLE, routeType3);

        RouteType routeType4 = RouteTypeTransformer.getRouteTypeFromBundle("Fußgänger", bundle);
        Assertions.assertEquals(RouteType.PEDESTRIAN, routeType4);
    }

    @Test
    public void testGetBundleFromRouteType() {
        String value1 = RouteTypeTransformer.getBundleFromRouteType(RouteType.FASTEST, bundle);
        Assertions.assertEquals("Schnellste Auto-Route", value1);

        String value2 = RouteTypeTransformer.getBundleFromRouteType(RouteType.SHORTEST, bundle);
        Assertions.assertEquals("Kürzeste Auto-Route", value2);

        String value3 = RouteTypeTransformer.getBundleFromRouteType(RouteType.BICYCLE, bundle);
        Assertions.assertEquals("Fahrrad", value3);

        String value4 = RouteTypeTransformer.getBundleFromRouteType(RouteType.PEDESTRIAN, bundle);
        Assertions.assertEquals("Fußgänger", value4);
    }

    @Test
    public void testGetRouteTypeFromBundleWithNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> RouteTypeTransformer.getRouteTypeFromBundle("null", bundle));
    }

}
