package eu.ohim.sp.integration.domain.smooks;

import java.io.Serializable;
import java.util.List;

public class SmooksGoodsServicesTermsTemp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String classNumber;
	private List <SmooksGoodsServicesDescriptionTemp> goodsServicesDescription;
	
	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public List<SmooksGoodsServicesDescriptionTemp> getGoodsServicesDescription() {
		return goodsServicesDescription;
	}

	public void setGoodsServicesDescription(
			List<SmooksGoodsServicesDescriptionTemp> goodsServicesDescription) {
		this.goodsServicesDescription = goodsServicesDescription;
	}
		
}
