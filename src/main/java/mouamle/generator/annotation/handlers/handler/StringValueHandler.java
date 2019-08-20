package mouamle.generator.annotation.handlers.handler;

import mouamle.generator.annotation.handlers.value.StringValue;
import mouamle.generator.classes.ButtonHolder;
import mouamle.registry.AnnotationHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringValueHandler implements AnnotationHandler<StringValue> {

    @Override
    public List<List<ButtonHolder>> generate(StringValue stringValue, Object o, Field field) throws Exception {
        String valueKey = stringValue.key();

        List<List<ButtonHolder>> part = new ArrayList<>();
        if (!stringValue.header().isEmpty()) {
            addValueHeader(stringValue.header(), part);
        }

        switch (stringValue.orientation()) {
            case StringValue.Horizontal:
                generateHorizontal(stringValue, field, valueKey, part);
                break;
            case StringValue.Vertical:
                generateVertical(stringValue, field, valueKey, part);
                break;
            default:
                throw new IllegalArgumentException("The orientation must be StringValue.Horizontal or StringValue.Vertical");
        }

        return part;
    }

    private void generateVertical(StringValue stringValue, Field field, String valueKey, List<List<ButtonHolder>> part) {
        String[] values = stringValue.values();
        for (String value : values) {
            String data = String.format("StringValue;%s;%s;%s", field.getName(), valueKey, value);
            part.add(Collections.singletonList(new ButtonHolder(value, data)));
        }
    }

    private void generateHorizontal(StringValue stringValue, Field field, String valueKey, List<List<ButtonHolder>> part) {
        List<ButtonHolder> row = new ArrayList<>();

        String[] values = stringValue.values();
        for (String value : values) {
            String data = String.format("StringValue;%s;%s;%s", field.getName(), valueKey, value);
            row.add(new ButtonHolder(value, data));
        }

        part.add(row);
    }

}
