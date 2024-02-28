/*******************************************************************************
 * * $Id:: Result.java 14329 2012-10-29 13:02:02Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.common;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

public class Result extends Id implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3511253534993456883L;

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String NOTFOUND = "notfound";
    public static final String SERVERERROR = "servererror";

    private String result;
    private String errorCode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
