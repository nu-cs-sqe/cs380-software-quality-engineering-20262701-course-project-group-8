package i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Messages {
    
    private final Locale locale;

    private static final ResourceBundle.Control NO_FALLBACK = 
        ResourceBundle.Control.getNoFallbackControl(
            ResourceBundle.Control.FORMAT_PROPERTIES);

    public Messages(String baseName, Locale locale) {
        Objects.requireNonNull(baseName, "baseName must not be null");
        Objects.requireNonNull(locale, "locale must not be null");
        try {
            ResourceBundle.getBundle(baseName, locale, NO_FALLBACK);
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException(
                "No resource  bundle found for " + baseName, e);
        }
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
