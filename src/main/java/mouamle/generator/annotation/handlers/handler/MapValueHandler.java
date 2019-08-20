package mouamle.generator.annotation.handlers.handler;

import mouamle.generator.annotation.handlers.value.ListValue;
import mouamle.generator.annotation.handlers.value.MapValue;
import mouamle.generator.classes.ButtonHolder;
import mouamle.registry.AnnotationHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MapValueHandler implements AnnotationHandler<MapValue> {

    @Override
    public List<List<ButtonHolder>> generate(MapValue mapValue, Object o, Field field) throws Exception {
        String valueKey = mapValue.key();

        if (!field.getType().isAssignableFrom(Map.class)) {
            throw new IllegalArgumentException("Annotation @MapValue must only be applied to a java.util.Map<Object, Object>");
        }

        Map<?, ?> map = (Map<?, ?>) field.get(o);
        if (map == null) {
            throw new IllegalArgumentException("Can't handle the map if it doesn't exist");
        }

        List<List<ButtonHolder>> part = new ArrayList<>();

        if (!mapValue.header().isEmpty()) {
            addValueHeader(mapValue.header(), part);
        }

        List<ButtonHolder> row = new ArrayList<>();

        for (Object key : map.keySet()) {
            String text = String.valueOf(key);
            String callback = String.valueOf(map.get(key));

            String data = String.format("MapValue;%s;%s;%s", field.getName(), valueKey, callback);
            ButtonHolder button = new ButtonHolder(text, data);

            switch (mapValue.orientation()) {
                case MapValue.Horizontal:
                    row.add(button);
                    break;
                case MapValue.Vertical:
                    part.add(Collections.singletonList(button));
                    break;
                default:
                    throw new IllegalArgumentException("The orientation must be MapValue.Horizontal or MapValue.Vertical");
            }
        }

        if (!row.isEmpty()) {
            part.add(row);
        }

        return part;
    }

}
