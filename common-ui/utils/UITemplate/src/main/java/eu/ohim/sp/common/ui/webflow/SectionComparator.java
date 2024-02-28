/*******************************************************************************
 * * $Id:: SectionComparator.java 113489 2013-04-22 14:59:26Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.webflow;

import java.io.Serializable;
import java.util.Comparator;

import eu.ohim.sp.core.configuration.domain.xsd.Section;

public class SectionComparator implements Serializable, Comparator<Section> {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -6308258078826644671L;

	@Override
	public int compare(Section arg0, Section arg1) {
		return arg0.getOrder().compareTo(arg1.getOrder());
	}
}
