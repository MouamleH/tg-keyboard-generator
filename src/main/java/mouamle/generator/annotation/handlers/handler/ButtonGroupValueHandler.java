package mouamle.generator.annotation.handlers.handler;

import mouamle.generator.annotation.handlers.value.ButtonGroupValue;
import mouamle.generator.classes.ButtonHolder;
import mouamle.registry.AnnotationHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonGroupValueHandler implements AnnotationHandler<ButtonGroupValue> {

    @Override
    public List<List<ButtonHolder>> generate(ButtonGroupValue buttonGroup, Object o, Field field) throws Exception {
        String valueKey = buttonGroup.key();

        List<List<ButtonHolder>> part = new ArrayList<>();

        String[] texts = buttonGroup.texts();
        String[] callbacks = buttonGroup.callbacks();

        if (!buttonGroup.header().isEmpty()) {
            addValueHeader(buttonGroup.header(), part);
        }

        List<ButtonHolder> row = new ArrayList<>();

        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];
            String callback = callbacks[i];

            String data = String.format("ButtonValue;%s;%s;%s", field.getName(), valueKey, callback);
            ButtonHolder button = new ButtonHolder(text, data);
            switch (buttonGroup.orientation()) {
                case ButtonGroupValue.Horizontal:
                    row.add(button);
                    break;
                case ButtonGroupValue.Vertical:
                    part.add(Collections.singletonList(button));
                    break;
                default:
                    throw new IllegalArgumentException("The orientation must be ButtonGroupValue.Horizontal or ButtonGroupValue.Vertical");
            }
        }

        if (!row.isEmpty()) {
            part.add(row);
        }

        return part;
    }

}
