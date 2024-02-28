/*
 *  CoreDomain:: ClassScope $revision 08/08/13 17:56 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

public class ClassScope extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5337121492593857451L;
	
	private String language;
	private String classNumber;
	private String description;


	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
