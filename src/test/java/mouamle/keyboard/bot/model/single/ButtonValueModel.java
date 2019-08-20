package mouamle.keyboard.bot.model.single;

import mouamle.generator.annotation.handlers.value.ButtonValue;

public class ButtonValueModel {

    @ButtonValue(
            key = "button1",
            text = "Click me",
            callbackText = "button1-click"
    )
    private Object buttonValue;

}
