/*
 *  CoreDomain:: IPPriority 15/10/13 16:15 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.claim;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 19/08/13
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public interface IPPriority {
    /**
     * Gets the filing office.
     *
     * @return the filing office
     */
    String getFilingOffice();

    /**
     * Sets the filing office.
     *
     * @param filingOffice
     *            the new filing office
     */
    void setFilingOffice(String filingOffice);

    /**
     * Gets the filing number.
     *
     * @return the filing number
     */
    String getFilingNumber();

    /**
     * Sets the filing number.
     *
     * @param filingNumber
     *            the new filing number
     */
    void setFilingNumber(String filingNumber);

    /**
     * Gets the filing date.
     *
     * @return the filing date
     */
    Date getFilingDate();

    /**
     * Sets the filing date.
     *
     * @param filingDate
     *            the new filing date
     */
    void setFilingDate(Date filingDate);

    /**
     * Checks if is certicate attached indicator.
     *
     * @return true, if is certicate attached indicator
     */
    boolean isCerticateAttachedIndicator();

    /**
     * Sets the certicate attached indicator.
     *
     * @param certicateAttachedIndicator
     *            the new certicate attached indicator
     */
    void setCerticateAttachedIndicator(boolean certicateAttachedIndicator);

    /**
     * Checks if is translation attached indicator.
     *
     * @return true, if is translation attached indicator
     */
    boolean isTranslationAttachedIndicator();

    /**
     * Sets the translation attached indicator.
     *
     * @param translationAttachedIndicator
     *            the new translation attached indicator
     */
    void setTranslationAttachedIndicator(
            boolean translationAttachedIndicator);

}
