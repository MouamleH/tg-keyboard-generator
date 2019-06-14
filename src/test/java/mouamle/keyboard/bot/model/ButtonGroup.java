package mouamle.keyboard.bot.model;

import mouamle.generator.annotation.handlers.ButtonGroupValue;

public class ButtonGroup {

    @ButtonGroupValue(
            texts = {"Click 1", "Click 2"},
            callbacks = {"1", "2"},
            orientation = ButtonGroupValue.Vertical
    )
    private Object group;

}
