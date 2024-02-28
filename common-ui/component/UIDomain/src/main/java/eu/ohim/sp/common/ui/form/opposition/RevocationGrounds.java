package eu.ohim.sp.common.ui.form.opposition;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;

/**
 * Stores all the necessary information for the Designers.
 *
 * @author ssegura
 */
public class RevocationGrounds implements java.io.Serializable, Cloneable
{
	
	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private String explanations;
	private List <RevocationGroundLawArticle> revocationGroundLawArticleForm;	
	private List <String> revocationGroundText;
	private FileWrapper gExplanationGroundsMarks;
	private FileWrapper gProposalDecide;
	private FileWrapper gEvidence;
	
	
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
     * Method that returns the list of articles of revocation Grounds
     *
     * @return the list of articles of revocation Grounds
     */
	public List<RevocationGroundLawArticle> getRevocationGroundLawArticleForm() 
	{
		return revocationGroundLawArticleForm;
	}

	/**
     * Method that sets the list of articles of revocation Grounds
     *
     * @param revocationGroundLawArticleForm the list of articles of revocation Grounds to set
     */
	public void setRevocationGroundLawArticleForm(
			List<RevocationGroundLawArticle> revocationGroundLawArticleForm) 
	{
		this.revocationGroundLawArticleForm = revocationGroundLawArticleForm;
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
	 * @return the revocationGroundText
	 */
	public List<String> getrevocationGroundText() {
		return revocationGroundText;
	}

	/**
	 * @param revocationGroundText the revocationGroundText to set
	 */
	public void setrevocationGroundText(List<String> revocationGroundText) {
		this.revocationGroundText = revocationGroundText;
	}

	/**
	 * @return the gReputationClaimed
	 */
	

/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   RevocationGrounds revocationGroundsForm = new RevocationGrounds();
	   revocationGroundsForm.setRevocationGroundLawArticleForm(revocationGroundLawArticleForm);
	   revocationGroundsForm.setExplanations(explanations);
	   revocationGroundsForm.setgEvidence(gEvidence);
	   revocationGroundsForm.setgExplanationGroundsMarks(gExplanationGroundsMarks);
	   revocationGroundsForm.setgProposalDecide(gProposalDecide);
	   revocationGroundsForm.setrevocationGroundText(revocationGroundText);
       return revocationGroundsForm;
   }
	
   public boolean isEmpty()
   {
	   return (StringUtils.isBlank(explanations) && (revocationGroundLawArticleForm == null)||(revocationGroundLawArticleForm.isEmpty()) &&
			    gEvidence==null && gExplanationGroundsMarks==null && gProposalDecide==null && revocationGroundText!=null && revocationGroundText.size()>0);
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof RevocationGrounds))
       {
           return false;
       }

       RevocationGrounds that = (RevocationGrounds) o;

       if (revocationGroundLawArticleForm != null ? !revocationGroundLawArticleForm.equals(that.revocationGroundLawArticleForm) : that.revocationGroundLawArticleForm != null)
    	   return false;
       if (explanations != null ? !explanations.equals(that.explanations) : that.explanations != null)
    	   return false;
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
       if (revocationGroundText != null ? !revocationGroundText.equals(that.revocationGroundText) : that.revocationGroundText != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   { 
       int result =  (revocationGroundLawArticleForm != null && revocationGroundLawArticleForm.size() > 0 ? revocationGroundLawArticleForm.hashCode() : 0);
       result = value31 * result + (explanations != null ? explanations.hashCode() : 0);
       result = value31 * result + (gEvidence != null ? gEvidence.hashCode() : 0);
       result = value31 * result + (gExplanationGroundsMarks != null ? gExplanationGroundsMarks.hashCode() : 0);
       result = value31 * result + (gProposalDecide != null ? gProposalDecide.hashCode() : 0);
       result = value31 * result + (revocationGroundText != null ? revocationGroundText.hashCode() : 0);
       return result;
   }
	
		
}
