package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;


/**
 * Stores all the necessary information for the employee representatives
 *
 * @author ssegura
 */

public class RevocationGroundLawArticle implements java.io.Serializable,Cloneable
{

	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private String reference;
	private String text;
	private String language;
	private Boolean nonUse;
	private int nonUsePeriod;
	
	
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
	
	
	
	
	 /**
	 * @return the nonUse
	 */
	public Boolean getNonUse() {
		return nonUse;
	}

	/**
	 * @param nonUse the nonUse to set
	 */
	public void setNonUse(Boolean nonUse) {
		this.nonUse = nonUse;
	}

	/**
	 * @return the nonUsePeriod
	 */
	public int getNonUsePeriod() {
		return nonUsePeriod;
	}

	/**
	 * @param nonUsePeriod the nonUsePeriod to set
	 */
	public void setNonUsePeriod(int nonUsePeriod) {
		this.nonUsePeriod = nonUsePeriod;
	}

/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   RevocationGroundLawArticle revocationGroundLawArticle = new RevocationGroundLawArticle();
	   revocationGroundLawArticle.setReference(reference);
	   revocationGroundLawArticle.setText(text);
	   revocationGroundLawArticle.setLanguage(language);
       return revocationGroundLawArticle;
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
       if (!(o instanceof RevocationGroundLawArticle))
       {
           return false;
       }

       RevocationGroundLawArticle that = (RevocationGroundLawArticle) o;

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
