/*******************************************************************************
 * * $Id:: RepresentativeFactory.java 113496 2013-04-22 15:03:04Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.core.domain.person.Representative;

@Component
public class RepresentativeFactory implements UIFactory<Representative, RepresentativeForm> {

	@Autowired
	private ProfessionalPractitionerFactory professionalPractitionerFactorty;

	@Autowired
	private EmployeeRepresentativeFactory employeeRepresentativeFactory;

	@Autowired
	private LegalPractitionerFactory legalPractitionerFactory;

	@Autowired
	private RepresentativeNaturalPersonFactory representativeNaturalPersonFactory;

	@Autowired
	private RepresentativeLegalEntityFactory representativeLegalEntityFactory;

	@Autowired
	private RepresentativeAssociationFactory representativeAssociationFactory;

	@Autowired
	private LawyerCompanyFactory lawyerCompanyFactory;

	@Autowired
	private LawyerAssociationFactory lawyerAssociationFactory;

	@Autowired
	private RepresentativeTemporaryFactory representativeTemporaryFactory;

	@Autowired
	private IntlPRepresentativeFactory intlPRepresentativeFactory;

	@Override
	public Representative convertTo(RepresentativeForm form) {
		if(form == null)
		{
			return null;
		}
		switch (form.getType()) {
			case EMPLOYEE_REPRESENTATIVE:
				if (form instanceof EmployeeRepresentativeForm) {
					return employeeRepresentativeFactory.convertTo((EmployeeRepresentativeForm) form);
				}
				break;
			case LEGAL_ENTITY:
				if (form instanceof RepresentativeLegalEntityForm) {
					return representativeLegalEntityFactory.convertTo((RepresentativeLegalEntityForm) form);
				}
				break;
			case LEGAL_PRACTITIONER:
				if (form instanceof LegalPractitionerForm) {
					return legalPractitionerFactory.convertTo((LegalPractitionerForm) form);
				}
				break;
			case NATURAL_PERSON:
				if (form instanceof RepresentativeNaturalPersonForm) {
					return representativeNaturalPersonFactory.convertTo((RepresentativeNaturalPersonForm) form);
				}
				break;
			case PROFESSIONAL_PRACTITIONER:
				if (form instanceof ProfessionalPractitionerForm) {
					return professionalPractitionerFactorty.convertTo((ProfessionalPractitionerForm) form);
				}
				break;
			case ASSOCIATION:
				if (form instanceof RepresentativeAssociationForm) {
					return representativeAssociationFactory.convertTo((RepresentativeAssociationForm) form);
				}
				break;
			case LAWYER_COMPANY:
				if (form instanceof LawyerCompanyForm) {
					return lawyerCompanyFactory.convertTo((LawyerCompanyForm) form);
				}
				break;
			case LAWYER_ASSOCIATION:
				if (form instanceof LawyerAssociationForm) {
					return lawyerAssociationFactory.convertTo((LawyerAssociationForm) form);
				}
				break;
			case TEMPORARY_REPRESENTATIVE:
				if (form instanceof RepresentativeTemporaryForm) {
					return representativeTemporaryFactory.convertTo((RepresentativeTemporaryForm) form);
				}
				break;
			case INTELLECTUAL_PROPERTY_REPRESENTATIVE:
				if (form instanceof IntlPRepresentativeForm) {
					return intlPRepresentativeFactory.convertTo((IntlPRepresentativeForm) form);
				}
				break;
		}

		return null;
	}

	@Override
	public RepresentativeForm convertFrom(Representative representative) {
		if (representative == null) {
			return null;
		}
		else if (representative.getIdentifiers() != null && !representative.getIdentifiers().isEmpty()
			&& representative.getRepresentativeKind() == null) {
			RepresentativeForm representativeForm = new RepresentativeForm();
			representativeForm.setId(representative.getIdentifiers().get(0).getValue());
		}

		//choose kind
		if (representative.getRepresentativeKind() == null) {
			if(representative.getKind() == null)
			{
				return null;
			}
			else
			{
				switch (representative.getKind()) {
					case NATURAL_PERSON:
						return representativeNaturalPersonFactory.convertFrom(representative);
					case LEGAL_ENTITY:
						return representativeLegalEntityFactory.convertFrom(representative);
				}
			}
		}else{
			switch (representative.getRepresentativeKind()) {
				case ASSOCIATION:
					return representativeAssociationFactory.convertFrom(representative);
				case PROFESSIONAL_REPRESENTATIVE:
					return professionalPractitionerFactorty.convertFrom(representative);
				case EMPLOYEE:
				case EMPLOYEE_WITH_ECONOMIC_CONNECTIONS:
					return employeeRepresentativeFactory.convertFrom(representative);
				case LAWYER:
					return legalPractitionerFactory.convertFrom(representative);
				//TODO to be checked again with the current transformation
				case LAWYER_ASSOCIATION:
					return lawyerAssociationFactory.convertFrom(representative);
				case LAWYER_COMPANY:
					return lawyerCompanyFactory.convertFrom(representative);
				case TEMPORARY_REPRESENTATIVE:
					return representativeTemporaryFactory.convertFrom(representative);
				case INTELLECTUAL_PROPERTY_REPRESENTATIVE:
					return intlPRepresentativeFactory.convertFrom(representative);
				case OTHER:
					switch (representative.getKind()) {
						case NATURAL_PERSON:
							return representativeNaturalPersonFactory.convertFrom(representative);
						case LEGAL_ENTITY:
							return representativeLegalEntityFactory.convertFrom(representative);
					}
			}
		}
		return null;
	}

}
