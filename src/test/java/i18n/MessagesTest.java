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
}
