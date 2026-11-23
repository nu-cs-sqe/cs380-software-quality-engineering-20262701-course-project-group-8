package localization;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;

public final class LanguageCatalog {
    
    public static final String LANGUAGES_KEY = "languages";

    private final Properties config;

    public LanguageCatalog(String configResource) {
        Objects.requireNonNull(configResource, "configResource must not be null");
        this.config = loadConfig(configResource);
        if (config.getProperty(LANGUAGES_KEY) == null) {
            throw new IllegalStateException(
                "Missing key '" + LANGUAGES_KEY + "' in " + configResource);
        }
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
}
