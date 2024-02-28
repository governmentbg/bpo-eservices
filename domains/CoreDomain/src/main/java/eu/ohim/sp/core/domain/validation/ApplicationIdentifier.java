/*******************************************************************************
 * * $Id:: ApplicationIdentifier.java 134500 2013-08-14 13:50:21Z velasca        $
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

public class ApplicationIdentifier extends Id implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -102390815720839926L;
	
	private String office;
	private String year;
	private String code;
	private String finalCode;
	private String applicationType;
	
	public ApplicationIdentifier(String office, String year, String code, String finalCode, String applicationType) {
		this.office = office;
		this.year = year;
		this.code = code;
		this.finalCode = finalCode;
		this.applicationType = applicationType;
	}
	
	public String getFinalCode() {
		return finalCode;
	}
	public void setFinalCode(String finalCode) {
		this.finalCode = finalCode;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}	
	
}
