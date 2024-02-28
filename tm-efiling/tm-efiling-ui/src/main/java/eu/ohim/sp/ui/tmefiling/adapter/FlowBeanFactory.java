package eu.ohim.sp.ui.tmefiling.adapter;

import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.*;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.ConversionPriority;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.MatchedFee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.trademark.*;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.form.MainForm;
import eu.ohim.sp.ui.tmefiling.service.FormUtil;
import eu.ohim.sp.ui.tmefiling.util.FactoryUtil;
import eu.ohim.sp.ui.tmefiling.util.MarkViewUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FlowBeanFactory implements eu.ohim.sp.common.ui.adapter.FlowBeanFactory<TradeMark, FlowBeanImpl> {

    @Autowired
    private SeniorityFactory seniorityFactory;

    @Autowired
    private PriorityFactory priorityFactory;

    @Autowired
    private TransformationFactory transformationFactory;   
    
    @Autowired
    private ConversionFactory conversionFactory;

    @Autowired
    private ExhibitionPriorityFactory exhibitionPriorityFactory;

    @Autowired
    private SignatoryFactory signatoryFactory;

    @Autowired
    private GoodsServicesFactory goodsServicesFactory;

    @Autowired
    private ApplicantFactory applicantFactory;

    @Autowired
    private RepresentativeFactory representativeFactory;

    @Autowired
    private MainFormFactory mainFormFactory;

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

    @Value("${tmefiling.office}")
    private String office;

    @Autowired
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Autowired
    private PaymentFactory paymentFactory;
    
    @Autowired
    private FeesFactory feesFactory;

    @Autowired
    private MarkViewFactory markViewFactory;

    @Autowired
    private ForeignRegistrationFactory foreignRegistrationFactory;

    @Autowired
    private FormUtil formUtil;


    private Map<Class<? extends AbstractForm>, UIFactory<?, ?>> map;

    @PostConstruct
    public void init() {
        map = new HashMap<Class<? extends AbstractForm>, UIFactory<?, ?>>();
        map.put(PriorityForm.class, priorityFactory);
        map.put(SeniorityForm.class, seniorityFactory);
        map.put(ExhPriorityForm.class, exhibitionPriorityFactory);
        map.put(TransformationForm.class, transformationFactory);
        map.put(ConversionForm.class, conversionFactory);

        map.put(ProfessionalPractitionerForm.class, professionalPractitionerFactory);
        map.put(EmployeeRepresentativeForm.class, employeeRepresentativeFactory);
        map.put(LegalPractitionerForm.class, legalPractitionerFactory);
        map.put(RepresentativeNaturalPersonForm.class, representativeNaturalPersonFactory);
        map.put(RepresentativeLegalEntityForm.class, representativeLegalEntityFactory);

        map.put(LegalEntityForm.class, legalEntityFactory);
        map.put(NaturalPersonForm.class, naturalPersonFactory);
        map.put(NaturalPersonSpecialForm.class, naturalPersonSpecialFactory);
        map.put(FlowBeanImpl.class, this);

        map.put(MarkViewForm.class, markViewFactory);
    }

    public UIFactory<?, ?> getFactory(Class<?> clazz) {
        return map.get(clazz);
    }

    /**
     * Receives as a parameter a UI FlowBean object and converts it to a core
     * TradeMark object.
     * 
     * @param form the UI FlowBean to convert
     * @return the core TradeMark object
     */
    @Override
    public TradeMark convertTo(FlowBeanImpl form) {
        if (form == null) {
            return new TradeMark();
        }

        TradeMark core = mainFormFactory.convertTo(form);

        if(core.getImageSpecifications() != null && core.getImageSpecifications().size()>0 && form.getMarkViews() != null && form.getMarkViews().size()>0) {
            List<Colour> colourList = core.getImageSpecifications().get(0).getColours();
            boolean coloursClaimed = core.getImageSpecifications().get(0).isColourClaimedIndicator();

            List<ImageSpecification> imageSpecificationList = form.getMarkViews().stream().map(e -> {
                ImageSpecification imageSpec = markViewFactory.convertTo(e);
                imageSpec.setColourClaimedIndicator(coloursClaimed);
                imageSpec.setColours(colourList);
                return imageSpec;
            }).collect(Collectors.toList());

            core.setImageSpecifications(imageSpecificationList);

        }


        core.setApplicationLanguage(form.getFirstLang());
        core.setSecondLanguage(form.getSecLang());

        core.setRegistrationOffice(office);
        core.setReceivingOffice(office);

        core.setSecondLanguageTranslation(form.getMainForm().getSecondLanguageTranslation());
        if (form.getMainForm().getCorrespondenceLanguageCheckBox() != null
                && form.getMainForm().getCorrespondenceLanguageCheckBox()) {
            core.setCorrespondenceLanguage(form.getSecLang());
        } else {
            core.setCorrespondenceLanguage(form.getFirstLang());
        }

        if (form.getMainForm() != null) {

            core.setPriorityClaimLaterIndicator(convertClaimIndicatorToBoolean(form.getMainForm().getClaimPriority(),
                    form.getPriorities()));
            core.setExhibitionPriorityClaimLaterIndicator(convertClaimIndicatorToBoolean(form.getMainForm()
                    .getClaimExhibitionPriority(), form.getTransformations()));
            core.setNationalSearchReportIndicator(form.getMainForm().getNationalSearchReport() == null ? false : form
                    .getMainForm().getNationalSearchReport());
        }

        // Priorities
        if (form.getPriorities() != null && !form.getPriorities().isEmpty()) {
        	core.setPriorities(new ArrayList<Priority>());
        	for (PriorityForm item : form.getPriorities()) {
        		core.getPriorities().add(priorityFactory.convertTo(item));
        	}
        }
        // Seniorities
        if (form.getSeniorities() != null && !form.getSeniorities().isEmpty()) {
        	core.setSeniorities(new ArrayList<Seniority>());
	        for (SeniorityForm item : form.getSeniorities()) {
	            core.getSeniorities().add(seniorityFactory.convertTo(item));
	        }
        }
        // ExhibitionPriorities
        if (form.getExhpriorities() != null && !form.getExhpriorities().isEmpty()) {
        	core.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
	        for (ExhPriorityForm item : form.getExhpriorities()) {
	            core.getExhibitionPriorities().add(exhibitionPriorityFactory.convertTo(item));
	        }
        }
        // TransformationPriorities
        if (form.getTransformations() != null && !form.getTransformations().isEmpty()) {
        	core.setTransformationPriorities(new ArrayList<TransformationPriority>());
	        for (TransformationForm item : form.getTransformations()) {
	            core.getTransformationPriorities().add(transformationFactory.convertTo(item));
	        }
        }
        //ConversionPriorities
        if(form.getConversions() != null && !form.getConversions().isEmpty()) {
        	core.setConversionPriorities(new ArrayList<ConversionPriority>());
        	for(ConversionForm item: form.getConversions()) {
        		core.getConversionPriorities().add(conversionFactory.convertTo(item));
        	}
        }
        
        // GoodsAndServices
        if (form.getGoodsAndServices() != null && !form.getGoodsAndServices().isEmpty()) {
        	core.setClassDescriptions(new ArrayList<ClassDescription>());
	        for (GoodAndServiceForm item : form.getGoodsAndServices()) {
	            ClassDescription goodServices = goodsServicesFactory.convertTo(item);
	            if (goodServices != null) {
	                core.getClassDescriptions().add(goodServices);
	            }
	        }
        }

        if(form.getForeignRegistrations() != null && !form.getForeignRegistrations().isEmpty()){
            core.setForeignRegistrations(form.getForeignRegistrations().stream().map(fr -> foreignRegistrationFactory.convertTo(fr)).collect(Collectors.toList()));
        }
        
		// Applicants
		applicantFormsToCore(form.getApplicants(), core);

		// Representatives
		representativeFormsToCore(form.getRepresentatives(), core);
		
        if (form.getMainForm().getClaimDivisionalApplication() != null
                && form.getMainForm().getClaimDivisionalApplication()) {
            core.setDivisionalApplicationDetails(new DivisionalApplicationDetails());
            core.getDivisionalApplicationDetails().setInitialApplicationNumber(
                    form.getMainForm().getNumberDivisionalApplication());
            core.getDivisionalApplicationDetails().setInitialApplicationDate(
                    form.getMainForm().getDateDivisionalApplication());
            core.getDivisionalApplicationDetails().setInitialApplicationImported(
                    form.getMainForm().getDivisionalApplicationImported());
        } else if (form.getMainForm().getClaimDivisionalApplication() != null
                && !form.getMainForm().getClaimDivisionalApplication()) {
            core.setDivisionalApplicationDetails(null);
        }

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

        core.setApplicationUseReference(form.getUseReference());
        core.setApplicationReference(form.getReference());

        core.setComment(form.getComment());

        return core;
    }
    
	/**
	 *
	 * @param applicantForms
	 * @param core
	 */
	private void applicantFormsToCore(List<ApplicantForm> applicantForms, TradeMark core) {
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
	 * @param representativeForms
	 * @param core
	 */
	private void representativeFormsToCore(List<RepresentativeForm> representativeForms, TradeMark core) {
		List<Representative> representatives = new ArrayList<Representative>();
		if (CollectionUtils.isNotEmpty(representativeForms)) {
			for (RepresentativeForm representativeForm : representativeForms) {
				representatives.add(representativeFactory.convertTo(representativeForm));
			}
		}
		core.setRepresentatives(representatives);
	} 

    /**
     * Receives as a parameter a UI FlowBean object and converts it to a core
     * TradeMark object.
     * 
     * @param core the UI FlowBean to convert
     * @return the core TradeMark object
     */
    @Override
    public FlowBeanImpl convertFrom(TradeMark core) {
        if (core == null) {
            return new FlowBeanImpl();
        }

        FlowBeanImpl form = mainFormFactory.convertFrom(core);
        MainForm mainForm = form.getMainForm();

        if(FactoryUtil.markContainsViews(form.getMainForm().getMarkType())){
            if (core.getImageSpecifications() != null && !core.getImageSpecifications().isEmpty()) {
                core.getImageSpecifications().stream().forEach( img -> {
                   MarkViewForm view = markViewFactory.convertFrom(img);
                   view.setMarkType(mainForm.getMarkType());
                   form.addObject(view);
                });
            }

            MarkViewUtil.addViewsSequences(form);
        }

        // add ApplicantForm objects
        if (core.getApplicants() != null && !core.getApplicants().isEmpty()) {
	        for (Applicant item : core.getApplicants()) {
	            ApplicantForm applicantForm = applicantFactory.convertFrom(item);
	            if (applicantForm != null) {
	                form.addObject(applicantForm);
	            }
	        }
        }
        if (core.getRepresentatives() != null && !core.getRepresentatives().isEmpty()) {
        	Set<String> addedIds = new HashSet<>();
	        for (Representative item : core.getRepresentatives()) {
	            RepresentativeForm representativeForm = representativeFactory.convertFrom(item);
	            if (representativeForm != null) {
	            	if(!addedIds.contains(representativeForm.getId())){
	            		// Add only if it is not there already
	            		form.addObject(representativeForm);
	            		addedIds.add(representativeForm.getId());
	            	}
	            }
	        }
        }
        if (core.getSeniorities() != null && !core.getSeniorities().isEmpty()) {
	        for (Seniority item : core.getSeniorities()) {
	            SeniorityForm seniorityForm = seniorityFactory.convertFrom(item);
	            if (seniorityForm != null) {
	                form.addObject(seniorityForm);
	            }
	        }
        }
        if (core.getPriorities() != null && !core.getPriorities().isEmpty()) {
        	for (Priority item : core.getPriorities()) {
        		PriorityForm priorityForm = priorityFactory.convertFrom(item);
        		if (priorityForm != null) {
        			form.addObject(priorityForm);
        		}
        	}
        }
        if (core.getExhibitionPriorities() != null && !core.getExhibitionPriorities().isEmpty()) {
	        for (ExhibitionPriority item : core.getExhibitionPriorities()) {
	            ExhPriorityForm exhPriorityForm = exhibitionPriorityFactory.convertFrom(item);
	            if (exhPriorityForm != null) {
	                form.addObject(exhPriorityForm);
	            }
	        }
        }
        if (core.getTransformationPriorities() != null && !core.getTransformationPriorities().isEmpty()) {
	        for (TransformationPriority item : core.getTransformationPriorities()) {
	            TransformationForm transformationForm = transformationFactory.convertFrom(item);
	            if (transformationForm != null) {
	                form.addObject(transformationForm);
	            }
	        }
        }
        if(core.getConversionPriorities() != null && !core.getConversionPriorities().isEmpty()) {
        	for(ConversionPriority item : core.getConversionPriorities()) {
        		ConversionForm conversionForm = conversionFactory.convertFrom(item);
        		if(conversionForm != null) {
        			form.addObject(conversionForm);
        		}
        	}
        }
        if (core.getClassDescriptions() != null && !core.getClassDescriptions().isEmpty()) {
	        for (ClassDescription gs : core.getClassDescriptions()) {
	            GoodAndServiceForm gsForm = goodsServicesFactory.convertFrom(gs);
	            if (gsForm != null) {
	                form.addGoodAndService(gsForm);
	            }
	        }
        }

        if (core.getDivisionalApplicationDetails() != null) {
            form.getMainForm().setClaimDivisionalApplication(true);
            form.getMainForm().setNumberDivisionalApplication(
                    core.getDivisionalApplicationDetails().getInitialApplicationNumber());
            form.getMainForm().setDateDivisionalApplication(
                    core.getDivisionalApplicationDetails().getInitialApplicationDate());
            form.getMainForm().setDivisionalApplicationImported(
                   true);
        }

        if(core.getForeignRegistrations() != null && !core.getForeignRegistrations().isEmpty()){
            core.getForeignRegistrations().stream().forEach(fr -> form.addObject(foreignRegistrationFactory.convertFrom(fr)));
        }
        
        form.getMainForm().setNationalSearchReport(core.isNationalSearchReportIndicator());

        form.setFirstLang(core.getApplicationLanguage());
        form.setSecLang(core.getSecondLanguage());


        mainForm.setClaimExhibitionPriority(claimPriorityValue(core.getExhibitionPriorityClaimLaterIndicator(),
                core.getExhibitionPriorities()));
        mainForm.setClaimPriority(claimPriorityValue(core.getPriorityClaimLaterIndicator(), core.getPriorities()));

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

        mainForm.setSecondLanguageTranslation(core.getSecondLanguageTranslation());

        if (core.getCorrespondenceLanguage() != null
                && core.getCorrespondenceLanguage().equals(core.getSecondLanguage())) {
            mainForm.setCorrespondenceLanguageCheckBox(true);
        } else {
            mainForm.setCorrespondenceLanguageCheckBox(false);
        }

        form.setUseReference(core.getApplicationUseReference());
        form.setReference(core.getApplicationReference());

       


        if (core.getDivisionalApplicationDetails() != null) {
            form.getMainForm().setClaimDivisionalApplication(Boolean.TRUE);
            form.getMainForm().setDateDivisionalApplication(
                    core.getDivisionalApplicationDetails().getInitialApplicationDate());
            form.getMainForm().setNumberDivisionalApplication(
                    core.getDivisionalApplicationDetails().getInitialApplicationNumber());
        }

        form.setComment(core.getComment());

        return form;
    }

    /**
     * Receives as a parameter a core TradeMarkApplication object and converts
     * it to a UI FlowBean object. This method converts the inner trade mark and
     * additional signatory, other included documents and payment information.
     * 
     * @param core
     * @return flowBean trademark application converted to flowBean
     */
    public FlowBeanImpl convertFromTradeMarkApplication(TradeMarkApplication core) {
        if (core == null) {
            return new FlowBeanImpl();
        }

        FlowBeanImpl form = new FlowBeanImpl();
        if (core.getTradeMark() != null) {
            form = convertFrom(core.getTradeMark());
        }
        form.setFastTrack(core.getFastTrack());
        form.setWillPayOnline(core.getWillPayOnline());
        
        // Payment Details conversion
        if (core.getPayments() != null && !core.getPayments().isEmpty()) {
        	form.setPaymentForm(paymentFactory.convertFrom(core.getPayments().get(0)));
        }

        // Fees
        if (core.getFees() != null && !core.getFees().isEmpty()) {
        	form.setFeesForm(feesFactory.convertTo(core.getFees()));
        }

        if(core.getDocuments() != null && core.getDocuments().size()>0){
            form.setOtherAttachments(listAttachedDocumentFactory.convertFrom(core.getDocuments()));
            form.setTrueDocumentsIndicator(core.getTrueDocumentsIndicator());
        }

        form.setCertificateRequestedIndicator(core.getCertificateRequestedIndicator());

        return form;
    }

    /**
     *
     * @param paymentForm
     * @param core
     */
    private void paymentFormToCore(PaymentForm paymentForm, TradeMarkApplication core) {
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
     * @param feesForm
     * @param core
     */
    private void feesFormToCore(FeesForm feesForm, TradeMarkApplication core) {
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
     * Receives as a parameter a UI FlowBean object and converts it to a core
     * TradeMarkApplication object. This method converts the inner trade mark
     * and additional signatory, other included documents and payment
     * information.
     *
     * @param form the UI FlowBean to convert
     * @return the core TradeMarkApplication object
     */
    public TradeMarkApplication convertToTradeMarkApplication(FlowBeanImpl form) {
        if (form == null) {
            return new TradeMarkApplication();
        }

        TradeMarkApplication core = new TradeMarkApplication();
        core.setApplicationType(formUtil.getType());
        core.setUser(form.getCurrentUserName());
        core.setUserEmail(form.getCurrentUserEmail());
        core.setFastTrack(form.getFastTrack());
        core.setWillPayOnline(form.getWillPayOnline());

        core.setApplicationFilingNumber(form.getIdreserventity());

        TradeMark markDetails = convertTo(form);

        core.setTradeMark(markDetails);

        if (form.getOtherAttachments() != null) {
        	if(core.getDocuments() == null) {
                core.setDocuments(new ArrayList<AttachedDocument>());
            }
            core.getDocuments().addAll(
                    listAttachedDocumentFactory.convertTo(form.getOtherAttachments()));
            core.setTrueDocumentsIndicator(form.getTrueDocumentsIndicator());
        }

        // Payment Details conversion
        paymentFormToCore(form.getPaymentForm(), core);

        // Fees
        feesFormToCore(form.getFeesForm(), core);

        core.setCertificateRequestedIndicator(form.getCertificateRequestedIndicator());

        return core;
    }


    private Boolean claimPriorityValue(Boolean coreLaterIndicator, Collection<?> coreList) {
        if (coreLaterIndicator != null && coreLaterIndicator){
            return true;
        }else {
            if (coreList != null && !coreList.isEmpty()){
                return false;
            }else{
                return null;
            }
        }
    }

    /**
     * Converts CLAIM_PRIORITIES_LATER indicator into Frue if collection is not
     * empty, then the CLAIM_PRIORITIES_NOW is active and False is returned if
     * other type of indicator passed and if collection is empty, returns null
     * (DON'T_CLAIM_PRIORITY)
     * 
     * @param claimIndicator
     * @param formCollection
     * @return
     */
    private Boolean convertClaimIndicatorToBoolean(Boolean claimIndicator, Collection<?> formCollection) {
        if (claimIndicator != null && claimIndicator) {
            return true;
        }
        if (formCollection != null && !formCollection.isEmpty()) {
            return false;
        }
        return null;
    }

    /**
  	 *
  	 * @param form
  	 * @param core
  	 */
	private void signatureFormsToCore(FlowBeanImpl form, TradeMark core) {
  		List<Signatory> signatures = new ArrayList<Signatory>();
  		if (form.getMainForm().getSignatoryForm() != null) {
  			signatures.add(signatoryFactory.convertTo(form.getMainForm().getSignatoryForm()));
  		}
  		if (BooleanUtils.isTrue(form.getMainForm().getAddSecondSign()) && form.getMainForm().getSecondSignatoryForm() != null) {
  			signatures.add(signatoryFactory.convertTo(form.getMainForm().getSecondSignatoryForm()));
  		}
  		core.setSignatures(signatures);
  	}
}
