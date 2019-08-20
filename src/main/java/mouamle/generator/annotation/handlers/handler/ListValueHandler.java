package mouamle.generator.annotation.handlers.handler;

import mouamle.generator.annotation.handlers.value.ListValue;
import mouamle.generator.classes.Button;
import mouamle.generator.classes.ButtonHolder;
import mouamle.registry.AnnotationHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class ListValueHandler implements AnnotationHandler<ListValue> {

    @Override
    public List<List<ButtonHolder>> generate(ListValue listValue, Object o, Field field) throws Exception {
        String valueKey = listValue.key();

        if (!field.getType().isAssignableFrom(List.class)) {
            throw new IllegalArgumentException("Annotation @ListValue must only be applied to a java.util.List<Button>");
        }

        List<?> list = (List<?>) field.get(o);
        if (list == null) {
            throw new IllegalArgumentException("Can't handle the list if it doesn't exist");
        }

        if (!list.isEmpty()) {
            Object obj = list.get(0);
            if (!(obj instanceof Button)) {
                throw new IllegalArgumentException("List elements must be of type Button");
            }
        }

        List<Button> checkedList = (List<Button>) list;

        List<List<ButtonHolder>> part = new ArrayList<>();

        if (!listValue.header().isEmpty()) {
            addValueHeader(listValue.header(), part);
        }

        List<ButtonHolder> row = new ArrayList<>();

        for (Button aButton : checkedList) {
            String text = aButton.getText();
            String callback = aButton.getValue();

            String data = String.format("ListValue;%s;%s;%s", field.getName(), valueKey, callback);
            ButtonHolder button = new ButtonHolder(text, data);
            switch (listValue.orientation()) {
                case ListValue.Horizontal:
                    row.add(button);
                    break;
                case ListValue.Vertical:
                    part.add(Collections.singletonList(button));
                    break;
                default:
                    throw new IllegalArgumentException("The orientation must be ListValue.Horizontal or ListValue.Vertical");
            }
        }

        if (!row.isEmpty()) {
            part.add(row);
        }

        return part;
    }

}
