/*******************************************************************************
 * * $Id:: TilesExposingBeansViewResolver.java 50553 2012-11-12 16:06:53Z villam#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.servlet.view;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import eu.ohim.sp.common.SPException;

/**
 * It is an extension of the UrlBasedViewResolver that exposes
 * beans on the jsp
 * @see UrlBasedViewResolver
 * @author karalch
 *
 */
public class TilesExposingBeansViewResolver extends UrlBasedViewResolver {

    private Boolean exposeContextBeansAsAttributes;
    private String[] exposedContextBeanNames;

    /**
     * Set whether to make all Spring beans in the application context accessible
     * as request attributes, through lazy checking once an attribute gets accessed.
     * <p>This will make all such beans accessible in plain <code>${...}</code>
     * expressions in a JSP 2.0 page, as well as in JSTL's <code>c:out</code>
     * value expressions.
     * <p>Default is "false".
     * @see InternalResourceView#setExposeContextBeansAsAttributes
     */
    public  void setExposeContextBeansAsAttributes(boolean exposeContextBeansAsAttributes) {
        this.exposeContextBeansAsAttributes = exposeContextBeansAsAttributes;
    }

    /**
     * Specify the names of beans in the context which are supposed to be exposed.
     * If this is non-null, only the specified beans are eligible for exposure as
     * attributes.
     * @see InternalResourceView#setExposedContextBeanNames
     */
    public  void setExposedContextBeanNames(String[] exposedContextBeanNames) {
        // Constructors and methods receiving arrays should clone objects
        // and store the copy. This prevents that future changes from
        // the user affect the internal functionality.
        this.exposedContextBeanNames = exposedContextBeanNames.clone();
    }

    @Override
    protected  AbstractUrlBasedView buildView(String viewName) throws SPException {
        AbstractUrlBasedView superView;
        try {
            superView = super.buildView(viewName);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;

            throw new SPException(e);
        }
        if (superView instanceof TilesExposingBeansView) {
            TilesExposingBeansView view = (TilesExposingBeansView) superView;
            if (this.exposeContextBeansAsAttributes != null) {
                view.setExposeContextBeansAsAttributes(this.exposeContextBeansAsAttributes);
            }
            if (this.exposedContextBeanNames != null) {
                view.setExposedContextBeanNames(this.exposedContextBeanNames);
            }
        }
        return superView;
    }
}
