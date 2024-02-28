package eu.ohim.sp.core.domain.design;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.List;

public class ProductIndicationClass extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5880822169559680062L;
	
	private String mainClass;
	
	private String subClass;
	
	private String description;
	
	private List<ProductIndicationTerm> terms;

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductIndicationTerm> getTerms() {
		return terms;
	}

	public void setTerms(List<ProductIndicationTerm> terms) {
		this.terms = terms;
	}
}
