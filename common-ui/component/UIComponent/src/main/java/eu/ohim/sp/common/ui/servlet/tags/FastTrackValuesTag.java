/*******************************************************************************
 * $Id:: FastTrackValuesTag.java 2020/06/30 18:02 jmunoze
 *
 *        . * .
 *      * RRRR  *   Copyright (c) 2012-2020 EUIPO: European Intelectual
 *     .  RR  R  .  Property Organization (trademarks and designs).
 *     *  RRR    *
 *      . RR RR .   ALL RIGHTS RESERVED
 *       *. _ .*
 *
 *  The use and distribution of this software is under the restrictions exposed in 'license.txt'
 *
 ******************************************************************************/

package eu.ohim.sp.common.ui.servlet.tags;

import eu.ohim.sp.common.ui.util.TagUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.PageContext;
import java.util.List;

public class FastTrackValuesTag extends FlowContextAwareTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2290152088501092762L;

	private String var;

	@Override
	protected int doStartTagInternal() throws Exception {		
		SectionViewConfiguration s = (SectionViewConfiguration) getRequestContext().getWebApplicationContext().getBean("sectionViewConfiguration");
		List<String> values = s.getFastTrackValues(AvailableSection.fromValue(getSectionId()), TagUtil.parseFieldName(getField()), getFlowModeId());
		if (StringUtils.isNotBlank(var)) {
			pageContext.setAttribute(var, values, PageContext.PAGE_SCOPE);
		} else {
			pageContext.getOut().print(values);
		}
		return (SKIP_BODY);
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
}
