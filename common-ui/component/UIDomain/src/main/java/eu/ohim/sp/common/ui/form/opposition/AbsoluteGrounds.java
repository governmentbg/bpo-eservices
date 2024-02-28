package eu.ohim.sp.common.ui.form.opposition;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;

/**
 * Stores all the necessary information for the Designers.
 *
 * @author ssegura
 */
public class AbsoluteGrounds implements java.io.Serializable, Cloneable
{
	
	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private String explanations;
	private List <AbsoluteGroundLawArticle> absoluteGroundLawArticleForm;	
	private List <String> absoluteGroundText;
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
     * Method that returns the list of articles of absolute Grounds
     *
     * @return the list of articles of absolute Grounds
     */
	public List<AbsoluteGroundLawArticle> getAbsoluteGroundLawArticleForm() 
	{
		return absoluteGroundLawArticleForm;
	}

	/**
     * Method that sets the list of articles of absolute Grounds
     *
     * @param absoluteGroundLawArticleForm the list of articles of absolute Grounds to set
     */
	public void setAbsoluteGroundLawArticleForm(
			List<AbsoluteGroundLawArticle> absoluteGroundLawArticleForm) 
	{
		this.absoluteGroundLawArticleForm = absoluteGroundLawArticleForm;
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
	 * @return the absoluteGroundText
	 */
	public List<String> getAbsoluteGroundText() {
		return absoluteGroundText;
	}

	/**
	 * @param absoluteGroundText the absoluteGroundText to set
	 */
	public void setAbsoluteGroundText(List<String> absoluteGroundText) {
		this.absoluteGroundText = absoluteGroundText;
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
	   AbsoluteGrounds absoluteGroundsForm = new AbsoluteGrounds();
	   absoluteGroundsForm.setAbsoluteGroundLawArticleForm(absoluteGroundLawArticleForm);
	   absoluteGroundsForm.setExplanations(explanations);
	   absoluteGroundsForm.setgEvidence(gEvidence);
	   absoluteGroundsForm.setgExplanationGroundsMarks(gExplanationGroundsMarks);
	   absoluteGroundsForm.setgProposalDecide(gProposalDecide);
	   absoluteGroundsForm.setAbsoluteGroundText(absoluteGroundText);
       return absoluteGroundsForm;
   }
	
   public boolean isEmpty()
   {
	   return (StringUtils.isBlank(explanations) && (absoluteGroundLawArticleForm == null)||(absoluteGroundLawArticleForm.isEmpty()) &&
			    gEvidence==null && gExplanationGroundsMarks==null && gProposalDecide==null && absoluteGroundText!=null && absoluteGroundText.size()>0);
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof AbsoluteGrounds))
       {
           return false;
       }

       AbsoluteGrounds that = (AbsoluteGrounds) o;

       if (absoluteGroundLawArticleForm != null ? !absoluteGroundLawArticleForm.equals(that.absoluteGroundLawArticleForm) : that.absoluteGroundLawArticleForm != null)
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
       if (absoluteGroundText != null ? !absoluteGroundText.equals(that.absoluteGroundText) : that.absoluteGroundText != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   { 
       int result =  (absoluteGroundLawArticleForm != null && absoluteGroundLawArticleForm.size() > 0 ? absoluteGroundLawArticleForm.hashCode() : 0);
       result = value31 * result + (explanations != null ? explanations.hashCode() : 0);
       result = value31 * result + (gEvidence != null ? gEvidence.hashCode() : 0);
       result = value31 * result + (gExplanationGroundsMarks != null ? gExplanationGroundsMarks.hashCode() : 0);
       result = value31 * result + (gProposalDecide != null ? gProposalDecide.hashCode() : 0);
       result = value31 * result + (absoluteGroundText != null ? absoluteGroundText.hashCode() : 0);
       return result;
   }
	
		
}
