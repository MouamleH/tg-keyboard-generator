package keyboard.callback;

import mouamle.generator.annotations.KeyboardCallback;
import mouamle.generator.annotations.callbacks.ButtonValueCallback;
import mouamle.generator.annotations.callbacks.IntValueCallback;
import mouamle.generator.annotations.callbacks.StringValueCallback;
import mouamle.processor.classess.Callback;

@KeyboardCallback
public class DataCallback {

    @ButtonValueCallback
    public void buttonPressed(Callback callback) {
        System.out.println(callback);
    }

    @IntValueCallback(valueKey = "Age")
    public void ageCallback(Callback callback) {
        System.out.println("Age: " + callback);
    }

    @IntValueCallback
    public void callback(Callback callback) {
        System.out.println("All: " + callback);
    }

    @StringValueCallback
    public void stringCallback(Callback callback) {
        System.out.println("All Strings: " + callback);
    }

}
