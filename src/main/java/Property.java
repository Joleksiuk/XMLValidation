import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.regex.Pattern;

public class Property {
    private static final Pattern ISSUE_KEY_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{3}$");

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        if ("issue-key".equals(name) && (value == null || !ISSUE_KEY_PATTERN.matcher(value).matches())) {
            throw new IllegalArgumentException("Invalid issue-key format: " + value);
        }
        this.value = value;
    }
}
