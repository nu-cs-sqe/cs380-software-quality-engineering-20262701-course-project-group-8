package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

public class IntersectionsTest {

    // TC 1
    @Test
    public void addBuilding_locationIsNullBuildingIsACity_throwsNullPointerException() {
        Location location = null;
        Building building = Building.CITY;
        Intersections intersections = new Intersections();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersections.addBuilding(location, building));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 2
    @Test
    public void addBuildings_locationIsValidBuildingIsCity_constructsIntersections() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.CITY;
        Intersections intersections = new Intersections();
        assertDoesNotThrow(() -> intersections.addBuilding(location, building));
    }
 
    // TC 3
    @Test
    public void addBuildings_locationIsNullBuildingIsNull_throwsNullPointerException() {
        Location location = EasyMock.createMock(Location.class);
        Building building = null;
        Intersections intersections = new Intersections();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersections.addBuilding(location, building));
        assertEquals("Error: Building cannot be a null value", exception.getMessage());
    }

    // TC 4
    @Test
    public void getBuilding_locationIsNull_throwsNullPointerException() {
        Location location1 = EasyMock.createMock(Location.class);
        Location location2 = null;
        Building building = Building.CITY;
        
        Intersections intersection = new Intersections();
        assertDoesNotThrow(() -> intersection.addBuilding(location1, building));
        assertNotNull(intersection); 

        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersection.getBuilding(location2));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 5
    @Test
    public void getBuilding_locationIsValid_ReturnsBuildingAtLocationObject() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.ROAD;

        Intersections intersection = new Intersections();
        intersection.addBuilding(location, building);
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
        
        Intersections intersections = new Intersections();
        assertDoesNotThrow(() -> intersections.addBuilding(location1, building));
        assertNotNull(intersections); 

        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersections.getNeighboringHexes(location2));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }


}