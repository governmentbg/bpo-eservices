package eu.ohim.sp.common.ui.adapter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.form.person.HolderLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.HolderNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.core.domain.person.Holder;

@Component
public class HolderFactory implements UIFactory<Holder, HolderForm> {

	@Autowired
	private HolderLegalEntityFactory holderLegalEntityFactory;

	@Autowired
	private HolderNaturalPersonFactory holderNaturalPersonFactory;

	@Override
	public Holder convertTo(HolderForm form) {
		if(form == null)
        {
            return null;
        }
		switch (form.getType()) {
			case LEGAL_ENTITY:
				if (form instanceof HolderLegalEntityForm) {
					return holderLegalEntityFactory.convertTo((HolderLegalEntityForm) form);
				}
			case NATURAL_PERSON:
				if (form instanceof HolderNaturalPersonForm) {
					return holderNaturalPersonFactory.convertTo((HolderNaturalPersonForm) form);
				}			
		}
		return null;
	}

	@Override
	public HolderForm convertFrom(Holder holder) {
		if (holder == null) {
			return null;
		} else if (StringUtils.isNotBlank(holder.getPersonNumber()) 
				&& holder.getKind() == null) {
			HolderForm holderForm = new HolderForm();
			holderForm.setId(holder.getPersonNumber());
			return holderForm;
		} else if (holder.getKind()==null) {
			return null;
		}
		switch (holder.getKind()) {
			case LEGAL_ENTITY:
				return holderLegalEntityFactory.convertFrom(holder);
			case NATURAL_PERSON:
				return holderNaturalPersonFactory.convertFrom(holder);			
			default:
				return null;
		}
	}
	
	public HolderForm convertFromApplicantForm(ApplicantForm applicant) {
		if (applicant == null) {
			return null;
		} 
		HolderForm holder = null;
		if (applicant instanceof NaturalPersonForm) {
			holder = new HolderNaturalPersonForm();
			NaturalPersonForm nApplicant = (NaturalPersonForm) applicant;
			((HolderNaturalPersonForm)holder).setFirstName(nApplicant.getFirstName());
			((HolderNaturalPersonForm)holder).setMiddleName(nApplicant.getMiddleName());
			((HolderNaturalPersonForm)holder).setSurname(nApplicant.getSurname());
			((HolderNaturalPersonForm)holder).setNationality(nApplicant.getNationality());
		} else if(applicant instanceof LegalEntityForm) {
			holder = new HolderLegalEntityForm();
		}
		if(holder != null) {
			holder.setAddress(applicant.getAddress());
			holder.setName(applicant.getName());
			holder.setEmail(applicant.getEmail());
			holder.setFax(applicant.getFax());
			holder.setPhone(applicant.getPhone());
			holder.setWebsite(applicant.getWebsite());
			holder.setImported(applicant.getImported());
			holder.setId(applicant.getId());
		}
		return holder;
	}

}
