package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;

public class IntersectionTest {

    // TC 1
    @Test
    public void constructor_validLocationNullBuilding_throwsNullPointerException() {
        Location location = EasyMock.createMock(Location.class);
        // EasyMock.
    }
}