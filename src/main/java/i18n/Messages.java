package i18n;

import java.util.Locale;
import java.util.Objects;

public class Messages {
    
    public Messages(String baseName, Locale locale) {
        Objects.requireNonNull(baseName, "baseName must not be null");
    }
}
