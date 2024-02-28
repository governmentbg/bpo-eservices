package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;


/**
 * Stores all the necessary information for the employee representatives
 *
 * @author ssegura
 */

public class AbsoluteGroundLawArticle implements java.io.Serializable,Cloneable
{

	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private String reference;
	private String text;
	private String language;
	
	
	/**
	 * Method that returns the reference
	 *
	 * @return the reference
	 */
	public String getReference() 
	{
		return reference;
	}
	
	/**
     * Method that sets the reference
     *
     * @param reference the reference to set
     */
	public void setReference(String reference) 
	{
		this.reference = reference;
	}
	
	/**
	 * Method that returns the text
	 *
	 * @return the text
	 */
	public String getText() 
	{
		return text;
	}
	
	/**
     * Method that sets the reference
     *
     * @param reference the reference to set
     */
	public void setText(String text) 
	{
		this.text = text;
	}
	
	/**
	 * Method that returns the language
	 *
	 * @return the language
	 */
	public String getLanguage() 
	{
		return language;
	}
	
	/**
     * Method that sets the language
     *
     * @param language the language to set
     */
	public void setLanguage(String language) 
	{
		this.language = language;
	}
	
	
	 /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   AbsoluteGroundLawArticle absoluteGroundLawArticleForm = new AbsoluteGroundLawArticle();
	   absoluteGroundLawArticleForm.setReference(reference);
	   absoluteGroundLawArticleForm.setText(text);
	   absoluteGroundLawArticleForm.setLanguage(language);
       return absoluteGroundLawArticleForm;
   }
	
   public boolean isEmpty()
   {
       return (StringUtils.isBlank(reference) && StringUtils.isEmpty(text) && StringUtils.isEmpty(language));
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof AbsoluteGroundLawArticle))
       {
           return false;
       }

       AbsoluteGroundLawArticle that = (AbsoluteGroundLawArticle) o;

       if (reference != null ? !reference.equals(that.reference) : that.reference != null)
       {
           return false;
       }
       if (text != null ? !text.equals(that.text) : that.text != null)
       {
           return false;
       }
       if (language != null ? !language.equals(that.language) : that.language != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   {
       int result = reference != null ? reference.hashCode() : 0;
       result = value31 * result + (text != null ? text.hashCode() : 0);
       result = value31 * result + (language != null ? language.hashCode() : 0);
       return result;
   }
		
}
