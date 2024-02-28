package eu.ohim.sp.common.ui.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import eu.ohim.sp.core.domain.licence.Licence;

@Component
public class LicenceFactory implements UIFactory<Licence, LicenceForm>{
	
	@Autowired
	private GSHelperFactory gsHelperFactory;
	
	@Override
	public Licence convertTo(LicenceForm form) {
		if(form == null) {
			return new Licence();
		}

		Licence licence = new Licence();
		if(form.getLicenceKind() != null){
			switch(form.getLicenceKind()){
			case NONEXCLUSIVE: licence.setLicenceKind(eu.ohim.sp.core.domain.licence.LicenceKind.NONEXCLUSIVE); break;
			case EXCLUSIVE: licence.setLicenceKind(eu.ohim.sp.core.domain.licence.LicenceKind.EXCLUSIVE); break;
			default: licence.setLicenceKind(eu.ohim.sp.core.domain.licence.LicenceKind.UNKNOWN); break;
			}
		}
		licence.setPeriodLimitationEndDate(form.getPeriodLimitationEndDate());
		licence.setSubLicenceIndicator(form.getSubLicenceIndicator());
		licence.setTerritoryLimitationIndicator(form.getTerritoryLimitationIndicator());
		licence.setTerritoryLimitationText(form.getTerritoryLimitationText());
		licence.setPeriodLimitationIndicator(form.getPeriodLimitationIndicator());
		licence.setGsHelper(gsHelperFactory.convertTo(form.getGsHelper()));
		return licence;
	}

	@Override
	public LicenceForm convertFrom(Licence core) {
		if(core == null) {
			return new LicenceForm();
		}
		LicenceForm form = new LicenceForm();
		if(core.getLicenceKind() != null){
			switch(core.getLicenceKind()) {
			case NONEXCLUSIVE: form.setLicenceKind( eu.ohim.sp.common.ui.form.licence.LicenceKind.NONEXCLUSIVE); break;
			case EXCLUSIVE: form.setLicenceKind( eu.ohim.sp.common.ui.form.licence.LicenceKind.EXCLUSIVE); break;
			default: form.setLicenceKind( eu.ohim.sp.common.ui.form.licence.LicenceKind.UNKNOWN); break;
			}
		}
		form.setPeriodLimitationEndDate(core.getPeriodLimitationEndDate());
		form.setSubLicenceIndicator(core.getSubLicenceIndicator());
		form.setTerritoryLimitationIndicator(core.getTerritoryLimitationIndicator());
		form.setTerritoryLimitationText(core.getTerritoryLimitationText());
		form.setPeriodLimitationIndicator(core.getPeriodLimitationIndicator());
		form.setGsHelper(gsHelperFactory.convertFrom(core.getGsHelper()));
		return form;
	}

}
