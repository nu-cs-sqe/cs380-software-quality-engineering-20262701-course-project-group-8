package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;

public class PlayerTest {

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
        Player player = new Player("Alice", PlayerColor.RED);
        String expected = "Alice";
        String actual = player.getName();
        assertEquals(expected, actual);
    }

    // TC7
    @Test
    public void getColor_colorIsValid_returnsColor() {
        Player player = new Player("Alice", PlayerColor.RED);
        PlayerColor expected = PlayerColor.RED;
        PlayerColor actual = player.getColor();
        assertEquals(expected, actual);
    }

    // TC8
    @Test
    public void addResource_resourceIsNullAmountIsValid_throwNullPointerException() {
        Player player = new Player("Alice", PlayerColor.RED);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> player.addResource(null, 1));
        assertEquals("Error: Resource cannot be a null value", exception.getMessage());
    }

    // TC9
    @Test
    public void addResource_resourceIsValidAmountIsNegative_throwIllegalArgumentException() {
        Player player = new Player("Alice", PlayerColor.RED);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.addResource(ResourceType.WOOL, -1));
        assertEquals("Error: Amount cannot be a non-positive value", exception.getMessage());
    }

    // TC10
    public void addResource_resourceIsValidAmountIsZero_throwIllegalArgumentException() {
        Player player = new Player("Alice", PlayerColor.RED);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> player.addResource(ResourceType.WOOL, 0));
        assertEquals("Error: Amount cannot be a non-positive value", exception.getMessage()); 
    }

    // TC11
    public void addResource_resourceIsValidAmountIsOne_addToZeroCurrentSupply_returnPlayerHasOneOfThisResourceType() {
        Player player = new Player("Alice", PlayerColor.RED);
        player.addResource(ResourceType.WOOL, 1);
        int expected = 1;
        int actual = player.getResourceCount(ResourceType.WOOL);
        assertEquals(expected, actual);
    }

    // TC19
    @Test
    public void getResourceCount_resourceIsNull_throwsNullPointerException() {
        Player player = new Player("Alice", PlayerColor.RED);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> player.getResourceCount(null));
        assertEquals("Error: Resource cannot be a null value", exception.getMessage());
    }

    // TC20
    @Test
    public void getResourceCount_resourceIsBrickPlayerHasNoBrick_returnZero() {
        Player player = new Player("Alice", PlayerColor.RED);
        int expected = 0;
        int actual = player.getResourceCount(ResourceType.BRICK);
        assertEquals(expected, actual);
    }

}
