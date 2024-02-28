package eu.ohim.sp.common.ui.form.design;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;

/**
 * POJO that contains information about a Locarno class.
 * TODO Revise with Locarno integration
 */
public class ESDesignApplicationDataForm implements Serializable, Cloneable {
	

	private static final long serialVersionUID = -4506882109444352108L;
	
	private String applicationNumber;
	private Date applicationDate;
	private Date publicationDate;
	private List<ApplicantForm> holders;
	private List<RepresentativeForm> representatives;
	private List<DesignerForm> designers;
    private String applicantST13;
    private String representativeST13;
		
	/**
	 * @return the applicationNumber
	 */
	public String getApplicationNumber() {
		return applicationNumber;
	}

	/**
	 * @param applicationNumber the applicationNumber to set
	 */
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	/**
	 * @return the publicationDate
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @param publicationDate the publicationDate to set
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	/**
	 * @return the holders
	 */
	public List<ApplicantForm> getHolders() {
		return holders;
	}

	/**
	 * @param holders the holders to set
	 */
	public void setHolders(List<ApplicantForm> holders) {
		this.holders = holders;
	}

	/**
	 * @return the representatives
	 */
	public List<RepresentativeForm> getRepresentatives() {
		return representatives;
	}

	/**
	 * @param representatives the representatives to set
	 */
	public void setRepresentatives(List<RepresentativeForm> representatives) {
		this.representatives = representatives;
	}

	/**
	 * @return the designers
	 */
	public List<DesignerForm> getDesigners() {
		return designers;
	}

	/**
	 * @param designers the designers to set
	 */
	public void setDesigners(List<DesignerForm> designers) {
		this.designers = designers;
	}

	/**
    * (non-Javadoc)
    *
    * @see java.lang.Object#clone()
    */
	@Override
	public ESDesignApplicationDataForm clone() {
		ESDesignApplicationDataForm designApplicationDataForm = new ESDesignApplicationDataForm();
		designApplicationDataForm.setApplicationDate(applicationDate);
		designApplicationDataForm.setApplicationNumber(applicationNumber);
		designApplicationDataForm.setDesigners(designers);
		designApplicationDataForm.setHolders(holders);
		designApplicationDataForm.setPublicationDate(publicationDate);
		designApplicationDataForm.setRepresentatives(representatives);
		return designApplicationDataForm;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationDate == null) ? 0 : applicationDate.hashCode());
		result = prime * result + ((applicationNumber == null) ? 0 : applicationNumber.hashCode());
		result = prime * result + ((designers == null) ? 0 : designers.hashCode());
		result = prime * result + ((holders == null) ? 0 : holders.hashCode());
		result = prime * result + ((publicationDate == null) ? 0 : publicationDate.hashCode());
		result = prime * result + ((representatives == null) ? 0 : representatives.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	   {
	       if (this == o)
	       {
	           return true;
	       }
	       if (!(o instanceof ESDesignApplicationDataForm))
	       {
	           return false;
	       }

	       ESDesignApplicationDataForm that = (ESDesignApplicationDataForm) o;

	       if (applicationDate != null ? !applicationDate.equals(that.applicationDate) : that.applicationDate != null)
	    	   return false;
	       if (applicationNumber != null ? !applicationNumber.equals(that.applicationNumber) : that.applicationNumber != null)
	    	   return false;
	       if (designers != null ? !designers.equals(that.designers) : that.designers != null)
	       {
	           return false;
	       }
	       if (holders != null ? !holders.equals(that.holders) : that.holders != null)
	       {
	           return false;
	       }
	       if (publicationDate != null ? !publicationDate.equals(that.publicationDate) : that.publicationDate != null)
	       {
	           return false;
	       }
	       if (representatives != null ? !representatives.equals(that.representatives) : that.representatives != null)
	       {
	           return false;
	       }

	       return true;
	   }
	
	public boolean isEmpty()
	   {
		   return (StringUtils.isBlank(applicationNumber) && applicationDate == null && designers==null && holders==null && representatives==null && publicationDate==null);
	   }

    public String getApplicantST13() {
        if(applicantST13 != null) {
            return applicantST13.substring(applicantST13.lastIndexOf("/") + 1);
        }
        return applicantST13;
    }

    public void setApplicantST13(String applicantST13) {
        this.applicantST13 = applicantST13;
    }

    public String getRepresentativeST13() {
        if(representativeST13 != null) {
            return representativeST13.substring(representativeST13.lastIndexOf("/") + 1);
        }
        return representativeST13;
    }

    public void setRepresentativeST13(String representativeST13) {
        this.representativeST13 = representativeST13;
    }
}
