/*******************************************************************************
 * * $Id:: RequiredTag.java 54608 2013-01-16 16:08:26Z ionitdi                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.servlet.tags;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.util.TagUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.io.IOException;

public class RequiredTag extends FlowContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2290152088501092762L;

	@Override
	protected int doStartTagInternal() {
		try {
			SectionViewConfiguration s = (SectionViewConfiguration) getRequestContext().getWebApplicationContext().getBean("sectionViewConfiguration");
			pageContext.getOut().print(s.getRequired(AvailableSection.fromValue(getSectionId()), TagUtil.parseFieldName(getField()),
                                                                                                                        getFlowModeId()));
			return (SKIP_BODY); 		
		} catch (IOException e) {
			throw new SPException("Failed to get Required information", e, "error.generic.code");
		}
	}
	
}
