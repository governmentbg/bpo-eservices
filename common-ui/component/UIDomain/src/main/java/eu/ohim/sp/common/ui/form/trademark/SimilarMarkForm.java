/*******************************************************************************
 * * $Id:: SimilarMarkForm.java 49264 2012-10-29 13:23:34Z karalch               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.trademark;

import java.io.Serializable;
import java.util.List;

/**
 * @author ionitdi
 */
public class SimilarMarkForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5196711780014267956L;
	
	private String applicationNumber;
	private String name;
	private String type;
	private String office;
	private String ownerName;
    private String officeSpecificId;
	private List<String> inputTerms;

	/**
	 * Gets input terms
	 * @return string list of input terms
	 */
	public List<String> getInputTerms() {
		return inputTerms;
	}

	/**
	 * 
	 * @param inputTerms
	 */
	public void setInputTerms(List<String> inputTerms) {
		this.inputTerms = inputTerms;
	}

	/**
	 * Gets name 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets office
	 * @return String office
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * 
	 * @param office
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * Gets owner name
	 * @return String owner name
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * 
	 * @param ownerName
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

    /**
     *
     * @return
     */
	public String getApplicationNumber() {
		return applicationNumber;
	}

    /**
     *
     * @param applicationNumber
     */
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

    /**
     *
     * @return
     */
	public String getType() {
		return type;
	}

    /**
     *
     * @param type
     */
	public void setType(String type) {
		this.type = type;
	}

    /**
     *
     * @return
     */
    public String getOfficeSpecificId()
    {
        return officeSpecificId;
    }

    /**
     *
     * @param officeSpecificId
     */
    public void setOfficeSpecificId(String officeSpecificId)
    {
        this.officeSpecificId = officeSpecificId;
    }
}
