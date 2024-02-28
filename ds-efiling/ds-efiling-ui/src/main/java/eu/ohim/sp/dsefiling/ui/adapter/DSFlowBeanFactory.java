package eu.ohim.sp.dsefiling.ui.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.core.domain.payment.MatchedFee;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import eu.ohim.sp.common.service.Component;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.AttachmentsFactory;
import eu.ohim.sp.common.ui.adapter.EntitlementFactory;
import eu.ohim.sp.common.ui.adapter.FeesFactory;
import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.PaymentFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.adapter.SignatoryFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.design.DSDivisionalApplicationFactory;
import eu.ohim.sp.common.ui.adapter.design.DSExhPriorityFactory;
import eu.ohim.sp.common.ui.adapter.design.DSPriorityFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignerFactory;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.design.ContainsDesignsLinkForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.domain.DSMainForm;

@Component
public class DSFlowBeanFactory implements UIFactory<DesignApplication, DSFlowBean> {

	@Autowired
	private FeesFactory feesFactory;

	@Autowired
	private DesignFactory designFactory;

	@Autowired
	private DSDivisionalApplicationFactory divisionalApplicationFactory;

	@Autowired
	private DSPriorityFactory priorityFactory;

	@Autowired
	private DSExhPriorityFactory exhibitionPriorityFactory;

	@Autowired
	private ApplicantFactory applicantFactory;

	@Autowired
	private RepresentativeFactory representativeFactory;

	@Autowired
	private DesignerFactory designerFactory;

	@Autowired
	private SignatoryFactory signatoryFactory;

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;

	@Autowired
	private PaymentFactory paymentFactory;

	@Autowired
	private EntitlementFactory entitlementFactory;

	@Autowired
	private ContactDetailsFactory contactDetailsFactory;

	@Autowired
	private AttachmentsFactory attachmentsFactory;
	
	@Value("${sp.office}")
	private String office;

	/**
	 * Receives as a parameter a UI FlowBean object and converts it to a core
	 * DesignApplication object.
	 *
	 * @param form
	 *            the UI FlowBean to convert
	 * @return the core DesignApplication object
	 */
	@Override
	public DesignApplication convertTo(DSFlowBean form) {

		DesignApplication core = new DesignApplication();
		List<AttachedDocument> documents = new ArrayList<AttachedDocument>();
		core.setDocuments(documents);
		core.setFastTrack(form.getFastTrack());
		core.setWillPayOnline(form.getWillPayOnline());

        if (form != null) {
			core.setComment(form.getComment());
			core.setUser(form.getCurrentUserName());
			core.setUserEmail(form.getCurrentUserEmail());

			core.setApplicationLanguage(form.getFirstLang());
			core.setSecondLanguage(form.getSecLang());
			core.setReceivingOffice(office);
			core.setCorrespondenceLanguage(form.getMainForm().getCorrespondenceLanguageCheckBox() ? form.getSecLang() : form.getFirstLang());
			core.setApplicationFilingNumber(form.getIdreserventity());

			// Main form
			DSMainForm mainForm = form.getMainForm();
			mainFormToCore(mainForm, core);

			// Designs
			designFormsToCore(form, core);
			
			// Supplementary information
			supplementaryInformationFormToCore(form.getOtherAttachments(), core);

			// Fees
			feesFormToCore(form.getFeesForm(), core);

			// Divisional application
			divisionalApplicationFormToCore(form.getDivisionalApplication(), core);

			// Priorities
			priorityFormsToCore(form.getPriorities(), core);

			// Exhibitions
			exhibitionFormsToCore(form.getExhpriorities(), core);

			// Applicants
			applicantFormsToCore(form.getApplicants(), core);

			// Representatives
			representativeFormsToCore(form.getRepresentatives(), core);

			// Designers
			designerFormsToCore(form.getDesigners(), core);

			// Payment
			paymentFormToCore(form.getPaymentForm(), core);
			
			// Attachments
			attachmentsToCore(form, core);

			// Signatures
			core.setSignatures(new ArrayList<Signatory>());
			if (!form.getSignatures().isEmpty()){
				for(SignatureForm item : form.getSignatures()){
					core.getSignatures().add(signatoryFactory.convertTo(item));
				}
			}
			if (form.getMainForm().getSignatoryForm() != null && !(form.getMainForm().getSignatoryForm().isEmpty())){
				core.getSignatures().add(signatoryFactory.convertTo(form.getMainForm().getSignatoryForm()));
			}
			if(core.getSignatures().isEmpty()){
				core.setSignatures(null);
			}
        }

		return core;
	}

	/**
	 *
	 * @param paymentForm
	 * @param core
	 */
	private void paymentFormToCore(PaymentForm paymentForm, DesignApplication core) {
		if (paymentForm != null) {
			List<PaymentFee> paymentFeesAlreadyStored = new ArrayList();
			if(core.getPayments() != null && core.getPayments().size()>0){
				paymentFeesAlreadyStored = core.getPayments();
			}
			List<PaymentFee> payments = new ArrayList<PaymentFee>();
			core.setPayments(payments);
			PaymentFee paymentFee = paymentFactory.convertTo(paymentForm);
			for(PaymentFee p:paymentFeesAlreadyStored){
				List<MatchedFee> feesAlreadyStored = p.getFees();
				if(paymentFee.getFees() != null){
					paymentFee.getFees().addAll(feesAlreadyStored);
				} else {
					paymentFee.setFees(feesAlreadyStored);
				}
			}
			payments.add(paymentFee);
		}
	}

	/**
	 *
	 * @param payments
	 * @param form
	 */
	private void coreToPaymentForm(List<PaymentFee> payments, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(payments)) {
			for (PaymentFee payment : payments) {
				form.setPaymentForm(paymentFactory.convertFrom(payment));
				break;
            }
        }
	}

	/**
	 *
	 * @param designerForms
	 * @param core
	 */
	private void designerFormsToCore(List<DesignerForm> designerForms, DesignApplication core) {
		List<Designer> designers = new ArrayList<Designer>();
		if (CollectionUtils.isNotEmpty(designerForms)) {
            int i = 0;
			for (DesignerForm designerForm : designerForms) {
				if (!designerForm.isWaiver()) {
					Designer designer = designerFactory.convertTo(designerForm);
					designer.setSequenceNumber(i++);
					designers.add(designer);
				}
			}
		}
		core.setDesigners(designers);
	}

	/**
	 *
	 * @param designers
	 * @param form
	 */
	private void coreToDesignerForms(List<Designer> designers, List<Design> designs, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(designers)) {
			DesignerForm designerForm;
			for (Designer designer : designers) {
				designerForm = designerFactory.convertFrom(designer);
				if (designerForm != null) {
					form.addObject(designerForm);
				}
            }
        }
		
		// We need to check if there are any design whose designer waives
		// Only can be one designer who waives so in the first occurrence we can break the loop.
		if (CollectionUtils.isNotEmpty(designs)) {
			for (Design design : designs) {
				if (design.isDesignerWaiverIndicator()) {
					Designer designer = new Designer();
					designer.setWaiver(true);
					form.addObject(designerFactory.convertFrom(designer));
					break;
				}
			}
		}
	}

	/**
	 *
	 * @param representativeForms
	 * @param core
	 */
	private void representativeFormsToCore(List<RepresentativeForm> representativeForms, DesignApplication core) {
		List<Representative> representatives = new ArrayList<Representative>();
		if (CollectionUtils.isNotEmpty(representativeForms)) {
			for (RepresentativeForm representativeForm : representativeForms) {
				representatives.add(representativeFactory.convertTo(representativeForm));
			}
		}
		core.setRepresentatives(representatives);
	}

	/**
	 *
	 * @param representatives
	 * @param form
	 */
	private void coreToRepresentativeForms(List<Representative> representatives, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(representatives)) {
			RepresentativeForm representativeForm;
			for (Representative representative : representatives) {
				representativeForm = representativeFactory.convertFrom(representative);
				if (representativeForm != null) {
					form.addObject(representativeForm);
				}
			}
        }
	}

	/**
	 *
	 * @param applicantForms
	 * @param core
	 */
	private void applicantFormsToCore(List<ApplicantForm> applicantForms, DesignApplication core) {
		List<Applicant> applicants = new ArrayList<Applicant>();
		if (CollectionUtils.isNotEmpty(applicantForms)) {
			for (ApplicantForm applicantForm : applicantForms) {
				applicants.add(applicantFactory.convertTo(applicantForm));
			}
		}
		core.setApplicants(applicants);
	}

	/**
	 *
	 * @param applicants
	 * @param form
	 */
	private void coreToAplicantForms(List<Applicant> applicants, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(applicants)) {
			ApplicantForm applicantForm;
			for (Applicant applicant : applicants) {
				applicantForm = applicantFactory.convertFrom(applicant);
				if (applicantForm != null) {
					form.addObject(applicantForm);
				}
			}
		}
	}

	/**
	 *
	 * @param exhibitionForms
	 * @param core
	 */
	private void exhibitionFormsToCore(List<DSExhPriorityForm> exhibitionForms, DesignApplication core) {
		List<ExhibitionPriority> exhibitions = new ArrayList<ExhibitionPriority>();
		if (BooleanUtils.isFalse(core.getExhibitionPriorityClaimLaterIndicator()) && CollectionUtils.isNotEmpty(exhibitionForms)) {
            int i = 0;
			for (DSExhPriorityForm exhibitionForm : exhibitionForms) {
                ExhibitionPriority exhibitionPriority = exhibitionPriorityFactory.convertTo(exhibitionForm);
                exhibitionPriority.setSequenceNumber(i++);
				exhibitions.add(exhibitionPriority);
			}
		}
		core.setExhibitionPriorities(exhibitions);
	}

	/**
	 *
	 * @param exhibitionPriorities
	 * @param form
	 */
	private void coreToExhibitionForms(List<ExhibitionPriority> exhibitionPriorities, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(exhibitionPriorities)) {
			ExhPriorityForm exhPriorityForm;
            for (ExhibitionPriority exhibition : exhibitionPriorities) {
            	exhPriorityForm = exhibitionPriorityFactory.convertFrom(exhibition);
            	if (exhPriorityForm != null) {
            		form.addObject(exhPriorityForm);
            	}
            }
        }
	}

	/**
	 *
	 * @param priorityForms
	 * @param core
	 */
	private void priorityFormsToCore(List<DSPriorityForm> priorityForms, DesignApplication core) {
		List<Priority> priorities = new ArrayList<Priority>();
		if (BooleanUtils.isFalse(core.getPriorityClaimLaterIndicator()) && CollectionUtils.isNotEmpty(priorityForms)) {
            int i = 0;
			for (DSPriorityForm priorityForm : priorityForms) {
                Priority priority = priorityFactory.convertTo(priorityForm);
                priority.setSequenceNumber(i++);
				priorities.add(priority);
			}
		}
		core.setPriorities(priorities);
	}

    public List<PriorityForm> coreToPriorityForms(List<Priority> priorities) {
        List<PriorityForm> result = new ArrayList<PriorityForm>();
        if (CollectionUtils.isNotEmpty(priorities)) {
            PriorityForm priorityForm;
            for(Priority priority : priorities) {
                priorityForm = priorityFactory.convertFrom(priority);
                if (priorityForm != null) {
                    result.add(priorityForm);
                }
            }
        }
        return result;
    }

	/**
	 *
	 * @param priorities
	 * @param form
	 */
    public List<PriorityForm> coreToPriorityForms(List<Priority> priorities, DSFlowBean form) {
        List<PriorityForm> result = new ArrayList<PriorityForm>();
        if (priorities != null && CollectionUtils.isNotEmpty(priorities)) {
            PriorityForm priorityForm;
            for(Priority priority : priorities) {
                priorityForm = priorityFactory.convertFrom(priority);
                if (priorityForm != null) {
                    result.add(priorityForm);
                    form.addObject(priorityForm);
                }
            }
        }
        return result;
    }

	/**
	 *
	 * @param divisionalApplicationForm
	 * @param core
	 */
	private void divisionalApplicationFormToCore(DSDivisionalApplicationForm divisionalApplicationForm, DesignApplication core) {
		if (divisionalApplicationForm != null) {
			core.setDivisionalApplicationDetails(divisionalApplicationFactory.convertTo(divisionalApplicationForm));
		}
	}

	/**
	 *
	 * @param divisionalApplicationDetails
	 * @param form
	 */
	private void coreToDivisionalApplicationForm(DivisionalApplicationDetails divisionalApplicationDetails, DSFlowBean form) {
     	if (divisionalApplicationDetails != null) {
     		DSDivisionalApplicationForm dsDivisionalApplicationForm = divisionalApplicationFactory.convertFrom((DesignDivisionalApplicationDetails) divisionalApplicationDetails);
     		if (dsDivisionalApplicationForm != null) {
     			form.addObject(dsDivisionalApplicationForm);
     		}
     	}
	}

	/**
	 *
	 * @param feesForm
	 * @param core
	 */
	private void feesFormToCore(FeesForm feesForm, DesignApplication core) {
		if (feesForm != null) {
			List<Fee> fees = feesFactory.convertFrom(feesForm);
			core.setFees(fees);
			List<MatchedFee> matchedFees = new ArrayList();
			for(Fee f:fees){
				MatchedFee matchedFee = new MatchedFee();
				matchedFee.setFee(f);
				matchedFees.add(matchedFee);
			}
			if(core.getPayments() != null && core.getPayments().size()>0 ){
				core.getPayments().get(0).setFees(matchedFees);
			} else {
				PaymentFee paymentFee = new PaymentFee();
				paymentFee.setFees(matchedFees);
				List<PaymentFee> paymentFees = new ArrayList();
				paymentFees.add(paymentFee);
				core.setPayments(paymentFees);
			}
		}
	}

	/**
	 *
	 * @param fees
	 * @param form
	 */
	private void coreToFeesForm(List<Fee> fees, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(fees)) {
			form.setFeesForm(feesFactory.convertTo(fees));
		}
	}

	/**
	 *
	 * @param otherAttachments
	 * @param core
	 */
	private void supplementaryInformationFormToCore(FileWrapper otherAttachments, DesignApplication core) {
		if (CollectionUtils.isNotEmpty(otherAttachments.getStoredFiles())) {
			core.getDocuments().addAll(listAttachedDocumentFactory.convertTo(otherAttachments));
		}
	}

	/**
	 *
	 * @param documents
	 * @param form
	 */
	private void coreToSupplementaryInformation(List<AttachedDocument> documents, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(documents)) {
     		form.setOtherAttachments(listAttachedDocumentFactory.convertFrom(documents));
     	}
	}

	/**
	 * * Design forms to core design objects.
	 * @param form Domain object.
	 * @param core Design application.
	 */
	private void designFormsToCore(DSFlowBean form, DesignApplication core) {
		List<DesignForm> designForms = form.getDesigns();
		DSMainForm mainForm = form.getMainForm();
		
		List<Design> designs = new ArrayList<Design>();
		if (CollectionUtils.isNotEmpty(designForms)) {
			Design design;
			for (DesignForm designForm : designForms) {
	
				designForm.setDesignerWaiverIndicator(false);
				for (DesignerForm designerForm : form.getDesigners()) {
					if (designerForm.isWaiver() && designerForm.getDesignsLinked().contains(designForm)) {
						designForm.setDesignerWaiverIndicator(true);
					}
				}
				
				design = designFactory.convertTo(designForm);
				if (core.isRequestDeferredPublication()) {
					design.setPublicationDefermentIndicator(true);
					design.setPublicationDefermentTillDate(mainForm.getDefermentTillDate());
				}
				
				design.setPriorities(getCorePrioritiesFromDesignForm(designForm, form.getPriorities()));
				design.setExhibitionPriorities(getCoreExhibitionsFromDesignForm(designForm, form.getExhpriorities()));
				design.setDesigners(getCoreDesignersFromDesignForm(designForm, form.getDesigners()));
				designs.add(design);
			}
		}
		core.setDesignDetails(designs);
	}

	/**
	 * 
	 * @param designForm
	 * @param priorityForms
	 * @return
	 */
	private List<Priority> getCorePrioritiesFromDesignForm(DesignForm designForm, List<DSPriorityForm> priorityForms) {
		List<Priority> priorities = new ArrayList<Priority>();
		Priority priority;
		int i = 0;
		for (DSPriorityForm priorityForm : priorityForms) {
			if (priorityForm.getDesignsLinked().contains(designForm)) {
				priority = priorityFactory.convertTo(priorityForm);
				priority.setSequenceNumber(i);
				priorities.add(priority);
			}
			i++;
		}
		return priorities;
	}
	
	/**
	 * 
	 * @param designForm
	 * @param priorityForms
	 * @return
	 */
	private List<ExhibitionPriority> getCoreExhibitionsFromDesignForm(DesignForm designForm, List<DSExhPriorityForm> exhibitionsForms) {
		List<ExhibitionPriority> exhibitions = new ArrayList<ExhibitionPriority>();
		ExhibitionPriority exhPriority;
		int i = 0;
		for (DSExhPriorityForm exhPriorityForm : exhibitionsForms) {
			if (exhPriorityForm.getDesignsLinked().contains(designForm)) {
				exhPriority = exhibitionPriorityFactory.convertTo(exhPriorityForm);
				exhPriority.setSequenceNumber(i);
				exhibitions.add(exhPriority);
			}
			i++;
		}
		return exhibitions;
	}
	
	/**
	 * 
	 * @param designForm
	 * @param priorityForms
	 * @return
	 */
	private List<Designer> getCoreDesignersFromDesignForm(DesignForm designForm, List<DesignerForm> designerForms) {
		List<Designer> designers = new ArrayList<Designer>();
		Designer designer;
		int i = 0;
		for (DesignerForm designerForm : designerForms) {
			
			// We have to discard designer who waives.
			if (designerForm.isWaiver()) {
				continue;
			}
			
			if (designerForm.getDesignsLinked().contains(designForm)) {
				designer = designerFactory.convertTo(designerForm);
				designer.setSequenceNumber(i);
				designers.add(designer);
			}
			i++;
		}
		return designers;
	}
	
	/**
	 *
	 * @param corressAddress
	 * @param core
	 */
	private void correspondanceAddressFormToCore(List<ApplicationCAForm> corressAddress, DesignApplication core) {
		List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
		if (CollectionUtils.isNotEmpty(corressAddress)) {
			for (ApplicationCAForm appCAForm : corressAddress) {
				contactDetailsList.add(contactDetailsFactory.convertTo(appCAForm.getCorrespondenceAddressForm()));
			}
		}
		core.setContactDetails(contactDetailsList);
	}

	/**
	 *
	 * @param contactsDetails
	 * @param mainForm
	 */
	private void coreToCorrespondanceAddressForm(List<ContactDetails> contactsDetails, DSFlowBean form) {
		if (CollectionUtils.isNotEmpty(contactsDetails)) {
			ApplicationCAForm appCAForm;
			for (ContactDetails contactDetails : contactsDetails) {
				appCAForm = new ApplicationCAForm();
				appCAForm.setCorrespondenceAddressForm(contactDetailsFactory.convertFrom(contactDetails));
				form.addObject(appCAForm);
			}
		}
	}

	/**
	 * Convert the main form to the core object.
	 * @param mainForm Main form.
	 * @param core Design application.
	 */
	private void mainFormToCore(DSMainForm mainForm, DesignApplication core) {
		if (mainForm != null) {
			// Reference
			core.setReference(mainForm.getReference());

			// Designs - Product description
			core.setProductDescription(mainForm.getProductDescription());
			core.setApplicationVerbalElementsEn(mainForm.getApplicationVerbalElementsEn());

			// Deferment of publication
			core.setRequestDeferredPublication(mainForm.getRequestDeferredPublication());

			// Correspondence addresses
			correspondanceAddressFormToCore(mainForm.getCorrespondanceAddresses(), core);

			// Entitlement to apply
			if (mainForm.getEntitlement() != null) {
				core.setEntitlement(entitlementFactory.convertTo(mainForm.getEntitlement()));
			}

			// Claim indicators
			core.setPriorityClaimLaterIndicator(mainForm.isPriorityClaimLater());
			core.setExhibitionPriorityClaimLaterIndicator(mainForm.isExhPriorityClaimLater());

			// Terms and conditions.
			core.setTermsAndConditionsAcceptance(BooleanUtils.toBoolean(mainForm.getTermsAndConditions()));

			core.setReceivingOfficeDate(mainForm.getDateOfSigning());
		}
	}

	/**
	 * Convert the core object to the main form.
	 * @param core Design application.
	 * @param mainForm Main form.
	 */
	private void coreToMainForm(DesignApplication core, DSFlowBean form) {
		DSMainForm mainForm = form.getMainForm();
		
		// Reference
		mainForm.setReference(core.getReference());

		// Designs - Product description
		mainForm.setProductDescription(core.getProductDescription());
		mainForm.setApplicationVerbalElementsEn(core.getApplicationVerbalElementsEn());

		// Deferment of publication
		mainForm.setRequestDeferredPublication(core.isRequestDeferredPublication());
		if (core.isRequestDeferredPublication() && CollectionUtils.isNotEmpty(core.getDesignDetails())) {
			for (Design design : core.getDesignDetails()) {
				mainForm.setDefermentTillDate(design.getPublicationDefermentTillDate());
				break;
			}
		}

		// Correspondence addresses
		coreToCorrespondanceAddressForm(core.getContactDetails(), form);

		// Entitlement to apply
		if (core.getEntitlement() != null) {
			mainForm.setEntitlement(entitlementFactory.convertFrom(core.getEntitlement()));
		}

		// Claim indicators
		mainForm.setPriorityClaimLater(BooleanUtils.toBoolean(core.getPriorityClaimLaterIndicator()));
		mainForm.setExhPriorityClaimLater(BooleanUtils.toBoolean(core.getExhibitionPriorityClaimLaterIndicator()));

		// Terms and conditions.
		mainForm.setTermsAndConditions(BooleanUtils.toBoolean(core.isTermsAndConditionsAcceptance()));
	}

	/**
	 * Receives as a parameter a UI FlowBean object and converts it to a core
	 * TradeMark object.
	 *
	 * @param core
	 *            the UI FlowBean to convert
	 * @return the core TradeMark object
	 */
	@Override
	public DSFlowBean convertFrom(DesignApplication core) {
        DSFlowBean form = new DSFlowBeanImpl();

		if (core != null) {
			form.setComment(core.getComment());
            form.setFirstLang(core.getApplicationLanguage());
            form.setSecLang(core.getSecondLanguage());
            form.getMainForm().setCorrespondenceLanguageCheckBox(StringUtils.equals(core.getCorrespondenceLanguage(), core.getSecondLanguage()));
            form.setIdreserventity(core.getApplicationFilingNumber());
            form.setFastTrack(core.getFastTrack());
			form.setWillPayOnline(core.getWillPayOnline());

            // Main form
         	coreToMainForm(core, form);

         	// Supplementary information
         	coreToSupplementaryInformation(core.getDocuments(), form);

         	// Fees
         	coreToFeesForm(core.getFees(), form);

         	// Divisional application
         	coreToDivisionalApplicationForm(core.getDivisionalApplicationDetails(), form);

			// Priorities
			coreToPriorityForms(core.getPriorities(), form);

			// Exhibitions
			coreToExhibitionForms(core.getExhibitionPriorities(), form);

			// Applicants
			coreToAplicantForms(core.getApplicants(), form);

			// Representatives
			coreToRepresentativeForms(core.getRepresentatives(), form);

			// Designers
			coreToDesignerForms(core.getDesigners(), core.getDesignDetails(), form);

			// Payment
			coreToPaymentForm(core.getPayments(), form);

         	// Designs and their references to divisional application, priorities, exhibitions and designers.
         	coreToDesignForms(core, form);

			if(!org.springframework.util.CollectionUtils.isEmpty(core.getSignatures())){
				for (Signatory signatory : core.getSignatures()) {
					SignatureForm signatoryForm = signatoryFactory.convertFrom(signatory);
					if (signatoryForm != null) {
						if (form.getSignatures() == null) {
							form.setSignatures(new ArrayList<>());
						}
						form.addObject(signatoryForm);
					}
				}
			}
		}

		return form;
	}

	/**
	 * Core designs to form.
	 * @param core Core designs.
	 * @param form Domain object.
	 */
	private void coreToDesignForms(DesignApplication core, DSFlowBean form) {
		int designNumber = 0;
		List<Design> designs = core.getDesignDetails();
        if (CollectionUtils.isNotEmpty(designs)) {
            for(Design design : designs) {
                DesignForm designForm = designFactory.convertFrom(design);
                if (designForm != null) {
                	designForm.setNumber(++designNumber);
                	
	                form.addObject(designForm);
	
	                // Add design to divisional application
	                addDesignFormToDivisionalApplicationForm(designForm, form.getDivisionalApplications());
	
	                // Add design to each priority
	                addDesignFormToPriorityForms(designForm, form.getPriorities(), design);
	
	                // Add design to each exhibition
	                addDesignFormToExhibitionForms(designForm, form.getExhpriorities(), design);
	
	                // Add design to each designer
	                addDesignFormToDesignerForms(designForm, form.getDesigners(), design);
                }
            }
            
            adjustDesignersDesignsLists(form);
        }
	}

	/**
	 *
	 * @param designForm
	 * @param forms
	 * @param design
	 */
	private void addDesignFormToDivisionalApplicationForm(DesignForm designForm, List<DSDivisionalApplicationForm> forms) {
		for (DSDivisionalApplicationForm form : forms) {
			addDesignFormToContainsDesignLinkForm(designForm, form, designForm.isDivisonalApplicationIndicator());
		}
	}

	/**
	 *
	 * @param designForm
	 * @param forms
	 * @param design
	 */
	private void addDesignFormToPriorityForms(DesignForm designForm, List<DSPriorityForm> forms, Design design) {
        for (DSPriorityForm form : forms) {
        	addDesignFormToContainsDesignLinkForm(designForm, form, isDesignAssociatedToPriority(design, form));
        }
	}

	/**
	 *
	 * @param design
	 * @param form
	 * @return
	 */
	private boolean isDesignAssociatedToPriority(Design design, DSPriorityForm form) {
		if (CollectionUtils.isNotEmpty(design.getPriorities())) {
			for (Priority priority : design.getPriorities()) {
				if (ObjectUtils.equals(priority.getSequenceNumber(), form.getDesignSequenceNumber())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @param designForm
	 * @param forms
	 * @param design
	 */
	private void addDesignFormToExhibitionForms(DesignForm designForm, List<DSExhPriorityForm> forms, Design design) {
        for (DSExhPriorityForm form : forms) {
        	addDesignFormToContainsDesignLinkForm(designForm, form, isDesignAssociatedToExhibition(design, form));
        }
	}

	/**
	 *
	 * @param design
	 * @param form
	 * @return
	 */
	private boolean isDesignAssociatedToExhibition(Design design, DSExhPriorityForm form) {
		if (CollectionUtils.isNotEmpty(design.getExhibitionPriorities())) {
			for (ExhibitionPriority exhibition : design.getExhibitionPriorities()) {
				if (ObjectUtils.equals(exhibition.getSequenceNumber(), form.getDesignSequenceNumber())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @param designForm
	 * @param forms
	 * @param design
	 */
	private void addDesignFormToDesignerForms(DesignForm designForm, List<DesignerForm> forms, Design design) {
        for (DesignerForm form : forms) {
        	addDesignFormToContainsDesignLinkForm(designForm, form, isDesignAssociatedToDesigner(design, form));
        }
	}

	/**
	 *
	 * @param design
	 * @param form
	 * @return
	 */
	private boolean isDesignAssociatedToDesigner(Design design, DesignerForm designerForm) {
		if (designerForm.isWaiver()) {
			if (design.isDesignerWaiverIndicator()) {
				return true;
			}
		} else {
			if (CollectionUtils.isNotEmpty(design.getDesigners())) {
				for (Designer designer : design.getDesigners()) {
					if (ObjectUtils.equals(designer.getSequenceNumber(), designerForm.getDesignSequenceNumber())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @param designForm
	 * @param form
	 * @param toLinkedDesigns
	 */
	private void addDesignFormToContainsDesignLinkForm(DesignForm designForm, ContainsDesignsLinkForm form, boolean toLinkedDesigns) {
		if (toLinkedDesigns) {
    		form.getDesignsLinked().add(designForm);
    	} else {
    		form.getDesignsNotLinked().add(designForm);
    	}
	}
	
	/**
	 * 
	 * @param flowBean
	 */
	private void adjustDesignersDesignsLists(DSFlowBean flowBean) {
		List<DesignerForm> designers = flowBean.getDesigners();
		DesignerForm designerWhoWaives = null;
		for (DesignerForm designerForm : designers) {
			if (designerForm.isWaiver()) {
				designerWhoWaives = designerForm;
				break;
			}
		}
		
		// If there is a designer who waives it changes everything...
		if (designerWhoWaives != null) {
			for (Iterator<DesignForm> it = designerWhoWaives.getDesignsNotLinked().iterator(); it.hasNext(); ) {
				if (isDesignLinkedInOtherDesigner(it.next(), designers)) {
					it.remove();
				}
			}
			
			for (Iterator<DesignForm> it = designerWhoWaives.getDesignsLinked().iterator(); it.hasNext(); ) {
				removeDesignInOtherDesigners(it.next(), designers);
			}
		}
		
	}
	
	/**
	 * 
	 * @param designForm
	 * @param designerForms
	 * @return
	 */
	private boolean isDesignLinkedInOtherDesigner(DesignForm designForm, List<DesignerForm> designerForms) {
		boolean linked = false;
		for (DesignerForm designerForm : designerForms) {
			if (!designerForm.isWaiver() && designerForm.getDesignsLinked().contains(designForm)) {
				linked = true;
				break;
			}
		}
		return linked;
	}
	
	/**
	 * 
	 * @param designForm
	 * @param designerForms
	 */
	private void removeDesignInOtherDesigners(DesignForm designForm, List<DesignerForm> designerForms) {
		for (DesignerForm designerForm : designerForms) {
			if (!designerForm.isWaiver()) {
				designerForm.getDesignsNotLinked().remove(designForm);
			}
		}
	}
    /**
     * 
     * @return
     */
    private void attachmentsToCore(DSFlowBean form, DesignApplication core) {
    	List<String> attachments = new ArrayList<String>();
    	
    	if (form.getPriorities()!=null ) {
    		attachmentsFactory.addPrioritiesAttachments(attachments, form.getPriorities());
    	}
    	
    	if (form.getExhpriorities()!=null ) {
    		attachmentsFactory.addExhibitionsAttachments(attachments, form.getExhpriorities());
    	}
    	
    	if (form.getRepresentatives()!=null ) {
    		attachmentsFactory.addRepresentativesAttachaments(attachments, form.getRepresentatives());
    	}
    	
    	if (form.getOtherAttachments()!=null ) {
    		attachmentsFactory.addOtherAttachments(attachments, form.getOtherAttachments());
    	}
    	
    	if (form.getMainForm()!=null && form.getMainForm().getEntitlement()!=null) {
    		attachmentsFactory.addEntitlementAttachments(attachments, form.getMainForm().getEntitlement());
    	}
    	
    	if (form.getPaymentForm()!=null ) {
    		attachmentsFactory.addPaymentAttachments(attachments, form.getPaymentForm());
    	}
    	
    	core.setAttachments(attachments);
    	
    }
  

}