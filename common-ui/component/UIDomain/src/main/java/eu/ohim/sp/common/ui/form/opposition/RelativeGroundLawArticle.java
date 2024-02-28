package eu.ohim.sp.common.ui.form.opposition;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RelativeGroundLawArticle implements java.io.Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private String lawArticleReference;
	private String lawArticleText;
	private String language;
	private Boolean reputationClaim;
	private Boolean exclusivity;
	private List <String> excludedLawArticleReference;
	/**
	 * @return the lawArticleReference
	 */
	public String getLawArticleReference() {
		return lawArticleReference;
	}
	/**
	 * @param lawArticleReference the lawArticleReference to set
	 */
	public void setLawArticleReference(String lawArticleReference) {
		this.lawArticleReference = lawArticleReference;
	}
	/**
	 * @return the lawArticleText
	 */
	public String getLawArticleText() {
		return lawArticleText;
	}
	/**
	 * @param lawArticleText the lawArticleText to set
	 */
	public void setLawArticleText(String lawArticleText) {
		this.lawArticleText = lawArticleText;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the reputationClaim
	 */
	public Boolean getReputationClaim() {
		return reputationClaim;
	}
	/**
	 * @param reputationClaim the reputationClaim to set
	 */
	public void setReputationClaim(Boolean reputationClaim) {
		this.reputationClaim = reputationClaim;
	}
	/**
	 * @return the exclusivity
	 */
	public Boolean getExclusivity() {
		return exclusivity;
	}
	/**
	 * @param exclusivity the exclusivity to set
	 */
	public void setExclusivity(Boolean exclusivity) {
		this.exclusivity = exclusivity;
	}
	/**
	 * @return the excludedLawArticleReference
	 */
	public List<String> getExcludedLawArticleReference() {
		return excludedLawArticleReference;
	}
	/**
	 * @param excludedLawArticleReference the excludedLawArticleReference to set
	 */
	public void setExcludedLawArticleReference(
			List<String> excludedLawArticleReference) {
		this.excludedLawArticleReference = excludedLawArticleReference;
	}
	
	 /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   RelativeGroundLawArticle relativeGroundLawArticleForm = new RelativeGroundLawArticle();
	   relativeGroundLawArticleForm.setExcludedLawArticleReference(excludedLawArticleReference);
	   relativeGroundLawArticleForm.setExclusivity(exclusivity);
	   relativeGroundLawArticleForm.setLanguage(language);
	   relativeGroundLawArticleForm.setLawArticleReference(lawArticleReference);
	   relativeGroundLawArticleForm.setLawArticleText(lawArticleText);
	   relativeGroundLawArticleForm.setLawArticleText(lawArticleText);
	   relativeGroundLawArticleForm.setReputationClaim(reputationClaim);
       return relativeGroundLawArticleForm;
   }
	
   public boolean isEmpty()
   {
       return (StringUtils.isBlank(language) && StringUtils.isEmpty(lawArticleReference) && StringUtils.isEmpty(lawArticleText) && exclusivity == null && reputationClaim == null &&
    		   (excludedLawArticleReference == null || excludedLawArticleReference.isEmpty()));
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof RelativeGroundLawArticle))
       {
           return false;
       }

       RelativeGroundLawArticle that = (RelativeGroundLawArticle) o;

       if (language != null ? !language.equals(that.language) : that.language != null)
       {
           return false;
       }
       if (lawArticleReference != null ? !lawArticleReference.equals(that.lawArticleReference) : that.lawArticleReference != null)
       {
           return false;
       }
       if (lawArticleText != null ? !lawArticleText.equals(that.lawArticleText) : that.lawArticleText != null)
       {
           return false;
       }
       if (exclusivity != null ? !exclusivity.equals(that.exclusivity) : that.exclusivity != null)
       {
           return false;
       }
       if (reputationClaim != null ? !reputationClaim.equals(that.reputationClaim) : that.reputationClaim != null)
       {
           return false;
       }
       if (excludedLawArticleReference != null ? !excludedLawArticleReference.equals(that.excludedLawArticleReference) : that.excludedLawArticleReference != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   {
       int result = language != null ? language.hashCode() : 0;
       result = value31 * result + (lawArticleReference != null ? lawArticleReference.hashCode() : 0);
       result = value31 * result + (lawArticleText != null ? lawArticleText.hashCode() : 0);
       result = value31 * result + (exclusivity != null ? exclusivity.hashCode() : 0);
       result = value31 * result + (reputationClaim != null ? reputationClaim.hashCode() : 0);
       result = value31 * result + (excludedLawArticleReference != null ? excludedLawArticleReference.hashCode() : 0);
       return result;
   }
	

}
