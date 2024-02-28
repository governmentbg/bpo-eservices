/*
 *  CoreDomain:: IPApplication 17/10/13 16:50 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.trademark;

import eu.ohim.sp.core.domain.resources.AttachedDocument;

import java.util.Date;
import java.util.List;

public interface IPApplication {

	/**
	 * Gets the user
	 * @return
	 */
	String getUser();

	/**
	 * Modifies the user
	 * @param user
	 */
	void setUser(String user);
	
	/**
	 * Gets the application filing number.
	 *
	 * @return the application filing number
	 */
	String getApplicationFilingNumber();
	
	/**
	 * Sets the application filing number.
	 *
	 * @param applicationFilingNumber the new application filing number
	 */
	void setApplicationFilingNumber(String applicationFilingNumber);

    /**
     * Gets the application number.
     *
     * @return the application number
     */
    String getApplicationNumber();

    /**
     * Sets the application number.
     *
     * @param applicationNumber the new application number
     */
    void setApplicationNumber(String applicationNumber);


    /**
     * Gets the application filing date.
     *
     * @return the application filing date
     */
    Date getApplicationFilingDate();

    /**
     * Sets the application filing date.
     *
     * @param applicationFilingDate the new application filing date
     */
    void setApplicationFilingDate(Date applicationFilingDate);

    /**
     * Gets the application type.
     *
     * @return the application type
     */
    String getApplicationType();

    /**
     * Sets the application type.
     *
     * @param applicationType the new application type
     */
    void setApplicationType(String applicationType);

    List<AttachedDocument> getDocuments();


    void setDocuments(List<AttachedDocument> documents);

    Boolean getElectronicCorrespondenceRequested();

    String getTitleApplication();

	String getAppSubtype();

}
