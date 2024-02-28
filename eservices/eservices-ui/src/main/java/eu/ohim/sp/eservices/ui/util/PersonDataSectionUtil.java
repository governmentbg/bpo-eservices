/*******************************************************************************
 * * $Id:: PersonDataSectionUtil.java 113496 2016-11-16 15:03:04Z velosma                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.util;

import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;

import java.util.List;

public final class PersonDataSectionUtil {

	private PersonDataSectionUtil() {}

	public static String getSetionId(String flowModeId) {
        if (flowModeId != null && flowModeId.endsWith("-rem")) {
            return "remcreditor";
        }
        if (flowModeId != null && flowModeId.endsWith("-invdenial")) {
            return "inventor";
        }
        if (flowModeId != null && (flowModeId.endsWith("-licence") || flowModeId.endsWith("-compulsorylicence"))) {
            return "licensee";
        }
        if (flowModeId != null && flowModeId.endsWith("-security")) {
            return "assignee_security";
        }
        if (flowModeId != null && flowModeId.endsWith("-bankruptcy")) {
            return "assignee_bankruptcy";
        }
        if ("tm-objection".equals(flowModeId)) {
            return "applicant";
        }
		return "assignee";
	}

    public static List<String> getTypesBySectionId(ConfigurationServiceDelegatorInterface configurationServiceDelegator, String flowModeId) {
        if (flowModeId != null && flowModeId.endsWith("-rem")) {
            return configurationServiceDelegator.getRemCreditorTypes(flowModeId);
        }
        if (flowModeId != null && (flowModeId.endsWith("-licence") || flowModeId.endsWith("-compulsorylicence"))) {
            return configurationServiceDelegator.getLicenseeTypes(flowModeId);
        }
        if (flowModeId != null && flowModeId.endsWith("-security")) {
            return configurationServiceDelegator.getAssigneeSecurityTypes(flowModeId);
        }
        if (flowModeId != null && flowModeId.endsWith("-bankruptcy")) {
            return configurationServiceDelegator.getAssigneeBankruptcyTypes(flowModeId);
        }
        return configurationServiceDelegator.getAssigneeTypes(flowModeId);
    }
}
