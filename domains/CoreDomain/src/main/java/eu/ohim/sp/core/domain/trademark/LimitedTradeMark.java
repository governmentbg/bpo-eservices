/**
 * 
 */
package eu.ohim.sp.core.domain.trademark;

import java.util.List;

/**
 * @author segurse
 *
 */
public class LimitedTradeMark extends TradeMark {
	
	private static final long serialVersionUID = 1L;
	private ApplicationExtent applicationExtent;
    private List<ClassDescription> limitedClassDescriptions;
	/**
	 * @return the applicationExtent
	 */
	public ApplicationExtent getApplicationExtent() {
		return applicationExtent;
	}
	/**
	 * @param applicationExtent the applicationExtent to set
	 */
	public void setApplicationExtent(ApplicationExtent applicationExtent) {
		this.applicationExtent = applicationExtent;
	}
	/**
	 * @return the limitedClassDescriptions
	 */
	public List<ClassDescription> getLimitedClassDescriptions() {
		return limitedClassDescriptions;
	}
	/**
	 * @param limitedClassDescriptions the limitedClassDescriptions to set
	 */
	public void setLimitedClassDescriptions(
			List<ClassDescription> limitedClassDescriptions) {
		this.limitedClassDescriptions = limitedClassDescriptions;
	}
    
    

}
