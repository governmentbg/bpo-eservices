/*
 *  CoreDomain:: ClassDescription 08/08/13 18:42 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author ionitdi
 * 
 */
public class ClassDescription implements Serializable {

	private static final long serialVersionUID = 1340656506826434411L;

	private String classNumber;
	private String language;

	private String goodsServicesDescription;

    private Boolean fullClassCoverageIndicator;
	
	private List<ClassificationTerm> classificationTerms;

	/**
	 * @return the classNumber
	 */
	public String getClassNumber() {
		return classNumber;
	}

	/**
	 * @param classNumber
	 *            the classNumber to set
	 */
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public String getGoodsServicesDescription() {
		return goodsServicesDescription;
	}

	public void setGoodsServicesDescription(String goodsServicesDescription) {
		this.goodsServicesDescription = goodsServicesDescription;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<ClassificationTerm> getClassificationTerms() {
		if (classificationTerms == null) {
			classificationTerms = new ArrayList<ClassificationTerm>();
		}
		return classificationTerms;
	}

	public void setClassificationTerms(List<ClassificationTerm> classificationTerms) {
		this.classificationTerms = classificationTerms;
	}

    /**
     *
     * @return
     */
    public Boolean isFullClassCoverageIndicator()
    {
        return fullClassCoverageIndicator;
    }

    /**
     *
     * @param fullClassCoverageIndicator
     */
    public void setFullClassCoverageIndicator(Boolean fullClassCoverageIndicator)
    {
        this.fullClassCoverageIndicator = fullClassCoverageIndicator;
    }

	public Integer getClassNumberInt(){
		if(classNumber == null){
			return null;
		}
		try {
			return Integer.parseInt(classNumber);
		} catch (Exception e){
			return -1;
		}
	}
}
