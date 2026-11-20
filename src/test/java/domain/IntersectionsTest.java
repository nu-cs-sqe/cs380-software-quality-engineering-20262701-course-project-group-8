package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.easymock.EasyMock;

public class IntersectionsTest {

    private Intersections intersections;

    @BeforeEach
    public void setUp() {
        intersections = new Intersections();
    }

    // TC 1
    @Test
    public void addBuilding_locationIsNullBuildingIsACity_throwsNullPointerException() {
        Location location = null;
        Building building = Building.CITY;

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> intersections.addBuilding(location, building)
        );

        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 2
    @Test
    public void addBuildings_locationIsValidBuildingIsCity_addsBuilding() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.CITY;

        assertDoesNotThrow(() -> intersections.addBuilding(location, building));
    }

    // TC 3
    @Test
    public void addBuildings_locationIsValidBuildingIsNull_throwsNullPointerException() {
        Location location = EasyMock.createMock(Location.class);
        Building building = null;

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> intersections.addBuilding(location, building)
        );

        assertEquals("Error: Building cannot be a null value", exception.getMessage());
    }

    // TC 4
    @Test
    public void getBuilding_locationIsNull_throwsNullPointerException() {
        Location location = null;

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> intersections.getBuilding(location)
        );

        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 5
    @Test
    public void getBuilding_locationIsValid_ReturnsBuildingAtLocationObject() {
        Location location = EasyMock.createMock(Location.class);
        Building building = Building.ROAD;

        intersections.addBuilding(location, building);

        Building expected = building;
        Building actual = intersections.getBuilding(location);

        assertEquals(expected, actual);
    }

    // TC 6
    @Test
    public void getNeighboringHexes_locationIsNull_throwNullPointerException() {
        Location location = null;

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> intersections.getNeighboringHexes(location)
        );

        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 7
    @Test
    public void getNeighboringHexes_locationIsValid_returnsHexList() {
        Location center = EasyMock.createMock(Location.class);

        EasyMock.expect(center.getXPos()).andStubReturn(1);
        EasyMock.expect(center.getYPos()).andStubReturn(1);

        EasyMock.replay(center);

        Hex hexLeft = EasyMock.createMock(Hex.class);
        Hex hexRight = EasyMock.createMock(Hex.class);
        Hex hexUp = EasyMock.createMock(Hex.class);
        Hex hexDown = EasyMock.createMock(Hex.class);

        intersections.addHex(new Location(0, 1), hexLeft);
        intersections.addHex(new Location(2, 1), hexRight);
        intersections.addHex(new Location(1, 0), hexDown);
        intersections.addHex(new Location(1, 2), hexUp);

        List<Hex> actual = intersections.getNeighboringHexes(center);

        int expectedSize = 4;
        int actualSize = actual.size();

        assertEquals(expectedSize, actualSize);
        assertTrue(actual.contains(hexLeft));
        assertTrue(actual.contains(hexRight));
        assertTrue(actual.contains(hexDown));
        assertTrue(actual.contains(hexUp));
    }

    // TC 8
    @Test
    public void addHex_locationIsNullHexIsValid_throwNullPointerException() {
        Location location = null;
        Hex hex = EasyMock.createMock(Hex.class);

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> intersections.addHex(location, hex)
        );

        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 9
    @Test
    public void addHex_locationIsValidHexIsValid_addsHex() {
        Location location = EasyMock.createMock(Location.class);
        Hex hex = EasyMock.createMock(Hex.class);

        assertDoesNotThrow(() -> intersections.addHex(location, hex));
    }

    // TC 10
    @Test
    public void addHex_locationIsValidHexIsNull_throwsNullPointerException() {
        Location location = EasyMock.createMock(Location.class);
        Hex hex = null;

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> intersections.addHex(location, hex)
        );

        assertEquals("Error: Hex cannot be a null value", exception.getMessage());
    }

    // TC 11
    @Test
    public void getHex_locationIsNull_throwNullPointerException() {
        Location location = null;

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> intersections.getHex(location)
        );

        assertEquals("Error: Location cannot be a null value", exception.getMessage());
    }

    // TC 12
    @Test
    public void getHex_locationIsValid_returnsHex() {
        Location location = EasyMock.createMock(Location.class);
        Hex hex = EasyMock.createMock(Hex.class);

        intersections.addHex(location, hex);

        Hex expected = hex;
        Hex actual = intersections.getHex(location);

        assertEquals(expected, actual);
    }

    // TC 13
    @Test
    public void getBuildingCount_buildingIsNull_throwNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersections.getBuildingCount(null));
        assertEquals("Error: Building cannot be a null value", exception.getMessage());
    }

    // TC 14
    @Test
    public void getBuildingCount_buildingIsAValidRoatType_playerHasZeroRoadBuildings_returnNumberOfBuildingsOfRoadType() {
        int expected = 0;
        int actual = intersections.getBuildingCount(Building.ROAD);
        assertEquals(expected, actual);
    }

    // TC 15
    @Test
    public void getBuildingCount_buildingIsAValidRoadType_playerHasOneRoadBuilding_returnOne() {
        Location location = EasyMock.createMock(Location.class);
        intersections.addBuilding(location, Building.ROAD);
        int expected = 1;
        int actual = intersections.getBuildingCount(Building.ROAD);
        assertEquals(expected, actual);
    }

    // TC 16
    @Test
    public void getBuildingLocations_buildingIsNull_throwNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> intersections.getBuildingLocations(null));
        assertEquals("Error: Building cannot be a null value", exception.getMessage());
    }
}