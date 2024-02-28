/*******************************************************************************
 * * $Id:: TilesExposingBeansView.java 49264 2012-10-29 13:23:34Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.servlet.view;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.servlet.view.tiles2.TilesView;

public class TilesExposingBeansView extends TilesView {

	private boolean exposeContextBeansAsAttributes = false;
    private Set<String> exposedContextBeanNames;

    public void setExposeContextBeansAsAttributes(boolean exposeContextBeansAsAttributes) {
        this.exposeContextBeansAsAttributes = exposeContextBeansAsAttributes;
    }

    public void setExposedContextBeanNames(String[] exposedContextBeanNames) {
        this.exposedContextBeanNames = new HashSet<String>(Arrays.asList(exposedContextBeanNames));
    } 

    protected HttpServletRequest getRequestToExpose(HttpServletRequest originalRequest) {
        if (this.exposeContextBeansAsAttributes || this.exposedContextBeanNames != null) {
        	return new ContextExposingHttpServletRequest(originalRequest, getWebApplicationContext(), this.exposedContextBeanNames);
        }
        return originalRequest;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest requestToExpose = getRequestToExpose(request);
        exposeModelAsRequestAttributes(model, requestToExpose);
        super.renderMergedOutputModel(model, requestToExpose, response);
    }
}
