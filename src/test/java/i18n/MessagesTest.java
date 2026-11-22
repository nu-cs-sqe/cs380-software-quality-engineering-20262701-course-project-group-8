package i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
    public void get_englishKey_returnsText() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertEquals("Hello", messages.get("greeting"));
    }
    
    // TC9
    @Test 
    public void get_japaneseKey_returnsUtf8Text() {
        Messages messages = new Messages("i18n/test_messages", Locale.JAPANESE);
        assertEquals("こんにちは", messages.get("greeting"));
    }

    // TC10
    @Test
    public void get_localeWithoutBundle_fallsBackToDefaultBundle() {
        Messages messages = new Messages("i18n/test_messages", Locale.FRENCH);
        assertEquals("Hello", messages.get("greeting"));
    }

    // TC11
    @Test
    public void get_jvmDefaultIsJapanese_doesNotFallBackToJvmDefault() {
        Locale original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.JAPANESE);
            ResourceBundle.clearCache();
            Messages messages = new Messages("i18n/test_messages", Locale.FRENCH);
            assertEquals("Hello", messages.get("greeting"));
        } finally {
            Locale.setDefault(original);
        }
    }

    // TC12
    @Test
    public void get_keyOnlyInDefaultBundle_fallsBackForThatKey() {
        Messages messages = new Messages("i18n/test_messages", Locale.JAPANESE);
        assertEquals("Goodbye", messages.get("farewell"));
    }

    // TC13
    @Test
    public void get_keyInNoBundle_throwsMissingResourceException() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertThrows(MissingResourceException.class, 
            () -> messages.get("missing"));
    }

    // TC14
    @Test
    public void get_emptyKey_throwsMissingResourceException() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertThrows(MissingResourceException.class, 
            () -> messages.get(""));
    }

    // TC15
    @Test
    public void get_nullKey_throwsNullPointerException() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertThrows(NullPointerException.class,
            () -> messages.get(null));
    }

    // TC16
    @Test
    public void format_exactArgs_fillsPlaceholders() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertEquals("Alice rolled 8", messages.format("roll", "Alice", 8));
    }

    // TC17
    @Test
    public void format_japanese_fillsPlaceholders() {
        Messages messages = new Messages("i18n/test_messages", Locale.JAPANESE);
        assertEquals("Aliceは8を出しました", messages.format("roll", "Alice", 8));
    }

    // TC18
    @Test
    public void format_noPlaceholdersNoArgs_returnsTest() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertEquals("Hello", messages.format("greeting"));
    }

    // TC19
    @Test
    public void format_fewerArgs_leavesPlaceholderAsText() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertEquals("Alice rolled {1}", messages.format("roll", "Alice"));
    }

    // TC20
    @Test
    public void format_moreArgs_ignoresExtraArgs() {
        Messages messages = new Messages("i18n/test_messages", Locale.ENGLISH);
        assertEquals("Alice rolled 8", messages.format("roll", "Alice", 8, "extra"));
    }
}
