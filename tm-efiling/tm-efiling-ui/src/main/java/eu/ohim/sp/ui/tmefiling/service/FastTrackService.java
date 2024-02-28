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

package eu.ohim.sp.ui.tmefiling.service;

import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FastTrackService extends eu.ohim.sp.common.ui.service.FastTrackService<FlowBeanImpl> {

    @Autowired
    private FlowBeanFactory flowBeanFactory;

    @Override
    public IPApplication getIPApplicationInput(FlowBeanImpl fastTrackBean) {
        return flowBeanFactory.convertToTradeMarkApplication(fastTrackBean);
    }

    @Override
    public String getComponent() {
        return "tmefiling";
    }
}
