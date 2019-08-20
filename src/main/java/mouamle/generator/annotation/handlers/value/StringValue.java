package mouamle.generator.annotation.handlers.value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValue {

    int Horizontal = 0;
    int Vertical = 1;

    String key() default "*";

    String[] values();

    int orientation() default Horizontal;

    String header() default "";

}
