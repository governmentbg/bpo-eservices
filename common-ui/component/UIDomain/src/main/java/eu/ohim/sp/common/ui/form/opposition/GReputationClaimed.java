package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;

public class GReputationClaimed implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private Boolean reputationClaimed;
	private String article;
	
	/**
	 * @return the reputationClaimed
	 */
	public Boolean getReputationClaimed() {
		return reputationClaimed;
	}
	/**
	 * @param reputationClaimed the reputationClaimed to set
	 */
	public void setReputationClaimed(Boolean reputationClaimed) {
		this.reputationClaimed = reputationClaimed;
	}
	/**
	 * @return the article
	 */
	public String getArticle() {
		return article;
	}
	/**
	 * @param article the article to set
	 */
	public void setArticle(String article) {
		this.article = article;
	}
	
	/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   GReputationClaimed gReputationClaimedForm = new GReputationClaimed();
	   gReputationClaimedForm.setArticle(article);
	   gReputationClaimedForm.setReputationClaimed(reputationClaimed);
       return gReputationClaimedForm;
   }
	
   public boolean isEmpty()
   {
       return (StringUtils.isBlank(article) && reputationClaimed == null);
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof GReputationClaimed))
       {
           return false;
       }

       GReputationClaimed that = (GReputationClaimed) o;

       if (article != null ? !article.equals(that.article) : that.article != null)
       {
           return false;
       }
       
       if (reputationClaimed != null ? !reputationClaimed.equals(that.reputationClaimed) : that.reputationClaimed != null)
       {
           return false;
       }
       
       return true;
   }

   @Override
   public int hashCode()
   {
       int result = reputationClaimed != null ? reputationClaimed.hashCode() : 0;
       result = value31 * result + (article != null ? article.hashCode() : 0);
       return result;
   }
		

	
}
