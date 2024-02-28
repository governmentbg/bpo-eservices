package eu.ohim.sp.common.ui.adapter;

import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.securitymeasure.SecurityMeasureForm;
import eu.ohim.sp.core.domain.application.SecurityMeasure;
@Component
public class SecurityMeasureFactory implements UIFactory<SecurityMeasure, SecurityMeasureForm>{

	@Override
	public SecurityMeasure convertTo(SecurityMeasureForm form) {
		if(form == null) {
			return null;
		}
		SecurityMeasure core = new SecurityMeasure();
		core.setSecurityMeasureForbidRightsManage(form.getForbidManage());
		core.setSecurityMeasureForbidRightsUse(form.getForbidUse());
		return core;
	}

	@Override
	public SecurityMeasureForm convertFrom(SecurityMeasure core) {
		if(core == null){
			return new SecurityMeasureForm();
		}
		SecurityMeasureForm form = new SecurityMeasureForm();
		form.setForbidManage(core.getSecurityMeasureForbidRightsManage());
		form.setForbidUse(core.getSecurityMeasureForbidRightsUse());
		return form;
	}
	
}
