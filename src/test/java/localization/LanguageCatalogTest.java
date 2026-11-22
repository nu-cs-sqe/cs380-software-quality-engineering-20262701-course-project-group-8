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
}
