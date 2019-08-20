package mouamle.processor;

import mouamle.generator.annotation.KeyboardCallback;
import mouamle.generator.annotation.callbacks.ValueCallback;
import mouamle.processor.classess.Callback;
import mouamle.processor.classess.MethodKeyPair;
import mouamle.processor.classess.RegisteredHandler;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardProcessor {

    private static final Map<String, RegisteredHandler> handlers = new HashMap<>();

    /**
     * Registers a handler that is annotated with @KeyboardCallback
     *
     * @param object the handler object (must be annotated with @KeyboardCallback)
     */
    public static void registerHandler(Object object) {
        Class cClass = object.getClass();

        if (!cClass.isAnnotationPresent(KeyboardCallback.class)) {
            throw new IllegalArgumentException("The handler must be annotated with @KeyboardCallback");
        }

        KeyboardCallback keyboardCallback = (KeyboardCallback) cClass.getDeclaredAnnotation(KeyboardCallback.class);
        String callbackName = keyboardCallback.name();

        if (handlers.containsKey(callbackName)) {
            throw new IllegalArgumentException(String.format("Callback name %s is already registered.", callbackName));
        }

        RegisteredHandler handler = new RegisteredHandler(object);

        Method[] methods = cClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ValueCallback.class)) {
                ValueCallback callback = method.getDeclaredAnnotation(ValueCallback.class);
                String key = callback.valueKey();

                handler.addMethodKeyPair(new MethodKeyPair(method, key));
            }
        }

        handlers.put(callbackName, handler);
    }

    /**
     * Handles all the callbacks calling
     *
     * @param callbackQuery the callback query
     * @return true if the callback was processed successfully, false otherwise
     * @throws InvocationTargetException if there was a problem invoking any of your callback methods
     * @throws IllegalAccessException    if there was a problem accessing any of your callback methods
     * @throws IllegalArgumentException  if the callbackQuery had no data
     */
    public static boolean processCallback(CallbackQuery callbackQuery) throws InvocationTargetException, IllegalAccessException {
        if (callbackQuery == null) {
            throw new IllegalArgumentException("CallbackQuery can't be null");
        }

        if (callbackQuery.getData() == null || callbackQuery.getData().trim().isEmpty()) {
            throw new IllegalArgumentException("CallbackQuery must have data in it");
        }

        String data = callbackQuery.getData();
        String[] split = data.split(";");

        if (split.length == 4) {
            String valueType = split[0];
            String filedName = split[1];
            String valueKey = split[2];
            String value = split[3];

            Callback callback = new Callback(valueType, filedName, valueKey, value, callbackQuery);

            List<RegisteredHandler> registeredHandlers = new ArrayList<>(handlers.values());

            for (RegisteredHandler handler : registeredHandlers) {
                List<MethodKeyPair> keyPairs = handler.getMethods();
                for (MethodKeyPair keyPair : keyPairs) {
                    String key = keyPair.getMethodKey();
                    Method method = keyPair.getMethod();

                    if (key.equals("*") || key.equals(valueKey)) {
                        method.invoke(handler.getInstance(), callback);
                    }
                }
            }

            return true;
        }

        return false;
    }

}
