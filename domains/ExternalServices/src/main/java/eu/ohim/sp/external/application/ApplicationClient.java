/*
 *  ApplicationClient:: ApplicationClient 14/11/13 15:48 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.external.application;

import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.common.Result;

import eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber;

public interface ApplicationClient {

	/**
	 * 
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
	 * @param applicationType
	 * @param provisionalNumber
	 * @return {@code ApplicationNumber}
	 */
	ApplicationNumber getApplicationNumber(String module, String applicationType, String provisionalNumber);

    /**
     * Makes the call to the client to save an application.
     *
     * @param office        the office code
     * @param user          the user
     * @param provisionalId the provisional id of the application to save
     * @param finalDraft    flag to indicate if changes can be made to application
     * @return the result of the call
     */
    Result saveApplication(String office, String user, String provisionalId, boolean finalDraft);

    /**
     * Saves the application
     * @param office office id
     * @param user user name
     * @param provisionalId user name
     * @return Result with the save result
     *
     */
	Result saveApplication(String office, String user, String provisionalId);

    /**
     * Check if the application exists with the given credentials
     * @param applicationType type of application
     * @param formName form name
     * @param applicationNumber the application number
     * @param registrationNumber the registration number
     * @return {@code Boolean}
     */
    Boolean checkExistingApplication(String applicationType, String formName, String applicationNumber, String registrationNumber);

    /**
     * Gets the application
     * @param office office id
     * @param user user name
     * @param provisionalId user name
     * @return byte array with the application
     *
     */
	byte[] loadApplication(String office, String user, String provisionalId);

	/**
     * Publish a notification that a new application has been filed to the filing queue, so that
     * the Back Office can retrieve it.
     * @param office
     * @param module
     * @param draftApplication
     */
	void notifyApplicationFiling(String office, String module, DraftApplication draftApplication);
	
}