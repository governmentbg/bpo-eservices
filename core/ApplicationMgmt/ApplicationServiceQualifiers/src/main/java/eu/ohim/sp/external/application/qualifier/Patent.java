package eu.ohim.sp.external.application.qualifier;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Stereotype;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Dependent
@Stereotype
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Patent {
}
