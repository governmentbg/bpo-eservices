/*******************************************************************************
 * * $Id:: FormattedTag.java 49264 2012-10-29 13:23:34Z karalch                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.servlet.tags;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.util.TagUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class FormattedTag extends FlowContextAwareTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2290152088501092762L;

	@Override
	protected int doStartTagInternal() throws Exception {
		SectionViewConfiguration s = getSectionViewConfiguration();
		String result = s.getFormatted(AvailableSection.fromValue(getSectionId()), TagUtil.parseFieldName(getField()), getFlowModeId());
		if (StringUtils.isNotBlank(result)) {
			pageContext.getOut().print(result);
		} else {
			pageContext.getOut().print("");
		}
		return (SKIP_BODY);
	}

}
