package i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class MessagesTest {

    // TC1
    @Test
    public void constructor_nullBaseName_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
        () -> new Messages(null, Locale.ENGLISH));
    }

    // TC2
    @Test
    public void constructor_nullLocale_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
            () -> new Messages("i18n/test_messages", null));
    }

    // TC3
    @Test
    public void constructor_missingBundle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new Messages("i18n/does_not_exist", Locale.ENGLISH));
    }

    // TC4
    @Test
    public void constructor_existingBundle_createsMessages() {
        Messages messages = assertDoesNotThrow(
            () -> new Messages("i18n/test_messages", Locale.ENGLISH));
        assertNotNull(messages);
    }
}
