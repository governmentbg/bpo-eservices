/*******************************************************************************

 * * $Id:: ApplicantFactory.java 113496 2013-04-22 15:03:04Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

@Component
public class ApplicantFactory implements UIFactory<Applicant, ApplicantForm> {

	@Autowired
	private LegalEntityFactory legalEntityFactory;

	@Autowired
	private NaturalPersonFactory naturalPersonFactory;

	@Autowired
	private UniversityFactory universityFactory;

	@Autowired
	private NaturalPersonSpecialFactory naturalPersonSpecialFactory;

	@Override
	public Applicant convertTo(ApplicantForm form) {
		if(form == null)
        {
            return null;
        }
		
		if(form.getType() != null){
			switch (form.getType()) {
				case LEGAL_ENTITY:
					if (form instanceof LegalEntityForm) {
						return legalEntityFactory.convertTo((LegalEntityForm) form);
					}					
				case NATURAL_PERSON:
					if (form instanceof NaturalPersonForm) {
						return naturalPersonFactory.convertTo((NaturalPersonForm) form);
					}
				case UNIVERSITY:
					if (form instanceof UniversityForm) {						 
						return universityFactory.convertTo((UniversityForm) form);
					}
				case NATURAL_PERSON_SPECIAL:
					if (form instanceof NaturalPersonSpecialForm) {
						return naturalPersonSpecialFactory.convertTo((NaturalPersonSpecialForm) form);
					}
				default:
					Applicant applicant = naturalPersonFactory.internalUiApplicantToCoreApplicant(form);
					if(applicant == null){
						applicant = new Applicant();
					}
					PersonName name = new PersonName();
					if(form.getType() != null && form.getType().equals(ApplicantKindForm.NATURAL_PERSON)) {
						name.setFirstName(form.getName());
					} else {
					    name.setOrganizationName(form.getName());
					    if(form.getType() == null) {
					    	applicant.setKind(PersonKind.LEGAL_ENTITY);
					    }
					}
					applicant.setName(name);					
					return applicant;
			}
		}
		return null;
	}

	@Override
	public ApplicantForm convertFrom(Applicant applicant) {
		if (applicant == null) {
			return null;
		} else if (StringUtils.isNotBlank(applicant.getPersonNumber()) 
				&& applicant.getKind() == null) {
			ApplicantForm applicantForm = new ApplicantForm();
			applicantForm.setId(applicant.getPersonNumber());
			return applicantForm;
		} else if (applicant.getKind()==null) {
			return null;
		}
		switch (applicant.getKind()) {
			case LEGAL_ENTITY:
				return legalEntityFactory.convertFrom(applicant);
			case NATURAL_PERSON:
				return naturalPersonFactory.convertFrom(applicant);
			case NATURAL_PERSON_SPECIAL:
				return naturalPersonSpecialFactory.convertFrom(applicant);
			case OTHER:
				if(applicant.getName() != null && applicant.getName().getOrganizationName() != null){
					applicant.setKind(PersonKind.LEGAL_ENTITY);
					return legalEntityFactory.convertFrom(applicant);
				} else {
					applicant.setKind(PersonKind.NATURAL_PERSON);
					return naturalPersonFactory.convertFrom(applicant);
				}
			default:
				return null;
		}
	}

	public ApplicantForm convertFromHolderForm(HolderForm holder) {
		if (holder == null) {
			return null;
		}
		ApplicantForm applicant = null;
		if (holder instanceof HolderNaturalPersonForm) {
			applicant = new NaturalPersonForm();
			HolderNaturalPersonForm nHolder = (HolderNaturalPersonForm) holder;
			((NaturalPersonForm)applicant).setFirstName(nHolder.getFirstName());
			((NaturalPersonForm)applicant).setMiddleName(nHolder.getMiddleName());
			((NaturalPersonForm)applicant).setSurname(nHolder.getSurname());
			((NaturalPersonForm)applicant).setNationality(nHolder.getNationality());
		} else if(holder instanceof HolderLegalEntityForm) {
			applicant = new LegalEntityForm();
		}
		if(applicant != null) {
			applicant.setAddress(holder.getAddress());
			applicant.setName(holder.getName());
			applicant.setEmail(holder.getEmail());
			applicant.setFax(holder.getFax());
			applicant.setPhone(holder.getPhone());
			applicant.setWebsite(holder.getWebsite());
			applicant.setImported(holder.getImported());
			applicant.setId(holder.getId());
		}
		return applicant;
	}

	public ApplicantForm convertFromAssigneeForm(AssigneeForm assignee) {
		if (assignee == null) {
			return null;
		}
		ApplicantForm applicant = null;
		if (assignee instanceof AssigneeNaturalPersonForm) {
			applicant = new NaturalPersonForm();
			AssigneeNaturalPersonForm nAssignee = (AssigneeNaturalPersonForm) assignee;
			((NaturalPersonForm)applicant).setFirstName(nAssignee.getFirstName());
			((NaturalPersonForm)applicant).setMiddleName(nAssignee.getMiddleName());
			((NaturalPersonForm)applicant).setSurname(nAssignee.getSurname());
			((NaturalPersonForm)applicant).setNationality(nAssignee.getNationality());
		} else if(assignee instanceof AssigneeLegalEntityForm) {
			applicant = new LegalEntityForm();
		}
		if(applicant != null) {
			applicant.setAddress(assignee.getAddress());
			applicant.setName(assignee.getName());
			applicant.setEmail(assignee.getEmail());
			applicant.setFax(assignee.getFax());
			applicant.setPhone(assignee.getPhone());
			applicant.setWebsite(assignee.getWebsite());
			applicant.setImported(assignee.getImported());
			applicant.setId(assignee.getId());
		}
		return applicant;
	}

}
