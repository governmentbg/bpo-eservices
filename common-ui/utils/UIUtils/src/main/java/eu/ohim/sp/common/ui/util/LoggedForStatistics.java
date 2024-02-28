package eu.ohim.sp.common.ui.util;

/**
 * @author ionitdi
 */

import eu.ohim.sp.common.logging.model.ActionType;
import eu.ohim.sp.common.logging.model.ApplicationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggedForStatistics
{
    ActionType ActionType();
    boolean passingFlowModeId() default false;
}
