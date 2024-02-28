package eu.ohim.sp.eservices.ui.adapter;

import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignerFactory;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.adapter.opposition.OppositionGroundFactory;
import eu.ohim.sp.common.ui.adapter.patent.ESPatentFactory;
import eu.ohim.sp.common.ui.adapter.patent.MarketPermissionFactory;
import eu.ohim.sp.common.ui.adapter.userdoc.UserdocFactory;
import eu.ohim.sp.common.ui.flow.AbstractFlowBeanImpl;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.patent.MarketPermissionForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.securitymeasure.SecurityMeasureForm;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.licence.Licence;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.other.OLeServiceApplication;
import eu.ohim.sp.core.domain.patent.PTeServiceApplication;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.MatchedFee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.*;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.GSHelperDetails;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.domain.userdoc.UserdocRelationRestriction;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import eu.ohim.sp.eservices.ui.domain.ESMainForm;
import eu.ohim.sp.eservices.ui.service.FormUtil;
import eu.ohim.sp.eservices.ui.service.FormUtil.FormMainType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sun.security.krb5.internal.crypto.Des;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ESFlowBeanFactory implements
		FlowBeanFactory<EServiceApplication, ESFlowBean> {

	@Autowired
	private SignatoryFactory signatoryFactory;

	@Autowired
	private ApplicantFactory applicantFactory;

	@Autowired
	private RepresentativeFactory representativeFactory;
	
	@Autowired
	private AssigneeFactory assigneeFactory;

	@Autowired
	private DesignerFactory designerFactory;
	
	@Autowired
	private HolderFactory holderFactory;
	
	@Autowired
	private LimitedTrademarkFactory limitedTrademarkFactory;

	@Autowired
	private OppositionGroundFactory oppositionGroundFactory;

	@Autowired
	private ProfessionalPractitionerFactory professionalPractitionerFactory;

	@Autowired
	private EmployeeRepresentativeFactory employeeRepresentativeFactory;

	@Autowired
	private LegalPractitionerFactory legalPractitionerFactory;

	@Autowired
	private RepresentativeNaturalPersonFactory representativeNaturalPersonFactory;

	@Autowired
	private RepresentativeLegalEntityFactory representativeLegalEntityFactory;

	@Autowired
	private LegalEntityFactory legalEntityFactory;

	@Autowired
	private NaturalPersonFactory naturalPersonFactory;

	@Autowired
	private NaturalPersonSpecialFactory naturalPersonSpecialFactory;

	@Autowired
	private ContactDetailsFactory contactDetailsFactory;

	@Autowired
	private LicenceFactory licenceFactory;

	@Autowired
	private SecurityMeasureFactory securityMeasureFactory;

	@Autowired
	private GSHelperFactory gsHelperFactory;

	@Autowired
	private AppealFactory appealFactory;

	@Autowired
	private UserdocFactory userdocFactory;

	@Value("${sp.office}")
	private String office;
	
	@Value("${sp.languageOffice}")
	private String languageOffice;

	@Autowired
	private AttachedDocumentFactory attachedDocumentFactory;

	@Autowired
	private PaymentFactory paymentFactory;

	@Autowired
	private FeesFactory feesFactory;
	
	@Autowired
	private ESDesignFactory designFactory;

	@Autowired
	private PersonChangeFactory personChangeFactory;

	@Autowired
	private ESPatentFactory esPatentFactory;

	private Map<Class<? extends AbstractForm>, UIFactory<?, ?>> map;
	
	@Autowired
	private FormUtil formUtil;

	@Autowired
	private MarketPermissionFactory marketPermissionFactory;

	@PostConstruct
	public void init() {
		map = new HashMap<Class<? extends AbstractForm>, UIFactory<?, ?>>();

		map.put(ProfessionalPractitionerForm.class,
				professionalPractitionerFactory);
		map.put(EmployeeRepresentativeForm.class, employeeRepresentativeFactory);
		map.put(LegalPractitionerForm.class, legalPractitionerFactory);
		map.put(RepresentativeNaturalPersonForm.class,
				representativeNaturalPersonFactory);
		map.put(RepresentativeLegalEntityForm.class,
				representativeLegalEntityFactory);

		map.put(HolderLegalEntityForm.class, holderFactory);
		map.put(HolderNaturalPersonForm.class, holderFactory);
		map.put(AssigneeLegalEntityForm.class, assigneeFactory);
		map.put(AssigneeNaturalPersonForm.class, assigneeFactory);
		map.put(DesignerForm.class, designerFactory);
		
		map.put(LegalEntityForm.class, legalEntityFactory);
		map.put(NaturalPersonForm.class, naturalPersonFactory);
		map.put(NaturalPersonSpecialForm.class, naturalPersonSpecialFactory);
		map.put(AbstractFlowBeanImpl.class, this);

        map.put(CorrespondenceAddressForm.class, contactDetailsFactory);
        map.put(LicenceForm.class, licenceFactory);
        map.put(SecurityMeasureForm.class, securityMeasureFactory);
        map.put(GSHelperForm.class, gsHelperFactory);
        map.put(AppealForm.class, appealFactory);
		map.put(MarketPermissionForm.class, marketPermissionFactory);
	}

	public UIFactory<?, ?> getFactory(Class<?> clazz) {
		return map.get(clazz);
	}

	/**
	 * Receives as a parameter a UI ESFlowBean object and converts it to a core
	 * TMeServiceApplication object.
	 * 
	 * @param form
	 *            the UI FlowBean to convert
	 * @return the core TMeServiceApplication object
	 */
	@Override
	public EServiceApplication convertTo(ESFlowBean form) {
		FormMainType type =  formUtil.getMainType();
		
		EServiceApplication eserviceApplication = null;
		if(type == FormMainType.TM){
			eserviceApplication = new TMeServiceApplication();
		}else if(type == FormMainType.DS){
			eserviceApplication = new DSeServiceApplication();
		} else if(type == FormMainType.PT || type == FormMainType.UM || type == FormMainType.EP || type == FormMainType.SV || type == FormMainType.SPC){
			eserviceApplication = new PTeServiceApplication();
		} else if(type == FormMainType.OL){
			eserviceApplication = new OLeServiceApplication();
		}
		if (form == null){
			return eserviceApplication;
		}

		ESMainForm mainForm = (ESMainForm)form.getMainForm();
        if (mainForm != null && mainForm.getSmallCompanyFiles() != null && !mainForm.getSmallCompanyFiles().getStoredFiles().isEmpty()) {
            eserviceApplication.setSmallCompanyFiles(new ArrayList<AttachedDocument>());
            for (StoredFile item : ((ESMainForm)form.getMainForm()).getSmallCompanyFiles().getStoredFiles()) {
                eserviceApplication.getSmallCompanyFiles().add(attachedDocumentFactory.convertTo(item));
            }
        }
		eserviceApplication.setSmallCompany(mainForm.getSmallCompany());
		eserviceApplication.setLicenceAvailability(mainForm.getLicenceAvailability());

		eserviceApplication.setProcessInitiatedBeforePublication(form.getProcessInitiatedBeforePublication());
		eserviceApplication.setEsignDocDeclaration(form.getEsignDocDeclaration());
		eserviceApplication.setApplicationReference(form.getReference());

		eserviceApplication.setApplicationLanguage(languageOffice);
		
		eserviceApplication.setSecondLanguage(form.getSecLang());

		eserviceApplication.setApplicationFilingNumber(form.getIdreserventity());
		
		eserviceApplication.setApplicationType(formUtil.getType());
		
		eserviceApplication.setRegistrationOffice(office);
		
		eserviceApplication.setReceivingOffice(office);
		
		eserviceApplication.setFees(feesFactory.convertFrom(form.getFeesForm()));
		
		eserviceApplication.setPayments(new ArrayList<PaymentFee>());		
		
		PaymentFee paymentFee = paymentFactory.convertTo(form.getPaymentForm());
		if(eserviceApplication.getFees() != null && !eserviceApplication.getFees().isEmpty()){
			List <MatchedFee> matchedFees = new ArrayList<MatchedFee>();
			for (Fee fee:eserviceApplication.getFees()){
				MatchedFee matchedFee = new MatchedFee();
				matchedFee.setFee(fee);
				matchedFees.add(matchedFee);
			}
			paymentFee.setFees(matchedFees);
		}
		if(paymentFee.getIdentifier() == null) {
			paymentFee.setIdentifier(form.getIdReservePayment());
		}
		eserviceApplication.getPayments().add(paymentFee);
				
		eserviceApplication.setApplicants(new ArrayList<Applicant>());
		for (ApplicantForm item : form.getApplicants()) {
			eserviceApplication.getApplicants().add(applicantFactory.convertTo(item));
		}
		
		eserviceApplication.setRepresentatives(new ArrayList<Representative>());
		for (RepresentativeForm item : form.getRepresentatives()) {
			eserviceApplication.getRepresentatives().add(representativeFactory.convertTo(item));
		}
		
		eserviceApplication.setAssignees(new ArrayList<Assignee>());
		for (AssigneeForm item : form.getAssignees()) {
			eserviceApplication.getAssignees().add(assigneeFactory.convertTo(item));
		}

		eserviceApplication.setDesigners(new ArrayList<Designer>());
		for (DesignerForm item : form.getDesigners()) {
			eserviceApplication.getDesigners().add(designerFactory.convertTo(item));
		}
		
		eserviceApplication.setHolders(new ArrayList<Holder>());
		for (HolderForm item : form.getHolders()) {
			eserviceApplication.getHolders().add(holderFactory.convertTo(item));
		}
		
		eserviceApplication.setSignatures(new ArrayList<Signatory>());
		if (!form.getSignatures().isEmpty()){
			for(SignatureForm item : form.getSignatures()){
				eserviceApplication.getSignatures().add(signatoryFactory.convertTo(item));
			}
		}
		if (form.getMainForm().getSignatoryForm() != null && !(form.getMainForm().getSignatoryForm().isEmpty())){
			eserviceApplication.getSignatures().add(signatoryFactory.convertTo(form.getMainForm().getSignatoryForm()));
		}
		if(eserviceApplication.getSignatures().isEmpty()){
			eserviceApplication.setSignatures(null);
		}
		
		
		if(!form.getOtherAttachments().getStoredFiles().isEmpty()){
			eserviceApplication.setDocuments(new ArrayList<AttachedDocument>());
			for (StoredFile item : form.getOtherAttachments().getStoredFiles()) {
				eserviceApplication.getDocuments().add(attachedDocumentFactory.convertTo(item));
			}
		}
		if(!form.getObsList().isEmpty()){
			eserviceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
			for (OppositionBasisForm item:form.getObsList()){
				eserviceApplication.getOppositionGrounds().add(oppositionGroundFactory.convertTo(item));
			}
		}
		
		eserviceApplication.setComment(form.getComment());
		eserviceApplication.setPagesCountAttachment(form.getPagesCountAttachment());
		eserviceApplication.setHolderIsInventor(form.getHolderIsInventor());
		eserviceApplication.setPartialInvalidity(form.getPartialInvalidity());
		
		//specific mappings
		if(eserviceApplication instanceof TMeServiceApplication && !form.getTmsList().isEmpty()){
			((TMeServiceApplication)eserviceApplication).setTradeMarks(new ArrayList<LimitedTradeMark>());
			for (TMDetailsForm item : form.getTmsList()) {
				((TMeServiceApplication)eserviceApplication).getTradeMarks().add(limitedTrademarkFactory.convertTo(item));
			}
		}else if(eserviceApplication instanceof DSeServiceApplication && !form.getDssList().isEmpty()){
			((DSeServiceApplication)eserviceApplication).setDesignDetails(new ArrayList<DesignApplication>());
			for (ESDesignDetailsForm item : form.getDssList()) {
				((DSeServiceApplication)eserviceApplication).getDesignDetails().add(designFactory.convertTo(item));
			}
		} else if(eserviceApplication instanceof PTeServiceApplication && !form.getPatentsList().isEmpty()){
			((PTeServiceApplication)eserviceApplication).setPatentList(new ArrayList<Patent>());
			for (ESPatentDetailsForm item : form.getPatentsList()) {
				((PTeServiceApplication)eserviceApplication).getPatentList().add(esPatentFactory.convertTo(item));
			}
			((PTeServiceApplication)eserviceApplication).setMarketPermission(marketPermissionFactory.convertTo((form.getMarketPermission())));
		}

		eserviceApplication.setContactDetails(new ArrayList<ContactDetails>());
		if(!form.getCorrespondanceAddresses().isEmpty()){
			for(ApplicationCAForm appCAForm: form.getCorrespondanceAddresses()) {
				if(appCAForm.getCorrespondenceAddressForm() != null) {
					ContactDetails contactDetails = contactDetailsFactory.convertTo(appCAForm.getCorrespondenceAddressForm());
					eserviceApplication.getContactDetails().add(contactDetails);
				}
			}
		}

		eserviceApplication.setLicences(new ArrayList<Licence>());
		if(!form.getLicences().isEmpty()) {
			for(LicenceForm lcForm: form.getLicences()) {
				Licence lc = licenceFactory.convertTo(lcForm);
				eserviceApplication.getLicences().add(lc);
			}
		}

		if( form.getSecurityMeasure() != null && (form.getSecurityMeasure().getForbidManage() || form.getSecurityMeasure().getForbidUse())){
			eserviceApplication.setSecurityMeasure(securityMeasureFactory.convertTo(form.getSecurityMeasure()));
		}

		if(form.getGsHelpers()!= null && form.getGsHelpers().size() >0){
			eserviceApplication.setGsHelpers(new ArrayList<GSHelperDetails>());
			for(GSHelperForm gsHelper: form.getGsHelpers()){
				GSHelperDetails hDetails = gsHelperFactory.convertTo(gsHelper);
				if(hDetails != null) {
					eserviceApplication.getGsHelpers().add(hDetails);
				}
			}
		}

		if(form.getAppeals() != null && form.getAppeals().size() > 0){
			eserviceApplication.setAppeals(new ArrayList<Appeal>());
			for(AppealForm appeal: form.getAppeals()){
				Appeal app = appealFactory.convertTo(appeal);
				if(app != null) {
					eserviceApplication.getAppeals().add(app);
				}
			}
		}

        if(StringUtils.isNotEmpty(form.getChangeType())){
            eserviceApplication.setChangeType(form.getChangeType());
        }

        if(form.getPersonChanges() != null){
            eserviceApplication.setPersonChanges(new ArrayList<>());
            for (RepresentativeForm representativeForm : form.getPersonChanges()) {
                eserviceApplication.getPersonChanges().add(personChangeFactory.convertTo(representativeForm));
            }
        }

		if(form.getSelectedUserdoc() != null) {
			eserviceApplication.setSelectedUserdoc(userdocFactory.convertTo(form.getSelectedUserdoc()));
		}
		if(form.getUserdocRelationRestriction() != null && !form.getUserdocRelationRestriction().isEmpty()){
			eserviceApplication.setUserdocRelationRestriction(UserdocRelationRestriction.valueOf(form.getUserdocRelationRestriction()));
		}
		eserviceApplication.setRelateRequestToObject(form.getRelateRequestToObject());

		return eserviceApplication;				
	}

	
	/**
	 * FlowBean object and converts it to a core TradeMark object.
	 * 
	 * @param eserviceApplication
	 *            the UI FlowBean to convert
	 * @return the core TradeMark object
	 */
	@Override
	public ESFlowBean convertFrom(EServiceApplication eserviceApplication) {
		if (eserviceApplication == null){
			return new ESFlowBeanImpl();
		}


		ESFlowBean form = new ESFlowBeanImpl();
		ESMainForm mainForm = (ESMainForm)form.getMainForm();
        if (eserviceApplication.getSmallCompanyFiles() != null) {
            for (AttachedDocument item : eserviceApplication.getSmallCompanyFiles()) {
                StoredFile storedFile = attachedDocumentFactory.convertFrom(item);
                if (storedFile != null) {
					mainForm.getSmallCompanyFiles().getStoredFiles().add(storedFile);
                }
            }
        }
		mainForm.setSmallCompany(eserviceApplication.getSmallCompany());
		mainForm.setLicenceAvailability(eserviceApplication.getLicenceAvailability());

		form.setProcessInitiatedBeforePublication(eserviceApplication.getProcessInitiatedBeforePublication());
		form.setEsignDocDeclaration(eserviceApplication.getEsignDocDeclaration());
		form.setReference(eserviceApplication.getApplicationReference());

		form.setFeesForm(feesFactory.convertTo(eserviceApplication.getFees()));
		
		form.setFirstLang(eserviceApplication.getApplicationLanguage());
		
		form.setSecLang(eserviceApplication.getSecondLanguage());
		
		form.setComment(eserviceApplication.getComment());

		form.setPagesCountAttachment(eserviceApplication.getPagesCountAttachment());

		form.setHolderIsInventor(eserviceApplication.getHolderIsInventor());

		form.setPartialInvalidity(eserviceApplication.getPartialInvalidity());
		
		form.setFirstLang(eserviceApplication.getApplicationLanguage());
		
		form.setSecLang(eserviceApplication.getSecondLanguage());
		
		form.setIdreserventity(eserviceApplication.getApplicationFilingNumber());
		
		// add ApplicantForm objects
		if(eserviceApplication.getApplicants() != null) {
			for (Applicant item : eserviceApplication.getApplicants()) {
				ApplicantForm applicantForm = applicantFactory.convertFrom(item);
				if (applicantForm != null) {
					form.addObject(applicantForm);
				}
			}
		}
		
		if(eserviceApplication.getRepresentatives() != null){
			for (Representative item : eserviceApplication.getRepresentatives()) {
				RepresentativeForm representativeForm = representativeFactory.convertFrom(item);
				if (representativeForm != null) {
					form.addObject(representativeForm);
				}
			}
		}
		
		if(eserviceApplication.getHolders() != null){
			for (Holder item : eserviceApplication.getHolders()) {
				HolderForm holderForm = holderFactory.convertFrom(item);
				if (holderForm != null) {
					form.addObject(holderForm);
				}
			}
		}
		
		if(eserviceApplication.getOppositionGrounds() != null){
			for (OppositionGround item: eserviceApplication.getOppositionGrounds()){
				OppositionBasisForm oppositionBasisForm = oppositionGroundFactory.convertFrom(item);
				if (oppositionBasisForm!=null){
					form.addObject(oppositionBasisForm);
				}
			}
		}
		
		if(eserviceApplication.getAssignees() != null){
			for (Assignee item : eserviceApplication.getAssignees()) {
				AssigneeForm assigneeForm = assigneeFactory.convertFrom(item);
				if (assigneeForm != null) {
					form.addObject(assigneeForm);
				}
			}
		}

		if(eserviceApplication.getDesigners() != null){
			for (Designer item : eserviceApplication.getDesigners()) {
				DesignerForm designerForm = designerFactory.convertFrom(item);
				if (designerForm != null) {
					form.addObject(designerForm);
				}
			}
		}
		
		List <Fee> fees = new ArrayList<Fee>();
		for (PaymentFee item : eserviceApplication.getPayments()) {
			PaymentForm paymentForm = paymentFactory.convertFrom(item);
			if (paymentForm != null) {
				form.setPaymentForm(paymentForm);
			}
			
			if (item.getFees()!=null){
				for (MatchedFee matchedFee:item.getFees()){
					if(matchedFee != null){
						fees.add(matchedFee.getFee());
					}
				}
			}
		}
		if (form.getFeesForm()==null || form.getFeesForm().getFeeLineFormList()==null || form.getFeesForm().getFeeLineFormList().size()==0){
			form.setFeesForm(feesFactory.convertTo(fees));
		}

		if(!CollectionUtils.isEmpty(eserviceApplication.getSignatures())){
			for (Signatory signatory : eserviceApplication.getSignatures()) {
				SignatureForm signatoryForm = signatoryFactory.convertFrom(signatory);
				if (signatoryForm != null) {
					if (form.getSignatures() == null) {
						form.setSignatures(new ArrayList<>());
					}
					form.addObject(signatoryForm);
				}
			}
		}
		
		if(eserviceApplication.getDocuments() != null){
			for(AttachedDocument item : eserviceApplication.getDocuments()){
				StoredFile storedFile = attachedDocumentFactory.convertFrom(item);
				if (storedFile != null) {
					form.getOtherAttachments().getStoredFiles().add(storedFile);
				}
			}
		}
		
		//specific mappings
		if(eserviceApplication instanceof TMeServiceApplication && ((TMeServiceApplication)eserviceApplication).getTradeMarks() != null){
			for(LimitedTradeMark item : ((TMeServiceApplication)eserviceApplication).getTradeMarks()){
				TMDetailsForm tmDetailsForm = limitedTrademarkFactory.convertFrom(item);
				if (tmDetailsForm != null) {
					form.addObject(tmDetailsForm);
				}
			}
		}else if(eserviceApplication instanceof DSeServiceApplication && ((DSeServiceApplication)eserviceApplication).getDesignDetails() != null){
			for(DesignApplication item : ((DSeServiceApplication)eserviceApplication).getDesignDetails()){
				ESDesignDetailsForm designForm = designFactory.convertFrom(item);
				if (designForm != null) {
					form.addObject(designForm);
				}
			}
		} else if(eserviceApplication instanceof PTeServiceApplication && ((PTeServiceApplication)eserviceApplication).getPatentList() != null){
			for(Patent item : ((PTeServiceApplication)eserviceApplication).getPatentList()){
				ESPatentDetailsForm patentForm = esPatentFactory.convertFrom(item);
				if (patentForm != null) {
					form.addObject(patentForm);
				}
			}
			form.setMarketPermission(marketPermissionFactory.convertFrom(((PTeServiceApplication)eserviceApplication).getMarketPermission()));
		}

        if(eserviceApplication.getContactDetails() != null) {
            for(ContactDetails contactDetails: eserviceApplication.getContactDetails()) {
                ApplicationCAForm appCa = new ApplicationCAForm();
                CorrespondenceAddressForm caForm = contactDetailsFactory.convertFrom(contactDetails);
                appCa.setCorrespondenceAddressForm(caForm);
                form.addObject(appCa);
            }
        }

        if(eserviceApplication.getLicences() != null) {
            for(Licence lc: eserviceApplication.getLicences()) {
                LicenceForm lcForm = licenceFactory.convertFrom(lc);
                form.addObject(lcForm);
            }
        }

        if(eserviceApplication.getSecurityMeasure() != null){
            form.setSecurityMeasure(securityMeasureFactory.convertFrom(eserviceApplication.getSecurityMeasure()));
        }

        if(eserviceApplication.getGsHelpers() != null) {
            for(GSHelperDetails hDetail: eserviceApplication.getGsHelpers()){
                GSHelperForm hForm = gsHelperFactory.convertFrom(hDetail);
                if(hForm != null){
                    form.addObject(hForm);
                }
            }
        }

        if(eserviceApplication.getAppeals() != null) {
            for(Appeal appeal: eserviceApplication.getAppeals()) {
                AppealForm appForm = appealFactory.convertFrom(appeal);
                if(appForm != null) {
                    form.addObject(appForm);
                }
            }
        }

        if(StringUtils.isNotEmpty(eserviceApplication.getChangeType())){
            form.setChangeType(eserviceApplication.getChangeType());
        }

		if(eserviceApplication.getPersonChanges() != null){
            form.setPersonChanges(new ArrayList<>());
            for (PersonChange personChange : eserviceApplication.getPersonChanges()) {
                form.getPersonChanges().add(personChangeFactory.convertFrom(personChange));
            }
		}

		if(eserviceApplication.getSelectedUserdoc() != null){
			form.setSelectedUserdoc(userdocFactory.convertFrom(eserviceApplication.getSelectedUserdoc()));
		}
		if(eserviceApplication.getUserdocRelationRestriction() != null){
			form.setUserdocRelationRestriction(eserviceApplication.getUserdocRelationRestriction().name());
		}
		form.setRelateRequestToObject(eserviceApplication.getRelateRequestToObject());

        return form;
	}

}
