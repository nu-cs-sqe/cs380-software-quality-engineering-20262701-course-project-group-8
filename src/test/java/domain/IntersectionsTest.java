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
        NullPointerException exception = assertThrows(NullPointerException.class, () -> Intersections.addBuilding(location, building));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 2
    @Test
    public void addBuildings_locationIsValidBuildingIsCity_addsBuilding() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.CITY;
        assertDoesNotThrow(() -> Intersections.addBuilding(location, building));
    }
 
    // TC 3
    @Test
    public void addBuildings_locationIsNullBuildingIsNull_throwsNullPointerException() {
        Location location = EasyMock.createMock(Location.class);
        Building building = null;
        NullPointerException exception = assertThrows(NullPointerException.class, () -> Intersections.addBuilding(location, building));
        assertEquals("Error: Building cannot be a null value", exception.getMessage());
    }

    // TC 4
    @Test
    public void getBuilding_locationIsNull_throwsNullPointerException() {
        Location location = null;
        NullPointerException exception = assertThrows(NullPointerException.class, () -> Intersections.getBuilding(location));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 5
    @Test
    public void getBuilding_locationIsValid_ReturnsBuildingAtLocationObject() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.ROAD;

        Intersections.addBuilding(location, building);
        Building expected = building;
        Building actual = Intersections.getBuilding(location);
        assertEquals(expected, actual);
    }

    // TC 6
    @Test
    public void getNeighboringHexes_locationIsNull_throwNullPointerException() {
        Location location = null;
        NullPointerException exception = assertThrows(NullPointerException.class, () -> Intersections.getNeighboringHexes(location));
        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 8
    @Test
    public void addHex_locationIsNullHexIsValid_throwNullPointerException() {
        Location location = null;
        Hex hex = EasyMock.createMock(Hex.class);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> Intersections.addHex(location, hex));
        assertEquals("Error: Location cannot be a null value", exception.getMessage()); 
        
    }


}