package mouamle.keyboard.bot.model;

import mouamle.generator.annotation.handlers.value.ListValue;
import mouamle.generator.annotation.handlers.value.MapValue;
import mouamle.generator.classes.Button;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListAndMap {

    @ListValue(key = "values", header = "Labels", orientation = ListValue.Horizontal)
    private List<Button> data = Arrays.asList(new Button("Click Me", "?"), new Button("Click Me too", "?"));

    @MapValue(key = "MEM", header = "Bla bla bla")
    private Map<String, Integer> map = Collections.singletonMap("Potato?", 5);

}
