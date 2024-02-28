package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;

public class TermsValidated extends Id implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4027705299304102138L;
	private String niceClass;
	private List<Term> terms;
	private String language;
	private String description;

	public TermsValidated(){
		
	}
	
	public TermsValidated(String niceClass, List<Term> terms, String language,
			String description) {
		super();
		this.niceClass = niceClass;
		this.terms = terms;
		this.language = language;
		this.description = description;
	}

	public String getNiceClass() {
		return niceClass;
	}

	public void setNiceClass(String niceClass) {
		this.niceClass = niceClass;
	}

	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
