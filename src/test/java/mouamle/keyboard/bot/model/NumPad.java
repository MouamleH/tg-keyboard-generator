package mouamle.keyboard.bot.model;

import mouamle.generator.annotation.handlers.value.StringValue;

public class NumPad {

    @StringValue(key = "ro1", values = {"1",  "2", "3"})
    private String row1;

    @StringValue(key = "ro2", values = {"4",  "5", "6"})
    private String row2;

    @StringValue(key = "ro3", values = {"7",  "8", "9"})
    private String row3;

    @StringValue(key = "ro4", values = {"*",  "0", "#"})
    private String row4;

}
