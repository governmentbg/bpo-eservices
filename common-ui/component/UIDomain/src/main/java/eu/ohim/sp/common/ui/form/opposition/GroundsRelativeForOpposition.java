package eu.ohim.sp.common.ui.form.opposition;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;

public class GroundsRelativeForOpposition implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private List <String> relativeGroundText;
	private FileWrapper gExplanationGroundsMarks;
	private FileWrapper gProposalDecide;
	private FileWrapper gEvidence;
	private GReputationClaimed gReputationClaimed;
	private GReputationClaimDetails gReputationClaimDetails;
	private List <String> gROCountryReputationClaimed;
	private String explanations;
	
	
	/**
	 * @return the explanations
	 */
	public String getExplanations() {
		return explanations;
	}
	
	/**
	 * @param explanations the explanations to set
	 */
	public void setExplanations(String explanations) {
		this.explanations = explanations;
	}
	/**
	 * @return the relativeGroundText
	 */
	public List<String> getRelativeGroundText() {
		return relativeGroundText;
	}
	/**
	 * @param relativeGroundText the relativeGroundText to set
	 */
	public void setRelativeGroundText(List<String> relativeGroundText) {
		this.relativeGroundText = relativeGroundText;
	}
	
	 /**
	 * @return the gExplanationGroundsMarks
	 */
	public FileWrapper getgExplanationGroundsMarks() {
		return gExplanationGroundsMarks;
	}

	/**
	 * @param gExplanationGroundsMarks the gExplanationGroundsMarks to set
	 */
	public void setgExplanationGroundsMarks(
			FileWrapper gExplanationGroundsMarks) {
		this.gExplanationGroundsMarks = gExplanationGroundsMarks;
	}

	/**
	 * @return the gProposalDecide
	 */
	public FileWrapper getgProposalDecide() {
		return gProposalDecide;
	}

	/**
	 * @param gProposalDecide the gProposalDecide to set
	 */
	public void setgProposalDecide(FileWrapper gProposalDecide) {
		this.gProposalDecide = gProposalDecide;
	}

	/**
	 * @return the gEvidence
	 */
	public FileWrapper getgEvidence() {
		return gEvidence;
	}

	/**
	 * @param gEvidence the gEvidence to set
	 */
	public void setgEvidence(FileWrapper gEvidence) {
		this.gEvidence = gEvidence;
	}

	/**
	 * @return the gReputationClaimed
	 */
	public GReputationClaimed getgReputationClaimed() {
		return gReputationClaimed;
	}

	/**
	 * @param gReputationClaimed the gReputationClaimed to set
	 */
	public void setgReputationClaimed(GReputationClaimed gReputationClaimed) {
		this.gReputationClaimed = gReputationClaimed;
	}

	/**
	 * @return the gReputationClaimDetails
	 */
	public GReputationClaimDetails getgReputationClaimDetails() {
		return gReputationClaimDetails;
	}

	/**
	 * @param gReputationClaimDetails the gReputationClaimDetails to set
	 */
	public void setgReputationClaimDetails(
			GReputationClaimDetails gReputationClaimDetails) {
		this.gReputationClaimDetails = gReputationClaimDetails;
	}

/**
	 * @return the gROCountryReputationClaimed
	 */
	public List<String> getgROCountryReputationClaimed() {
		return gROCountryReputationClaimed;
	}

	/**
	 * @param gROCountryReputationClaimed the gROCountryReputationClaimed to set
	 */
	public void setgROCountryReputationClaimed(
			List<String> gROCountryReputationClaimed) {
		this.gROCountryReputationClaimed = gROCountryReputationClaimed;
	}

/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   GroundsRelativeForOpposition groundsRelativeForOppositionForm = new GroundsRelativeForOpposition();
	   groundsRelativeForOppositionForm.setgROCountryReputationClaimed(gROCountryReputationClaimed);
	   groundsRelativeForOppositionForm.setgEvidence(gEvidence);
	   groundsRelativeForOppositionForm.setgExplanationGroundsMarks(gExplanationGroundsMarks);
	   groundsRelativeForOppositionForm.setgProposalDecide(gProposalDecide);
	   groundsRelativeForOppositionForm.setgReputationClaimDetails(gReputationClaimDetails);
	   groundsRelativeForOppositionForm.setgReputationClaimed(gReputationClaimed);
	   groundsRelativeForOppositionForm.setRelativeGroundText(relativeGroundText);
	   groundsRelativeForOppositionForm.setExplanations(explanations);
       return groundsRelativeForOppositionForm;
   }
	
   public boolean isEmpty()
   {
	   
       return (StringUtils.isBlank(explanations) && (relativeGroundText == null || relativeGroundText.isEmpty()) && (gROCountryReputationClaimed == null || gROCountryReputationClaimed.isEmpty()) && gEvidence==null && gExplanationGroundsMarks==null && gProposalDecide==null &&
    		   gReputationClaimDetails.isEmpty() && gReputationClaimed.isEmpty());
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof GroundsRelativeForOpposition))
       {
           return false;
       }

       GroundsRelativeForOpposition that = (GroundsRelativeForOpposition) o;

       if (explanations != null ? !explanations.equals(that.explanations) : that.explanations != null)
       {
           return false;
       }
       
       if (relativeGroundText != null ? !relativeGroundText.equals(that.relativeGroundText) : that.relativeGroundText != null)
       {
           return false;
       }
       if (gROCountryReputationClaimed != null ? !gROCountryReputationClaimed.equals(that.gROCountryReputationClaimed) : that.gROCountryReputationClaimed != null)
       {
           return false;
       }
       if (gEvidence != null ? !gEvidence.equals(that.gEvidence) : that.gEvidence != null)
       {
           return false;
       }
       if (gExplanationGroundsMarks != null ? !gExplanationGroundsMarks.equals(that.gExplanationGroundsMarks) : that.gExplanationGroundsMarks != null)
       {
           return false;
       }
       if (gProposalDecide != null ? !gProposalDecide.equals(that.gProposalDecide) : that.gProposalDecide != null)
       {
           return false;
       }
       if (gReputationClaimDetails != null ? !gReputationClaimDetails.equals(that.gReputationClaimDetails) : that.gReputationClaimDetails != null)
       {
           return false;
       }
       if (gReputationClaimed != null ? !gReputationClaimed.equals(that.gReputationClaimed) : that.gReputationClaimed != null)
       {
           return false;
       }
       
      
       return true;
   }

   @Override
   public int hashCode()
   {
       int result = relativeGroundText != null ? relativeGroundText.hashCode() : 0;
       result = value31 * result + (gROCountryReputationClaimed != null ? gROCountryReputationClaimed.hashCode() : 0);
       result = value31 * result + (gEvidence != null ? gEvidence.hashCode() : 0);
       result = value31 * result + (gExplanationGroundsMarks != null ? gExplanationGroundsMarks.hashCode() : 0);
       result = value31 * result + (gProposalDecide != null ? gProposalDecide.hashCode() : 0);
       result = value31 * result + (gReputationClaimDetails != null ? gReputationClaimDetails.hashCode() : 0);
       result = value31 * result + (gReputationClaimed != null ? gReputationClaimed.hashCode() : 0);
       result = value31 * result + (explanations != null ? explanations.hashCode() : 0);
       return result;
   }
		
	
	
}
