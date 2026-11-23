package localization;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class LanguageCatalogTest {

    // TC1
    @Test
    public void constructor_nullConfigResource_throwsNullPointerException() {
        assertThrows(NullPointerException.class, 
            () -> new LanguageCatalog(null));
    }

    // TC2
    @Test 
    public void constructor_missingConfigResource_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new LanguageCatalog("i18n/does_not_exist.properties"));
    }

    // TC3
    @Test
    public void constructor_missingLanguagesKey_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, 
            () -> new LanguageCatalog("i18n/languages_no_key.properties"));
    }

    // TC4
    @Test
    public void constructor_blankLanguagesList_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class,
            () -> new LanguageCatalog("i18n/languages_blank.properties"));
    }

    // TC5
    @Test
    public void constructor_validConfigWithTwoCodes_createsObject() {
        assertDoesNotThrow(
            () -> new LanguageCatalog("i18n/languages_two.properties"));
    }

    // TC6
    @Test
    public void getAvailableLocales_oneCode_returnsSingleLocale() {
        LanguageCatalog catalog = new LanguageCatalog("i18n/languages_one.properties");
        assertEquals(List.of(Locale.forLanguageTag("en")), catalog.getAvailableLocales());
    }

    // TC7
    @Test 
    public void getAvailableLocales_twoCodes_returnsLocalesInFileOrder() {
        LanguageCatalog catalog = new LanguageCatalog("i18n/languages_two.properties");

        assertEquals(
            List.of(Locale.forLanguageTag("en"), Locale.forLanguageTag("ja")),
            catalog.getAvailableLocales());
    }
}
