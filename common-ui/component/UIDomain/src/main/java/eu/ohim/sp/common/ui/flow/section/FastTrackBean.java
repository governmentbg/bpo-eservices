/*******************************************************************************
 * $Id:: FastTrackBean.java 2020/06/30 18:02 jmunoze
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

package eu.ohim.sp.common.ui.flow.section;

import eu.ohim.sp.common.ui.flow.FlowBean;

public interface FastTrackBean extends FlowBean {
    boolean isFastTrackStarted();
    void setFastTrackStarted(boolean fastTrackStarted);
    Boolean getFastTrack();
    void setFastTrack(Boolean fastTrack);
}
