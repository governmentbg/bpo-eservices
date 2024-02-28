/*******************************************************************************
 * * $Id:: GoodsServices.java 123589 2013-06-17 10:49:02Z karalch                $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author ionitdi
 * 
 */
@Deprecated
public class GoodsServices implements Serializable {

	private static final long serialVersionUID = 1340656506826434411L;

	private String classNumber;

	private String goodsServicesDescription;

    private Boolean fullClassCoverageIndicator;
	
	private String languageCode;
	private List<ClassificationTerm> terms;

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

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public List<ClassificationTerm> getTerms() {
		if (terms == null) {
			terms = new ArrayList<ClassificationTerm>();
		}
		return terms;
	}

	public void setTerms(List<ClassificationTerm> terms) {
		this.terms = terms;
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
}
