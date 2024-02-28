package eu.ohim.sp.common.ui.form.claim;

import java.util.Date;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * 
 * @author raya
 *
 */
public class ConversionForm extends AbstractImportableForm {
	
	private String conversionApplicationNumber;
	private Date conversionApplicationDate;
	private Date conversionPriorityDate;
		
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

	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.CONVERSION;		
	}

	@Override
	public ConversionForm clone() throws CloneNotSupportedException {
		ConversionForm conversionForm = new ConversionForm();
		conversionForm.setId(id);
		conversionForm.setConversionApplicationDate(conversionApplicationDate);
		conversionForm.setConversionApplicationNumber(conversionApplicationNumber);
		conversionForm.setConversionPriorityDate(conversionPriorityDate);
		conversionForm.setImported(getImported());
		return conversionForm;
	}
	
}
