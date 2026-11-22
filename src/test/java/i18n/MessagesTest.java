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

    // TC5
    @Test
    public void getLocale_localeWithBundle_returnsThatLocale() {
        Messages messages = new Messages("i18n/test_messages", Locale.JAPANESE);
        assertEquals(Locale.JAPANESE, messages.getLocale());
    }

    // TC6
    @Test
    public void getLocale_defaultLanguage_returnsEnglish() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertEquals(Locale.ENGLISH, messages.getLocale());
    }

    // TC7
    @Test
    public void getLocale_localeWithoutBundle_returnsDefaultLocale() {
        Messages messages = new Messages("i18n/test_messages", Locale.FRENCH);
        assertEquals(Messages.DEFAULT_LOCALE, messages.getLocale());
    }

    // TC8
    @Test
    public void get_englishKey_returnsTest() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertEquals("Hello", messages.get("greeting"));
    }
    
    // TC9
    @Test 
    public void get_japaneseKey_returnsUtf8Text() {
        Messages messages = new Messages("i18n/test_messages", Locale.JAPANESE);
        assertEquals("こんにちは", messages.get("greeting"));
    }
}
