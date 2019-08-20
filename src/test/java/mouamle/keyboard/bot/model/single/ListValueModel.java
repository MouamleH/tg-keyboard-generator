package mouamle.keyboard.bot.model.single;

import mouamle.generator.annotation.handlers.value.ListValue;
import mouamle.generator.classes.Button;

import java.util.Arrays;
import java.util.List;

public class ListValueModel {

    @ListValue(
            key = "buttonsList",
            header = "list of buttons!",
            orientation = ListValue.Horizontal
    )
    private List<Button> buttonList = Arrays.asList(
            new Button("button 1", "b1-press"),
            new Button("button 2", "b2-press")
    );

}
