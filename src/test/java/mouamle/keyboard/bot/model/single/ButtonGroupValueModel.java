package mouamle.keyboard.bot.model.single;

import mouamle.generator.annotation.handlers.value.ButtonGroupValue;

public class ButtonGroupValueModel {

    @ButtonGroupValue(
            key = "buttonGroup1",
            texts = {"button1", "button2"},
            callbacks = {"b1-press", "b2-press"},
            header = "",
            orientation = ButtonGroupValue.Horizontal
    )
    private Object buttonGroup;

}
