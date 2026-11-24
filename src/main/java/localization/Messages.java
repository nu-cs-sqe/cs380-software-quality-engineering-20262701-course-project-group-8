package localization;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public final class Messages {
    
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    private final ResourceBundle bundle;
    private final Locale locale;

    private static final ResourceBundle.Control NO_FALLBACK = 
        ResourceBundle.Control.getNoFallbackControl(
            ResourceBundle.Control.FORMAT_PROPERTIES);

    public Messages(String baseName, Locale locale) {
        Objects.requireNonNull(baseName, "baseName must not be null");
        Objects.requireNonNull(locale, "locale must not be null");
        this.bundle = loadBundle(baseName, locale);
        this.locale = resolveLocale(bundle);
    }

    public Locale getLocale() {
        return locale;
    }
    
    public String get(String key) {
        return bundle.getString(key);
    }
    
    public String format(String key, Object... args) {
        return new MessageFormat(get(key), locale).format(args);
    }

    private static ResourceBundle loadBundle(String baseName, Locale locale) {
        try {
            return ResourceBundle.getBundle(baseName, locale, NO_FALLBACK);
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("No resource bundle found for " + baseName, e);
        }
    }

    private static Locale resolveLocale(ResourceBundle bundle) {
        Locale loaded = bundle.getLocale();
        if (Locale.ROOT.equals(loaded)) {
            return DEFAULT_LOCALE;
        }
        return loaded;
    }
}
