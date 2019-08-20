package mouamle.keyboard.bot.model.single;

import mouamle.generator.annotation.handlers.value.MapValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class MapValueModel {

    @MapValue(
            key = "my-map",
            header = "press something!",
            orientation = MapValue.Horizontal
    )
    private Map<String, Integer> mapValue = Collections
            .singletonMap("number 4", 4);

}
