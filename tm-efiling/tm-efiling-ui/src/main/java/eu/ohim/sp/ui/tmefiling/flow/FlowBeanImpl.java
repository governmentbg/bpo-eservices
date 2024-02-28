/*******************************************************************************
 * * $Id::                                                      $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import eu.ohim.sp.common.ui.flow.section.*;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.trademark.ForeignRegistrationForm;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.claim.ConversionForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.form.payment.PayerKindForm;
import eu.ohim.sp.common.ui.form.payment.PayerTypeForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.trademark.SimilarMarkForm;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.ui.tmefiling.form.MainForm;

/**
 * Bean that will hold all the information about an application. It's scope
 * would be session so to keep all the information during the session.
 * 
 * @author ckara
 */
public class FlowBeanImpl extends CommonSpFlowBean
		implements eu.ohim.sp.common.ui.flow.FlowBean, ClaimFlowBean, PersonFlowBean,
		OtherAttachmentsFlowBean, PaymentFlowBean, FeesFlowBean, LanguagesFlowBean, GoodsServicesFlowBean, TMFlowBean, FastTrackBean {

	private static final long serialVersionUID = 1L;
	
	private static transient final Logger logger = Logger.getLogger(FlowBeanImpl.class);
	
	private Map<String, GoodAndServiceForm> tempGoodsServices = new HashMap<String, GoodAndServiceForm>();

	/**
	 * The collection of stored priorities in the session
	 */
	private List<PriorityForm> priorities;
	/**
	 * The collection of stored seniorities in the session
	 */
	private List<SeniorityForm> seniorities;
	/**
	 * The collection of stored international applications in the session
	 */
	private List<TransformationForm> transformations;

	/**
	 * The collection of stored international conversions in the session
	 */
	private List<ConversionForm> conversions;

	/**
	 * The collection of stored exhibition priorities in the session
	 */
	private List<ExhPriorityForm> exhpriorities;

	/**
	 * The collection of stored goods and services in session
	 */
	private Set<GoodAndServiceForm> goodsServices;

	/**
	 * List of similar marks
	 */
	private List<SimilarMarkForm> similarMarks;

	/**
	 * The first lang selected by user stored in session.
	 */
	private String firstLang;

	/**
	 * Second lang selected by user stored in session.
	 */
	private String secLang;

	/**
	 * Indicate if personal reference for this trademark will be used
	 */
	private Boolean useReference;

	/**
	 * Personal reference for this trademark
	 */
	private String reference;

	/**
	 * Flag indicating if there is a warning for validation
	 */
	private Boolean warningValidated;

	/**
	 * The MainForm
	 */
	private MainForm mainForm;

	/**
	 * The fee section
	 */
	private FeesForm feesForm;

	/**
	 * Search terms that are returned from Euroclass and stored in the session
	 */
	private List<TermForm> terms;

	private String term;

	/**
	 * Private counter that helps to create new identifiers for the various
	 * elements
	 */
	private int i = 0;
	/**
	 * CTM Id reserved for the application after it was successfully saved
	 */
	private String idreserventity = null;

	/**
	 * Payment details
	 */
	private PaymentForm paymentForm;

	private FileWrapper otherAttachments;

	/**
	 * Additional information field
	 */
	private String comment;

	/**
	 * Error code that can be set when the application is created. It is used
	 * because on-start needs one created flowbean, even empty but we pass the
	 * error.
	 */
	private String initializationErrorCode = null;

	/**
	 * True document indicator
	 */
	private Boolean trueDocumentsIndicator;
	private Boolean certificateRequestedIndicator;

	private List<MarkViewForm> markViews;

	/** Flag indicating if filling of the app started. Skips first display of the form */
	private boolean fastTrackStarted;

	/** Flag indicating if filling is fastTrack */
	private Boolean fastTrack;

	private Boolean willPayOnline;

	private List<ForeignRegistrationForm> foreignRegistrations;

	@Override
	public Boolean getTrueDocumentsIndicator() {
		return trueDocumentsIndicator;
	}

	@Override
	public void setTrueDocumentsIndicator(Boolean trueDocumentsIndicator) {
		this.trueDocumentsIndicator = trueDocumentsIndicator;
	}

	/**
	 * Default constructor for FlowBeanImpl Initializes the collections
	 */
	public FlowBeanImpl() {
		init();
	}
	
	private void init() {
		terms = new ArrayList<TermForm>();
		term = null;
		mainForm = new MainForm();
		paymentForm = new PaymentForm();
		feesForm = new FeesForm();
		goodsServices = new TreeSet<GoodAndServiceForm>();
		firstLang = null;
		secLang = null;
		idreserventity = null;
		id = null;
		otherAttachments = new FileWrapper();
		similarMarks = new ArrayList<>();
		warningValidated = null;
		fastTrackStarted = false;
		useReference = null;
		reference = null;
		initializationErrorCode = null;
		comment = null;
		
		map.put(ApplicationCAForm.class, mainForm.getCorrespondanceAddresses());
		
		clearPriorities();
		clearSeniorities();
		clearExhibitions();
		clearTransformations();
		clearConversions();
		clearMarkViews();
		clearForeignRegistrations();
	}
	
	/**
	 * Initializes the entities in the session
	 */
	@Override
	public void clear() {
		super.clear();
		this.init();
	}

	/**
	 * Clear the applicants
	 */
	@Override
	public void clearApplicants() {
		applicants = new ArrayList<ApplicantForm>();
		map.put(LegalEntityForm.class, applicants);
		map.put(NaturalPersonForm.class, applicants);
		map.put(NaturalPersonSpecialForm.class, applicants);
		map.put(ApplicantForm.class, applicants);
	}
	
	/**
	 * Clear the representatives
	 */
	@Override
	public void clearRepresentatives() {
		representatives = new ArrayList<RepresentativeForm>();
		map.put(LegalPractitionerForm.class, representatives);
		map.put(EmployeeRepresentativeForm.class, representatives);
		map.put(ProfessionalPractitionerForm.class, representatives);
		map.put(RepresentativeNaturalPersonForm.class, representatives);
		map.put(RepresentativeLegalEntityForm.class, representatives);
		map.put(RepresentativeAssociationForm.class, representatives);
		map.put(LawyerCompanyForm.class, representatives);
		map.put(LawyerAssociationForm.class, representatives);
		map.put(RepresentativeTemporaryForm.class, representatives);
		map.put(IntlPRepresentativeForm.class, representatives);
		map.put(RepresentativeForm.class, representatives);
	}

	/**
	 * Clear the seniorities
	 */
	@Override
	public void clearSeniorities() {
		seniorities = new ArrayList<SeniorityForm>();
		map.put(SeniorityForm.class, seniorities);
	}

	/**
	 * Clear the priorities
	 */
	@Override
	public void clearPriorities() {
		priorities = new ArrayList<PriorityForm>();
		map.put(PriorityForm.class, priorities);
	}

	/**
	 * Clear then exhibition priorities
	 */
	@Override
	public void clearExhibitions() {
		exhpriorities = new ArrayList<ExhPriorityForm>();
		map.put(ExhPriorityForm.class, exhpriorities);
	}

	/**
	 * Clear the transformations
	 */
	@Override
	public void clearTransformations() {
		transformations = new ArrayList<TransformationForm>();
		map.put(TransformationForm.class, transformations);
	}

	@Override
	public void clearConversions() {
		conversions = new ArrayList<ConversionForm>();
		map.put(ConversionForm.class, conversions);
	}

	@Override
	public void clearMarkViews(){
		markViews = new ArrayList<>();
		map.put(MarkViewForm.class, markViews);
	}

	@Override
	public void clearForeignRegistrations(){
		foreignRegistrations = new ArrayList<>();
		map.put(ForeignRegistrationForm.class, foreignRegistrations);
	}


	@Override
	public String getFilingNumber() {
		return idreserventity;
	}
	
	/**
	 * Retrieves all the stored priorities
	 * 
	 * @return All the stored priorities
	 */
	@Override
	public List<PriorityForm> getPriorities() {
		return priorities;
	}
	
	@Override
	public List<ApplicationCAForm> getCorrespondanceAddresses() {
		return this.mainForm.getCorrespondanceAddresses();
	}

	/**
	 * Retrieves all the stored seniorities
	 * 
	 * @return All the stored seniorities
	 */
	@Override
	public List<SeniorityForm> getSeniorities() {
		return seniorities;
	}

	@Override
	public List<SimilarMarkForm> getSimilarMarks() {
		return similarMarks;
	}

	@Override
	public void setSimilarMarks(List<SimilarMarkForm> similarMarks) {
		this.similarMarks = similarMarks;
	}

	/**
	 * Returns the main form that is stored in the session
	 * 
	 * @return the mainForm
	 */
	@Override
	public MainForm getMainForm() {
		return mainForm;
	}

	/**
	 * Returns the fee form that is stored in the session
	 * 
	 * @return the feeform
	 */
	@Override
	public FeesForm getFeesForm() {
		return feesForm;
	}


	/**
	 * Stores the fee form
	 * 
	 * @param feesForm
	 *            the feeForm to set
	 */
	@Override
	public void setFeesForm(FeesForm feesForm) {
		this.feesForm = feesForm;
	}

	public void resetTempGoodsServices(String uuid, String classId, String langId) {
		if(!this.tempGoodsServices.containsKey(uuid)) {
			GoodAndServiceForm tempGoodsServices = new GoodAndServiceForm();
			tempGoodsServices.setClassId(classId);
			tempGoodsServices.setLangId(langId);
			tempGoodsServices.setDesc("");
			this.tempGoodsServices.put(uuid, tempGoodsServices);
		}
	}

	public void addTempGoodsServices(String uuid, GoodAndServiceForm current) {
		GoodAndServiceForm tempGoodsServices = this.tempGoodsServices.get(uuid);

		if (tempGoodsServices == null || !current.getClassId().equals(tempGoodsServices.getClassId())
				|| !current.getLangId().equals(tempGoodsServices.getLangId())) {
			throw new RuntimeException("There is no tempGoodsServices for the given uuid " + uuid
					+ " or the current classId and langId are not equal to the temp ones....");
		}

		if (tempGoodsServices.getDesc().length() != 0) {
			tempGoodsServices.setDesc(tempGoodsServices.getDesc() + "; ");
		}
		tempGoodsServices.setDesc(tempGoodsServices.getDesc() + current.getDesc());
		for (TermForm tf : current.getTermForms()) {
			tempGoodsServices.addTermForm(tf);
		}
	}

	public GoodAndServiceForm getTempGoodsServices(String uuid) {
		return tempGoodsServices.get(uuid);
	}

	public void removeTempGoodsServices(String uuid) {
		tempGoodsServices.remove(uuid);
	}

	@Override
	public void addGoodAndService(GoodAndServiceForm gs) {
		try {
			sanitizeGS(gs);
			if (goodsServices.contains(gs)) {
				if (logger.isDebugEnabled()) {
					logger.debug("The goodsService collection contains key : " + gs);
				}
				Iterator<GoodAndServiceForm> iterator = goodsServices.iterator();
				while (iterator.hasNext()) {
					GoodAndServiceForm goodsAndServiceForm = iterator.next();
					if (goodsAndServiceForm.equals(gs)) {
						for (TermForm termForm : gs.getTermForms()) {
							goodsAndServiceForm.addTermForm(termForm);
						}
					}
				}
			} else {
				goodsServices.add(gs);
			}
		} catch (Exception e) {
			logger.error("An exception occured in FlowBean.addGoodAndService method");
			throw new SPException(e);
		}
	}

    /* (non-Javadoc)
     * @see eu.ohim.sp.ui.tmefiling.flow.GoodsServicesFlowBean#replaceEditedTerm(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    public void replaceEditedTerm(List<GoodAndServiceForm> newGS, String termClass, String termDescription) {
        try {
            for (GoodAndServiceForm newTerms : newGS) {
                sanitizeGS(newTerms);
                if (goodsServices.contains(newTerms)) {
                    // Add new terms in same class
                    goodsServices.stream()
						.filter(f -> f.equals(newTerms))
						.forEach(currentTerms -> {
							currentTerms.getTermForms().addAll(newTerms.getTermForms());
						});
                } else {
                    // Add new terms in a different class
                    goodsServices.add(newTerms);
                }
            }

            // Remove old term
            goodsServices.stream().forEach(currentTerms -> {
                Set<TermForm> terms = currentTerms.getTermForms();
                TermForm oldTerm = findTerm(terms, termClass, termDescription);
                if (oldTerm != null) {
                    terms.remove(oldTerm);
                }
            });

        } catch (Exception e) {
            logger.error("An exception occurred in FlowBean.replaceEditedTerm method");
            throw new SPException(e);
        }
    }

    /**
     * Find term.
     *
     * @param terms the terms
     * @param termClass the term class
     * @param termDescription the term description
     * @return the term form
     */
    private TermForm findTerm(Set<TermForm> terms, String termClass, String termDescription) {
        if (terms != null && termClass != null && termDescription != null) {
            for (TermForm termForm : terms) {
                if (termForm.getIdClass().trim().equals(termClass.trim()) && termForm.getDescription().trim().equals(termDescription.trim())) {
                    return termForm;
                }
            }
        }
        return null;
    }

	public Integer getNotValidatedTerms() {
		Integer notValidatedTerms = 0;
		for (GoodAndServiceForm goods : goodsServices) {
			for (TermForm term : goods.getTermForms()) {
				if (term.getError() != null) {
					notValidatedTerms++;
				}
			}
		}
		if (notValidatedTerms == 0) {
			return null;
		} else {
			return notValidatedTerms;
		}
	}

	private void fixGoodsAndServices() {
		Iterator<GoodAndServiceForm> iterator = goodsServices.iterator();
		List<GoodAndServiceForm> addedGoodAndServiceForms = new ArrayList<GoodAndServiceForm>();
		while (iterator.hasNext()) {
			GoodAndServiceForm goodAndServiceForm = iterator.next();
			if (goodAndServiceForm.getLangId() == null) {
				iterator.remove();
			} else if (goodAndServiceForm.getLangId().equals(firstLang)) {
				if (goodAndServiceForm.getTermForms().size() == 0) {
					// check how to remove the goods for the same class but in
					// second language
					iterator.remove();
				} else {
					if (mainForm.getSecondLanguageTranslation() != null && mainForm.getSecondLanguageTranslation()
							&& secLang != null) {
						GoodAndServiceForm secondLanguageGoodAndServiceForm = new GoodAndServiceForm();
						secondLanguageGoodAndServiceForm.setClassId(goodAndServiceForm.getClassId());
						secondLanguageGoodAndServiceForm.setLangId(secLang);
						if (!goodsServices.contains(secondLanguageGoodAndServiceForm)) {
							addedGoodAndServiceForms.add(secondLanguageGoodAndServiceForm);
						}
					}
				}
			} else {
				if (secLang == null || mainForm.getSecondLanguageTranslation() == null
						|| !mainForm.getSecondLanguageTranslation()
						|| !goodAndServiceForm.getLangId().equals(secLang)) {
					iterator.remove();
				} else {
					GoodAndServiceForm firstLanguageGoodAndServiceForm = new GoodAndServiceForm();
					firstLanguageGoodAndServiceForm.setClassId(goodAndServiceForm.getClassId());
					firstLanguageGoodAndServiceForm.setLangId(firstLang);
					if (!goodsServices.contains(firstLanguageGoodAndServiceForm)) {
						iterator.remove();
					}
				}
			}
		}
		goodsServices.addAll(addedGoodAndServiceForms);
	}

	private void sanitizeGS(GoodAndServiceForm goodsServices) {
		Iterator<TermForm> iterator = goodsServices.getTermForms().iterator();
		while (iterator.hasNext()) {
			if ("".equals(iterator.next().getDescription().trim())) {
				iterator.remove();
			}
		}
	}

	/**
	 * Help method to retrieve goods and services as a Map with key of type
	 * ${ClassId}_${LangId}.
	 * 
	 * @return List of GoodAndServiceForms
	 */
	@Override
	public Set<GoodAndServiceForm> getGoodsAndServices() {
		synchronized (this) {
			fixGoodsAndServices();
		}
		return goodsServices;
	}

	@Override
	public GoodAndServiceForm getGoodsAndService(String langId, String classId) {
		GoodAndServiceForm goodAndServiceForm = new GoodAndServiceForm(langId, classId);
		for (GoodAndServiceForm goodAndService : goodsServices) {
			if (goodAndService.equals(goodAndServiceForm)) {
				return goodAndService;
			}
		}
		return null;
	}

	/**
	 * Returns the total number of classes
	 * 
	 * @return int number of classes
	 */
	@Override
	public int getNumberofClasses() {
		if (getGoodsAndServices() == null) {
			return 0;
		} else {
			Set<String> classes = new HashSet<String>();
			for (GoodAndServiceForm good : getGoodsAndServices()) {
				classes.add(good.getClassId());
			}
			return classes.size();
		}
	}

	/**
	 * Getter method for firstLang
	 * 
	 * @return the firstLang
	 */
	@Override
	public String getFirstLang() {
		return firstLang;
	}

	/**
	 * Setter method for first lang
	 * 
	 * @param firstLang
	 *            the firstLang to set
	 */
	@Override
	public void setFirstLang(String firstLang) {
		this.firstLang = firstLang;
	}

	/**
	 * Getter method for second lang
	 * 
	 * @return the secLang
	 */
	@Override
	public String getSecLang() {
		return secLang;
	}

	/**
	 * Setter method for second lang
	 * 
	 * @param secLang
	 *            the secLang to set
	 */
	@Override
	public void setSecLang(String secLang) {
		this.secLang = secLang;
	}

	/**
	 * Clear the Goods and Services
	 */
	@Override
	public void clearGandS() {
		goodsServices = new TreeSet<GoodAndServiceForm>();
	}

	/**
	 * @return the exhpriorities
	 */
	@Override
	public List<ExhPriorityForm> getExhpriorities() {
		return exhpriorities;
	}

	/**
	 * @return the transformations
	 */
	@Override
	public List<TransformationForm> getTransformations() {
		return transformations;
	}

	@Override
	public List<ConversionForm> getConversions() {
		return conversions;
	}

	/**
	 * Method that returns the terms
	 * 
	 * @return the terms
	 */
	@Override
	public List<TermForm> getTerms() {
		return terms;
	}

	/**
	 * Method that sets the terms
	 * 
	 * @param terms
	 *            the terms to set
	 */
	@Override
	public void setTerms(List<TermForm> terms) {
		this.terms = terms;
	}

	/**
	 * Method that returns the term
	 * 
	 * @return the term
	 */
	@Override
	public String getTerm() {
		return term;
	}

	/**
	 * Method that sets the term
	 * 
	 * @param term
	 *            the term to set
	 */
	@Override
	public void setTerm(String term) {
		this.term = term;
	}

	@Override
	public Boolean getWarningValidated() {
		return warningValidated;
	}

	@Override
	public void setWarningValidated(Boolean warningValidated) {
		this.warningValidated = warningValidated;
	}

	@Override
	public PaymentForm getPaymentForm() {
		return paymentForm;
	}

	@Override
	public void setPaymentForm(PaymentForm paymentForm) {
		this.paymentForm = paymentForm;
	}

	/**
	 * Method that returns the useReference
	 * 
	 * @return the useReference
	 */
	@Override
	public Boolean getUseReference() {
		return useReference;
	}

	/**
	 * Method that sets the useReference
	 * 
	 * @param useReference
	 *            the reference to set
	 */
	@Override
	public void setUseReference(Boolean useReference) {
		this.useReference = useReference;
	}

	/**
	 * Method that returns the reference
	 * 
	 * @return the reference
	 */
	@Override
	public String getReference() {
		return reference;
	}

	/**
	 * Method that sets the reference
	 * 
	 * @param reference
	 *            the reference to set
	 */
	@Override
	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		return null;
	}

	@Override
	public FileWrapper getOtherAttachments() {
		if (otherAttachments == null) {
			otherAttachments = new FileWrapper();
		}
		return otherAttachments;
	}

	@Override
	public void setOtherAttachments(FileWrapper otherAttachments) {
		this.otherAttachments = otherAttachments;
	}

	@Override
	public String getInitializationErrorCode() {
		return initializationErrorCode;
	}

	@Override
	public void setInitializationErrorCode(String initializationErrorCode) {
		this.initializationErrorCode = initializationErrorCode;
	}

	@Override
	public FlowBeanImpl clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * According to the activePayerTypes sent as parameter It returns the
	 * applicants, representatives and/or "other" that can pay
	 * 
	 * @param activePayerTypes
	 *            list of active payer types
	 * @return list of applicants, representatives and/or "other" that can pay
	 */
	@Override
	public List<PayerTypeForm> getPayerTypes(List<PayerType> activePayerTypes) {
		List<PayerTypeForm> payerTypes = new ArrayList<PayerTypeForm>();

		// Loop activePayerTypes and add the possible payers
		for (PayerType activePayerType : activePayerTypes) {
			// Check whether the applicants have to be added as possible payers
			if (activePayerType.getCode().equals(PayerKindForm.APPLICANT.getCode())) {
				for (int i = 0; i < getApplicants().size(); i++) {
					ApplicantForm applicant = getApplicants().get(i);
					PayerTypeForm payer = new PayerTypeForm();
					payer.setCode(applicant.getId());
					payer.setDescription(applicant.getName());
					payer.setPayerTypeCode(activePayerType.getCode());
					payerTypes.add(payer);
				}
			}
			if (activePayerType.getCode().equals(PayerKindForm.REPRESENTATIVE.getCode())) {
				for (int i = 0; i < getRepresentatives().size(); i++) {
					RepresentativeForm representative = getRepresentatives().get(i);
					PayerTypeForm payer = new PayerTypeForm();
					payer.setCode(representative.getId());
					payer.setDescription(representative.getName());
					payer.setPayerTypeCode(activePayerType.getCode());
					payerTypes.add(payer);
				}
			}
			if (activePayerType.getCode().equals(PayerKindForm.OTHER.getCode())) {
				PayerTypeForm payer = new PayerTypeForm();
				payer.setCode(activePayerType.getCode());
				payer.setDescription("payment.method.type.other");
				payer.setPayerTypeCode(activePayerType.getCode());
				payerTypes.add(payer);
			}
		}

		return payerTypes;
	}

	@Override
	public List<RepresentativeForm> getUserDataRepresentatives() {
		ArrayList<RepresentativeForm> userData = new ArrayList<RepresentativeForm>();
		for (RepresentativeForm form : representatives) {
			if (form.getCurrentUserIndicator()) {
				userData.add(form);
			}
		}
		return userData;
	}

	@Override
	public List<ApplicantForm> getUserDataApplicants() {
		ArrayList<ApplicantForm> userData = new ArrayList<ApplicantForm>();
		for (ApplicantForm form : applicants) {
			if (form.getCurrentUserIndicator()) {
				userData.add(form);
			}
		}
		return userData;
	}

	@Override
	public List<RepresentativeForm> getAddedRepresentatives() {
		ArrayList<RepresentativeForm> added = new ArrayList<RepresentativeForm>();
		for (RepresentativeForm form : representatives) {
			if (!form.getCurrentUserIndicator()) {
				added.add(form);
			}
		}
		return added;
	}

	@Override
	public List<ApplicantForm> getAddedApplicants() {
		ArrayList<ApplicantForm> added = new ArrayList<ApplicantForm>();
		for (ApplicantForm form : applicants) {
			if (!form.getCurrentUserIndicator()) {
				added.add(form);
			}
		}
		return added;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends AbstractForm> Integer getIndex(Class<T> commandClass, String id) {
		List<T> myCol = (List<T>) map.get(commandClass);
		Iterator<T> iter = myCol.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			T obj = iter.next();
			String iteratedid = obj.getId();
			if (iteratedid.equals(id)) {
				return i;
			}
		}
		return null;
	}

	@Override
	public void clearAssignees() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	public List<AssigneeForm> getAddedAssignees() {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	@Override
	public List<AssigneeForm> getUserDataAssignees() {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	@Override
	public List<AssigneeForm> getAssignees() {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	/**
	 * @return the comment
	 */
	@Override
	public String getComment() {
		return comment;
	}

	public List<MarkViewForm> getMarkViews() {
		return markViews;
	}

	public void setMarkViews(List<MarkViewForm> markViews) {
		this.markViews = markViews;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<PersonForm> getPersons() {
		List<PersonForm> personsForm = new ArrayList<PersonForm>();
		for (PersonForm form : this.getRepresentatives()) {
			personsForm.add(form);
		}
		for (PersonForm form : this.getApplicants()) {
			personsForm.add(form);
		}
		return personsForm;
	}

	public Boolean getCertificateRequestedIndicator() {
		return certificateRequestedIndicator;
	}

	public void setCertificateRequestedIndicator(Boolean certificateRequestedIndicator) {
		this.certificateRequestedIndicator = certificateRequestedIndicator;
	}

	public boolean isFastTrackStarted() {
		return fastTrackStarted;
	}

	public void setFastTrackStarted(boolean fastTrackStarted) {
		this.fastTrackStarted = fastTrackStarted;
	}

	public Boolean getFastTrack() {
		return fastTrack;
	}

	public void setFastTrack(Boolean fastTrack) {
		this.fastTrack = fastTrack;
	}

	public Boolean getWillPayOnline() {
		return willPayOnline;
	}

	public void setWillPayOnline(Boolean willPayOnline) {
		this.willPayOnline = willPayOnline;
	}

	public List<ForeignRegistrationForm> getForeignRegistrations() {
		return foreignRegistrations;
	}

	public void setForeignRegistrations(List<ForeignRegistrationForm> foreignRegistrations) {
		this.foreignRegistrations = foreignRegistrations;
	}
}
