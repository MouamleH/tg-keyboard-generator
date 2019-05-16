package keyboard.model;

import mouamle.generator.annotations.handlers.ButtonValue;
import mouamle.generator.annotations.handlers.IntValue;
import mouamle.generator.annotations.handlers.StringValue;

public class Data {

    @IntValue(key = "Age")
    private int age;

    @StringValue(key = "Color", values = {"r", "g", "b", "a"})
    private String color;

    @IntValue(key = "Size")
    private int size;

    @ButtonValue(text = "Click me", callbackText = "Me clicked :D")
    private Object buttonTest;


}
