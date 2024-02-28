package eu.ohim.sp.common.ui.form.opposition;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class LegalActVersion implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private String nameVersion;
	private Date entryForceDate;
	private Date endApplicabilityDate;
	private Boolean confirmLegalActVersion;
	private GroundCategoryKindLegalAct groundCategory;
	private String code;
	/**
	 * @return the nameVersion
	 */
	public String getNameVersion() {
		return nameVersion;
	}
	/**
	 * @param nameVersion the nameVersion to set
	 */
	public void setNameVersion(String nameVersion) {
		this.nameVersion = nameVersion;
	}
	/**
	 * @return the entryForceDate
	 */
	public Date getEntryForceDate() {
		return entryForceDate;
	}
	/**
	 * @param entryForceDate the entryForceDate to set
	 */
	public void setEntryForceDate(Date entryForceDate) {
		this.entryForceDate = entryForceDate;
	}
	/**
	 * @return the endApplicabilityDate
	 */
	public Date getEndApplicabilityDate() {
		return endApplicabilityDate;
	}
	/**
	 * @param endApplicabilityDate the endApplicabilityDate to set
	 */
	public void setEndApplicabilityDate(Date endApplicabilityDate) {
		this.endApplicabilityDate = endApplicabilityDate;
	}
	
	
	
	 /**
	 * @return the confirmLegalActVersion
	 */
	public Boolean getConfirmLegalActVersion() {
		return confirmLegalActVersion;
	}
	/**
	 * @param confirmLegalActVersion the confirmLegalActVersion to set
	 */
	public void setConfirmLegalActVersion(Boolean confirmLegalActVersion) {
		this.confirmLegalActVersion = confirmLegalActVersion;
	}
	
	
/**
	 * @return the groundCategory
	 */
	public GroundCategoryKindLegalAct getGroundCategory() {
		return groundCategory;
	}
	/**
	 * @param groundCategory the groundCategory to set
	 */
	public void setGroundCategory(GroundCategoryKindLegalAct groundCategory) {
		this.groundCategory = groundCategory;
	}
	
	
/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   LegalActVersion legalActVersionForm = new LegalActVersion();
	   legalActVersionForm.setNameVersion(nameVersion);
	   legalActVersionForm.setEntryForceDate(entryForceDate);
	   legalActVersionForm.setEndApplicabilityDate(endApplicabilityDate);
	   legalActVersionForm.setConfirmLegalActVersion(confirmLegalActVersion);
	   legalActVersionForm.setGroundCategory(groundCategory);
	   legalActVersionForm.setCode(code);
       return legalActVersionForm;
   }
	
   public boolean isEmpty()
   {		   
       return (StringUtils.isBlank(nameVersion) && entryForceDate == null && endApplicabilityDate == null && confirmLegalActVersion==null);
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof LegalActVersion))
       {
           return false;
       }

       LegalActVersion that = (LegalActVersion) o;

       if (nameVersion != null ? !nameVersion.equals(that.nameVersion) : that.nameVersion != null)
       {
           return false;
       }
       if (entryForceDate != null ? !entryForceDate.equals(that.entryForceDate) : that.entryForceDate != null)
       {
           return false;
       }
       if (endApplicabilityDate != null ? !endApplicabilityDate.equals(that.endApplicabilityDate) : that.endApplicabilityDate != null)
       {
           return false;
       }
       if (confirmLegalActVersion != null ? !confirmLegalActVersion.equals(that.confirmLegalActVersion) : that.confirmLegalActVersion != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   {
       int result = nameVersion != null ? nameVersion.hashCode() : 0;
       result = value31 * result + (entryForceDate != null ? entryForceDate.hashCode() : 0);
       result = value31 * result + (endApplicabilityDate != null ? endApplicabilityDate.hashCode() : 0);
       result = value31 * result + (confirmLegalActVersion != null ? confirmLegalActVersion.hashCode() : 0);
       return result;
   }
	
	
		
}
