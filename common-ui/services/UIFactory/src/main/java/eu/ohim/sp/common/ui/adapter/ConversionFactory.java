package eu.ohim.sp.common.ui.adapter;

import org.springframework.stereotype.Component;
import eu.ohim.sp.common.ui.form.claim.ConversionForm;
import eu.ohim.sp.core.domain.claim.ConversionPriority;

/**
 * 
 * @author raya
 *
 */
@Component
public class ConversionFactory implements UIFactory<ConversionPriority, ConversionForm> {

	@Override
	public ConversionPriority convertTo(ConversionForm form) {
		if(form == null) {
			return null;
		}
		ConversionPriority conversion = new ConversionPriority();
		conversion.setConversionPriorityDate(form.getConversionPriorityDate());
		conversion.setConversionApplicationDate(form.getConversionApplicationDate());
		conversion.setConversionApplicationNumber(form.getConversionApplicationNumber());
		return conversion;
	}

	@Override
	public ConversionForm convertFrom(ConversionPriority core) {
		if(core == null) {
			return null;
		}
		ConversionForm form = new ConversionForm();
		form.setConversionApplicationDate(core.getConversionApplicationDate());
		form.setConversionApplicationNumber(core.getConversionApplicationNumber());
		form.setConversionPriorityDate(core.getConversionPriorityDate());
		return form;
	}


}
