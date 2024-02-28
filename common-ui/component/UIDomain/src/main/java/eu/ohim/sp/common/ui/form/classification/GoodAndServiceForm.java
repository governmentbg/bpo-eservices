/*******************************************************************************
 * * $Id:: GoodAndServiceForm.java 50674 2012-11-13 18:48:15Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.classification;

import java.util.LinkedHashSet;
import java.util.Set;
import eu.ohim.sp.common.ui.interfaces.Importable;

public class GoodAndServiceForm implements java.io.Serializable, Cloneable, Comparable<GoodAndServiceForm>, Importable {

    private static final long serialVersionUID = 1L;
    
	private String classId;
	private String langId;
	private String desc;
	private Set<TermForm> termForms;
    private String error;
	private boolean imported;
	private boolean disabledRemoval = false;

	public GoodAndServiceForm() {
		termForms = new LinkedHashSet<TermForm>();
	}
	
	public GoodAndServiceForm(String langId, String classId) {
		this.classId = classId;
		this.langId = langId;
		termForms = new LinkedHashSet<TermForm>();
	}
	
	/** Getter method for error
	 * 
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	
	/** Setter method for error
	 * 
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	/** Getter method for classId
	 * 
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}
	
	/** Setter method for classId
	 * 
	 * @param classId the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	/** Getter method for langId
	 * 
	 * @return the langId
	 */
	public String getLangId() {
		return langId;
	}
	
	/** Setter method for langId
	 * 
	 * @param langId the langId to set
	 */
	public void setLangId(String langId) {
		this.langId = langId;
	}
	
	/** Getter method for desc
	 * 
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
	/** Setter method for desc
	 * 
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Set<TermForm> getTermForms() {
		return termForms;
	}

	public void setTermForms(Set<TermForm> termForms) {
		this.termForms = termForms;
	}
	
	public void addTermForm(TermForm termForm) {
		termForms.add(termForm);
	}

	@Override
	public int hashCode() {
		final int prime = 59;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + ((langId == null) ? 0 : langId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GoodAndServiceForm other = (GoodAndServiceForm) obj;
		if (classId == null) {
			if (other.classId != null) {
				return false;
			}
		} else if (!classId.equals(other.classId)) {
			return false;
		}
		if (langId == null) {
			if (other.langId != null) {
				return false;
			}
		} else if (!langId.equals(other.langId)) {
			return false;
		}
		return true;
	}

   @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("getLang ").append(getLangId()).append("\n").
        append("getDescription ").append(getDesc());
        return sb.toString();
    }

    public boolean contains(String termText) {
    	for (TermForm term : termForms) {
    		if (termText.equals(term.getDescription())) {
    			return true;
    		}
    	}
    	return false;
    }

	@Override
	public int compareTo(GoodAndServiceForm o) {
		if ((o!=null) && (this!=null)) {
			return this.hashCode() - o.hashCode();
		}
		return 0;
	}

	@Override
	public boolean getImported() {
		return imported;
	}

	@Override
	public void setImported(boolean imported) {
		this.imported = imported;
	}

	public boolean isDisabledRemoval() {
		return disabledRemoval;
	}

	public void setDisabledRemoval(boolean disabledRemoval) {
		this.disabledRemoval = disabledRemoval;
	}
	
	/**
	 * Creates a clone of the original object
	 */
	@Override
	public GoodAndServiceForm clone() throws CloneNotSupportedException{
		GoodAndServiceForm goodAndServiceForm = new GoodAndServiceForm();
		goodAndServiceForm.setClassId(classId);
		goodAndServiceForm.setDesc(desc);
		goodAndServiceForm.setDisabledRemoval(disabledRemoval);
		goodAndServiceForm.setError(error);
		goodAndServiceForm.setImported(imported);
		goodAndServiceForm.setLangId(langId);
		
		for(TermForm term : termForms){
			TermForm cloneTerm = term.clone();
			goodAndServiceForm.addTermForm(cloneTerm);
		}
	
		return goodAndServiceForm;
	}
}
