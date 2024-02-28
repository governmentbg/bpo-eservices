package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;

public class GReputationClaimDetails implements java.io.Serializable, Cloneable {


	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private String explanationClaim;
	private FileWrapper attachments;
	
	/**
	 * @return the explanationClaim
	 */
	public String getExplanationClaim() {
		return explanationClaim;
	}
	/**
	 * @param explanationClaim the explanationClaim to set
	 */
	public void setExplanationClaim(String explanationClaim) {
		this.explanationClaim = explanationClaim;
	}

	
	/**
	 * @return the attachments
	 */
	public FileWrapper getAttachments() {
		return attachments;
	}
	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(FileWrapper attachments) {
		this.attachments = attachments;
	}
/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   GReputationClaimDetails gReputationClaimDetailsForm = new GReputationClaimDetails();
	   gReputationClaimDetailsForm.setAttachments(attachments);
	   gReputationClaimDetailsForm.setExplanationClaim(explanationClaim);
       return gReputationClaimDetailsForm;
   }
	
   public boolean isEmpty()
   {
       return (StringUtils.isBlank(explanationClaim)&& attachments==null);
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof GReputationClaimDetails))
       {
           return false;
       }

       GReputationClaimDetails that = (GReputationClaimDetails) o;

       if (explanationClaim != null ? !explanationClaim.equals(that.explanationClaim) : that.explanationClaim != null)
       {
           return false;
       }
       
       if (attachments != null ? !attachments.equals(that.attachments) : that.attachments != null)
       {
           return false;
       }
       
       return true;
   }

   @Override
   public int hashCode()
   {
       int result = explanationClaim != null ? explanationClaim.hashCode() : 0;
       result = value31 * result + (attachments != null ? attachments.hashCode() : 0);
       return result;
   }
		
	
	
}
