package eu.ohim.sp.dsefiling.ui.service;

import java.util.*;
import java.util.stream.Collectors;

import eu.ohim.sp.core.register.IPOAutocompleteSearchService;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.design.DSDivisionalApplicationFactory;
import eu.ohim.sp.common.ui.adapter.design.DSExhPriorityFactory;
import eu.ohim.sp.common.ui.adapter.design.DSPriorityFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignViewFactory;
import eu.ohim.sp.common.ui.adapter.design.ProductIndicationFactory;
import eu.ohim.sp.common.ui.adapter.design.ProductIndicationSearchFactory;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.design.ContainsDesignsLinkForm;
import eu.ohim.sp.common.ui.form.design.ContainsLocarnoForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.classification.LocarnoClassificationService;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewTypes.DesignViewType;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.core.register.IPApplicationService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * Manage the addition and removal of designs with the objects that contain them in the flowBean, like DesignerForm.
 * @author serrajo
 */
@Service(value="designsService")
public class DSDesignsService implements DSDesignsServiceInterface {
	
    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    private static final String SETTING_DESIGNS_LINK_AUTOMATICALLY_DESIGNERS = "designs.link.automatically.designers";
    private static final String SETTING_DESIGNS_LINK_AUTOMATICALLY_PRIORITIES = "designs.link.automatically.priorities";
    private static final String SETTING_DESIGNS_LINK_AUTOMATICALLY_EXHIBITIONS = "designs.link.automatically.exhPriorities";
    private static final String SETTING_DESIGNS_LINK_AUTOMATICALLY_DIVISIONA_APPLICATION = "designs.link.automatically.divisionalApplication";
    
    private static final String COMPONENT_DESIGNS_LINK_AUTOMATICALLY = "eu.ohim.sp.common.ui.form";
    
	@Autowired
	private DesignFactory designFactory;
	
	@Autowired
	private DesignViewFactory designViewFactory;	

	@Autowired
	private DSExhPriorityFactory dsExhPriorityFactory;	

	@Autowired
	private DSPriorityFactory dsPriorityFactory;	
	
	@Autowired
	private DSDivisionalApplicationFactory dsDivisionapplicationFactory;	
	
	@Autowired
	private ProductIndicationFactory productIndicationFactory;
	
	@Autowired
	private ProductIndicationSearchFactory productIndicationSearchFactory;
	
	
    @Autowired
    private DocumentFactory documentFactory;
	
	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;
	
    @Autowired
    private DesignSearchService designSearchService;
    
    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private IPApplicationService ipApplicationService;    
  
    @Autowired
    private LocarnoClassificationService locarnoClassificationService; 
    
    @Autowired
    private ContactDetailsFactory contactDetailsFactory;
    
    @Autowired
    private DSFlowBeanFactory dsFlowBeanFactory;
    
    @Value("${sp.office}")
    private String office;

	@Autowired
	private IPOAutocompleteSearchService ipoAutocompleteSearchService;

	/**
     * Returns if the application has to add automatically all the designs to a new form.
     * @return true o false.
     */
    private boolean isEnabledAddAutomaticallyDesigns(ContainsDesignsLinkForm form) {
    	String setting;
    	if (form instanceof DesignerForm) {
    		setting = SETTING_DESIGNS_LINK_AUTOMATICALLY_DESIGNERS;
    	} else if (form instanceof DSPriorityForm) {
    		setting = SETTING_DESIGNS_LINK_AUTOMATICALLY_PRIORITIES;
    	} else if (form instanceof DSExhPriorityForm) {
    		setting = SETTING_DESIGNS_LINK_AUTOMATICALLY_EXHIBITIONS;
    	} else if (form instanceof DSDivisionalApplicationForm) {
    		setting = SETTING_DESIGNS_LINK_AUTOMATICALLY_DIVISIONA_APPLICATION;
    	} else {
    		throw new SPException("Fail to find the form type");
    	}
    	return BooleanUtils.toBoolean(configurationServiceDelegator.getValue(setting, COMPONENT_DESIGNS_LINK_AUTOMATICALLY));
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void addViewsNumber(DSFlowBean flowBean) {
    	final List<DesignViewType> designViewTypes = configurationServiceDelegator.getDesignViewTypes();
		
    	// For certain offices the view type don't have to be provided and the order will be the order the user added them.    	
    	Collections.sort(flowBean.getDesignViews(), new Comparator<DesignViewForm>() {
			@Override
			public int compare(DesignViewForm view1, DesignViewForm view2) {
				int result = 0;
				if (view1.getType() != null) {
					int orderView1 = getOrderDesignView(view1);
					int orderView2 = getOrderDesignView(view2);
					result = orderView1 < orderView2 ? -1 : orderView1 > orderView2 ? 1 : 0; 
				}
				return result;
			}
			private int getOrderDesignView(DesignViewForm designView) {
		    	for (DesignViewType designViewType : designViewTypes) {
		    		if (designViewType.getCode().equals(designView.getType())) {
		    			return designViewType.getOrder();
		    		}
		    	}
		    	return 0;
		    }
		});
    	
    	int sequence = 1;
        for (DesignViewForm designViewForm : flowBean.getDesignViews()) {
        	designViewForm.setSequence(sequence++);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addDesignToLinkedLists(DesignForm designForm, DSFlowBean flowBean) {
    	addDesignToLists(designForm, flowBean, false);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDesignLinkedInSomeForm(DesignForm designForm, DSFlowBean flowBean) {
    	return isDesignLinkedInSomeFormInTheList(designForm, flowBean.getDivisionalApplications()) ||
    			isDesignLinkedInSomeFormInTheList(designForm, flowBean.getPriorities()) ||
    			isDesignLinkedInSomeFormInTheList(designForm, flowBean.getExhpriorities()) ||
    			isDesignLinkedInSomeFormInTheList(designForm, flowBean.getDesigners());
    }
    
    /**
     * 
     * @param designForm
     * @param forms
     * @return
     */
    @Override
    public boolean isDesignLinkedInSomeFormInTheList(DesignForm designForm, List<? extends ContainsDesignsLinkForm> forms) {
    	for (ContainsDesignsLinkForm form : forms) {
    		if (isDesignLinkedInForm(designForm, form)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 
     */
    @Override
    public DesignerForm getDesignerWhoWaives(List<DesignerForm> allDesigners) {
    	for (DesignerForm designer : allDesigners) {
    		if (designer.isWaiver()) {
    			return designer;
    		}
    	}
    	return null;
    }
    
    /**
     * 
     * @param designForm
     * @param form
     * @return
     */
    private boolean isDesignLinkedInForm(DesignForm designForm, ContainsDesignsLinkForm form) {
    	return isDesignInList(designForm, form.getDesignsLinked());
    }
    
    /**
     * 
     * @param designForm
     * @param designs
     * @return
     */
    private boolean isDesignInList(DesignForm designForm, List<DesignForm> designs) {
    	return designs.contains(designForm);
    }
    
    /**
     * {@inheritDoc}
     */
	@Override
	public void addDesignToLists(DesignForm design, DSFlowBean flowBean) {
		addDesignNumber(design, flowBean);
		addDesignToLists(design, flowBean, true);
	}
	
	/**
	 * 
	 * @param design
	 * @param flowBean
	 * @param checkAddAutomatically
	 */
	private void addDesignToLists(DesignForm design, DSFlowBean flowBean, boolean checkAddAutomatically) {
		addDesignToList(design, flowBean.getPriorities(), checkAddAutomatically);
		addDesignToList(design, flowBean.getExhpriorities(), checkAddAutomatically);
		addDesignToList(design, flowBean.getDivisionalApplications(), checkAddAutomatically);
		addDesignToListDesigners(design, flowBean.getDesigners(), checkAddAutomatically);
	}

	/**
	 * 
	 * @param design
	 * @param designers
	 * @param checkAddAutomatically
	 */
	private void addDesignToListDesigners(DesignForm design, List<DesignerForm> designers, boolean checkAddAutomatically) {
		int numDesigners = designers.size();
		if (numDesigners == 1) {
			addDesignToForm(design, designers.get(0), checkAddAutomatically);
		} else {
			for (DesignerForm designer : designers) {
				if (!designer.isWaiver() || !isAddToLinkedList(designer, checkAddAutomatically)) {
					// If we don't have to add the design to the linked list we can add it to all the designers, he waives or not.
					addDesignToForm(design, designer, checkAddAutomatically);
				}
			}
		}
	}
	
	/**
	 *  
	 * @param design
	 * @param forms
	 */
	private void addDesignToList(DesignForm design, List<? extends ContainsDesignsLinkForm> forms, boolean checkAddAutomatically) {
		for (ContainsDesignsLinkForm form : forms) {
			addDesignToForm(design, form, checkAddAutomatically);
		}
	}
	
	/**
	 * 
	 * @param form
	 * @param checkAddAutomatically
	 * @return
	 */
	private boolean isAddToLinkedList(ContainsDesignsLinkForm form, boolean checkAddAutomatically) {
		return !checkAddAutomatically || (checkAddAutomatically && isEnabledAddAutomaticallyDesigns(form));
	}
	
	/**
	 * 
	 * @param design
	 * @param form
	 */
	private void addDesignToForm(DesignForm design, ContainsDesignsLinkForm form, boolean checkAddAutomatically) {
		if (isAddToLinkedList(form, checkAddAutomatically)) {
			form.getDesignsLinked().add(design);
		} else {
			form.getDesignsNotLinked().add(design);
		}
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void replaceDesignInLists(DesignForm design, DSFlowBean flowBean) {
		replaceDesignInList(design, flowBean.getDesigners());
		replaceDesignInList(design, flowBean.getPriorities());
		replaceDesignInList(design, flowBean.getExhpriorities());
		replaceDesignInList(design, flowBean.getDivisionalApplications());
	}
	
	/**
	 * 
	 * @param design
	 * @param forms
	 */
	private void replaceDesignInList(DesignForm design, List<? extends ContainsDesignsLinkForm> forms) {
		for (ContainsDesignsLinkForm form : forms) {
			replaceDesignInForm(design, form);
		}
	}
	
	/**
	 * 
	 * @param design
	 * @param form
	 */
	private void replaceDesignInForm(DesignForm design, ContainsDesignsLinkForm form) {
		REPLACE: {
			for (ListIterator<DesignForm> lit = form.getDesignsLinked().listIterator(); lit.hasNext(); ) {
				if (lit.next().getId().equals(design.getId())) {
					lit.set(design);
					break REPLACE;
				}
		    }
		
			for (ListIterator<DesignForm> lit = form.getDesignsNotLinked().listIterator(); lit.hasNext(); ) {
				if (lit.next().getId().equals(design.getId())) {
					lit.set(design);
					break REPLACE;
				}
		    }
		}
	}
	  
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void removeDesignFromLists(DesignForm design, DSFlowBean flowBean) {
		refreshDesignsNumber(flowBean);
		removeDesignFromList(design, flowBean.getDesigners());
		removeDesignFromList(design, flowBean.getPriorities());
		removeDesignFromList(design, flowBean.getExhpriorities());
		removeDesignFromList(design, flowBean.getDivisionalApplications());
	}
	
	/**
	 * 
	 * @param design
	 * @param forms
	 */
	private void removeDesignFromList(DesignForm design, List<? extends ContainsDesignsLinkForm> forms) {
		for (ContainsDesignsLinkForm form : forms) {
			removeDesignFromForm(design, form);
		}
	}

	/**
	 * 
	 * @param design
	 * @param form
	 */
	private void removeDesignFromForm(DesignForm design, ContainsDesignsLinkForm form) {
		if ((form.getDesignsLinked() == null || !form.getDesignsLinked().remove(design)) && form.getDesignsNotLinked() != null) {
			form.getDesignsNotLinked().remove(design);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void fillDesignsListsFromToLink(DSFlowBean flowBean, ContainsDesignsLinkForm form) {
    	if (isEnabledAddAutomaticallyDesigns(form)) {
    		form.getDesignsLinked().addAll(flowBean.getDesigns());
    	} else {
    		form.getDesignsNotLinked().addAll(flowBean.getDesigns());
    	}
	}
	
	/**
	 * 
	 * @param designers
	 * @return
	 */
	@Override
	public boolean isThereADesignerThatWaiver(DSFlowBean flowBean) {
		for (DesignerForm otherDesignerForm : flowBean.getDesigners()) {
			if (otherDesignerForm.isWaiver()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param designerForm
	 * @param flowBean
	 */
	@Override
	public void addDesignerLinkedListToDesignersNotLinkedList(DesignerForm designerForm, DSFlowBean flowBean) {
		for (DesignForm designForm : designerForm.getDesignsLinked()) {
			addDesignToDesignersNotLinkedList(designForm, flowBean.getDesigners());
		}
	}
	
	/**
	 * 
	 * @param design
	 * @param designers
	 */
	private void addDesignToDesignersNotLinkedList(DesignForm design, List<DesignerForm> designers) {
		for (DesignerForm designer : designers) {
			designer.getDesignsNotLinked().add(design);
		}
	}

	/**
	 * 
	 * @param flowBean
	 * @param form
	 */
	@Override
	public void fillDesignerDesignsListsFromToLink(DSFlowBean flowBean, DesignerForm designerForm) {
		boolean designerMayWaive = true;
		
		
		if (designerForm.isWaiver()) {
			// The designer wants to waive, there is not any designer that waives.
			// We have to add only the designs that have not been associated yet.			
			fillDesignerDesignsWaiverListsFromToLink (flowBean, designerForm);
		} else {
			// Only can be one designer that waives.
			designerMayWaive = !isThereADesignerThatWaiver(flowBean);

			fillDesignerDesignsNotWaiverListsFromToLink (flowBean, designerForm);
		}
		
		designerForm.setMayWaiver(designerMayWaive);
	}
	

	private void fillDesignerDesignsWaiverListsFromToLink(DSFlowBean flowBean, DesignerForm designerForm) {	
		for (DesignForm designForm : flowBean.getDesigns()) {
			if (!isDesignLinkedInSomeFormInTheList(designForm, flowBean.getDesigners())) {
				if (isEnabledAddAutomaticallyDesigns(designerForm)) {
					designerForm.getDesignsLinked().add(designForm);
				} else {
					designerForm.getDesignsNotLinked().add(designForm);
				}
			}
		}
	}
	
	
	private void fillDesignerDesignsNotWaiverListsFromToLink(DSFlowBean flowBean, DesignerForm designerForm) {	
		for (DesignForm design : flowBean.getDesigns()) {
			boolean add = true;
			for (DesignerForm designerFormAux : flowBean.getDesigners()) {
				if (designerFormAux.isWaiver() && isDesignLinkedInForm(design, designerFormAux)) {
					add = false;
					break;
				}
			}
			if (add) {
				if (isEnabledAddAutomaticallyDesigns(designerForm)) {
					designerForm.getDesignsLinked().add(design);
				} else {
					designerForm.getDesignsNotLinked().add(design);
				}
			}
		}	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addFormLocarnoToFlow(ContainsLocarnoForm form, DSFlowBean flowBean) {
		for (LocarnoAbstractForm locarnoForm : form.getLocarno()) {
			flowBean.addObject(cloneLocarnoForm(locarnoForm));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addFlowLocarnoToForm(DSFlowBean flowBean, ContainsLocarnoForm form) {
		List<LocarnoAbstractForm> locarno = new ArrayList<LocarnoAbstractForm>();
	    for (LocarnoAbstractForm locarnoForm : flowBean.getLocarno()) {
    		locarno.add(cloneLocarnoForm(locarnoForm));
	    }
	    form.setLocarno(locarno);
	}
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearLocarnoFlow(DSFlowBean flowBean) {
		flowBean.clearLocarno();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearLocarnoForm(ContainsLocarnoForm form) {
		form.getLocarno().clear();
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public DesignForm getDesign(DSFlowBean flowBean, String id){
    	return flowBean.getObject(DesignForm.class, id);
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDesignViewsToFlow(DesignForm designForm, DSFlowBean flowBean) {
		for (DesignViewForm designViewForm : designForm.getViews()) {
			flowBean.addObject(cloneDesignViewForm(designViewForm));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addFlowViewsToDesign(DSFlowBean flowBean, DesignForm designForm) {
		List<DesignViewForm> views = new ArrayList<DesignViewForm>();
	    for (DesignViewForm designViewForm : flowBean.getDesignViews()) {
	    	views.add(cloneDesignViewForm(designViewForm));
	    }
	    designForm.setViews(views);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearViewsFlow(DSFlowBean flowBean) {
		flowBean.clearDesignViews();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearViewsDesignForm(DesignForm designForm) {
		designForm.getViews().clear();
	}
	
	/**
	 * Clone a locarno form.
	 * @param locarnoForm
	 * @return
	 */
	private LocarnoAbstractForm cloneLocarnoForm(LocarnoAbstractForm locarnoForm) {
		try {
    		return (LocarnoAbstractForm) locarnoForm.clone();
    	} catch (CloneNotSupportedException e) {
    		throw new SPException("Failed to find duplicate object", e, "error.genericError");
    	}
	}
	
	/**
	 * Clone a design view form.
	 * @param designViewForm
	 * @return
	 */
	private DesignViewForm cloneDesignViewForm(DesignViewForm designViewForm) {
		return (DesignViewForm) designViewForm.clone();
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public ErrorList validateDesign(String module, DesignForm designForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);
    	
    	Design design = designFactory.convertTo(designForm);    	   
    	return designSearchService.validateDesign(module, design, rules);
    }  	

    /**
     * {@inheritDoc}
     */
    @Override
	public ErrorList validateDesignView(String module, DesignViewForm designViewForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);
    	
    	DesignView designView = designViewFactory.convertTo(designViewForm);    	   
    	return designSearchService.validateDesignView(module, designView, rules);
    }

    @Override
	public ErrorList validatePriority(String module, DSPriorityForm dsPriorityForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);
    	
    	Priority priority = dsPriorityFactory.convertTo(dsPriorityForm);    	   
    	return ipApplicationService.validatePriorityClaim(module, priority, rules);
    }  	
    
    @Override
	public ErrorList validateExhPriority(String module, DSExhPriorityForm dsExhPriorityForm, RulesInformation rules, Errors errors, String flowModeId){
    	putSections(rules, flowModeId);
  	
		ExhibitionPriority exhibitionPriority = dsExhPriorityFactory.convertTo(dsExhPriorityForm);    	   
    	return ipApplicationService.validateExhibitionPriorityClaim(module, exhibitionPriority, rules);
    }  
    
    @Override
	public ErrorList validateApplicationCA(String module, ApplicationCAForm applicationCAForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);
    	
		ContactDetails contactDetails = contactDetailsFactory.convertTo(applicationCAForm.getCorrespondenceAddressForm());    	   
    	return designSearchService.validateApplicationCA(module, contactDetails, rules);
    }
    
    @Override
    public ErrorList validateStoredFile(String module, StoredFile storedFile, RulesInformation rules, Errors errors, String flowModeId) {
    	putSections(rules, flowModeId);
    	
    	Document document = documentFactory.convertTo(storedFile);
    	
    	return documentService.validateDocument(module, document, rules);
    }

    @Override
	public ErrorList validateDivisionalApplication(String module, DSDivisionalApplicationForm dsDivisionalApplicationForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);
    	
    	DesignDivisionalApplicationDetails designDivisionalApplicationDetails= dsDivisionapplicationFactory.convertTo(dsDivisionalApplicationForm);    	   
    	return designSearchService.validateDivisionalApplication(module, designDivisionalApplicationDetails, rules);
    }

    @Override
	public ErrorList validateLocarno(String module, LocarnoForm locarnoForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);
    	
    	ProductIndication productIndication= productIndicationFactory.convertTo(locarnoForm);    	   
    	return locarnoClassificationService.validateProductIndication(module, productIndication, rules);
    }  
    
    @Override
	public ErrorList validateLocarnoSearch(String module, LocarnoSearchForm locarnoSearchForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);
    	
    	ProductIndication productIndication= productIndicationSearchFactory.convertTo(locarnoSearchForm);    	   
    	return locarnoClassificationService.validateProductIndication(module, productIndication, rules);

    }     

    @Override
	public ErrorList validateLocarnoComplex(String module, LocarnoComplexForm locarnoComplexForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);

    	ProductIndication productIndication= productIndicationFactory.convertTo(locarnoComplexForm);    	   
    	return locarnoClassificationService.validateProductIndication(module, productIndication, rules);
    	    	
    }  
    /**
     * {@inheritDoc}
     */
    @Override
	public DesignForm getDesign(String designId) {
		Design design = designSearchService.getDesign(office, designId);
		return designFactory.convertFrom(design);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public DSFlowBean getDesignApplication(String designId) {
		DesignApplication designApplication = designSearchService.getDesignApplication(office, designId, null, true);
		return dsFlowBeanFactory.convertFrom(designApplication);
	}

	@Override
	public Set<String> getDesignApplicationsForDesignId(String designId){
		DesignApplication designApplication = designSearchService.getDesignApplication(office, designId, null, true);
		if(designApplication != null && designApplication.getDesignDetails() != null && designApplication.getDesignDetails().size()>0){
			return designApplication.getDesignDetails().stream().map(design -> design.getApplicationNumber()).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public String getDesignAutocomplete(String text, int numberOfRows) {
    	return this.getDesignAutocomplete(office, text, numberOfRows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDesignAutocomplete(String country, String text, int numberOfRows) {
		if(country.equals(this.office)){
			return ipoAutocompleteSearchService.ipoAutocomplete(text, numberOfRows, "ds-efiling");
		} else {
			return designSearchService.getDesignAutocomplete(country, text, numberOfRows);
		}
    }
    
    /**
     * 
     * @param rules
     * @param flowModeId
     */
    private void putSections(RulesInformation rules, String flowModeId) {
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rules.getCustomObjects().put("sections", sections);
    }

    /**
     * 
     * @param design
     * @param flowBean
     */
    private void addDesignNumber(DesignForm design, DSFlowBean flowBean) {
    	design.setNumber(flowBean.getDesignCount());
    }

    /**
     * 
     * @param flowBean
     */
    private void refreshDesignsNumber(DSFlowBean flowBean) {
    	int number = 1;
    	for (DesignForm designForm : flowBean.getDesigns()) {
    		designForm.setNumber(number++);
    	}
    }
 
}
