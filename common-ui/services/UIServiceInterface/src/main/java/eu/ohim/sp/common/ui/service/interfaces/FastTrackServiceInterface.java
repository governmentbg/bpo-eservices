/*******************************************************************************
 * $Id:: FastTrackServiceInterface.java 2020/06/30 18:02 jmunoze
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
package eu.ohim.sp.common.ui.service.interfaces;

import eu.ohim.sp.common.ui.flow.section.FastTrackBean;
import eu.ohim.sp.core.domain.fasttrack.FastTrackFail;

import java.util.Collection;

public interface FastTrackServiceInterface {

    Collection<FastTrackFail> calculateFastTrackFails(FastTrackBean fastTrackBean, String flowModeId, String stateId);

}
