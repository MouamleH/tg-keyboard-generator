package mouamle.processor.classess;

import java.util.ArrayList;
import java.util.List;

public class RegisteredHandler {

    private final Object instance;
    private final List<MethodKeyPair> methods = new ArrayList<>();

    public RegisteredHandler(Object instance) {
        this.instance = instance;
    }

    public void addMethodKeyPair(MethodKeyPair keyPair) {
        methods.add(keyPair);
    }

    public Object getInstance() {
        return instance;
    }

    public List<MethodKeyPair> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        return "RegisteredHandler{" +
                "instance=" + instance +
                ", methods=" + methods +
                '}';
    }

}
