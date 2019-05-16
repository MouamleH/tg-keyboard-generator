package mouamle.processor.classess;

import java.lang.reflect.Method;
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

    public static class MethodKeyPair {
        private final Method method;
        private final String methodKey;

        public MethodKeyPair(Method method, String methodKey) {
            this.method = method;
            this.methodKey = methodKey;
        }

        public Method getMethod() {
            return method;
        }

        public String getMethodKey() {
            return methodKey;
        }

        @Override
        public String toString() {
            return "MethodKeyPair{" +
                    "method=" + method +
                    ", methodKey='" + methodKey + '\'' +
                    '}';
        }
    }

}
