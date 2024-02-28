/**
 * 
 */
package eu.ohim.sp.common.ui.form.opposition;

/**
 * @author segurse
 *
 */
public enum GroundCategoryKind {
    ABSOLUTE_GROUNDS("Absolute Grounds"),
	RELATIVE_GROUNDS("Relative Grounds"),
    REVOCATION_GROUNDS("Revocation Grounds");
	
	
	private GroundCategoryKind(final String text)
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