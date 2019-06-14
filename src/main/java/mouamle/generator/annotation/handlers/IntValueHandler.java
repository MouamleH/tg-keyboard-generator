package mouamle.generator.annotation.handlers;

import mouamle.registry.AnnotationHandler;
import mouamle.generator.classes.ButtonHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IntValueHandler implements AnnotationHandler<IntValue> {

    @Override
    public List<List<ButtonHolder>> generate(IntValue intValue, Object o, Field field) throws Exception {
        String valueKey = intValue.key();

        List<List<ButtonHolder>> part = new ArrayList<>();
        if (intValue.addHeader()) {
            addValueHeader(valueKey, part);
        }

        List<ButtonHolder> row = new ArrayList<>();

        String decrementData = String.format("IntValue;%s;%s;-%s", field.getName(), valueKey, intValue.decrement());
        String incrementData = String.format("IntValue;%s;%s;+%s", field.getName(), valueKey, intValue.increment());

        row.add(new ButtonHolder("-", decrementData));
        row.add(new ButtonHolder(String.valueOf(field.getInt(o)), noData));
        row.add(new ButtonHolder("+", incrementData));

        part.add(row);

        return part;
    }

}
