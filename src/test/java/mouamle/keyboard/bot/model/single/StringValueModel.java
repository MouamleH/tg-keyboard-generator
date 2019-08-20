package mouamle.keyboard.bot.model.single;

import mouamle.generator.annotation.handlers.value.StringValue;

public class StringValueModel {

    @StringValue(
            key = "names",
            header = "Select a name",
            values = {"name1", "name2", "name3"},
            orientation = StringValue.Horizontal
    )
    private String stringValue;

}
