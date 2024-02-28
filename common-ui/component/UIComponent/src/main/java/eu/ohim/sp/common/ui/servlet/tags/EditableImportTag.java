/*******************************************************************************
 * * $Id:: EditableImportTag.java 49264 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.servlet.tags;

import eu.ohim.sp.common.ui.util.TagUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class EditableImportTag extends FlowContextAwareTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2290152088501092762L;

	@Override
	protected int doStartTagInternal() throws Exception {		
		SectionViewConfiguration s = (SectionViewConfiguration) getRequestContext().getWebApplicationContext().getBean("sectionViewConfiguration");
		pageContext.getOut().print(s.getEditableImport(AvailableSection.fromValue(getSectionId()), TagUtil.parseFieldName(getField()), getFlowModeId()));
		return (SKIP_BODY);
	}
	
}
