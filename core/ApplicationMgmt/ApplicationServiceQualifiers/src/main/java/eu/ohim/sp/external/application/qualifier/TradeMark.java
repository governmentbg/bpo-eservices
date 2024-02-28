/*
 *  ApplicationClientServiceInterface:: TradeMark 16/08/13 14:42 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.external.application.qualifier;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Stereotype;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 14/08/13
 * Time: 18:43
 * To change this template use File | Settings | File Templates.
 */
@Qualifier
@Dependent
@Stereotype
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TradeMark {
}
