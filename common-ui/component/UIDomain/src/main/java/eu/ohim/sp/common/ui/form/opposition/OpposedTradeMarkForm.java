package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;


public class OpposedTradeMarkForm extends TMDetailsForm implements java.io.Serializable ,Cloneable {


	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5819038877594996129L;

	private static final int value31 = 31;
	
	private String applicationExtent;
	//Applicant and Representative
	private ApplicantForm opposedTradeMarkApplicant;
	private RepresentativeForm opposedTradeMarkRepresentative;	
	
	/**
	 * @return the opposedTradeMarkApplicant
	 */
	public ApplicantForm getOpposedTradeMarkApplicant() {
		return opposedTradeMarkApplicant;
	}

	/**
	 * @param opposedTradeMarkApplicant the opposedTradeMarkApplicant to set
	 */
	public void setOpposedTradeMarkApplicant(
			ApplicantForm opposedTradeMarkApplicant) {
		this.opposedTradeMarkApplicant = opposedTradeMarkApplicant;
	}

	 /**
	 * @return the opposedTradeMarkRepresentative
	 */
	public RepresentativeForm getOpposedTradeMarkRepresentative() {
		return opposedTradeMarkRepresentative;
	}

	/**
	 * @param opposedTradeMarkRepresentative the opposedTradeMarkRepresentative to set
	 */
	public void setOpposedTradeMarkRepresentative(
			RepresentativeForm opposedTradeMarkRepresentative) {
		this.opposedTradeMarkRepresentative = opposedTradeMarkRepresentative;
	}
	
	/**
	 * @return the applicationExtent
	 */
	public String getApplicationExtent() {
		return applicationExtent;
	}

	/**
	 * @param applicationExtent the applicationExtent to set
	 */
	public void setApplicationExtent(String applicationExtent) {
		this.applicationExtent = applicationExtent;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public OpposedTradeMarkForm clone() throws CloneNotSupportedException{
	   OpposedTradeMarkForm opposedTradeMarkForm = new OpposedTradeMarkForm();
	   opposedTradeMarkForm.setOpposedTradeMarkApplicant(opposedTradeMarkApplicant);
	   opposedTradeMarkForm.setOpposedTradeMarkRepresentative(opposedTradeMarkRepresentative);
	   opposedTradeMarkForm.setApplicationExtent(applicationExtent);
       return opposedTradeMarkForm;
       
   }
	
   public boolean isEmpty()
   {
	   
       return (StringUtils.isBlank(applicationExtent) && StringUtils.isBlank(getTradeMarkStatus()) && getApplicationDate() == null &&  StringUtils.isBlank(getApplicationNumber()) && StringUtils.isBlank(getApplicationStatus()) && StringUtils.isBlank(getTradeMarkType().toString()) && 
    		   StringUtils.isBlank(getMarkDisclaimer()) && getPublicationDate() == null && getApplicationRepresentation().isEmpty() && opposedTradeMarkApplicant==null &&  opposedTradeMarkRepresentative == null &&
    		   getOriginalGS()==null && getGoodsAndServices() == null && StringUtils.isBlank(getTradeMarkName()));
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof OpposedTradeMarkForm))
       {
           return false;
       }

       OpposedTradeMarkForm that = (OpposedTradeMarkForm) o;

       if (applicationExtent != null ? !applicationExtent.equals(that.applicationExtent) : that.applicationExtent != null)
       {
           return false;
       }
       
       if (getTradeMarkName() != null ? !getTradeMarkName().equals(that.getTradeMarkName()) : that.getTradeMarkName() != null)
       {
           return false;
       }
       
       if (getTradeMarkStatus() != null ? !getTradeMarkStatus().equals(that.getTradeMarkStatus()) : that.getTradeMarkStatus() != null)
       {
           return false;
       }
       
       if (getApplicationNumber() != null ? !getApplicationNumber().equals(that.getApplicationNumber()) : that.getApplicationNumber() != null)
       {
           return false;
       }
       if (getApplicationDate() != null ? !getApplicationDate().equals(that.getApplicationDate()) : that.getApplicationDate() != null)
       {
           return false;
       }
       if (getApplicationStatus() != null ? !getApplicationStatus() .equals(that.getApplicationStatus() ) : that.getApplicationStatus()  != null)
       {
           return false;
       }
       if (getTradeMarkType() != null ? !getTradeMarkType().equals(that.getTradeMarkType()) : that.getTradeMarkType() != null)
       {
           return false;
       }
       if (getMarkDisclaimer() != null ? !getMarkDisclaimer().equals(that.getMarkDisclaimer()) : that.getMarkDisclaimer() != null)
       {
           return false;
       }
       if (getPublicationDate() != null ? !getPublicationDate().equals(that.getPublicationDate()) : that.getPublicationDate() != null)
       {
           return false;
       }
       if (getApplicationRepresentation() != null ? !getApplicationRepresentation().equals(that.getApplicationRepresentation()) : that.getApplicationRepresentation() != null)
       {
           return false;
       }
       if (opposedTradeMarkApplicant != null ? !opposedTradeMarkApplicant.equals(that.opposedTradeMarkApplicant) : that.opposedTradeMarkApplicant != null)
       {
           return false;
       }
       if (opposedTradeMarkRepresentative != null ? !opposedTradeMarkRepresentative.equals(that.opposedTradeMarkRepresentative) : that.opposedTradeMarkRepresentative != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   {
       int result = getApplicationNumber() != null ? getApplicationNumber().hashCode() : 0;
       result = value31 * result + (getTradeMarkName() != null ? getTradeMarkName().hashCode() : 0);
       result = value31 * result + (getTradeMarkStatus() != null ? getTradeMarkStatus().hashCode() : 0);
       result = value31 * result + (getApplicationDate() != null ? getApplicationDate().hashCode() : 0);
       result = value31 * result + (getApplicationStatus() != null ? getApplicationStatus().hashCode() : 0);
       result = value31 * result + (getTradeMarkType() != null ? getTradeMarkType().hashCode() : 0);
       result = value31 * result + (getMarkDisclaimer() != null ? getMarkDisclaimer().hashCode() : 0);
       result = value31 * result + (getPublicationDate() != null ? getPublicationDate().hashCode() : 0);
       result = value31 * result + (getApplicationRepresentation() != null ? getApplicationRepresentation().hashCode() : 0);
       result = value31 * result + (opposedTradeMarkApplicant != null ? opposedTradeMarkApplicant.hashCode() : 0);
       result = value31 * result + (opposedTradeMarkRepresentative != null ? opposedTradeMarkRepresentative.hashCode() : 0);
       result = value31 * result + (applicationExtent != null ? applicationExtent.hashCode() : 0);
       return result;
   }
	

}
