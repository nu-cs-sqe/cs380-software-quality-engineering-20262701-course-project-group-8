package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;

public class IntersectionTest {

    // TC 1
    @Test
    public void constructor_locationIsNullBuildingIsACity_throwsNullPointerException() {
        Location location = null;
        Building building = Building.CITY;
        
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Intersection(location, building));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }
    // TC 2
    @Test
    public void constructor_locationIsValidBuildingIsCity_constructsIntersection() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.CITY;

        Intersection intersection = assertDoesNotThrow(() -> new Intersection(location, building));
        assertNotNull(location);
    }
  
}