/*******************************************************************************
 * * $Id:: NoResultsException.java 49264 2012-10-29 13:23:34Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.exception;

import eu.ohim.sp.common.SPException;

public class NoResultsException extends SPException {

    public NoResultsException(String message, Throwable cause, String errorCode) {
    	super(message, cause, errorCode);
    }
}
