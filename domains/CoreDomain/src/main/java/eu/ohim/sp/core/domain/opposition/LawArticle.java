package eu.ohim.sp.core.domain.opposition;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;


public class LawArticle extends Id implements Serializable
{

	private static final long serialVersionUID = 1L;
	
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
	
	
	
}
