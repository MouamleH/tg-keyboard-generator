package mouamle.keyboard.bot.callback;

import mouamle.generator.annotation.KeyboardCallback;
import mouamle.generator.annotation.callbacks.ValueCallback;
import mouamle.processor.classess.Callback;

@KeyboardCallback(name = "dataCallback")
public class DataCallback {

    @ValueCallback(valueKey = "me2")
    public void buttonPressed(Callback callback) {
        System.out.println("me2 button: " + callback);
    }

    @ValueCallback(valueKey = "Age")
    public void ageCallback(Callback callback) {
        System.out.println("Age: " + callback);
    }

    @ValueCallback(valueKey = "Color")
    public void colorCallbacck(Callback callback) {
        System.out.println("Color: " + callback);
    }

    @ValueCallback
    public void stringCallback(Callback callback) {
        System.out.println("All: " + callback);
    }

}