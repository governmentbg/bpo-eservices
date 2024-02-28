package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;

public class GSHelperDetails  extends Id implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6019106792316622861L;
	private ApplicationExtent applicationExtent;
    private List<ClassDescription> classDescriptions;
	private String tmApplicationNumber;
	private boolean inclusive;
	/**
     * Comment on the gs - some explanations or other
     */
    private String goodsServicesComment;

	
	public ApplicationExtent getApplicationExtent() {
		return applicationExtent;
	}
	public void setApplicationExtent(ApplicationExtent applicationExtent) {
		this.applicationExtent = applicationExtent;
	}
	
	public List<ClassDescription> getClassDescriptions() {
		return classDescriptions;
	}
	public void setClassDescriptions(List<ClassDescription> classDescriptions) {
		this.classDescriptions = classDescriptions;
	}
	public String getTmApplicationNumber() {
		return tmApplicationNumber;
	}
	public void setTmApplicationNumber(String tmApplicationNumber) {
		this.tmApplicationNumber = tmApplicationNumber;
	}
	public boolean isInclusive() {
		return inclusive;
	}
	public void setInclusive(boolean inclusive) {
		this.inclusive = inclusive;
	}
	public String getGoodsServicesComment() {
		return goodsServicesComment;
	}

	public void setGoodsServicesComment(String goodsServicesComment) {
		this.goodsServicesComment = goodsServicesComment;
	}
 
}
