package mouamle.generator.annotation.handlers;

import mouamle.registry.AnnotationHandler;
import mouamle.generator.classes.ButtonHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonValueHandler implements AnnotationHandler<ButtonValue> {

    @Override
    public List<List<ButtonHolder>> generate(ButtonValue buttonValue, Object o, Field field) throws Exception {
        List<List<ButtonHolder>> part = new ArrayList<>();

        String valueKey = buttonValue.key();

        String data = String.format("ButtonValue;%s;%s;%s", field.getName(), valueKey, buttonValue.callbackText());
        part.add(Collections.singletonList(new ButtonHolder(buttonValue.text(), data)));

        return part;
    }

}
