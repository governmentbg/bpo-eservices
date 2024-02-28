package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class OppositionBasisForm  extends AbstractImportableForm  implements java.io.Serializable, Cloneable {

	


	private static final long serialVersionUID = 827307440486649654L;

	private static final int value31 = 31;
	
	private LegalActVersion legalActVersion;
	private GroundCategoryKind groundCategory;
	private AbsoluteGrounds absoluteGrounds;
	private RelativeGrounds relativeGrounds;
	private RevocationGrounds revocationGrounds;
	
	private String formMessages;
	
	private String formWarnings;

	/**
	 * @return the legalActVerskion
	 */
	public LegalActVersion getLegalActVersion() {
		return legalActVersion;
	}
	/**
	 * @param legalActVersion the legalActVersion to set
	 */
	public void setLegalActVersion(LegalActVersion legalActVersion) {
		this.legalActVersion = legalActVersion;
	}
	/**
	 * @return the groundCategory
	 */
	public GroundCategoryKind getGroundCategory() {
		return groundCategory;
	}
	/**
	 * @param groundCategory the groundCategory to set
	 */
	public void setGroundCategory(GroundCategoryKind groundCategory) {
		this.groundCategory = groundCategory;
	}
	/**
	 * @return the absoluteGrounds
	 */
	public AbsoluteGrounds getAbsoluteGrounds() {
		return absoluteGrounds;
	}
	/**
	 * @param absoluteGrounds the absoluteGrounds to set
	 */
	public void setAbsoluteGrounds(AbsoluteGrounds absoluteGrounds) {
		this.absoluteGrounds = absoluteGrounds;
	}

	 /**
	 * @return the relativeGrounds
	 */
	public RelativeGrounds getRelativeGrounds() {
		return relativeGrounds;
	}
	/**
	 * @param relativeGrounds the relativeGrounds to set
	 */
	public void setRelativeGrounds(RelativeGrounds relativeGrounds) {
		this.relativeGrounds = relativeGrounds;
	}
/**
	 * @return the revocationGrounds
	 */
	public RevocationGrounds getRevocationGrounds() {
		return revocationGrounds;
	}
	/**
	 * @param revocationGrounds the revocationGrounds to set
	 */
	public void setRevocationGrounds(RevocationGrounds revocationGrounds) {
		this.revocationGrounds = revocationGrounds;
	}
/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public OppositionBasisForm clone() throws CloneNotSupportedException
   {
	   OppositionBasisForm oppositionBasisForm = new OppositionBasisForm();
	   oppositionBasisForm.setId(id);
	   oppositionBasisForm.setAbsoluteGrounds(absoluteGrounds);
	   oppositionBasisForm.setGroundCategory(groundCategory);
	   oppositionBasisForm.setLegalActVersion(legalActVersion);
	   oppositionBasisForm.setRelativeGrounds(relativeGrounds);
	   oppositionBasisForm.setRevocationGrounds(revocationGrounds);
       return oppositionBasisForm;
   }
	
   public boolean isEmpty()
   {
       return (StringUtils.isBlank(groundCategory.toString()) && absoluteGrounds.isEmpty() && legalActVersion.isEmpty() && relativeGrounds.isEmpty() && revocationGrounds.isEmpty());
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof OppositionBasisForm))
       {
           return false;
       }

       OppositionBasisForm that = (OppositionBasisForm) o;

       if (groundCategory != null ? !groundCategory.equals(that.groundCategory) : that.groundCategory != null)
       {
           return false;
       }
       if (absoluteGrounds != null ? !absoluteGrounds.equals(that.absoluteGrounds) : that.absoluteGrounds != null)
       {
           return false;
       }
       if (legalActVersion != null ? !legalActVersion.equals(that.legalActVersion) : that.legalActVersion != null)
       {
           return false;
       }
       if (relativeGrounds != null ? !relativeGrounds.equals(that.relativeGrounds) : that.relativeGrounds != null)
       {
           return false;
       }
       if (revocationGrounds != null ? !revocationGrounds.equals(that.revocationGrounds) : that.revocationGrounds != null)
       {
           return false;
       }


       return true;
   }

   @Override
   public int hashCode()
   {
       int result = groundCategory != null ? groundCategory.hashCode() : 0;
       result = value31 * result + (absoluteGrounds != null ? absoluteGrounds.hashCode() : 0);
       result = value31 * result + (legalActVersion != null ? legalActVersion.hashCode() : 0);
       result = value31 * result + (relativeGrounds != null ? relativeGrounds.hashCode() : 0);
       result = value31 * result + (revocationGrounds != null ? revocationGrounds.hashCode() : 0);
       return result;
   }
/* (non-Javadoc)
 * @see Validatable#getAvailableSectionName()
 */
@Override
public AvailableSection getAvailableSectionName() {
	// TODO Auto-generated method stub
	return null;
}
public String getFormMessages() {
	return formMessages;
}
public void setFormMessages(String formMessages) {
	this.formMessages = formMessages;
}
public String getFormWarnings() {
	return formWarnings;
}
public void setFormWarnings(String formWarnings) {
	this.formWarnings = formWarnings;
}
	

}
