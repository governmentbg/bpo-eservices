/*******************************************************************************
 * * $Id:: PaymentRequestParam.java 14329 2012-10-29 13:02:02Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.payment.wrapper;

/**
 * Enumeration for identifying the parameters included
 * in the payment request.
 * 
 * @author monteca
 * 
 */
public enum PaymentRequestParam {

    /** Language of the UI */
    LANG_UI,

    /** Code for the specific product to be paid (tm-efiling, ds-efiling...) */
    PRODUCT_CODE,

    /** Webflow flowKey associated to the form */
    FLOW_KEY,

    /** Webflow flowMode associated to the form */
    FLOW_MODE,
    
    /** URL to invoke from payment */
    INVOKER_URL,

}
