/*******************************************************************************
 * $Id:: FastTrackService.java 2020/06/30 18:02 jmunoze
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

package eu.ohim.sp.common.ui.service;

import eu.ohim.sp.common.ui.flow.section.FastTrackBean;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.FastTrackServiceInterface;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.fasttrack.FastTrackFail;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class FastTrackService<T extends FastTrackBean> implements FastTrackServiceInterface {

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private RulesService businessRulesService;

    //Fast Track
    public static final String FASTTRACK_SET = "fastTrack_set";
    public static final String FLOW_MODE_ID_CONF = "-conf";

    @Override
    public Collection<FastTrackFail> calculateFastTrackFails(FastTrackBean fastTrackBean, String flowModeId, String stateId) {
        IPApplication ipApplication = getIPApplicationInput((T)fastTrackBean);

        Sections sections = configurationServiceDelegator.getObjectFromComponent(flowModeId + FLOW_MODE_ID_CONF, getComponent(), Sections.class);

        RulesInformation rulesInformation = new RulesInformation();
        rulesInformation.getCustomObjects().put("stateId", stateId);
        rulesInformation.getCustomObjects().put("flowModeId", flowModeId);
        rulesInformation.getCustomObjects().put("sections", sections);

        // Variable declaration
        List<Object> objects = new ArrayList<>();
        objects.add(ipApplication);
        objects.add(rulesInformation);
        objects.add(sections);

        // Starts the check
        Map<String, Object> results = businessRulesService.calculate(getComponent(), FASTTRACK_SET, objects);

        if (results != null && CollectionUtils.isNotEmpty(results.values())) {
            return (Collection)results.values().iterator().next();
        } else {
            return null;
        }
    }

    public abstract IPApplication getIPApplicationInput(T fastTrackBean);

    public abstract String getComponent();
}
