/*******************************************************************************
 * * $Id:: ErrorCore.java 128442 2013-07-11 13:35:23Z jaraful                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.validation;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Core domain object that holds all the errors found on the
 * business rule service.
 *
 * @author jaraful
 */
public class ErrorCore extends Id implements Serializable, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 638269057340637927L;

	/** The field. */
	private String field;
	
	/** The error code. */
	private String errorCode;
	
	/** The error args. */
	private List<String> errorArgs;
	
	/** The display message. */
	private String displayMessage;
	
	/** The stack trace. */
	private String stackTrace;
	
	/** The service name. */
	private String serviceName;
	
	/** The section. */
	private String section;
	
	/** The business rule. */
	private String businessRule;
	
	/** The warning. */
	private boolean warning; 
	
	/**
	 * Instantiates a new error core.
	 */
	public ErrorCore() {
		this.field = null;
		this.errorCode = null;
		this.errorArgs = null;
		this.displayMessage = null;
		this.stackTrace = null;
		this.serviceName = null;
		this.section = null;
		this.businessRule = null;
		this.warning = false;
	}
	
	/**
	 * Instantiates a new error core.
	 *
	 * @param field the field
	 * @param errorCode the error code
	 * @param errorArgs the error args
	 * @param displayMessage the display message
	 * @param stackTrace the stack trace
	 * @param serviceName the service name
	 * @param section the section
	 * @param businessRule the business rule
	 */
	public ErrorCore(String field,
			String errorCode,
			List<String> errorArgs,
			String displayMessage,
			String stackTrace,
			String serviceName,
			String section,
			String businessRule){
		this.field = field;
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
		this.displayMessage = displayMessage;
		this.stackTrace = stackTrace;
		this.serviceName = serviceName;
		this.section = section;
		this.businessRule = businessRule;
	}
	
	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	
	/**
	 * Sets the field.
	 *
	 * @param field the new field
	 */
	public void setField(String field) {
		this.field = field;
	}
	
	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}
	
	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * Gets the error args.
	 *
	 * @return the error args
	 */
	public List<String> getErrorArgs() {
		return errorArgs;
	}
	
	/**
	 * Sets the error args.
	 *
	 * @param errorArgs the new error args
	 */
	public void setErrorArgs(List<String> errorArgs) {
		this.errorArgs = errorArgs;
	}
	
	/**
	 * Gets the display message.
	 *
	 * @return the display message
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	
	/**
	 * Sets the display message.
	 *
	 * @param displayMessage the new display message
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	
	/**
	 * Gets the stack trace.
	 *
	 * @return the stack trace
	 */
	public String getStackTrace() {
		return stackTrace;
	}
	
	/**
	 * Sets the stack trace.
	 *
	 * @param stackTrace the new stack trace
	 */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return serviceName;
	}
	
	/**
	 * Sets the service name.
	 *
	 * @param serviceName the new service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	/**
	 * Gets the section.
	 *
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the section.
	 *
	 * @param section the new section
	 */
	public void setSection(String section) {
		this.section = section;
	}
	
	/**
	 * Gets the business rule.
	 *
	 * @return the business rule
	 */
	public String getBusinessRule() {
		return businessRule;
	}
	
	/**
	 * Sets the business rule.
	 *
	 * @param businessRule the new business rule
	 */
	public void setBusinessRule(String businessRule) {
		this.businessRule = businessRule;
	}

	/**
	 * Checks if is warning.
	 *
	 * @return true, if is warning
	 */
	public boolean isWarning() {
		return warning;
	}

	/**
	 * Sets the warning.
	 *
	 * @param warning the new warning
	 */
	public void setWarning(boolean warning) {
		this.warning = warning;
	}
	
}
