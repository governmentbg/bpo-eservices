package eu.ohim.sp.common.ui.flow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.ohim.sp.common.ui.flow.section.*;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.person.*;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.form.payment.PayerKindForm;
import eu.ohim.sp.common.ui.form.payment.PayerTypeForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;

public abstract class CommonSpFlowBean extends AbstractFlowBeanImpl
		implements FeesFlowBean, PersonFlowBean, LanguagesFlowBean, PaymentFlowBean, FileUploadFlowBean, SignaturesFlowBean {

	private static transient final Logger logger = Logger.getLogger(CommonSpFlowBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -2586027661584718347L;
		
    /**
     * The collection of stored applicants in the session
     */
    protected List<ApplicantForm> applicants;
    /**
     * The collection of stored representatives in the session
     */
    protected List<RepresentativeForm> representatives;
    
    /**
     * The collection of stored assignees in the session
     */
    private List<AssigneeForm> assignees;

	private List<DesignerForm> designers;
    
    /**
     * The first lang selected by user stored in session.
     */
    private String firstLang;

	/**
	 * Second lang selected by user stored in session.
	 */
	private String secLang;

	/**
	 * The fee section
	 */
	private FeesForm feesForm;

	/**
	 * CTM Id reserved for the application after it was successfully saved
	 */
	private String idreserventity = null;

	/**
	 * Payment details
	 */
	private PaymentForm paymentForm;


	/**
	 * Signatures
	 */
	private List<SignatureForm> signatures;

	protected Boolean esignDocDeclaration;


	protected String currentUserName;
	protected String currentUserEmail;

	private List<RepresentativeForm> userRepresentativesForm;
	private List<ApplicantForm> userApplicantsForm;

	/**
	 * Default constructor for FlowBeanImpl Initializes the collections
	 */
	public CommonSpFlowBean() {
		init();
	}

	private void init() {
		paymentForm = new PaymentForm();
		feesForm = new FeesForm();
		firstLang = null;
		secLang = null;
		idreserventity = null;
		clearApplicants();
		clearRepresentatives();
		clearAssignees();
		clearDesigners();
		clearSignatures();
	}
	/**
	 * Initializes the entities in the session
	 */
	@Override
	public void clear() {
		super.clear();
		init();
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
		map.put(UniversityForm.class, applicants);
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
		map.put(IntlPRepresentativeForm.class, representatives);
		map.put(LawyerCompanyForm.class, representatives);
		map.put(LawyerAssociationForm.class, representatives);
		map.put(RepresentativeTemporaryForm.class, representatives);
		map.put(RepresentativeForm.class, representatives);
	}

	/**
	 * Clear the assignees
	 */
	@Override
	public void clearAssignees() {
		assignees = new ArrayList<AssigneeForm>();
		map.put(AssigneeLegalEntityForm.class, assignees);
		map.put(AssigneeNaturalPersonForm.class, assignees);
		map.put(AssigneeForm.class, assignees);
	}

	@Override
	public void clearDesigners() {
		designers = new ArrayList<DesignerForm>();
		map.put(DesignerForm.class, designers);
	}

	public void clearSignatures() {
		signatures = new ArrayList<SignatureForm>();
		map.put(SignatureForm.class, signatures);
	}


	/**
	 * Retrieves all the stored applicants
	 * 
	 * @return All the stored applicants
	 */
	@Override
	public List<ApplicantForm> getApplicants() {
		return applicants;
	}

	/**
	 * Retrieves all the stored representatives
	 * 
	 * @return All the stored representatives
	 */
	@Override
	public List<RepresentativeForm> getRepresentatives() {
		return representatives;
	}

	/**
	 * Retrieves all the stored representatives
	 * 
	 * @return All the stored representatives
	 */
	@Override
	public List<AssigneeForm> getAssignees() {
		return assignees;
	}

	@Override
	public List<DesignerForm> getDesigners() {
		return designers;
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
	 * Method that returns the idreserventity
	 * 
	 * @return the idreserventity
	 */
	@Override
	public String getIdreserventity() {
		return idreserventity;
	}

	/**
	 * Method that sets the idreserventity
	 * 
	 * @param idreserventity
	 *            the idreserventity to set
	 */
	@Override
	public void setIdreserventity(String idreserventity) {
		this.idreserventity = idreserventity;
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
	public List<AssigneeForm> getUserDataAssignees() {
		ArrayList<AssigneeForm> userData = new ArrayList<AssigneeForm>();
		for (AssigneeForm form : assignees) {
			if (form.getCurrentUserIndicator()) {
				userData.add(form);
			}
		}
		return userData;
	}

	@Override
	public List<DesignerForm> getUserDataDesigners() {
		ArrayList<DesignerForm> userData = new ArrayList<DesignerForm>();
		for (DesignerForm form : designers) {
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

	@Override
	public List<AssigneeForm> getAddedAssignees() {
		ArrayList<AssigneeForm> added = new ArrayList<AssigneeForm>();
		for (AssigneeForm form : assignees) {
			if (!form.getCurrentUserIndicator()) {
				added.add(form);
			}
		}
		return added;
	}

	@Override
	public List<DesignerForm> getAddedDesigners() {
		ArrayList<DesignerForm> added = new ArrayList<DesignerForm>();
		for (DesignerForm form : designers) {
			if (!form.getCurrentUserIndicator()) {
				added.add(form);
			}
		}
		return added;
	}

	/**
	 * Returns the total number of classes
	 * 
	 * @return int number of classes
	 */
	@Override
	public int getNumberofClasses() {
		if (getGoodsAndServices() == null)
			return 0;
		else {
			Set<String> classes = new HashSet<String>();
			for (GoodAndServiceForm good : getGoodsAndServices()) {
				classes.add(good.getClassId());
			}
			return classes.size();
		}
	}

	/**
	 * Help method to retrieve goods and services as a Map with key of type
	 * ${ClassId}_${LangId}.
	 * 
	 * @return List of GoodAndServiceForms
	 */
	public Set<GoodAndServiceForm> getGoodsAndServices() {
		return null;
	}

	@Override
	public String getFilingNumber() {
		// TODO implement
		return "";
	}

	public List<SignatureForm> getSignatures() {
		return signatures;
	}

	public void setSignatures(List<SignatureForm> signatures) {
		this.signatures = signatures;
	}

	public Boolean getEsignDocDeclaration() {
		return esignDocDeclaration;
	}

	public void setEsignDocDeclaration(Boolean esignDocDeclaration) {
		this.esignDocDeclaration = esignDocDeclaration;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getCurrentUserEmail() {
		return currentUserEmail;
	}

	public void setCurrentUserEmail(String currentUserEmail) {
		this.currentUserEmail = currentUserEmail;
	}

	/**
	 * @return the userApplicantsForm
	 */
	public List<ApplicantForm> getUserApplicantsForm() {
		return userApplicantsForm;
	}

	/**
	 * @param userApplicantsForm
	 *            the userApplicantsForm to set
	 */
	public void setUserApplicantsForm(List<ApplicantForm> userApplicantsForm) {
		this.userApplicantsForm = userApplicantsForm;
	}

	public List<RepresentativeForm> getUserRepresentativesForm() {
		return userRepresentativesForm;
	}

	public void setUserRepresentativesForm(List<RepresentativeForm> userRepresentativesForm) {
		this.userRepresentativesForm = userRepresentativesForm;
	}
}
