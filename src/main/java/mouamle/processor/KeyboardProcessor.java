package mouamle.processor;

import mouamle.generator.annotations.KeyboardCallback;
import mouamle.generator.annotations.callbacks.ButtonValueCallback;
import mouamle.generator.annotations.callbacks.IntValueCallback;
import mouamle.generator.annotations.callbacks.StringValueCallback;
import mouamle.processor.classess.RegisteredHandler;
import mouamle.processor.classess.Callback;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardProcessor {

    private final Map<String, RegisteredHandler> handlers = new HashMap<>();

    /**
     * Registers a handler that is annotated with @KeyboardCallback
     *
     * @param object the handler object (must be annotated with @KeyboardCallback)
     */
    public void registerHandler(Object object) {
        Class cClass = object.getClass();

        if (!cClass.isAnnotationPresent(KeyboardCallback.class)) {
            throw new IllegalArgumentException("The handler must be annotated with @KeyboardCallback");
        }

        Method[] methods = cClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(IntValueCallback.class)) {
                registerIntCallback(object, method, method.getDeclaredAnnotation(IntValueCallback.class));
            } else if (method.isAnnotationPresent(StringValueCallback.class)) {
                registerStringCallback(object, method, method.getDeclaredAnnotation(StringValueCallback.class));
            } else if (method.isAnnotationPresent(ButtonValueCallback.class)) {
                registerButtonCallback(object, method, method.getDeclaredAnnotation(ButtonValueCallback.class));
            }
        }
    }

    /**
     * Handles all the callbacks calling
     *
     * @param callbackQuery the callback query
     * @throws InvocationTargetException if there was a problem invoking any of your callback methods
     * @throws IllegalAccessException    if there was a problem accessing any of your callback methods
     * @throws IllegalArgumentException  if the callbackQuery had no data
     */
    public void processCallback(CallbackQuery callbackQuery) throws InvocationTargetException, IllegalAccessException {
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

            RegisteredHandler handler = handlers.get(valueType);
            if (handler == null)
                return;

            List<RegisteredHandler.MethodKeyPair> keyPairs = handler.getMethods();
            for (RegisteredHandler.MethodKeyPair keyPair : keyPairs) {
                String key = keyPair.getMethodKey();
                Method method = keyPair.getMethod();

                if (key.equals("*") || key.equals(valueKey)) {
                    method.invoke(handler.getInstance(), callback);
                }
            }
        }
    }

    private void registerButtonCallback(Object o, Method method, ButtonValueCallback callback) {
        registerCallback("buttonValue", o, method, callback.valueKey());
    }

    private void registerStringCallback(Object o, Method method, StringValueCallback callback) {
        registerCallback("stringValue", o, method, callback.valueKey());
    }

    private void registerIntCallback(Object o, Method method, IntValueCallback callback) {
        registerCallback("intValue", o, method, callback.valueKey());
    }

    private void registerCallback(String key, Object o, Method method, String valueKey) {
        RegisteredHandler handler = handlers.getOrDefault(key, new RegisteredHandler(o));
        handler.addMethodKeyPair(new RegisteredHandler.MethodKeyPair(method, valueKey));
        handlers.put(key, handler);
    }

}
