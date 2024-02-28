/**
 * 
 */
package eu.ohim.sp.core.domain.application;

/**
 * @author segurse
 *
 */
public enum PrefixPdfKind {
    SUBMIT_QUEUE("PrefixPdfSubmitQueue"),
	SUBMIT_PORTAL("PrefixPdfSubmitPortal");
	
	
	private PrefixPdfKind(final String text)
	{
		this.text = text;
	}
	
	private final String text;
	
	@Override
	public String toString()
	{
		return text;
	}

}