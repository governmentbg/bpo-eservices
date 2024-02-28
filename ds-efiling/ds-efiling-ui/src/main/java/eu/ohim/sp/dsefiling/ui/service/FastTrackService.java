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

package eu.ohim.sp.dsefiling.ui.service;

import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FastTrackService extends eu.ohim.sp.common.ui.service.FastTrackService<DSFlowBeanImpl> {

    @Autowired
    private DSFlowBeanFactory flowBeanFactory;

    @Override
    public IPApplication getIPApplicationInput(DSFlowBeanImpl fastTrackBean) {
        return flowBeanFactory.convertTo(fastTrackBean);
    }

    @Override
    public String getComponent() {
        return "dsefiling";
    }
}
