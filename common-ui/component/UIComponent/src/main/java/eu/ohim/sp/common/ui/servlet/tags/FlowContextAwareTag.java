/*******************************************************************************
 * * $Id:: FlowContextAwareTag.java 49264 2012-10-29 13:23:34Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.servlet.tags;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;

public abstract class FlowContextAwareTag extends RequestContextAwareTag {

	private String flowModeId;
	private String sectionId;
	private String field;


	/**
	 * Returns the flow mode id under the context where the tag is rendered
	 * @return the flow mode id
	 */
	public String getFlowModeId() {
		return flowModeId;
	}

	/**
	 * Sets the flow mode id under the context where the tag is rendered
	 * @param flowModeId the flow mode id
	 */
	public void setFlowModeId(String flowModeId) {
		this.flowModeId = flowModeId;
	}

	/**
	 * Returns the section id under the context where the tag is rendered
	 * @return the section id
	 */
	public String getSectionId() {
		return sectionId;
	}

	/**
	 * Sets the section id under the context where the tag is rendered
	 * @param sectionId the section id
	 */
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	/**
	 * Returns the field under the context where the tag is rendered
	 * @return the field 
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field under the context where the tag is rendered
	 * @param field the field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Gets the section view configuration object
	 * @return the section view configuration object
	 */
	protected SectionViewConfiguration getSectionViewConfiguration() {
		return (SectionViewConfiguration) getRequestContext().getWebApplicationContext().getBean(
				"sectionViewConfiguration");
	}

	/**
	 * Called by doStartTag to perform the actual work.
	 * @return same as TagSupport.doStartTag
	 * @throws Exception any exception, any checked one other than
	 * a JspException gets wrapped in a JspException by doStartTag
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag
	 */
	@Override
	protected abstract int doStartTagInternal() throws Exception;

}
