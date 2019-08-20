package mouamle.generator.classes;

import java.io.Serializable;

public class Button implements Serializable {

    private final String text;
    private final String value;

    public Button(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Button{" +
                "text='" + text + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
