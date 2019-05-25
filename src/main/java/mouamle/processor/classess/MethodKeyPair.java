package mouamle.processor.classess;

import java.lang.reflect.Method;

public class MethodKeyPair {

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
