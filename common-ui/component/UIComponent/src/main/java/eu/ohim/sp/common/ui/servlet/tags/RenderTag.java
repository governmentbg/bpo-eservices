/*******************************************************************************
 * * $Id:: RenderTag.java 49264 2012-10-29 13:23:34Z karalch                     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.servlet.tags;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.util.TagUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class RenderTag extends FlowContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2290152088501092762L;

	private Boolean checkRender;		
    private String var;

			
	/**
	 * Returns true if the 'renderability' is checked
	 * @return true if the 'renderability' is checked
	 */
	public Boolean getCheckRender() {
		return checkRender;
	}

	/**
	 * Sets if the 'renderability' should be checked
	 * @param checkRender if the 'renderability' should be checked
	 */
	public void setCheckRender(Boolean checkRender) {
		this.checkRender = checkRender;
	}
	
	/**
	 * Gets the variable that contains the result of the implemented tag
	 * @return String var
	 */
	public String getVar() {
		return var;
	}

	/**
	 * Sets the result of the tag on a page scope variable
	 * @param var
	 */
	public void setVar(String var) {
		this.var = var;
	}

	
	/**
	 * doStartTagInternal overrided method
	 * @return int start tag
	 */
	@Override
	protected int doStartTagInternal() throws Exception {		
		SectionViewConfiguration s = getSectionViewConfiguration();
		boolean result = false;
		if(!checkRender || s.getRender(AvailableSection.fromValue(getSectionId()),
                                       TagUtil.parseFieldName(getField()),
                                       getFlowModeId())) {
			result = true;
		}
		
        if (StringUtils.isNotBlank(var)) {
            pageContext.setAttribute(var, result, PageContext.PAGE_SCOPE);
        }

        if (result) {
			return (EVAL_BODY_INCLUDE);
		} else {
			return (SKIP_BODY); // Nothing to output
		}
	}	
}
