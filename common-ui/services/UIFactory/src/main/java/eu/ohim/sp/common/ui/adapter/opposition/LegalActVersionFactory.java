package eu.ohim.sp.common.ui.adapter.opposition;

import org.springframework.stereotype.Component;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions;

@Component
public class LegalActVersionFactory implements UIFactory<eu.ohim.sp.core.domain.opposition.LegalActVersion , eu.ohim.sp.common.ui.form.opposition.LegalActVersion>{

	@Override

	public eu.ohim.sp.core.domain.opposition.LegalActVersion convertTo(eu.ohim.sp.common.ui.form.opposition.LegalActVersion form) {
		if (form == null) {
			return new eu.ohim.sp.core.domain.opposition.LegalActVersion();
		}
		eu.ohim.sp.core.domain.opposition.LegalActVersion core = new eu.ohim.sp.core.domain.opposition.LegalActVersion();
		core.setCodeVersion(form.getCode());
		core.setEndApplicabilityDate(form.getEndApplicabilityDate());
		core.setEntryForceDate(form.getEntryForceDate());
		core.setNameVersion(form.getNameVersion());
		return core;
	}

	@Override
	public eu.ohim.sp.common.ui.form.opposition.LegalActVersion convertFrom(eu.ohim.sp.core.domain.opposition.LegalActVersion core) {
		if (core == null) {
			return new eu.ohim.sp.common.ui.form.opposition.LegalActVersion();
		}
		eu.ohim.sp.common.ui.form.opposition.LegalActVersion form = new eu.ohim.sp.common.ui.form.opposition.LegalActVersion();
		form.setCode(core.getCodeVersion());
		form.setEndApplicabilityDate(core.getEndApplicabilityDate());
		form.setEntryForceDate(core.getEntryForceDate());
		form.setNameVersion(core.getNameVersion());
		return form;
		
		
	}
	
	public eu.ohim.sp.common.ui.form.opposition.LegalActVersion convertToLegalActUI(LegalActVersions.LegalActVersion la , String applicationType){
		eu.ohim.sp.common.ui.form.opposition.LegalActVersion legalActAvaible = new eu.ohim.sp.common.ui.form.opposition.LegalActVersion();
		legalActAvaible.setCode(la.getCode());
		legalActAvaible.setConfirmLegalActVersion(false);
		legalActAvaible.setEntryForceDate(la.getEntryIntoForceDate());
		legalActAvaible.setEndApplicabilityDate(la.getEndApplicabilityDate());
		if (applicationType.equals("tm-revocation")) {
			legalActAvaible.setGroundCategory(GroundCategoryKindLegalAct.REVOCATION_GROUNDS);
		}
		else{
			if (applicationType.equals("tm-invalidity")) {
			legalActAvaible.setGroundCategory(GroundCategoryKindLegalAct.BOTH); //TODO change to null
			}
			else{
				if (applicationType.equals("tm-opposition")) {
				legalActAvaible.setGroundCategory(GroundCategoryKindLegalAct.RELATIVE_GROUNDS); //TODO change to null
				}
			}
		}
		legalActAvaible.setNameVersion(la.getValue());
		return legalActAvaible;
	}

}
