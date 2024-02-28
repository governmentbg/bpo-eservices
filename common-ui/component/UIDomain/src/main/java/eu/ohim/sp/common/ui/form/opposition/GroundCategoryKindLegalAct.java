/**
 * 
 */
package eu.ohim.sp.common.ui.form.opposition;

/**
 * @author segurse
 *
 */
public enum GroundCategoryKindLegalAct {
    ABSOLUTE_GROUNDS("Absolute Grounds"),
	RELATIVE_GROUNDS("Relative Grounds"),
	REVOCATION_GROUNDS("Revocation Grounds"),
    BOTH("both");
	
	
	private GroundCategoryKindLegalAct(final String text)
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