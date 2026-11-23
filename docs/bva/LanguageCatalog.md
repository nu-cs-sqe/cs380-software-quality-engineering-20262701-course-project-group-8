# BVA Analysis: `LanguageCatalog`

## Design Under Test

```java
package localization;

public class LanguageCatalog {
    public static final String LANGUAGES_KEY = "languages";

    public LanguageCatalog(String configResource)
    public List<Locale> getAvailableLocales()
    public Locale getLocale(int index)
}
```

The list of languages lives in a config file, not in Java code, so a new language can be added without changing code (grading rubric, level A).

Real config file: `src/main/resources/i18n/languages.properties`
```properties
languages=en,ja
```

To add a new language (for example French):
1. Add `src/main/resources/i18n/messages_fr.properties` with every key from `messages.properties`.
2. Change the config to `languages=en,ja,fr`.

Each `messages_xx.properties` has a `language.name` key (for example `language.name=日本語`). The language menu shows each language by its own name, so the menu needs no extra code either.

Test config files in `src/test/resources/i18n/`:
- `languages_two.properties`: `languages=en,ja`
- `languages_one.properties`: `languages=en`
- `languages_spaces.properties`: `languages= en , ,ja `
- `languages_blank.properties`: `languages=`
- `languages_no_key.properties`: `other=en`

Rules:
- `null` argument -> `NullPointerException`.
- `configResource` not found on the classpath -> `IllegalArgumentException`.
- The `languages` key is missing, or the list has no language codes -> `IllegalStateException`.
- Codes are separated by commas. Spaces around a code are trimmed and empty entries are skipped.
- Each code becomes a locale with `Locale.forLanguageTag(code)`. The order in the file is kept, so the first code is the first menu option.
- `getAvailableLocales()` returns an unmodifiable list.
- `getLocale(index)` uses a 0-based index in [0, size - 1]. Outside that range -> `IndexOutOfBoundsException`. `Main` turns the player's menu choice (1, 2, ...) into this index.

## Step 1-3: Analysis

### Method: `LanguageCatalog(String configResource)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `configResource` | Pointer | `null`, non-null |
| Input | `configResource` | Cases | found, not found |
| Input | `languages` key | Cases | present, missing |
| Input | number of language codes | Interval [1, ∞) | 0, 1, 2 |
| Input | code format | Cases | clean, spaces around codes, empty entry |
| Output | new object, or exception | Cases | created, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

### Method: `getAvailableLocales()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | number of codes in the config | Interval [1, ∞) | 1, 2 |
| Output | list of locales | Collection | 1 locale, 2 locales in file order, unmodifiable |

### Method: `getLocale(int index)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `index` | Interval [0, size - 1] | -1, 0, size - 1, size |
| Output | | Cases | locale, `IndexOutOfBoundsException` |

## Step 4: Test Cases

### Method under test: `LanguageCatalog(String configResource)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 1  | `configResource` is `null` | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 2  | `configResource` is `"i18n/does_not_exist.properties"` | `IllegalArgumentException` is thrown | :white_check_mark: |
| Test Case 3  | `configResource` is `"i18n/languages_no_key.properties"` (no `languages` key) | `IllegalStateException` is thrown | :white_check_mark: |
| Test Case 4  | `configResource` is `"i18n/languages_blank.properties"` (0 codes) | `IllegalStateException` is thrown | :white_check_mark: |
| Test Case 5  | `configResource` is `"i18n/languages_two.properties"` | A `LanguageCatalog` object is created without exception | :white_check_mark: |

### Method under test: `getAvailableLocales()`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 6  | Created from `languages_one.properties` (1 code) | Returns `[en]` | :white_check_mark: |
| Test Case 7  | Created from `languages_two.properties` (2 codes) | Returns `[en, ja]` in this order | :white_check_mark: |
| Test Case 8  | Created from `languages_spaces.properties` (`" en , ,ja "`) | Returns `[en, ja]` (spaces trimmed, empty entry skipped) | :white_check_mark: |
| Test Case 9  | Created from `languages_two.properties`; the caller tries to `add()` to the returned list | `UnsupportedOperationException` is thrown | :white_check_mark: |

### Method under test: `getLocale(int index)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 10 | Created from `languages_two.properties`, `index` is 0 (minimum) | Returns `en` | :white_check_mark: |
| Test Case 11 | Created from `languages_two.properties`, `index` is 1 (size - 1, maximum) | Returns `ja` | :white_check_mark: |
| Test Case 12 | Created from `languages_two.properties`, `index` is -1 | `IndexOutOfBoundsException` is thrown | :white_check_mark: |
| Test Case 13 | Created from `languages_two.properties`, `index` is 2 (size) | `IndexOutOfBoundsException` is thrown | :white_check_mark: |
