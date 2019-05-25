package mouamle.generator.annotation.handlers;

import mouamle.registry.AnnotationHandler;
import mouamle.generator.classes.ButtonHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StringValueHandler implements AnnotationHandler<StringValue> {

    @Override
    public List<List<ButtonHolder>> generate(StringValue stringValue, Object o, Field field) throws Exception {
        String valueKey = stringValue.key();

        List<List<ButtonHolder>> part = new ArrayList<>();
        addValueHeader(valueKey, part);

        List<ButtonHolder> row = new ArrayList<>();

        String[] values = stringValue.values();
        for (String value : values) {
            String data = String.format("StringValue;%s;%s;%s", field.getName(), valueKey, value);
            row.add(new ButtonHolder(value, data));
        }

        part.add(row);
        return part;
    }

}
