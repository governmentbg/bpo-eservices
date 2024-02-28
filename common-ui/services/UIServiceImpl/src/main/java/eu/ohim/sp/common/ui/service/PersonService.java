package eu.ohim.sp.common.ui.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.adapter.design.DesignerFactory;
import eu.ohim.sp.common.ui.adapter.patent.InventorFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.flow.section.PersonFlowBean;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.service.util.CompanyNumberUtils;
import eu.ohim.sp.common.ui.service.util.NationalIdUtils;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.person.*;
import eu.ohim.sp.core.domain.user.FOUser;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.person.*;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.core.util.SectionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonService implements PersonServiceInterface {
	
    private static Logger logger = Logger.getLogger(PersonService.class);

    public static final String SECT_NM = "sectionName";

	@Autowired
	protected RepresentativeService representativeService;
	
    @Autowired
    private ApplicantService applicantService;
    
    @Autowired
	private DesignerService designerService;

	@Autowired
	private InventorService inventorService;
    
    @Autowired
    private UserSearchService userService;

	@Autowired
	private PersonChangeService personChangeService;
    
    @Autowired
    protected FilterImportable filterImportable;

	@Autowired
	protected ApplicantFactory applicantFactory;
	
	@Autowired
	protected HolderFactory holderFactory;
	
	@Autowired
	protected AssigneeFactory assigneeFactory;

	@Autowired
	protected RepresentativeFactory representativeFactory;

	@Autowired
	protected DesignerFactory designerFactory;

	@Autowired
	protected InventorFactory inventorFactory;
	
	@Autowired
	protected PersonChangeFactory personChangeFactory;

    @Autowired
    protected SignatoryFactory signatoryFactory;
	
	@Autowired
    private ConfigurationServiceDelegatorInterface configurationService;
	
	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;
	
	@Value("${sp.office}")
    private String office;
	
	@Autowired
	private RulesService businessRulesService;

	/**
	 * TODO Make "more" configurable.
	 */
	@Value("${sp.module}")
    private String mod;
	
	 /**
	 * 
	 * @param flowBean
	 * @param flowModeId
	 */
	@Override
	public UserPersonDetails addUserPersonDetails(FlowBean flowBean, String flowModeId) {
		try {
			UserPersonDetails userPersonDetails = getUserPersonDetails(flowModeId);
			if (userPersonDetails != null) {
				addApplicantsDetails(flowBean, userPersonDetails.getApplicants(), flowModeId);
				addRepresentativesDetails(flowBean, userPersonDetails.getRepresentatives(), flowModeId);	
	        }
			return userPersonDetails;
		} catch(Exception e) {
			logger.warn("Error obtaining user person details", e);
			return null;
		}
	}

	@Override
	public UserPersonDetails clearUserApplicantDetails(FlowBean flowBean, String flowModeId) {
		try {
			UserPersonDetails userPersonDetails = getUserPersonDetails(flowModeId);
			if (userPersonDetails != null) {
				for(Applicant a: userPersonDetails.getApplicants()){
					flowBean.removeObject(ApplicantForm.class, a.getIdentifiers().get(0).getValue());
				}
			}
			return getUserPersonDetails(flowModeId);
		} catch(Exception e) {
			logger.warn("Error obtaining user person details", e);
			return null;
		}
	}

	@Override
	public UserPersonDetails clearUserInventorDetails(FlowBean flowBean, String flowModeId) {
		try {
			UserPersonDetails userPersonDetails = getUserPersonDetails(flowModeId);
			if (userPersonDetails != null) {
				for(Designer a: userPersonDetails.getDesigners()){
					flowBean.removeObject(InventorForm.class, a.getIdentifiers().get(0).getValue());
				}
			}
			return getUserPersonDetails(flowModeId);
		} catch(Exception e) {
			logger.warn("Error obtaining user person details", e);
			return null;
		}
	}

	@Override
	public void addUserDetails(FlowBean flowBean, String flowModeId) {
		try {
			User user = getUser(flowModeId);
			if(user != null){
				flowBean.setCurrentUserEmail(user.getEmail());
				flowBean.setCurrentUserName(user.getUserName());
			}
		} catch(Exception e) {
			logger.warn("Error obtaining user details", e);
		}
	}

	/**
	 * 
	 * @param flowBean
	 * @param form
	 * @param flowModeId
	 */
	@Override
	public void addPersonFormDetails(FlowBean flowBean, PersonForm form, String flowModeId) {
		if (form != null) {
			form.setImported(true);
	        form.setCurrentUserIndicator(true);
	        if (flowBean.getObject(form.getClass(), form.getId()) == null) {
	            flowBean.addObject((AbstractForm) filterImportable.filterSingleObject(form, flowModeId, form.getAvailableSectionName(), null));
	        }
		}
	}
	    
	    
	/**
	 * 
	 * @param flowBean
	 * @param applicants
	 * @param flowModeId
	 */
	private void addApplicantsDetails(FlowBean flowBean, List<Applicant> applicants, String flowModeId) {
		if (CollectionUtils.isNotEmpty(applicants)) {
			for (Applicant applicant : applicants) {
               	addPersonFormDetails(flowBean, applicantFactory.convertFrom(applicant), flowModeId);
			}
		}
	}
		
	/**
	 * 
	 * @param flowBean
	 * @param representatives
	 * @param flowModeId
	 */
	private void addRepresentativesDetails(FlowBean flowBean, List<Representative> representatives, String flowModeId) {
		if (CollectionUtils.isNotEmpty(representatives)) {
			for (Representative representative : representatives) {
               	addPersonFormDetails(flowBean, representativeFactory.convertFrom(representative), flowModeId);
			}
		}
	}
	    
    
    @Override
    public ApplicantForm importApplicant(String applicantId, String flowModeId) {
    	if (StringUtils.isNotBlank(applicantId)) {
    		Applicant core = null;
    		if ("eservices".equalsIgnoreCase(mod))
    			core = applicantService.getApplicant(flowModeId, office, applicantId);
    		else{
    			core = applicantService.getApplicant(mod, office, applicantId);
    		}
			if(core == null){
				throw new SPException("No applicant found to import", "no.results.found");
			}
        	ApplicantForm uiForm = applicantFactory.convertFrom(core);
    		uiForm.setImported(true);
    		return (ApplicantForm) filterImportable.filterSingleObject(uiForm, flowModeId, uiForm.getAvailableSectionName(), null);
    	} else {
    		throw new SPException();
    	}
    }
    
    @Override
    public RepresentativeForm importRepresentative(String representativeId, String flowModeId) {
    	if (StringUtils.isNotBlank(representativeId)) {
    		Representative core = null;
    		if ("eservices".equalsIgnoreCase(mod))
    			core = representativeService.getRepresentative(flowModeId, office, representativeId);
    		else{
    			core = representativeService.getRepresentative(mod, office, representativeId);
    		}
			if(core == null){
				throw new SPException("No representative found to import", "no.results.found");
			}
        	RepresentativeForm uiForm = representativeFactory.convertFrom(core);
        	uiForm.setImported(true);
    		return (RepresentativeForm) filterImportable.filterSingleObject(uiForm, flowModeId, uiForm.getAvailableSectionName(), null);
    	} else {
    		throw new SPException();
    	}
    }

    /**
     * Checks whether an applicant/representative is imported from a loaded application
     * and then imports again the applicant/representative details, if the external service
     * is enabled, otherwise it removes it.
     * 
     * @param flowBean
     */
    @Override
    public void importLoadedPersons(PersonFlowBean flowBean) {
        if (logger.isDebugEnabled()) {
            logger.debug("Importing applicants & representatives on loaded application");
        }
        Map<String, ApplicantForm> importedApplicants = new HashMap<String, ApplicantForm>();
        for (ApplicantForm applicant : flowBean.getApplicants()) {
            if (configurationService.isServiceEnabled(AvailableServices.APPLICANT_IMPORT.value())) {
                try {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Importing applicant : " + applicant.getId());
                    }
                    if (applicant.getType() == null) {
                        importedApplicants.put(applicant.getId(),
                                applicantFactory.convertFrom(applicantService.getApplicant("", office, applicant.getId())));
                    }
                } catch (SPException e) {
                    importedApplicants.put(applicant.getId(), null);
                    logger.error(e);
                    // Do not add the applicant if the import fails
                }
            }
        }
        for (Map.Entry<String, ApplicantForm> entry : importedApplicants.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().setImported(true);
                flowBean.replaceObject(entry.getValue(), entry.getKey());
            } else {
                flowBean.removeObject(ApplicantForm.class, entry.getKey());
            }
        }

        Map<String, RepresentativeForm> importedRepresentatives = new HashMap<String, RepresentativeForm>();
        for (RepresentativeForm representative : flowBean.getRepresentatives()) {
            if (configurationService.isServiceEnabled(AvailableServices.REPRESENTATIVE_IMPORT.value())) {
                try {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Importing representative : " + representative.getId());
                    }
                    if (representative.getType() == null) {
                        importedRepresentatives.put(representative.getId(), representativeFactory
                                .convertFrom(representativeService.getRepresentative("", office, representative.getId())));
                    }
                } catch (SPException e) {
                    importedRepresentatives.put(representative.getId(), null);
                    logger.error(e);
                    // Do not add the representative if the import fails
                }
            }
        }
        for (Map.Entry<String, RepresentativeForm> entry : importedRepresentatives.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().setImported(true);
                flowBean.replaceObject(entry.getValue(), entry.getKey());
            } else {
                flowBean.removeObject(RepresentativeForm.class, entry.getKey());
            }
        }
    }
    
    @Override
    public List<ApplicantForm> matchApplicant(ApplicantForm applicantForm, int maxResults) {
    	Applicant applicant = applicantFactory.convertTo(applicantForm);
    	List<Applicant> applicants = applicantService.matchApplicant("", "", applicant, maxResults);

        List<ApplicantForm> matchedApplicants = new ArrayList<ApplicantForm>();
        for (Applicant matchedApplicant : applicants) {
        	matchedApplicants.add(applicantFactory.convertFrom(matchedApplicant));
        }
    	
        return matchedApplicants;
    }
    
    @Override
    public List<RepresentativeForm> matchRepresentative(RepresentativeForm representativeForm, int maxResults) {
    	Representative applicant = representativeFactory.convertTo(representativeForm);
    	List<Representative> applicants = representativeService.matchRepresentative("", "", applicant, maxResults);

        List<RepresentativeForm> matchedRepresentatives = new ArrayList<RepresentativeForm>();
        for (Representative matchedRepresentative : applicants) {
        	matchedRepresentatives.add(representativeFactory.convertFrom(matchedRepresentative));
        }
    	
        return matchedRepresentatives;
    }

    @Override
    public String getApplicantAutocomplete(String text, int numberOfRows) {
    	return applicantService.getApplicantAutocomplete(mod, office, text, numberOfRows);
    }
    
    @Override
    public String getApplicantAutocomplete(String text, int numberOfRows, String flowModeId) {
    	return applicantService.getApplicantAutocomplete(flowModeId , office, text, numberOfRows);
    }
    
    @Override
    public String getRepresentativeAutocomplete(String text, int numberOfRows) {
    	return representativeService.getRepresentativeAutocomplete(mod, office, text, numberOfRows);
    }
    
    @Override
    public String getRepresentativeAutocomplete(String text, int numberOfRows, String flowModeId) {
    	return representativeService.getRepresentativeAutocomplete(flowModeId, office, text, numberOfRows);
    }
    
    @Override
    public String getDesignerAutocomplete(String text, int numberOfRows) {
    	return designerService.getDesignerAutocomplete(mod, office, text, numberOfRows);
    }

	@Override
	public String getInventorAutocomplete(String text, int numberOfRows) {
		return inventorService.getInventorAutocomplete(mod, office, text, numberOfRows);
	}
    
    @Override
    public UserPersonDetails getUserPersonDetails(String flowModeId) {
    	User user = getUser(flowModeId);
   		if (user != null && user instanceof FOUser) {
   			return ((FOUser) user).getUserPersonDetails();
    	} else {
    		return null;
    	}
    }

    @Override
    public User getUser(String flowModeId) {
    	SPUser userAuth = AuthenticationUtil.getAuthenticatedUser();
    	if (userAuth == null) {
    		return null;
		}
		String module;
		if ("eservices".equalsIgnoreCase(mod)) {
			module = flowModeId;
		} else {
			module = mod;
		}

		/**
		 * user is getting cached in the session, because there are pages that make 3 or more requests to getUser(), which might generate multiple useless requests to other systems.
		 */
		javax.servlet.http.HttpServletRequest rq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		User u = (User) rq.getSession().getAttribute("user" + module);
		if (u == null) {
			u = userService.getUser(module, office, userAuth.getUsername());
			rq.getSession().setAttribute("user" + module, u);
		}
		return u;
    }
    
    @Override
    public ErrorList validateApplicant(String module, ApplicantForm applicantForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rules.getCustomObjects().put("sections", sections);
    	rules.getCustomObjects().put("imported", applicantForm.getImported());
    	rules.getCustomObjects().put("nationalIdIsValid",
				NationalIdUtils.validateNationalId(applicantForm.getNationalIdType(),
						applicantForm.getNationalId()));
		rules.getCustomObjects().put("companyIdIsValid",
				CompanyNumberUtils.validateCompanyNumber(applicantForm.getNationalOfficialBusinessRegister()));

		Applicant applicant = applicantFactory.convertTo(applicantForm);
		ErrorList errorList=applicantService.validateApplicant(module, applicant, rules);
    	
    	return errorList;
    	
    }

    @Override
    public ErrorList validateRepresentative(String module, RepresentativeForm representativeForm, RulesInformation rules, Errors errors, String flowModeId){  
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rules.getCustomObjects().put("sections", sections);
		rules.getCustomObjects().put("imported", representativeForm.getImported());
		rules.getCustomObjects().put("companyIdIsValid",
				CompanyNumberUtils.validateCompanyNumber(representativeForm.getNationalOfficialBusinessRegister()));
  	
    	Representative representative = representativeFactory.convertTo(representativeForm);
		ErrorList errorList=representativeService.validateRepresentative(module, representative, rules);
    	
    	return errorList;
    	
    }

	@Override
	public ErrorList validatePersonChange(String module, RepresentativeForm representativeForm, RulesInformation rules, Errors errors, String flowModeId){
		ErrorList errorList=new ErrorList();

		Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
		rules.getCustomObjects().put("sections", sections);


        PersonChange personChange = personChangeFactory.convertTo(representativeForm);

        // Updated person validation
        if (ChangePersonType.ADD_NEW_REPRESENTATIVE.equals(representativeForm.getChangeType())
                || ChangePersonType.REPLACE_REPRESENTATIVE.equals(representativeForm.getChangeType())
                || ChangePersonType.ADD_NEW_CORRESPONDENT.equals(representativeForm.getChangeType())
                || ChangePersonType.REPLACE_CORRESPONDENT.equals(representativeForm.getChangeType())) {
            errorList.addAllErrors(representativeService.validateRepresentative(module, personChange.getUpdatedPerson(), rules));
        }

        // Updated address validation
        if (ChangePersonType.CHANGE_REPRESENTATIVE_ADDRESS.equals(representativeForm.getChangeType())
                || ChangePersonType.CHANGE_CORRESPONDENT_ADDRESS.equals(representativeForm.getChangeType())
                || ChangePersonType.CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS.equals(representativeForm.getChangeType())) {
            errorList.addAllErrors(personChangeService.validatePersonChange(module, personChange, rules));
        }

        // Current person and change type validation
        rules.getCustomObjects().put(SECT_NM, "personChange");
        errorList.addAllErrors(personChangeService.validatePersonChange(module, personChange, rules));

		return errorList;

	}

	@Override
    public ErrorList validateHolder(String module, HolderForm holderForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rules.getCustomObjects().put("sections", sections);
		rules.getCustomObjects().put("companyIdIsValid",
				CompanyNumberUtils.validateCompanyNumber(holderForm.getNationalOfficialBusinessRegister()));
    	
    	Holder holder = holderFactory.convertTo(holderForm);
		ErrorList errorList=applicantService.validateHolder(module, holder, rules);
    	
    	return errorList;  
    
   }
    
    @Override
    public ErrorList validateAssignee(String module, AssigneeForm assigneeForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rules.getCustomObjects().put("sections", sections);
    	rules.getCustomObjects().put("imported", assigneeForm.getImported());
		rules.getCustomObjects().put("companyIdIsValid",
				CompanyNumberUtils.validateCompanyNumber(assigneeForm.getNationalOfficialBusinessRegister()));

    	Assignee assignee = assigneeFactory.convertTo(assigneeForm);
		ErrorList errorList=applicantService.validateAssignee(module, assignee, rules);
    	
    	return errorList;  
    
   }  

	@Override
	public DesignerForm importDesigner(String designerId, String flowModeId) {
		if (StringUtils.isNotBlank(designerId)) {
			Designer core = designerService.getDesigner(mod, office, designerId);
			if(core == null){
				throw new SPException("No result from designerService.getDesigner call");
			}
        	DesignerForm uiForm = designerFactory.convertFrom(core);
    		uiForm.setImported(true);
    		return (DesignerForm) filterImportable.filterSingleObject(uiForm, flowModeId, uiForm.getAvailableSectionName(), null);
    	} else {
    		throw new SPException();
    	}
	}

	@Override
	public InventorForm importInventor(String inventorId, String flowModeId) {
		if (StringUtils.isNotBlank(inventorId)) {
			Inventor core = inventorService.getInventor(mod, office, inventorId);
			if(core == null){
				throw new SPException("No result from inventorService.getInventor call");
			}
			InventorForm uiForm = inventorFactory.convertFrom(core);
			uiForm.setImported(true);
			return (InventorForm) filterImportable.filterSingleObject(uiForm, flowModeId, uiForm.getAvailableSectionName(), null);
		} else {
			throw new SPException();
		}
	}
	
    @Override
    public ErrorList validateDesigner(String module, DesignerForm designerForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rules.getCustomObjects().put("sections", sections);
		rules.getCustomObjects().put("imported", designerForm.getImported());
    	
    	Designer designer = designerFactory.convertTo(designerForm);
		ErrorList errorList=designerService.validateDesigner(module, designer, rules);
    	
    	return errorList;
    	
    }

	@Override
	public ErrorList validateInventor(String module, InventorForm inventorForm, RulesInformation rules, Errors errors, String flowModeId){
		Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
		rules.getCustomObjects().put("sections", sections);
		rules.getCustomObjects().put("imported", inventorForm.getImported());

		Inventor inventor = inventorFactory.convertTo(inventorForm);
		ErrorList errorList=inventorService.validateInventor(module, inventor, rules);

		return errorList;

	}
    
    @Override
    public ErrorList validateSignature(String module, SignatureForm signatureForm, RulesInformation rules, Errors errors, String flowModeId){ 
        boolean checkUserService = configurationService.isServiceEnabled(AvailableServices.VALIDATE_EXTERNAL_USER.value());
        
        Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
        rules.getCustomObjects().put("sections", sections);
        Boolean imported = false;
        if (signatureForm!=null){
        	imported = signatureForm.getImported();
        }
        rules.getCustomObjects().put("imported", imported);
        Signatory signature = signatoryFactory.convertTo(signatureForm);
        List<Object> objects = new ArrayList<Object>();     
        Section section = SectionUtil.getSectionByRulesInformation(rules);
        
        if(checkUserService){
        	FOUser userSignatory = (FOUser) userService.getUser(module, office, signature.getUserId());
        	objects.add(userSignatory);
        	rules.getCustomObjects().put("fOUser", userSignatory);
        }
        objects.add(section);
        objects.add(signature);
                
        objects.add(rules);
		ErrorList errorList =  businessRulesService.validate(module, "signature_user", objects);
        
        return errorList;
        
       }


    @Override
    public void initializePersonsForRegisteredUsers(FlowBean flowBean, String flowModeId) {
      //  SPUser user = AuthenticationUtil.getAuthenticatedUser();
/*        if (user != null) {
            UserPersonDetails userPersonDetails =  user.getUserPersonDetails();
            if (userPersonDetails != null) {
                for (Applicant applicant : userPersonDetails.getApplicants()) {
                    ApplicantForm form = applicantFactory.convertFrom(applicant);
                    form.setImported(true);
                    form.setCurrentUserIndicator(true);
                    logger.debug(form.getId() + " : " + flowBean.getObject(ApplicantForm.class, form.getId()));
                    if (flowBean.getObject(ApplicantForm.class, form.getId())==null) {
                        flowBean.addObject((AbstractForm) filterImportable.filterSingleObject(form, flowModeId,
                                form.getAvailableSectionName(), null));
                    }
                }
                for (Representative representative : userPersonDetails.getRepresentatives()) {
                    RepresentativeForm form = representativeFactory.convertFrom(representative);
                    form.setImported(true);
                    form.setCurrentUserIndicator(true);
                    logger.debug(form.getId() + " : " + flowBean.getObject(RepresentativeForm.class, form.getId()));
                    if (flowBean.getObject(RepresentativeForm.class, form.getId())==null) {
                        flowBean.addObject((AbstractForm) filterImportable.filterSingleObject(form, flowModeId,
                                form.getAvailableSectionName(), null));
                    }
                }
            }
        }*/
    }

	@Override
	public List<DesignerForm> matchDesigner(DesignerForm designerForm, int maxResults) {
		Designer designer = designerFactory.convertTo(designerForm);
		List<Designer> designers = designerService.matchDesigner("", "", designer, maxResults);

		List<DesignerForm> matchedDesigners = new ArrayList<DesignerForm>();
		for (Designer matched : designers) {
			matchedDesigners.add(designerFactory.convertFrom(matched));
		}

		return matchedDesigners;
	}

	@Override
	public List<InventorForm> matchInventor(InventorForm inventorForm, int maxResults) {
		Inventor inventor = inventorFactory.convertTo(inventorForm);
		List<Inventor> inventors = inventorService.matchInventor("", "", inventor, maxResults);

		List<InventorForm> matchedInventors = new ArrayList<InventorForm>();
		for (Inventor matched : inventors) {
			matchedInventors.add(inventorFactory.convertFrom(matched));
		}

		return matchedInventors;
	}

	@Override
	public RepresentativeForm importIntlPRepresentative(String representativeId, String flowModeId) {
		if (StringUtils.isNotBlank(representativeId)) {
			Representative core = representativeService.getIntlPRepresentative(representativeId);
			if(core == null){
				throw new SPException("No representative found to import", "no.results.found");
			}
			RepresentativeForm uiForm = representativeFactory.convertFrom(core);
			uiForm.setImported(true);
			return (RepresentativeForm) filterImportable.filterSingleObject(uiForm, flowModeId, uiForm.getAvailableSectionName(), null);
		} else {
			throw new SPException();
		}
	}

	@Override
	public String getIntlPRepresentativeList() {
		return representativeService.getIntlPRepresentativeList();
	}
}
