package espace.annotations;


import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * -Class-ra rakva az összes metódust a classból nem logoljuk </br>
 * -Method-ra rakva a metódust nem logoljuk
 */
@Inherited
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface ExcludeFromLog {

}
