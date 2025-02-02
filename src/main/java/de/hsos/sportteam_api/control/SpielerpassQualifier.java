package de.hsos.sportteam_api.control;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, 
        ElementType.FIELD,
        ElementType.METHOD
        })
public @interface SpielerpassQualifier {
    SpielerpassType type() default SpielerpassType.ATOMIC;
}
