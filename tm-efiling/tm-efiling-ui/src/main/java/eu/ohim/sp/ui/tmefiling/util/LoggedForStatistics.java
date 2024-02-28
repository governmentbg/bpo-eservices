package eu.ohim.sp.ui.tmefiling.util;

/**
 * @author ionitdi
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eu.ohim.sp.common.logging.model.ActionType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggedForStatistics
{
    ActionType ActionType();
    boolean passingFlowModeId() default false;
}
