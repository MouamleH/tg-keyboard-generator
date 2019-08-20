package mouamle.keyboard.bot.model;

import mouamle.generator.annotation.handlers.value.ButtonValue;
import mouamle.generator.annotation.handlers.value.IntValue;
import mouamle.generator.annotation.handlers.value.StringValue;

public class Data {

    @IntValue(key = "Age")
    private int age;

    @StringValue(key = "Color", values = {"r", "g", "b", "a"}, orientation = StringValue.Vertical)
    private String color;

    @IntValue(key = "Size")
    private int size;

    @ButtonValue(text = "Click me", callbackText = "Me clicked :D")
    private Object buttonTest;

    @ButtonValue(text = "Click me 2", callbackText = "Me clicked :D", key = "me2")
    private Object buttonTest2;

}
