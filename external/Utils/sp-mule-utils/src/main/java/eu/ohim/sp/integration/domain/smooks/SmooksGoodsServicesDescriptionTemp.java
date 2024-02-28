package eu.ohim.sp.integration.domain.smooks;

import java.io.Serializable;

public class SmooksGoodsServicesDescriptionTemp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String languageCode;
	private String value;

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
}
