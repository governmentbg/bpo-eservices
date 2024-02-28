package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.core.domain.person.Assignee;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssigneeFactory implements UIFactory<Assignee, AssigneeForm> {

	@Autowired
	private AssigneeLegalEntityFactory assigneeLegalEntityFactory;

	@Autowired
	private AssigneeNaturalPersonFactory assigneeNaturalPersonFactory;

	@Override
	public Assignee convertTo(AssigneeForm form) {
		if(form == null)
        {
            return null;
        }
		switch (form.getType()) {
			case LEGAL_ENTITY:
				if (form instanceof AssigneeLegalEntityForm) {
					return assigneeLegalEntityFactory.convertTo((AssigneeLegalEntityForm) form);
				}
			case NATURAL_PERSON:
				if (form instanceof AssigneeNaturalPersonForm) {
					return assigneeNaturalPersonFactory.convertTo((AssigneeNaturalPersonForm) form);
				}			
		}
		return null;
	}

	@Override
	public AssigneeForm convertFrom(Assignee assignee) {
		if (assignee == null) {
			return null;
		} else if (StringUtils.isNotBlank(assignee.getPersonNumber()) 
				&& assignee.getKind() == null) {
			AssigneeForm assigneeForm = new AssigneeForm();
			assigneeForm.setId(assignee.getPersonNumber());
			return assigneeForm;
		} else if (assignee.getKind()==null) {
			return null;
		}
		switch (assignee.getKind()) {
			case LEGAL_ENTITY:
				return assigneeLegalEntityFactory.convertFrom(assignee);
			case NATURAL_PERSON:
				return assigneeNaturalPersonFactory.convertFrom(assignee);			
			default:
				return null;
		}
	}

	public AssigneeForm convertFromApplicantForm(ApplicantForm applicant) {
		if (applicant == null) {
			return null;
		}
		AssigneeForm assignee = null;
		if (applicant instanceof NaturalPersonForm) {
			assignee = new AssigneeNaturalPersonForm();
			NaturalPersonForm nApplicant = (NaturalPersonForm) applicant;
			((AssigneeNaturalPersonForm)assignee).setFirstName(nApplicant.getFirstName());
			((AssigneeNaturalPersonForm)assignee).setMiddleName(nApplicant.getMiddleName());
			((AssigneeNaturalPersonForm)assignee).setSurname(nApplicant.getSurname());
			((AssigneeNaturalPersonForm)assignee).setNationality(nApplicant.getNationality());
		} else if(applicant instanceof LegalEntityForm) {
			assignee = new AssigneeLegalEntityForm();
		}
		if(assignee != null) {
			assignee.setAddress(applicant.getAddress());
			assignee.setName(applicant.getName());
			assignee.setEmail(applicant.getEmail());
			assignee.setFax(applicant.getFax());
			assignee.setPhone(applicant.getPhone());
			assignee.setWebsite(applicant.getWebsite());
			assignee.setImported(applicant.getImported());
			assignee.setId(applicant.getId());
		}
		return assignee;
	}

	public AssigneeForm convertFromInventorForm(InventorForm inventor) {
		if (inventor == null) {
			return null;
		}
		AssigneeNaturalPersonForm assignee = new AssigneeNaturalPersonForm();
		assignee.setType(AssigneeKindForm.NATURAL_PERSON);
		assignee.setFirstName(inventor.getFirstName());
		assignee.setMiddleName(inventor.getMiddleName());
		assignee.setSurname(inventor.getSurname());
		assignee.setNationality(inventor.getNationality());

		assignee.setAddress(inventor.getAddress());
		assignee.setName(inventor.getName());
		assignee.setEmail(inventor.getEmail());
		assignee.setFax(inventor.getFax());
		assignee.setPhone(inventor.getPhone());
		assignee.setWebsite(inventor.getWebsite());
		assignee.setImported(inventor.getImported());
		assignee.setId(inventor.getId());
		return assignee;
	}


}
