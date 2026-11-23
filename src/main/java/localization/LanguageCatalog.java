package localization;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

public final class LanguageCatalog {
    
    public static final String LANGUAGES_KEY = "languages";

    private final Properties config;
    private final List<Locale> locales;

    public LanguageCatalog(String configResource) {
        Objects.requireNonNull(configResource, "configResource must not be null");
        this.config = loadConfig(configResource);
        String languages = config.getProperty(LANGUAGES_KEY);
        if (languages == null) {
            throw new IllegalStateException(
                "Missing key '" + LANGUAGES_KEY + "' in " + configResource);
        }
        List<Locale> parsed = new ArrayList<>();
        for (String code : languages.split(",")) {
            String trimmed = code.trim();
            if (!trimmed.isEmpty()) {
                parsed.add(Locale.forLanguageTag(trimmed));
            }
        }
        if (parsed.isEmpty()) {
            throw new IllegalStateException(
                "No language codes in '" + LANGUAGES_KEY + "' in " + configResource);
        }
        this.locales = List.copyOf(parsed);
    }

    private static Properties loadConfig(String configResource) {
        ClassLoader loader = LanguageCatalog.class.getClassLoader();
        try (InputStream in = loader.getResourceAsStream(configResource)) {
            if (in == null) {
                throw new IllegalArgumentException("Config resource not found: " + configResource);
            }
            Properties config = new Properties();
            config.load(in);
            return config;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read " + configResource, e);
        }
    }

    public List<Locale> getAvailableLocales() {
        return locales;
    }
}
