package mouamle.generator.annotation.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntValue {

    String key();

    int increment() default 1;

    int decrement() default 1;

    boolean addHeader() default true;
    
}
