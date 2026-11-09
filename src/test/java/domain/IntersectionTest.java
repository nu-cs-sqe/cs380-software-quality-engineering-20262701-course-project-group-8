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
        assertNotNull(intersection);
    }
 
    // TC 3
    @Test
    public void constructor_locationIsNullBuildingIsNull_throwsNullPointerException() {
        Location location = EasyMock.createMock(Location.class);
        Building building = null;
        
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Intersection(location, building));
        assertEquals("Error: Building cannot be a null value", exception.getMessage());
    }

    // TC 4
    @Test
    public void getBuilding_locationIsNull_throwsNullPointerException() {
        Location location1 = EasyMock.createMock(Location.class);
        Location location2 = null;
        Building building = Building.CITY;
        
        Intersection intersection = assertDoesNotThrow(() -> new Intersection(location1, building));
        assertNotNull(intersection); 

        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersection.getBuilding(location2));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 5
    @Test
    public void getBuilding_locationIsValid_ReturnsBuildingAtLocationObject() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.ROAD;

        Intersection intersection = new Intersection(location, building);
        Building expected = building;
        Building actual = intersection.getBuilding(location);
        assertEquals(expected, actual);
    }

    // TC 6
    @Test
    public void getNeighboringHexes_locationIsNull_throwNullPointerException() {
        Location location1 = EasyMock.createMock(Location.class);
        Location location2 = null;
        Building building = Building.CITY;
        
        Intersection intersection = assertDoesNotThrow(() -> new Intersection(location1, building));
        assertNotNull(intersection); 

        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersection.getNeighboringHexes(location2));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }
}