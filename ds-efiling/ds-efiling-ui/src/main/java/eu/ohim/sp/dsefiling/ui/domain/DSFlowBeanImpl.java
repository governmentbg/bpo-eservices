package eu.ohim.sp.dsefiling.ui.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DSFlowBeanImpl extends CommonSpFlowBean implements DSFlowBean {

	private static final long serialVersionUID = 1L;

	private static final transient Logger LOGGER = Logger.getLogger(DSFlowBeanImpl.class);

	private List<DesignForm> designs;
	private List<DesignViewForm> designViews;
	private List<LocarnoAbstractForm> locarno;
	private List<LocarnoClass> locarnoClasses;
	private List<LocarnoClass> locarnoSubclasses;
	private Map<String, LocarnoClassification> locarnoClassifications;
	private Map<String, String> designersFromApplicants;
	private List<String> warningsMessage = new ArrayList<String>();

	private FileWrapper otherAttachments;

	// TODO Remove, it's already in CommonFspFlowBean.paymentForm
	private FileWrapper payLaterAttachment;

	/**
	 * The collection of stored designers in the session
	 */
	private List<DesignerForm> designers;

	/**
	 * The MainForm
	 */
	private DSMainForm mainForm;

	/**
	 * The collection of stored divisional application in the session. By the
	 * moment there will be only one.
	 */
	private List<DSDivisionalApplicationForm> divisionalApplications;

	/**
	 * The collection of stored priorities in the session
	 */
	private List<DSPriorityForm> priorities;

	/**
	 * The collection of stored exhibition priorities in the session
	 */
	private List<DSExhPriorityForm> exhpriorities;

	private String filingNumber;

	/** Flag indicating if filling of the app started. Skips first display of the form */
	private boolean fastTrackStarted;

	/** Flag indicating if filling is fastTrack */
	private Boolean fastTrack;

	private String comment;

	private Boolean willPayOnline;

	/**
	 * Default constructor for FlowBeanImpl Initializes the collections
	 */
	public DSFlowBeanImpl() {
		id = null;
		mainForm = new DSMainForm();
		mainForm.clearInformation();
		locarnoClasses = new ArrayList<LocarnoClass>();
		locarnoSubclasses = new ArrayList<LocarnoClass>();
		otherAttachments = new FileWrapper();
		payLaterAttachment = new FileWrapper();

		designs = new ArrayList<DesignForm>();
		map.put(DesignForm.class, designs);

		designViews = new ArrayList<DesignViewForm>();
		map.put(DesignViewForm.class, designViews);
		
		locarno = new ArrayList<LocarnoAbstractForm>();
		map.put(LocarnoForm.class, locarno);
		map.put(LocarnoComplexForm.class, locarno);
		map.put(LocarnoAbstractForm.class, locarno);
		
		designers = new ArrayList<DesignerForm>();
		map.put(DesignerForm.class, designers);
		
		divisionalApplications = new ArrayList<DSDivisionalApplicationForm>();
		map.put(DSDivisionalApplicationForm.class, divisionalApplications);
		
		priorities = new ArrayList<DSPriorityForm>();
		// Necessary for common RemoveController.
		map.put(PriorityForm.class, priorities);
		map.put(DSPriorityForm.class, priorities);
		
		exhpriorities = new ArrayList<DSExhPriorityForm>();
		// Necessary for common RemoveController.
		map.put(ExhPriorityForm.class, exhpriorities);
		map.put(DSExhPriorityForm.class, exhpriorities);
		map.put(ApplicationCAForm.class, mainForm.getCorrespondanceAddresses());

		this.designersFromApplicants = new HashMap<String, String>();
		
	}

	/**
	 * Initializes the entities in the session
	 */
	@Override
	public void clear() {
		super.clear();

		id = null;
		mainForm = new DSMainForm();
		mainForm.clearInformation();
		locarnoClasses = new ArrayList<LocarnoClass>();
		locarnoSubclasses = new ArrayList<LocarnoClass>();
		otherAttachments = new FileWrapper();
		payLaterAttachment = new FileWrapper();
		fastTrackStarted = false;

		clearDesigns();
		clearDesignViews();
		clearLocarno();
		clearDesigners();
		clearDivisionalApplications();
		clearPriorities();
		clearExhibitions();
		clearApplicationCA();
		clearDesignersFromApplicants();
	
	}

	/**
	 * Clear the divisional application.
	 */
	@Override
	public void clearDivisionalApplications() {
		divisionalApplications = new ArrayList<DSDivisionalApplicationForm>();
		map.put(DSDivisionalApplicationForm.class, divisionalApplications);
	}

	/**
	 * Clear the priorities
	 */
	@Override
	public void clearPriorities() {
		priorities = new ArrayList<DSPriorityForm>();
		// Necessary for common RemoveController.
		map.put(PriorityForm.class, priorities);
		map.put(DSPriorityForm.class, priorities);
	}

	/**
	 * Clear the priorities
	 */
	@Override
	public void clearExhibitions() {
		exhpriorities = new ArrayList<DSExhPriorityForm>();
		// Necessary for common RemoveController.
		map.put(ExhPriorityForm.class, exhpriorities);
		map.put(DSExhPriorityForm.class, exhpriorities);
	}

	/**
	 * Clear the designs
	 */
	@Override
	public void clearDesigns() {
		designs = new ArrayList<DesignForm>();
		map.put(DesignForm.class, designs);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DesignForm> getDesigns() {
		return designs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDesigns(List<DesignForm> designs) {
		this.designs = designs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getDesignCount() {
		return designs != null ? designs.size() : 0;
	}

	/**
	 * Clear the designers.
	 */
	@Override
	public void clearDesigners() {
		designers = new ArrayList<DesignerForm>();
		map.put(DesignerForm.class, designers);
	}

	/**
	 * Retrieves all the stored designers.
	 * 
	 * @return All the stored designers.
	 */
	@Override
	public List<DesignerForm> getDesigners() {
		return designers;
	}

	/**
	 * Returns the main form that is stored in the session
	 * 
	 * @return the mainForm
	 */
	@Override
	public DSMainForm getMainForm() {
		return mainForm;
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
	public List<DesignerForm> getAddedDesigners() {
		ArrayList<DesignerForm> added = new ArrayList<DesignerForm>();
		for (DesignerForm form : designers) {
			if (!form.getCurrentUserIndicator()) {
				added.add(form);
			}
		}
		return added;
	}

	@Override
	public void clearLocarno() {
		locarno = new ArrayList<LocarnoAbstractForm>();
		map.put(LocarnoForm.class, locarno);
		map.put(LocarnoComplexForm.class, locarno);
		map.put(LocarnoAbstractForm.class, locarno);
	}

	@Override
	public List<LocarnoAbstractForm> getLocarno() {
		return locarno;
	}

	@Override
	public void setLocarno(List<LocarnoAbstractForm> locarno) {
		this.locarno = locarno;
	}

	@Override
	public void setLocarnoClasses(List<LocarnoClass> locarnoClasses) {
		this.locarnoClasses = locarnoClasses;
	}

	@Override
	public List<LocarnoClass> getLocarnoClasses() {
		return this.locarnoClasses;
	}

	@Override
	public void setLocarnoSubclasses(List<LocarnoClass> locarnoSubclasses) {
		this.locarnoSubclasses = locarnoSubclasses;
	}

	@Override
	public List<LocarnoClass> getLocarnoSubclasses() {
		return this.locarnoSubclasses;
	}

	@Override
	public Map<String, LocarnoClassification> getLocarnoClassifications() {
		return this.locarnoClassifications;
	}

	@Override
	public void setLocarnoClassifications(Map<String, LocarnoClassification> locarnoClassifications) {
		this.locarnoClassifications = locarnoClassifications;
	}

	@Override
	public Collection<LocarnoClassification> getLocarnoClassificationsCollection() {
		return this.locarnoClassifications.values();
	}

	/**
	 * Stores the main form
	 * 
	 * @param mainForm
	 *            the mainForm to set
	 */
	public void setMainForm(MainForm mainForm) {
		this.mainForm = (DSMainForm) mainForm;
	}

	/**
	 * Clear the applicationCA
	 */
	@Override
	public void clearApplicationCA() {
		map.put(ApplicationCAForm.class, mainForm.getCorrespondanceAddresses());
	}

	/**
	 * Clear the DesignersFromApplicants
	 */
	@Override
	public void clearDesignersFromApplicants() {
		if (this.designersFromApplicants == null) {
			this.designersFromApplicants = new HashMap<String, String>();
		}
	}

	@Override
	public List<DSPriorityForm> getPriorities() {
		return priorities;
	}
	
	@Override
	public List<ApplicationCAForm> getCorrespondanceAddresses() {
		return this.mainForm.getCorrespondanceAddresses();
	}

	public void setPriorities(List<DSPriorityForm> priorities) {
		this.priorities = priorities;
	}

	@Override
	public List<DSExhPriorityForm> getExhpriorities() {
		return exhpriorities;
	}

	public void setExhpriorities(List<DSExhPriorityForm> exhpriorities) {
		this.exhpriorities = exhpriorities;
	}

	public void setDesigners(List<DesignerForm> designers) {
		this.designers = designers;
	}

	@Override
	public void setMainForm(DSMainForm mainForm) {
		this.mainForm = mainForm;
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
	public String getFilingNumber() {
		return filingNumber;
	}

	public void setFilingNumber(String filingNumber) {
		this.filingNumber = filingNumber;
	}

	@Override
	public Map<String, String> getDesignersFromApplicants() {
		return designersFromApplicants;
	}

	public void setDesignersFromApplicants(Map<String, String> designersFromApplicants) {
		this.designersFromApplicants = designersFromApplicants;
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
	public void clearDesignViews() {
		designViews = new ArrayList<DesignViewForm>();
		map.put(DesignViewForm.class, designViews);
	}

	@Override
	public List<DesignViewForm> getDesignViews() {
		return designViews;
	}

	@Override
	public List<DSDivisionalApplicationForm> getDivisionalApplications() {
		return divisionalApplications;
	}

	@Override
	public DSDivisionalApplicationForm getDivisionalApplication() {
		if (CollectionUtils.size(divisionalApplications) == 1) {
			return divisionalApplications.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @return the payLaterAttachment
	 */
	public FileWrapper getPayLaterAttachment() {
		return payLaterAttachment;
	}

	/**
	 * @param payLaterAttachment
	 *            the payLaterAttachment to set
	 */
	public void setPayLaterAttachment(FileWrapper payLaterAttachment) {
		this.payLaterAttachment = payLaterAttachment;
	}

	public List<String> getWarningsMessage() {
		return warningsMessage;
	}

	public void setWarningsMessage(List<String> warningsMessage) {
		this.warningsMessage = warningsMessage;
	}

	@Override
	public boolean isFastTrackStarted() {
		return fastTrackStarted;
	}

	@Override
	public void setFastTrackStarted(boolean fastTrackStarted) {
		this.fastTrackStarted = fastTrackStarted;
	}

	@Override
	public Boolean getFastTrack() {
		return fastTrack;
	}

	@Override
	public void setFastTrack(Boolean fastTrack) {
		this.fastTrack = fastTrack;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getWillPayOnline() {
		return willPayOnline;
	}

	public void setWillPayOnline(Boolean willPayOnline) {
		this.willPayOnline = willPayOnline;
	}
}
