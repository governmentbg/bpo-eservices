/**
 * 
 */
package eu.ohim.sp.core.domain.trademark;

/**
 * @author segurse
 *
 */
public enum ApplicationExtent {
	ALL_GOODS_AND_SERVICES("All goods and services"),
	PARTIAL_GOODS_AND_SERVICES("Partial goods and services"),
	OTHER("Other");
	
	private final String value;

	private ApplicationExtent(final String value) {
		this.value = value;
	}
	
	public String value(){
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
