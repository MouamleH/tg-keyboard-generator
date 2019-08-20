package mouamle.registry;

import mouamle.generator.annotation.handlers.handler.*;
import mouamle.generator.annotation.handlers.value.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A static class that holds the annotation and their respective handlers
 *
 * @author Mouamle H. Hameed <mouamle.hasan@gmail.com>
 */
public final class AnnotationsRegistry {

    private static final Map<String, AnnotationHandler> handlersMap = new HashMap<>();

    // Registers the base annotation
    static {
        registerAnnotation(IntValue.class.getName(), new IntValueHandler());
        registerAnnotation(StringValue.class.getName(), new StringValueHandler());
        registerAnnotation(ButtonValue.class.getName(), new ButtonValueHandler());
        registerAnnotation(ButtonGroupValue.class.getName(), new ButtonGroupValueHandler());
        registerAnnotation(ListValue.class.getName(), new ListValueHandler());
        registerAnnotation(MapValue.class.getName(), new MapValueHandler());
    }

    /**
     * Registers an Annotation handler alongside its name as the Annotation class name
     *
     * @param annotationName the name of the annotation class to be registered. <pre>Annotation.class.getName();</pre>
     * @param handler        the Annotation callback handler.
     * @param <A>            the Annotation type
     */
    public static <A> void registerAnnotation(String annotationName, AnnotationHandler<A> handler) {
        if (handlersMap.containsKey(annotationName)) {
            throw new IllegalArgumentException(String.format("Annotation %s is already registered.", annotationName));
        }

        handlersMap.put(annotationName, handler);
    }

    /**
     * Returns an Optional containing the handler
     *
     * @param name the name of the registered Annotation class name
     * @return the handler for the Annotation
     */
    public static Optional<AnnotationHandler> getHandler(String name) {
        return Optional.ofNullable(handlersMap.get(name));
    }


    /**
     * Checks if there was a handler registered
     *
     * @param name the name of the registered Annotation class name
     * @return true if there was a handler registered <br> false otherwise
     */
    public static boolean isHandlerPresent(String name) {
        return handlersMap.containsKey(name);
    }

}
