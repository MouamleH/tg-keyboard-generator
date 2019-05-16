package mouamle.generator.classes;

import java.io.Serializable;

public class ButtonHolder implements Serializable {

    private final String text;
    private final String data;

    public ButtonHolder(String text, String data) {
        this.text = text;
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ButtonHolder{" +
                "text='" + text + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

}
