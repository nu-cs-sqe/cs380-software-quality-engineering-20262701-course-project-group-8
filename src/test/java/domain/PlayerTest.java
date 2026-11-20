package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

public class PlayerTest {
    private Player player;
    @BeforeEach
    public void setUp() {
        player = new Player("Alice", PlayerColor.RED);
    }
    // TC1
    @Test
    public void constructor_nameISNullColorIsValid_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Player(null, PlayerColor.RED));
        assertEquals("Error: Name cannot be a null value", exception.getMessage());  
    }

    // TC2
    @Test
    public void constructor_nameIsEmptyColorIsValid_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player("", PlayerColor.RED));
        assertEquals("Error: Name cannot be an empty value", exception.getMessage());  
    }

    // TC3
    @Test
    public void constructor_nameIsBlankColorIsValid_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player(" ", PlayerColor.RED));
        assertEquals("Error: Name cannot be an empty value", exception.getMessage());  
    }

    // TC4
    @Test
    public void constructor_nameIsValidColorIsNull_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Player("Alice", null));
        assertEquals("Error: Color cannot be a null value", exception.getMessage());  
    }

    // TC5
    @Test
    public void constructor_nameIsValidColorIsValid_returnsPlayerObject() {
        assertDoesNotThrow(() -> new Player("Alice",  PlayerColor.RED));
    }

    // TC6
    @Test
    public void getName_nameIsValid_returnsName() {
        String expected = "Alice";
        String actual = player.getName();
        assertEquals(expected, actual);
    }

    // TC7
    @Test
    public void getColor_colorIsValid_returnsColor() {
        PlayerColor expected = PlayerColor.RED;
        PlayerColor actual = player.getColor();
        assertEquals(expected, actual);
    }

    // TC8
    @Test
    public void addResource_resourceIsNullAmountIsValid_throwNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> player.addResource(null, 1));
        assertEquals("Error: Resource cannot be a null value", exception.getMessage());
    }

    // TC9
    @Test
    public void addResource_resourceIsValidAmountIsNegative_throwIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.addResource(ResourceType.WOOL, -1));
        assertEquals("Error: Amount cannot be a non-positive value", exception.getMessage());
    }

    // TC10
    @Test
    public void addResource_resourceIsValidAmountIsZero_throwIllegalArgumentException() {
        Player player = new Player("Alice", PlayerColor.RED);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.addResource(ResourceType.WOOL, 0));
        assertEquals("Error: Amount cannot be a non-positive value", exception.getMessage()); 
    }

    // TC11
    @Test
    public void addResource_resourceIsValidAmountIsOne_addToZeroCurrentSupplyOfZero_returnPlayerHasOneOfThisResourceType() {
        player.addResource(ResourceType.WOOL, 1);
        int expected = 1;
        int actual = player.getResourceCount(ResourceType.WOOL);
        assertEquals(expected, actual);
    }

    // TC12
    @Test
    public void addResource_resourceIsValidAmountIsOne_addOneToCurrentSupplyOfOne_returnPlayerHasTwoOfThisResourceType() {
        player.addResource(ResourceType.WOOL, 1);
        player.addResource(ResourceType.WOOL, 1);
        int expected = 2;
        int actual = player.getResourceCount(ResourceType.WOOL);
        assertEquals(expected, actual);
    }

    // TC13
    @Test
    public void removeResource_resourceIsNullAmountIsValid_throwNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> player.removeResource(null, 1));
        assertEquals("Error: Resource cannot be a null value", exception.getMessage());
    }

    // TC14
    @Test
    public void removeResource_resourceIsValidAmountIsNegative_playerHasTwoWoolInSupply_throwIllegalArgumentException() {
        player.addResource(ResourceType.WOOL, 2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.removeResource(ResourceType.WOOL, -1));
        assertEquals("Error: Amount cannot be a non-positive value", exception.getMessage());
    }

    // TC15
    @Test
    public void removeResource_resourceIsValidAmountIsZero_playerHasTwoWoolInSupply_throwIllegalArgumentException() {
        player.addResource(ResourceType.WOOL, 2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.removeResource(ResourceType.WOOL, 0));
        assertEquals("Error: Amount cannot be a non-positive value", exception.getMessage()); 
    }

    // TC16
    @Test
    public void removeResource_resourceIsWoolAmountIsOne_playerHasTwoWoolInSupply_returnPlayerHasOneWoolInSupply() {
        player.addResource(ResourceType.WOOL, 2); 
        player.removeResource(ResourceType.WOOL, 1);
        int expected = 1;
        int actual = player.getResourceCount(ResourceType.WOOL);
        assertEquals(expected, actual);
    }

    // TC17
    @Test
    public void removeResource_resourceIsWoolAmountIsTwo_playerHasTwoWoolInSupply_returnPlayerHasZeroSupply() {
        player.addResource(ResourceType.WOOL, 2); 
        player.removeResource(ResourceType.WOOL, 2);
        int expected = 0;
        int actual = player.getResourceCount(ResourceType.WOOL);
        assertEquals(expected, actual);       
    }

    // TC18 
    @Test
    public void removeResource_resourceIsWoolAmountIsThree_playerHasTwoWoolInSupply_throwIllegalStateException() {
        player.addResource(ResourceType.WOOL, 2);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> player.removeResource(ResourceType.WOOL, 3));
        assertEquals("Not enough " + ResourceType.WOOL + " to remove", exception.getMessage());
    }

    // TC19
    @Test
    public void getResourceCount_resourceIsNull_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> player.getResourceCount(null));
        assertEquals("Error: Resource cannot be a null value", exception.getMessage());
    }

    // TC20
    @Test
    public void getResourceCount_resourceIsBrickPlayerHasNoBrick_returnZero() {
        int expected = 0;
        int actual = player.getResourceCount(ResourceType.BRICK);
        assertEquals(expected, actual);
    }

    // TC21
    @Test
    public void getResourceCount_resourceIsBrick_playerHasZeroBrickInSupply_returnZero() {
        int expected = 0;
        int actual = player.getResourceCount(ResourceType.BRICK);
        assertEquals(expected, actual);
    }

    // TC22
    @Test
    public void addVictoryPoints_pointsIsNegative_playerHasZeroVictoryPoints_throwIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.addVictoryPoints(-1));
        assertEquals("Error: Points cannot be a non-positive value", exception.getMessage());
    }

    // TC23
    @Test
    public void addVictoryPoints_pointsIsZero_playerHasZeroVictoryPoints_throwIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.addVictoryPoints(0));
        assertEquals("Error: Points cannot be a non-positive value", exception.getMessage());
    }

    // TC24
    @Test
    public void addVictoryPoints_pointsIs1_playerHasZeroVictoryPoints_returnPlayerVictoryPointsIsOne() {
        player.addVictoryPoints(1);
        int expected = 1;
        int actual = player.getVictoryPoints();
        assertEquals(expected, actual);
    }

    // TC25
    @Test
    public void addVictoryPoints_pointsIs1_playerHas9VictoryPoints_returnPlayerHas10VictoryPoints() {
        player.addVictoryPoints(9);
        player.addVictoryPoints(1);
        int expected = 10;
        int actual = player.getVictoryPoints();
        assertEquals(expected, actual);
    }

    // TC26
    @Test
    public void getVictoryPoints_playerHasZeroVictoryPoints_returnZero() {
        int expected = 0;
        int actual = player.getVictoryPoints();
        assertEquals(expected, actual);
    }

    // TC27
    @Test
    public void getVictoryPoints_playerHas10VictoryPoints_return10() {
        player.addVictoryPoints(10);
        int expected = 10;
        int actual = player.getVictoryPoints();
        assertEquals(expected, actual);
    }

    // TC28
    @Test
    public void getCityCount_playerHasZeroCities_returnZero() {
        int expected = 0;
        int actual = player.getCityCount();
        assertEquals(expected, actual);
    }

    // TC29
    @Test
    public void getCityCount_playerHasOneCity_returnOne() {
        Location location = EasyMock.createMock(Location.class);
        player.intersections.addBuilding(location, Building.CITY);
        int expected = 1;
        int actual = player.getCityCount();
        assertEquals(expected, actual);
    }

    // TC30
    @Test
    public void getCityCount_playerHasFourCities_returnFour() {
        Location location1 = EasyMock.createMock(Location.class);
        Location location2 = EasyMock.createMock(Location.class);
        Location location3 = EasyMock.createMock(Location.class);
        Location location4 = EasyMock.createMock(Location.class);

        player.intersections.addBuilding(location1, Building.CITY);
        player.intersections.addBuilding(location2, Building.CITY);
        player.intersections.addBuilding(location3, Building.CITY);
        player.intersections.addBuilding(location4, Building.CITY);

        int expected = 4;
        int actual = player.getCityCount();
        assertEquals(expected, actual);
    }

    // TC31
    @Test
    public void getCityLocations_playerHasZeroCities_returnEmptyList() {
        List<Location> actual = player.getCityLocations();
        assertTrue(actual.isEmpty());
    }
    
    // TC32
    @Test
    public void getCityLocations_playerHasOneCity_returnListConsistingOfOneCity() {
        Location location = EasyMock.createMock(Location.class);
        player.intersections.addBuilding(location, Building.CITY);
        List<Location> expected = new ArrayList<>(List.of(location));
        List<Location> actual = player.getCityLocations();
        assertEquals(expected, actual);
    }
}
