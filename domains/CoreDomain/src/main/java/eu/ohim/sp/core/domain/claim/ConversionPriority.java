package eu.ohim.sp.core.domain.claim;

import java.util.Date;

/**
 * 
 * @author raya
 *
 */
public class ConversionPriority extends Claim {
	
	private static final long serialVersionUID = -625573354125409601L;
	private String conversionKind;
	private String conversionApplicationNumber;
	private Date conversionApplicationDate;
	private Date conversionPriorityDate;
	public String getConversionKind() {
		return conversionKind;
	}
	public void setConversionKind(String conversionKind) {
		this.conversionKind = conversionKind;
	}
	public String getConversionApplicationNumber() {
		return conversionApplicationNumber;
	}
	public void setConversionApplicationNumber(String conversionApplicationNumber) {
		this.conversionApplicationNumber = conversionApplicationNumber;
	}
	public Date getConversionApplicationDate() {
		return conversionApplicationDate;
	}
	public void setConversionApplicationDate(Date conversionApplicationDate) {
		this.conversionApplicationDate = conversionApplicationDate;
	}
	public Date getConversionPriorityDate() {
		return conversionPriorityDate;
	}
	public void setConversionPriorityDate(Date conversionPriorityDate) {
		this.conversionPriorityDate = conversionPriorityDate;
	}
	
	
	
}
