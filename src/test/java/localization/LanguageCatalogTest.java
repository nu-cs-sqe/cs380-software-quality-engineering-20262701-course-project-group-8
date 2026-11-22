package localization;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
