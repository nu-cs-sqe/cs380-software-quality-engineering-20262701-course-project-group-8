package localization;

import java.util.Objects;

public final class LanguageCatalog {
    
    public LanguageCatalog(String configResource) {
        Objects.requireNonNull(configResource, "configResource must not be null");
        if (LanguageCatalog.class.getClassLoader().getResource(configResource) == null) {
            throw new IllegalArgumentException("Config resource not found" + configResource);
        }
    }
}
