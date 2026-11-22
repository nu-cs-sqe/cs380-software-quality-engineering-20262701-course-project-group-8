# BVA Analysis: `Messages`

## Design Under Test

```java
package i18n;

public class Messages {
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public Messages(String baseName, Locale locale)
    public String get(String key)
    public String format(String key, Object... args)
    public Locale getLocale()
}
```

Resource files (UTF-8, read with `ResourceBundle`):
- `src/main/resources/i18n/messages.properties`: default bundle, English
- `src/main/resources/i18n/messages_ja.properties`: Japanese

Test resource files (so tests do not depend on the real game text):
- `src/test/resources/i18n/test_messages.properties`
  - `greeting=Hello`
  - `farewell=Goodbye`
  - `roll={0} rolled {1}`
  - `quote=It''s {0}`
- `src/test/resources/i18n/test_messages_ja.properties`
  - `greeting=こんにちは`
  - `roll={0}は{1}を出しました`
  - (no `farewell` and no `quote`, to test fallback for a single key)

- `null` arguments -> `NullPointerException`.
- `baseName` with no bundle on the classpath -> `IllegalArgumentException`.
- The constructor loads `baseName_<language>` for `locale`. If that bundle does not exist, it uses the default bundle `baseName.properties` (English).
- The lookup must **not** fall back to the JVM default locale (`Locale.getDefault()`). Otherwise the result would depend on the machine the game runs on. The implementation uses `ResourceBundle.Control.getNoFallbackControl(FORMAT_PROPERTIES)`.
- `getLocale()` returns `locale` when its bundle exists, otherwise `DEFAULT_LOCALE`.
- `get()` returns the text for `key`. If the locale bundle does not have the key, the text comes from the default bundle (normal `ResourceBundle` parent chain). If neither bundle has the key -> `MissingResourceException`.
- `format()` fills the `{0}`, `{1}`, ... placeholders with `java.text.MessageFormat`. Extra arguments are ignored. A placeholder without an argument stays as text (for example `{1}`). A single quote in a pattern must be written as `''`.

## Step 1-3: Analysis

### Method: `Messages(String baseName, Locale locale)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `baseName` | Pointer | `null`, non-null |
| Input | `baseName` | Cases | bundle exists, bundle does not exist |
| Input | `locale` | Pointer | `null`, non-null |
| Input | `locale` | Cases | has its own bundle (`ja`), default language (`en`), no bundle (`fr`) |
| Input | JVM default locale | Cases | same as the default bundle, has its own bundle (`ja`) |
| Output | new object, or exception | Cases | created, `NullPointerException`, `IllegalArgumentException` |

### Method: `getLocale()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | locale passed to the constructor | Cases | has a bundle, no bundle |
| Output | | Cases | requested locale, `DEFAULT_LOCALE` |

### Method: `get(String key)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `key` | Pointer | `null`, non-null |
| Input | `key` | String | empty `""`, non-empty |
| Input | where the key exists | Cases | locale bundle, only the default bundle, nowhere |
| Input | text | Cases | ASCII, non-ASCII (Japanese) |
| Output | | Cases | text, `NullPointerException`, `MissingResourceException` |

### Method: `format(String key, Object... args)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `key` | Pointer | `null`, non-null |
| Input | `key` | Cases | exists, missing |
| Input | number of `args` compared to placeholders | Interval | 0, fewer, exactly, more |
| Input | pattern | Cases | no quote, escaped quote `''` |
| Output | | Cases | formatted text, `NullPointerException`, `MissingResourceException` |

## Step 4: Test Cases

### Method under test: `Messages(String baseName, Locale locale)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 1  | `baseName` is `null`, `locale` is `ENGLISH` | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 2  | `baseName` is `"i18n/test_messages"`, `locale` is `null` | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 3  | `baseName` is `"i18n/does_not_exist"`, `locale` is `ENGLISH` | `IllegalArgumentException` is thrown | :white_check_mark: |
| Test Case 4  | `baseName` is `"i18n/test_messages"`, `locale` is `ENGLISH` | A `Messages` object is created without exception | :white_check_mark: |

### Method under test: `getLocale()`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 5  | Created with `locale` `ja` (bundle exists) | Returns `ja` | :white_check_mark: |
| Test Case 6  | Created with `locale` `ENGLISH` (the default bundle) | Returns `ENGLISH` | :white_check_mark: |
| Test Case 7  | Created with `locale` `fr` (no bundle) | Returns `DEFAULT_LOCALE` (`ENGLISH`) | :white_check_mark: |

### Method under test: `get(String key)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 8  | `locale` is `ENGLISH`, `key` is `"greeting"` | Returns `"Hello"` | :white_check_mark: |
| Test Case 9  | `locale` is `ja`, `key` is `"greeting"` | Returns `"こんにちは"` (UTF-8 is read correctly) | :white_check_mark: |
| Test Case 10 | `locale` is `fr` (no bundle), `key` is `"greeting"` | Returns `"Hello"` (falls back to the default bundle) | :white_check_mark: |
| Test Case 11 | JVM default locale is set to `ja` for this test only (restored after the test); `locale` is `fr`, `key` is `"greeting"` | Returns `"Hello"`, not `"こんにちは"` (no fallback to the JVM default locale) | :white_check_mark: |
| Test Case 12 | `locale` is `ja`, `key` is `"farewell"` (only in the default bundle) | Returns `"Goodbye"` | :white_check_mark: |
| Test Case 13 | `key` is `"missing"` (in no bundle) | `MissingResourceException` is thrown | :white_check_mark: |
| Test Case 14 | `key` is `""` | `MissingResourceException` is thrown | :white_check_mark: |
| Test Case 15 | `key` is `null` | `NullPointerException` is thrown | :x: |

### Method under test: `format(String key, Object... args)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 16 | `locale` is `ENGLISH`, `key` is `"roll"`, `args` are `"Alice"`, `8` (exactly the number of placeholders) | Returns `"Alice rolled 8"` | :x: |
| Test Case 17 | `locale` is `ja`, `key` is `"roll"`, `args` are `"Alice"`, `8` | Returns `"Aliceは8を出しました"` | :x: |
| Test Case 18 | `key` is `"greeting"` (no placeholders), no `args` | Returns `"Hello"` | :x: |
| Test Case 19 | `key` is `"roll"`, `args` is `"Alice"` (fewer than the placeholders) | Returns `"Alice rolled {1}"` | :x: |
| Test Case 20 | `key` is `"roll"`, `args` are `"Alice"`, `8`, `"extra"` (more than the placeholders) | Returns `"Alice rolled 8"` | :x: |
| Test Case 21 | `key` is `"quote"`, `args` is `"Alice"` (pattern has `''`) | Returns `"It's Alice"` | :x: |
| Test Case 22 | `key` is `"missing"`, `args` is `"Alice"` | `MissingResourceException` is thrown | :x: |
| Test Case 23 | `key` is `null` | `NullPointerException` is thrown | :x: |
