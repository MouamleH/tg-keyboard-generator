package mouamle.generator;

import mouamle.generator.annotations.handlers.ButtonValue;
import mouamle.generator.annotations.handlers.IntValue;
import mouamle.generator.annotations.handlers.StringValue;
import mouamle.generator.classes.ButtonHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyboardGenerator {

    private static final String noData = "*";
    private List<List<ButtonHolder>> keyboard = new ArrayList<>();

    private static KeyboardGenerator instance = new KeyboardGenerator();

    public static KeyboardGenerator getInstance() {
        return instance;
    }
    
    private KeyboardGenerator(){}

    /**
     * Creates an tg-keyboard from a template object in which it's fields are annotated with @IntValue, @StringValue
     *
     * @param obj an object that it fields are annotated with @IntValue, @StringValue
     */
    public synchronized List<List<ButtonHolder>> generateKeyboard(Object obj) throws IllegalAccessException {
        Class cClass = obj.getClass();

        Field[] fields = cClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(IntValue.class)) {
                handleIntValue(obj, field, field.getDeclaredAnnotation(IntValue.class));
            } else if (field.isAnnotationPresent(StringValue.class)) {
                handleStringValue(obj, field, field.getDeclaredAnnotation(StringValue.class));
            } else if (field.isAnnotationPresent(ButtonValue.class)) {
                handleButtonValue(obj, field, field.getDeclaredAnnotation(ButtonValue.class));
            }
        }

        return keyboard;
    }

    /**
     * Generates an callback button
     *
     * @param o           the object instance
     * @param field       the given field
     * @param buttonValue the annotation of the field
     */
    private void handleButtonValue(Object o, Field field, ButtonValue buttonValue) {
        String valueKey = buttonValue.key();

        String data = String.format("buttonValue;%s;%s;%s", field.getName(), valueKey, buttonValue.callbackText());
        keyboard.add(Collections.singletonList(new ButtonHolder(buttonValue.text(), data)));
    }

    /**
     * Generates the buttons for the given field
     *
     * @param o        the object instance
     * @param field    the given field
     * @param intValue the annotation of the field
     */
    private void handleIntValue(Object o, Field field, IntValue intValue) throws IllegalAccessException {
        String valueKey = intValue.key();
        addValueHeader(valueKey);

        List<ButtonHolder> row = new ArrayList<>();

        String incrementData = String.format("intValue;%s;%s;+%s", field.getName(), valueKey, intValue.increment());
        String decrementData = String.format("intValue;%s;%s;-%s", field.getName(), valueKey, intValue.decrement());

        row.add(new ButtonHolder("-", decrementData));
        row.add(new ButtonHolder(String.valueOf(field.getInt(o)), noData));
        row.add(new ButtonHolder("+", incrementData));

        keyboard.add(row);
    }

    /**
     * Generates the buttons for the given field
     *
     * @param o           the object instance
     * @param field       the given field
     * @param stringValue the annotation of the field
     */
    private void handleStringValue(Object o, Field field, StringValue stringValue) {
        String valueKey = stringValue.key();
        addValueHeader(valueKey);

        List<ButtonHolder> row = new ArrayList<>();

        String[] values = stringValue.values();
        for (String value : values) {
            String data = String.format("stringValue;%s;%s;%s", field.getName(), valueKey, value);
            row.add(new ButtonHolder(value, data));
        }

        keyboard.add(row);
    }

    /**
     * Adds the value header to the keyboard
     *
     * @param text the text of the value header
     */
    private void addValueHeader(String text) {
        keyboard.add(Collections.singletonList(new ButtonHolder(String.format("-= %s =-", text), noData)));
    }

}
