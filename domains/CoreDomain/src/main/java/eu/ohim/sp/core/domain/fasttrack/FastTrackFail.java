/*******************************************************************************
 * $Id:: FastTrackFail.java 2020/06/30 18:02 jmunoze
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

package eu.ohim.sp.core.domain.fasttrack;

import java.io.Serializable;

public class FastTrackFail implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private Object[] args;
    private boolean translateArgs;
    private String eventId;
    private String sectionAnchor;

    public FastTrackFail() {
    }

    public FastTrackFail(String message, Object[] args, boolean translateArgs, String eventId, String sectionAnchor) {
        this.message = message;
        this.args = args;
        this.translateArgs = translateArgs;
        this.eventId = eventId;
        this.sectionAnchor = sectionAnchor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public boolean isTranslateArgs() {
        return translateArgs;
    }

    public void setTranslateArgs(boolean translateArgs) {
        this.translateArgs = translateArgs;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSectionAnchor() {
        return sectionAnchor;
    }

    public void setSectionAnchor(String sectionAnchor) {
        this.sectionAnchor = sectionAnchor;
    }

    @Override
    public String toString() {
        return "FastTrackFail{" +
                "message='" + message + '\'' +
                ", args='" + args + '\'' +
                ", translateArgs=" + translateArgs +
                ", eventId='" + eventId + '\'' +
                ", sectionAnchor='" + sectionAnchor + '\'' +
                '}';
    }
}