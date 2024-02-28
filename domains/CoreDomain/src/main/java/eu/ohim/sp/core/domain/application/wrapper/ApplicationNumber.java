/*
 *  CoreDomain:: ApplicationNumber 19/08/13 10:57 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.application.wrapper;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;

/**
 * Wrapper class that wraps information of the application number provided
 * by an external service
 */
public class ApplicationNumber extends Id implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The provided application number
     */
    private String number;
    /**
     * The date on which this number was provided and
     * it can be transferred as application date
     */
    private Date date;

    /**
     * @return the date
     */
    public Date getDate() {
        return DateUtil.cloneDate(date);
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = DateUtil.cloneDate(date);
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

}
