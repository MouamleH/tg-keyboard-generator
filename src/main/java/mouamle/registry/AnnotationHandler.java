package mouamle.registry;

import mouamle.generator.classes.ButtonHolder;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface AnnotationHandler<A> {

    String noData = "*";

    /**
     * A callback that gets called when trying to generate the buttons for a field
     *
     * @param annotation the Annotation itself
     * @param o          the template object instance
     * @param field      the Annotated field
     * @return the part of the keyboard generated for the field
     * @throws Exception in case if you don't want to deal with reflection exceptions
     */
    List<List<ButtonHolder>> generate(A annotation, Object o, Field field) throws Exception;

    default void addValueHeader(String valueKey, List<List<ButtonHolder>> part) {
        part.add(Collections.singletonList(new ButtonHolder(valueKey, noData)));
    }

}
