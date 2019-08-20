package mouamle.keyboard.bot.model.single;

import mouamle.generator.annotation.handlers.value.IntValue;
import org.checkerframework.common.value.qual.IntVal;

public class IntValueModel {

    @IntValue(
            key = "fontSize",
            header = "Set Font Size",
            increment = 2,
            decrement = 2
    )
    private int fontSize;

}
