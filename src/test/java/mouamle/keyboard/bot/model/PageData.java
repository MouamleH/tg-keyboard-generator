package mouamle.keyboard.bot.model;

import mouamle.generator.annotation.handlers.ButtonValue;
import mouamle.generator.annotation.handlers.StringValue;

public class PageData {

    @StringValue(key = "ro1", values = {"One",  "Two", "Three"}, orientation = StringValue.Vertical)
    private String row1;

    @ButtonValue(key = "button1", text = "Click me", callbackText = "callback")
    private Object button1;

}
