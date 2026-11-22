package localization;

import java.util.Objects;

public final class LanguageCatalog {
    
    public LanguageCatalog(String configResource) {
        Objects.requireNonNull(configResource, "configResource must not be null");
    }
}
