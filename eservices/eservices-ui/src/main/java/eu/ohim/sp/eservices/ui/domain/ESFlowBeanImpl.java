package eu.ohim.sp.eservices.ui.domain;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.patent.MarketPermissionForm;
import eu.ohim.sp.common.ui.form.userdoc.UserdocForm;
import eu.ohim.sp.eservices.ui.util.PersonChangeUtil;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import eu.ohim.sp.common.ui.form.opposition.OpposedTradeMarkForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.form.person.ChangeEmployeeRepresentativeForm;
import eu.ohim.sp.common.ui.form.person.ChangeLegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.ChangeProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.ChangeRepresentativeAddressForm;
import eu.ohim.sp.common.ui.form.person.ChangeRepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.ChangeRepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.ChangeRepresentativeNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.form.person.HolderLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.HolderNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.securitymeasure.SecurityMeasureForm;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds;
import eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions;

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ESFlowBeanImpl extends CommonSpFlowBean implements ESFlowBean, Serializable {

	private FileWrapper otherAttachments;

	private List<HolderForm> holders;

	/**
	 * Form used to encapsulate the trade mark information related to the
	 * eServices
	 */
	private List<TMDetailsForm> tmsList;

	private List<ESDesignDetailsForm> dssList;
	private List<ESDesignDetailsForm> dssTempList;
	private List<LocarnoAbstractForm> locarno;
	private FileWrapper designRepresentationAttachment;
	private List<LocarnoClass> locarnoClasses;
	private List<LocarnoClass> locarnoSubclasses;
	private Map<String, LocarnoClassification> locarnoClassifications;

	private List<ESPatentDetailsForm> patentsList;

	private List<AssigneeForm> userAssigneesForm;
	private List<HolderForm> userHoldersForm;

	private List<ApplicationCAForm> correspondanceAddresses;

	private List<OpposedTradeMarkForm> tmosList;

	private List<OppositionBasisForm> obsList;

	private OpposedTradeMarkForm opposedTradeMark;

	private OppositionBasisForm oppositionBasis;

	private List<String> reputationCountries;

	private List<LegalActVersion> avaibleLegalActVersions;

	private String idReservePayment;

	private List<UserdocForm> fetchedUserdocs;
	private boolean fetchedUserdocsError;
	private UserdocForm selectedUserdoc;
	private String userdocRelationRestriction;
	private String userdocMainObject;
	private Boolean relateRequestToObject;

	/**
	 * This is a TEMPORAL object to populate the goods and services table from a
	 * specific Trademark. The trademarks information related to the application
	 * is in the @tmsList attribute.
	 */
	private Set<GoodAndServiceForm> goodsServices;

	/**
	 * This is a TEMPORAL object to keep the original goods and services from a
	 * specific Trademark. Its going to be put into the trademark once this is
	 * added to the @tmsList.
	 */
	private Set<GoodAndServiceForm> originalGS;

	/**
	 * This is a TEMPORAL object to keep the owners added manually to a
	 * trademark. Its going to be put into the trademark once this is added to
	 * the @tmsList.
	 */
	private List<ApplicantForm> owners;

	/**
	 * Additional information field
	 */
	private String comment;
	private Integer pagesCountAttachment;

	private Boolean holderIsInventor;
	private Boolean partialInvalidity;

	private MarketPermissionForm marketPermission;

	private Boolean processInitiatedBeforePublication;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The MainForm
	 */
	private ESMainForm mainForm;

	private String reference;

	/**
	 * The first lang selected by user stored in session.
	 */
	private String firstLang;

	/**
	 * Second lang selected by user stored in session.
	 */
	private String secLang;

	private boolean readOnly; // when true, the flowbean cannot be modified

	private List<LicenceForm> licences;

	private List<GSHelperForm> gsHelpers;

	private List<AppealForm> appeals;

	private SecurityMeasureForm securityMeasure;

	private String changeType;


    private List<RepresentativeForm> tmpImportRepresentativesList;

	/**
	 * The collection of stored person changes in the session
	 */
	private List<RepresentativeForm> personChanges;
	
	private static final transient Logger logger = Logger.getLogger(ESFlowBeanImpl.class);

	/**
	 * Default constructor for FlowBeanImpl Initializes the collections
	 */
	public ESFlowBeanImpl() {
		init();
	}

	private void init() {
		id = null;
		mainForm = new ESMainForm();
		otherAttachments = new FileWrapper();
		goodsServices = new TreeSet<GoodAndServiceForm>();
		opposedTradeMark = new OpposedTradeMarkForm();
		oppositionBasis = new OppositionBasisForm();
		originalGS = new TreeSet<GoodAndServiceForm>();
		reputationCountries = new ArrayList<String>();
		avaibleLegalActVersions = new ArrayList<LegalActVersion>();
		locarnoClasses = new ArrayList<LocarnoClass>();
		locarnoSubclasses = new ArrayList<LocarnoClass>();
		securityMeasure = new SecurityMeasureForm();
		gsHelpers = new ArrayList<>();
		dssTempList = new ArrayList<ESDesignDetailsForm>();
		personChanges = new ArrayList<RepresentativeForm>();
		marketPermission = new MarketPermissionForm();
		selectedUserdoc = null;
		userdocRelationRestriction = null;
		relateRequestToObject = null;

		clearHolders();
		clearTMs();
		clearDSs();
		clearLocarno();
		clearOpposedTMs();
		clearOBs();
		clearApplicationCA();
		clearLicences();
		clearGsHelpers();
		clearAppeals();
		clearPersonChanges();
		clearPatentsList();
		clearFetchedUserdocs();

		partialInvalidity = false;

	}

	/**
	 * Initializes the entities in the session
	 */
	public void clear() {
		super.clear();
		init();
	}

	public void clearPatentsList(){
		patentsList = new ArrayList<>();
		map.put(ESPatentDetailsForm.class, patentsList);
	}

	public void clearPersonChanges() {
		personChanges = new ArrayList<>();
		map.put(ChangeEmployeeRepresentativeForm.class, personChanges);
		map.put(ChangeLegalPractitionerForm.class, personChanges);
		map.put(ChangeProfessionalPractitionerForm.class, personChanges);
		map.put(ChangeRepresentativeAssociationForm.class, personChanges);
		map.put(ChangeRepresentativeLegalEntityForm.class, personChanges);
		map.put(ChangeRepresentativeNaturalPersonForm.class, personChanges);
        map.put(ChangeRepresentativeAddressForm.class, personChanges);
	}

	public void clearAppeals() {
		appeals = new ArrayList<>();
		map.put(AppealForm.class, appeals);
	}

	public void clearApplicationCA() {
		correspondanceAddresses = new ArrayList<ApplicationCAForm>();
		map.put(ApplicationCAForm.class, correspondanceAddresses);
	}

	public void clearLicences() {
		licences = new ArrayList<LicenceForm>();
		map.put(LicenceForm.class, licences);
	}

	public void clearGsHelpers() {
		gsHelpers = new ArrayList<>();
		map.put(GSHelperForm.class, gsHelpers);
	}

	public void clearUserAssigneesForm() {
		userAssigneesForm = new ArrayList<AssigneeForm>();
		map.put(AssigneeForm.class, userAssigneesForm);
	}

	public void clearUserHoldersForm() {
		userHoldersForm = new ArrayList<HolderForm>();
		map.put(HolderForm.class, userHoldersForm);
	}

	public void clearTMs() {
		tmsList = new ArrayList<TMDetailsForm>();
		map.put(TMDetailsForm.class, tmsList);
	}

	public void clearDSs() {
		dssList = new ArrayList<ESDesignDetailsForm>();
		map.put(ESDesignDetailsForm.class, dssList);
	}

	public void clearLocarno() {
		locarno = new ArrayList<LocarnoAbstractForm>();
		map.put(LocarnoForm.class, locarno);
		map.put(LocarnoComplexForm.class, locarno);
		map.put(LocarnoAbstractForm.class, locarno);
	}

	public void clearDesignRepresentationAttachment() {
		designRepresentationAttachment = null;
	}

	public void clearOpposedTMs() {
		tmosList = new ArrayList<OpposedTradeMarkForm>();
		map.put(OpposedTradeMarkForm.class, tmosList);
	}

	public void clearOBs() {
		obsList = new ArrayList<OppositionBasisForm>();
		map.put(OppositionBasisForm.class, obsList);
	}

	public void clearHolders() {
		holders = new ArrayList<HolderForm>();
		map.put(HolderLegalEntityForm.class, holders);
		map.put(HolderNaturalPersonForm.class, holders);
		map.put(HolderForm.class, holders);
	}

	public void clearFetchedUserdocs(){
		fetchedUserdocs = new ArrayList<>();
		map.put(UserdocForm.class, fetchedUserdocs);
	}


	@Override
	public MainForm getMainForm() {
		return mainForm;
	}

	@Override
	public FileWrapper getOtherAttachments() {
		return otherAttachments;
	}

	public List<HolderForm> getNoUserDataHolders() {
        ArrayList<HolderForm> userData = new ArrayList<HolderForm>();
        for (HolderForm form : holders) {
            if (!form.getCurrentUserIndicator()) {
                userData.add(form);
            }
        }
        return userData;
    }
	
    public List<HolderForm> getUserDataHolders() {
        ArrayList<HolderForm> userData = new ArrayList<HolderForm>();
        for (HolderForm form : holders) {
            if (form.getCurrentUserIndicator()) {
                userData.add(form);
            }
        }
        return userData;
    }

    public List<HolderForm> getHolders() {
    	return holders;
    }

    public List<RepresentativeForm> getAddedPersonChanges() {
        ArrayList<RepresentativeForm> added = new ArrayList<RepresentativeForm>();
        for (RepresentativeForm form : personChanges) {
            if (!form.getCurrentUserIndicator()) {
                added.add(form);
            }
        }
        return added;
    }

    public List<RepresentativeForm> getPersonChanges() {
        return personChanges;
    }

    public void setPersonChanges(List<RepresentativeForm> personChanges) {
        this.personChanges = personChanges;
    }

    @Override
	public void setOtherAttachments(FileWrapper otherAttachments) {
		this.otherAttachments = otherAttachments;
	}

	public List<TMDetailsForm> getTmsList() {
		return tmsList;
	}

	public void setTmsList(List<TMDetailsForm> tmsList) {
		this.tmsList = tmsList;
	}

	public List<ESDesignDetailsForm> getDssList() {
		return dssList;
	}

	public void setDssList(List<ESDesignDetailsForm> dssList) {
		this.dssList = dssList;
	}

	public List<ESDesignDetailsForm> getDssTempList() {
		return dssTempList;
	}

	public void setDssTempList(List<ESDesignDetailsForm> dssTempList) {
		this.dssTempList = dssTempList;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getPagesCountAttachment() {
		return pagesCountAttachment;
	}

	public void setPagesCountAttachment(Integer pagesCountAttachment) {
		this.pagesCountAttachment = pagesCountAttachment;
	}

	public Boolean getHolderIsInventor() {
		return holderIsInventor;
	}

	public void setHolderIsInventor(Boolean holderIsInventor) {
		this.holderIsInventor = holderIsInventor;
	}

	public Boolean getPartialInvalidity() {
		return partialInvalidity;
	}

	public void setPartialInvalidity(Boolean partialInvalidity) {
		this.partialInvalidity = partialInvalidity;
	}

	public MarketPermissionForm getMarketPermission() {
		return marketPermission;
	}

	public void setMarketPermission(MarketPermissionForm marketPermissionForm) {
		this.marketPermission = marketPermissionForm;
	}

	public Boolean getProcessInitiatedBeforePublication() {
		return processInitiatedBeforePublication;
	}

	public void setProcessInitiatedBeforePublication(Boolean processInitiatedBeforePublication) {
		this.processInitiatedBeforePublication = processInitiatedBeforePublication;
	}

	public Set<GoodAndServiceForm> getGoodsServices() {
		return goodsServices;
	}

	public void setGoodsServices(Set<GoodAndServiceForm> goodsServices) {
		this.goodsServices = goodsServices;
	}

	public Set<GoodAndServiceForm> getOriginalGS() {
		return originalGS;
	}

	public void setOriginalGS(Set<GoodAndServiceForm> originalGS) {
		this.originalGS = originalGS;
	}

	public List<ApplicantForm> getOwners() {
		return owners;
	}

	public void setOwners(List<ApplicantForm> owners) {
		this.owners = owners;
	}

	public String getFirstLang() {
		return firstLang;
	}

	public void setFirstLang(String firstLang) {
		this.firstLang = firstLang;
	}

	public String getSecLang() {
		return secLang;
	}

	public void setSecLang(String secLang) {
		this.secLang = secLang;
	}

	public void addGoodAndService(GoodAndServiceForm gs) {
		try {
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

	@Override
	public ApplicantForm getApplicant(String name, String domicile) {
		ApplicantForm applicantForm = new ApplicantForm(name, domicile);
		for (ApplicantForm owner : owners) {
			if (owner.getName().equalsIgnoreCase(applicantForm.getName())
					&& owner.getDomicile().equalsIgnoreCase(applicantForm.getDomicile())) {
				return owner;
			}
		}
		return null;
	}

	/**
	 * Clear the Goods and Services
	 */
	@Override
	public void clearGandS() {
		goodsServices = new TreeSet<GoodAndServiceForm>();
	}

	/**
	 * @return the opposedTradeMarkForm
	 */
	public OpposedTradeMarkForm getOpposedTradeMark() {
		return opposedTradeMark;
	}

	/**
	 * @param opposedTradeMark
	 *            the opposedTradeMarkForm to set
	 */
	public void setOpposedTradeMark(OpposedTradeMarkForm opposedTradeMark) {
		this.opposedTradeMark = opposedTradeMark;
	}

	/**
	 * @return the tmosList
	 */
	public List<OpposedTradeMarkForm> getTmosList() {
		return tmosList;
	}

	/**
	 * @param tmosList
	 *            the tmosList to set
	 */
	public void setTmosList(List<OpposedTradeMarkForm> tmosList) {
		this.tmosList = tmosList;
	}

	/**
	 * @return the obsList
	 */
	public List<OppositionBasisForm> getObsList() {
		return obsList;
	}

	/**
	 * @param obsList
	 *            the obsList to set
	 */
	public void setObsList(List<OppositionBasisForm> obsList) {
		this.obsList = obsList;
	}

	/**
	 * @return the oppositionBasis
	 */
	public OppositionBasisForm getOppositionBasis() {
		return oppositionBasis;
	}

	/**
	 * @param oppositionBasis
	 *            the oppositionBasis to set
	 */
	public void setOppositionBasis(OppositionBasisForm oppositionBasis) {
		this.oppositionBasis = oppositionBasis;
	}

	/**
	 * @return the reputationCountries
	 */
	public List<String> getReputationCountries() {
		return reputationCountries;
	}

	/**
	 * @param reputationCountries
	 *            the reputationCountries to set
	 */
	public void setReputationCountries(List<String> reputationCountries) {
		this.reputationCountries = reputationCountries;
	}

	public void addReputationCountry(String reputationCountry) {
		this.reputationCountries.add(reputationCountry);
	}

	public void removeReputationCountry(String reputationCountry) {
		this.reputationCountries.remove(reputationCountry);
	}

	/**
	 * @return the avaibleLegalActVersions
	 */
	public List<LegalActVersion> getAvaibleLegalActVersions() {
		return avaibleLegalActVersions;
	}

	/**
	 * @param avaibleLegalActVersions
	 *            the avaibleLegalActVersions to set
	 */
	public void setAvaibleLegalActVersions(List<LegalActVersion> avaibleLegalActVersions) {
		this.avaibleLegalActVersions = avaibleLegalActVersions;
	}

	public int getAbsoluteGroundsCount() {
		int count = 0;
		for (int x = 0; x < avaibleLegalActVersions.size(); x++) {
			if (avaibleLegalActVersions.get(x).getGroundCategory().equals(GroundCategoryKindLegalAct.ABSOLUTE_GROUNDS)
					|| avaibleLegalActVersions.get(x).getGroundCategory().equals(GroundCategoryKindLegalAct.BOTH)) {
				count++;
			}
		}
		return count;
	}

	public int getRelativeGroundsCount() {
		int count = 0;
		for (int x = 0; x < avaibleLegalActVersions.size(); x++) {
			if (avaibleLegalActVersions.get(x).getGroundCategory().equals(GroundCategoryKindLegalAct.RELATIVE_GROUNDS)
					|| avaibleLegalActVersions.get(x).getGroundCategory().equals(GroundCategoryKindLegalAct.BOTH)) {
				count++;
			}
		}
		return count;
	}

	public int getRevocationGroundsCount() {
		int count = 0;
		for (int x = 0; x < avaibleLegalActVersions.size(); x++) {
			if (avaibleLegalActVersions.get(x).getGroundCategory()
					.equals(GroundCategoryKindLegalAct.REVOCATION_GROUNDS)) {
				count++;
			}
		}
		return count;
	}

	public boolean isNonUsePeriod(int nonUsePeriod) {
		boolean result = false;
		if (tmsList != null && tmsList.size() > 0) {
			Date registrationDate = tmsList.get(0).getRegistrationDate();
			if (registrationDate != null) {
				Calendar cal = new GregorianCalendar();
				cal.setTimeInMillis(registrationDate.getTime());
				cal.add(Calendar.YEAR, nonUsePeriod);
				registrationDate = new Date(cal.getTimeInMillis());
				if (registrationDate.compareTo(new Date()) > 0) {
					result = true;

				}

			}

		}

		return result;
	}

	public void refreshRelativeGroundsNoFilter(ConfigurationServiceDelegatorInterface configurationService,
			String applicationType, LegalActVersionFactory legalActVersionFactory) {
		List<RelativeGrounds.RelativeGround> relativeGrounds = configurationService
				.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM,
						RelativeGrounds.class)
				.getRelativeGround();
		List<LegalActVersions.LegalActVersion> legalActVersions = configurationService
				.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM,
						LegalActVersions.class)
				.getLegalActVersion();
		List<String> codesFiltered = new ArrayList<String>();
		List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filteredCommon = new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();
		for (RelativeGrounds.RelativeGround itemRe : relativeGrounds) {
			if (itemRe.getApplicationType().equals(applicationType)) {
				if (itemRe.isEnabled()) {
					if (!codesFiltered.contains(itemRe.getLegalActVersion())) {
						codesFiltered.add(itemRe.getLegalActVersion());
					}
				}
			}
		}
		for (LegalActVersions.LegalActVersion itemLa : legalActVersions) {

			if (codesFiltered.contains(itemLa.getCode())) {
				if (itemLa.isEnabled()) {
					// TODO filtered must be List of LegalActVersion core
					// object.
					eu.ohim.sp.common.ui.form.opposition.LegalActVersion legalActAvaible = legalActVersionFactory
							.convertToLegalActUI(itemLa, applicationType);
					legalActAvaible.setGroundCategory(GroundCategoryKindLegalAct.RELATIVE_GROUNDS);
					if (!filteredCommon.contains(legalActAvaible)) {
						filteredCommon.add(legalActAvaible); // end filtered
																// must be List
																// of
																// LegalActVersion
																// core object.
					}
				}
			}
		}
		setAvaibleLegalActVersions(filteredCommon);

	}

	public boolean isExistsAvaibleLegalActVersions() {
		boolean result = false;
		if (getAvaibleLegalActVersions() != null && getAvaibleLegalActVersions().size() > 0) {
			result = true;
		}
		return result;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public boolean isExistsNiceClass(String originalGSClassId, Set<GoodAndServiceForm> limitedGS) {
		if (limitedGS != null) {
			for (GoodAndServiceForm limitedGSRow : limitedGS) {
				if (limitedGSRow.getClassId().equalsIgnoreCase(originalGSClassId)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isExistsTerm(String originalGSClassId, String originalGSTerm, Set<GoodAndServiceForm> limitedGS) {
		if (limitedGS != null) {
			for (GoodAndServiceForm limitedGSRow : limitedGS) {
				if (limitedGSRow.getClassId().equalsIgnoreCase(originalGSClassId)) {
					for (TermForm term : limitedGSRow.getTermForms()) {
						if (term.getDescription().equalsIgnoreCase(originalGSTerm)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isContainsRemovedTerm(String originalGSClassId, Set<GoodAndServiceForm> originalGS,
			Set<GoodAndServiceForm> limitedGS) {
		if (limitedGS != null) {
			for (GoodAndServiceForm limitedGSRow : limitedGS) {
				if (limitedGSRow.getClassId().equalsIgnoreCase(originalGSClassId)) {
					for (GoodAndServiceForm originalGSRow : originalGS) {
						if (originalGSRow.getClassId().equalsIgnoreCase(originalGSClassId)) {
							if (originalGSRow.getTermForms() != null && limitedGSRow.getTermForms() != null
									&& originalGSRow.getTermForms().size() != limitedGSRow.getTermForms().size()) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public Set<GoodAndServiceForm> getRemovedGS(GSHelperForm gsHelper) {
		Set<GoodAndServiceForm> result = new TreeSet<>();
		if(gsHelper == null || gsHelper.getTmApplicationNumber().isEmpty()){
			return result;
		}
		TMDetailsForm theTM = null;
		for(TMDetailsForm tmForm: getTmsList()){
			if(tmForm.getApplicationNumber() != null && tmForm.getApplicationNumber().equals(gsHelper.getTmApplicationNumber())){
				theTM = tmForm;
				break;
			}
		}
		if(theTM == null){
			return result;
		}

		Set<GoodAndServiceForm> originalGS = theTM.getOriginalGS();
		Set<GoodAndServiceForm> remainingGS = gsHelper.getGoodsAndServices();
		for(GoodAndServiceForm orGS: originalGS){
			boolean oFound = false;
			for(GoodAndServiceForm remGS: remainingGS){
				if(orGS.equals(remGS)){
					oFound = true;
					GoodAndServiceForm toAdd = null;
					try {
						toAdd = orGS.clone();
						toAdd.getTermForms().removeAll(remGS.getTermForms());
					} catch (CloneNotSupportedException e) {
						throw new SPException("Failed to find duplicate object", e, "error.genericError");
					}
					if(toAdd != null && !toAdd.getTermForms().isEmpty()){
						result.add(toAdd);
					}
					break;
				}
			}
			if(!oFound){
				result.add(orGS);
			}
		}
		return result;
	}

	public String getIdReservePayment() {
		return idReservePayment;
	}

	public void setIdReservePayment(String idReservePayment) {
		this.idReservePayment = idReservePayment;
	}

	/**
	 * @return the locarno
	 */
	public List<LocarnoAbstractForm> getLocarno() {
		return locarno;
	}

	/**
	 * @param locarno
	 *            the locarno to set
	 */
	public void setLocarno(List<LocarnoAbstractForm> locarno) {
		this.locarno = locarno;
	}

	/**
	 * @return the locarnoClasses
	 */
	public List<LocarnoClass> getLocarnoClasses() {
		return locarnoClasses;
	}

	/**
	 * @param locarnoClasses
	 *            the locarnoClasses to set
	 */
	public void setLocarnoClasses(List<LocarnoClass> locarnoClasses) {
		this.locarnoClasses = locarnoClasses;
	}

	/**
	 * @return the locarnoSubclasses
	 */
	public List<LocarnoClass> getLocarnoSubclasses() {
		return locarnoSubclasses;
	}

	public FileWrapper getDesignRepresentationAttachment() {
		return designRepresentationAttachment;
	}

	public void setDesignRepresentationAttachment(FileWrapper designRepresentationAttachment) {
		this.designRepresentationAttachment = designRepresentationAttachment;
	}

	/**
	 * @param locarnoSubclasses
	 *            the locarnoSubclasses to set
	 */
	public void setLocarnoSubclasses(List<LocarnoClass> locarnoSubclasses) {
		this.locarnoSubclasses = locarnoSubclasses;
	}

	/**
	 * @return the locarnoClassifications
	 */
	public Map<String, LocarnoClassification> getLocarnoClassifications() {
		return locarnoClassifications;
	}

	/**
	 * @param locarnoClassifications
	 *            the locarnoClassifications to set
	 */
	public void setLocarnoClassifications(Map<String, LocarnoClassification> locarnoClassifications) {
		this.locarnoClassifications = locarnoClassifications;
	}

	public Collection<LocarnoClassification> getLocarnoClassificationsCollection() {
		return this.locarnoClassifications.values();
	}

	/**
	 * @return the userAssigneesForm
	 */
	public List<AssigneeForm> getUserAssigneesForm() {
		return userAssigneesForm;
	}

	/**
	 * @param userAssigneesForm
	 *            the userAssigneesForm to set
	 */
	public void setUserAssigneesForm(List<AssigneeForm> userAssigneesForm) {
		this.userAssigneesForm = userAssigneesForm;
	}

	/**
	 * @return the userHoldersForm
	 */
	public List<HolderForm> getUserHoldersForm() {
		return userHoldersForm;
	}

	/**
	 * @param userHoldersForm
	 *            the userHoldersForm to set
	 */
	public void setUserHoldersForm(List<HolderForm> userHoldersForm) {
		this.userHoldersForm = userHoldersForm;
	}

	@Override
	public List<ApplicationCAForm> getCorrespondanceAddresses() {
		return correspondanceAddresses;
	}

	@Override
	public void setCorrespondanceAddresses(List<ApplicationCAForm> correspondanceAddresses) {
		this.correspondanceAddresses = correspondanceAddresses;
	}

	@Override
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

	@Override
	public List<PersonForm> getTmOwnersAndRepresentatives(){
		List<PersonForm> personsForm = new ArrayList<PersonForm>();
		for (PersonForm form : this.getRepresentatives()) {
			personsForm.add(form);
		}
		fillApplicantsFromTM(personsForm);
		return personsForm;
	}

	@Override
	public List<PersonForm> getDsOwnersAndRepresentatives(){
		List<PersonForm> personsForm = new ArrayList<PersonForm>();
		for (PersonForm form : this.getRepresentatives()) {
			personsForm.add(form);
		}
		if(this.getDssList() != null && !this.getDssList().isEmpty()){
			ESDesignDetailsForm esDsDetails = this.getDssList().get(0);
			if(esDsDetails != null && esDsDetails.geteSDesignApplicationData() != null && esDsDetails.geteSDesignApplicationData().getHolders() != null){
				for(PersonForm appl: esDsDetails.geteSDesignApplicationData().getHolders()){
					personsForm.add(appl);
				}
			}
		}
		return personsForm;
	}

	@Override
	public List<PersonForm> getPtOwnersAndRepresentatives(){
		List<PersonForm> personsForm = new ArrayList<PersonForm>();
		fillApplicantsFormPT(personsForm);
		for (PersonForm form : this.getRepresentatives()) {
			personsForm.add(form);
		}
		return personsForm;
	}

	@Override
	public List<PersonForm> getChangeCAPersons() {
		List<PersonForm> personsForm = new ArrayList<PersonForm>();

		fillApplicantsFromDS(personsForm);
		fillApplicantsFromTM(personsForm);
		fillApplicantsFormPT(personsForm);

		fillRepresentativesFormDS(personsForm);
		fillRepresentativesFormTM(personsForm);
		fillRepresentativesFormPT(personsForm);

		List<ApplicantForm> appApplicants = getApplicants();
		appApplicants.stream().forEach(appApplicant -> {
			if(!personsForm.stream().filter(person -> person.getId() != null && person.getId().equals(appApplicant.getId())).findFirst().isPresent()){
				personsForm.add(appApplicant);
			}
		});

		if (Objects.nonNull(getUserRepresentativesForm()) && getUserRepresentativesForm().size() > 0){
			if(!personsForm.stream().filter(person -> person.getId() != null && person.getId().equals(getUserRepresentativesForm().get(0).getId())).findFirst().isPresent()){
				personsForm.add(getUserRepresentativesForm().get(0));
			}
		}

		return personsForm;
	}

	private void fillApplicantsFormPT(List<PersonForm> personsForm) {
		if(this.getPatentsList() != null && !this.getPatentsList().isEmpty()){
			for(ESPatentDetailsForm ptForm: this.getPatentsList()){
				if(ptForm.getApplicants() != null){
					for(PersonForm appl: ptForm.getApplicants()){
						personsForm.add(appl);
					}
				}
			}
		}
	}

	private void fillApplicantsFromTM(List<PersonForm> personsForm) {
		if(this.getTmsList() != null && !this.getTmsList().isEmpty()){
			for(TMDetailsForm tmForm: this.getTmsList()){
				if(tmForm.getApplicants() != null){
					for(PersonForm appl: tmForm.getApplicants()){
						personsForm.add(appl);
					}
				}
			}
		}
	}

	private void fillApplicantsFromDS(List<PersonForm> personsForm){
		if (this.getDssList() != null && !this.getDssList().isEmpty()) {
			ESDesignDetailsForm esDsDetails = this.getDssList().get(0);
			if (esDsDetails != null && esDsDetails.geteSDesignApplicationData() != null && esDsDetails.geteSDesignApplicationData().getHolders() != null) {
				for (PersonForm appl : esDsDetails.geteSDesignApplicationData().getHolders()) {
					personsForm.add(appl);
				}
			}
		}
	}

	private void fillRepresentativesFormPT(List<PersonForm> personsForm) {
		if(this.getPatentsList() != null && !this.getPatentsList().isEmpty()){
			for(ESPatentDetailsForm ptForm: this.getPatentsList()){
				if(ptForm.getRepresentatives() != null){
					for(PersonForm repr: ptForm.getRepresentatives()){
						personsForm.add(repr);
					}
				}
			}
		}
	}

	private void fillRepresentativesFormTM(List<PersonForm> personsForm) {
		if(this.getTmsList() != null && !this.getTmsList().isEmpty()){
			for(TMDetailsForm tmForm: this.getTmsList()){
				if(tmForm.getRepresentatives() != null){
					for(PersonForm repr: tmForm.getRepresentatives()){
						personsForm.add(repr);
					}
				}
			}
		}
	}

	private void fillRepresentativesFormDS(List<PersonForm> personsForm){
		if (this.getDssList() != null && !this.getDssList().isEmpty()) {
			ESDesignDetailsForm esDsDetails = this.getDssList().get(0);
			if (esDsDetails != null && esDsDetails.geteSDesignApplicationData() != null && esDsDetails.geteSDesignApplicationData().getRepresentatives() != null) {
				for (PersonForm repr : esDsDetails.geteSDesignApplicationData().getRepresentatives()) {
					personsForm.add(repr);
				}
			}
		}
	}

	public SecurityMeasureForm getSecurityMeasure() {
		return securityMeasure;
	}

	public void setSecurityMeasure(SecurityMeasureForm securityMeasure) {
		this.securityMeasure = securityMeasure;
	}

	public List<AppealForm> getAppeals() {
		return appeals;
	}

	public void setAppeals(List<AppealForm> appeals) {
		this.appeals = appeals;
	}

	public List<GSHelperForm> getGsHelpers() {
		return gsHelpers;
	}

	public void setGsHelpers(List<GSHelperForm> gsHelpers) {
		this.gsHelpers = gsHelpers;
	}

	public List<LicenceForm> getLicences() {
		return licences;
	}

	public void setLicences(List<LicenceForm> licences) {
		this.licences = licences;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public List<ESPatentDetailsForm> getPatentsList() {
		return patentsList;
	}

	public void setPatentsList(List<ESPatentDetailsForm> patentsList) {
		this.patentsList = patentsList;
	}

	@Override
	public List<ApplicantForm> getTmOwners(){
		List<ApplicantForm> owners = new ArrayList<>();

		if(this.getTmsList().size() >0 && this.getTmsList().get(0) != null
				&& this.getTmsList().get(0).getApplicants() != null
				&& this.getTmsList().get(0).getApplicants().size() >0){
			for(ApplicantForm owner: this.getTmsList().get(0).getApplicants()) {
				owners.add(owner);
			}
		}

		return owners;
	}

    @Override
    public List<RepresentativeForm> getTmpImportRepresentativesList() {
        return tmpImportRepresentativesList;
    }

    @Override
    public void setTmpImportRepresentativesList(List<RepresentativeForm> tmpImportRepresentativesList) {
        this.tmpImportRepresentativesList = tmpImportRepresentativesList;
    }

    @Override
    public int getApplicantsCount(){
        return getApplicants().size();
    }

    public Map<String, List<RepresentativeForm>> getImportedRepresentatives() {
        return PersonChangeUtil.getImportedRepresentatives(this);
    }
    public RepresentativeForm getImportedRepresentative(String id) {
        return PersonChangeUtil.getImportedRepresentative(this, id);
    }
    public Integer countOfRemainRepresentatives(String representativeId) {
        return PersonChangeUtil.countOfRemainRepresentatives(this, representativeId);
    }

    public boolean getUnpublishedAppImported(){
		if(tmsList != null && tmsList.size()>0){
			return tmsList.get(0).getUnpublished() != null ? tmsList.get(0).getUnpublished() : false;
		} else if(dssList != null && dssList.size()>0){
			return dssList.get(0).getUnpublished() != null ? dssList.get(0).getUnpublished() : false;
		} else if(patentsList != null && patentsList.size()>0){
			return patentsList.get(0).getUnpublished() != null ? patentsList.get(0).getUnpublished() : false;
		}
		return false;
	}

	public List<UserdocForm> getFetchedUserdocs() {
		return fetchedUserdocs;
	}

	public void setFetchedUserdocs(List<UserdocForm> fetchedUserdocs) {
		this.fetchedUserdocs = fetchedUserdocs;
	}

	public boolean getFetchedUserdocsError() {
		return fetchedUserdocsError;
	}

	public void setFetchedUserdocsError(boolean fetchedUserdocsError) {
		this.fetchedUserdocsError = fetchedUserdocsError;
	}

	public UserdocForm getSelectedUserdoc() {
		return selectedUserdoc;
	}

	public void setSelectedUserdoc(UserdocForm selectedUserdoc) {
		this.selectedUserdoc = selectedUserdoc;
	}

	public String getUserdocRelationRestriction() {
		return userdocRelationRestriction;
	}

	public void setUserdocRelationRestriction(String userdocRelationRestriction) {
		this.userdocRelationRestriction = userdocRelationRestriction;
	}

	public String getUserdocMainObject() {
		return userdocMainObject;
	}

	public void setUserdocMainObject(String userdocMainObject) {
		this.userdocMainObject = userdocMainObject;
	}

	public Boolean getRelateRequestToObject() {
		return relateRequestToObject;
	}

	public void setRelateRequestToObject(Boolean relateRequestToObject) {
		this.relateRequestToObject = relateRequestToObject;
	}

	public List<UserdocForm> getFetchedUserdocsHierarchy(){
		List<UserdocForm> intermediateResult = new ArrayList<>();
		if(fetchedUserdocs == null || fetchedUserdocs.isEmpty()){
			return intermediateResult;
		}
		intermediateResult.addAll(fetchedUserdocs.stream().map(fetched -> {
			try {
				return (UserdocForm)fetched.clone();
			} catch (CloneNotSupportedException e) {
				throw new SPException(e);
			}
		}).collect(Collectors.toList()));

		intermediateResult.forEach(ud -> {
			if(ud.getParentProcessNbr() != null && ud.getParentProcessType() != null){
				Optional<UserdocForm> parentOpt = intermediateResult.stream().filter(searchEntry -> searchEntry.getProcessNbr().equals(ud.getParentProcessNbr()) && searchEntry.getProcessType().equals(ud.getParentProcessType())).findFirst();
				if(parentOpt.isPresent()){
					parentOpt.get().getChildren().add(ud);
				}
			}
		});

		return intermediateResult.stream().filter(res -> res.getParentProcessType() == null && res.getParentProcessNbr() == null).collect(Collectors.toList());
	}

	public boolean getObjectIsNational(){
		if(tmsList != null && tmsList.size() > 0){
			Optional<TMDetailsForm> notNational = tmsList.stream().filter(tm -> tm.getRegistrationOffice() != null && !tm.getRegistrationOffice().equals("BG")).findFirst();
			if(notNational.isPresent()){
				return false;
			}
		}
		return true;
	}
}
