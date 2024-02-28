package eu.ohim.sp.eservices.ui.controller;

import java.util.*;

import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.eservices.ui.service.interfaces.BackOfficeServiceInterface;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;
import eu.ohim.sp.eservices.ui.util.AutomaticImportUtils;
import eu.ohim.sp.eservices.ui.util.GroundsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESTrademarkService;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;
import eu.ohim.sp.eservices.ui.util.TaxonomyUtil;

/**
 * Controller in charge of handling the adding and the editing of the applicants.
 * @author ionitdi
 */
@Controller
public class AddTMController extends AddAbstractController {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(AddTMController.class);

    private static final String ERROR_IMPORT_TM = "java.lang.RuntimeException: Failed : HTTP error code : 500";

    private static final String UNKNOWN_EXCEPTION = "Unknown exception cause";

    /**
     * session bean
     */
    @Autowired
    private ESFlowBean flowBean;

    @Autowired
    private ESTrademarkServiceInterface esTrademarkService;

    @Autowired
    private BackOfficeServiceInterface backOfficeService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private GroundsUtil groundsUtil;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private HolderFactory holderFactory;
    /**
     * It returns a new TMDetailsForm obj if no parameters are passed, so a new
     * Trademark to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
    @RequestMapping(value = "addTMDetails", method = RequestMethod.GET)
    public ModelAndView formBackingTMDetails(@RequestParam(required = false, value = "id") String id) {
        flowBean.setOwners(new ArrayList<ApplicantForm>());
        flowBean.setTmpImportRepresentativesList(new ArrayList<>());
        if (!"".equals(id) && id != null) {
            TMDetailsForm command = flowBean.getObject(TMDetailsForm.class, id);

            Set<GoodAndServiceForm> flowBeanGS = new TreeSet<GoodAndServiceForm>();
            for (GoodAndServiceForm gs : command.getGoodsAndServices()) {
                try {
                    GoodAndServiceForm paintedGS = gs.clone();
                    flowBeanGS.add(paintedGS);
                } catch (CloneNotSupportedException e) {
                    throw new SPException("Failed to find duplicate object", e, EServicesConstants.ERROR_GE);
                }

            }
            flowBean.setGoodsServices(flowBeanGS);
            flowBean.setOriginalGS(command.getOriginalGS());


            if (command.getApplicants() != null) {
                flowBean.getOwners().addAll(command.getApplicants());
            }
            if(command.getRepresentatives() != null){
                flowBean.getTmpImportRepresentativesList().addAll(command.getRepresentatives());
            }

        }
        return innerFormBackingObject(id, flowBean, new AddParameters(TMDetailsForm.class, EServicesConstants.TM_DF, EServicesConstants.TM_DS, EServicesConstants.TM_DS, getMaxNumber()));
    }

    /**
     * Check for attached files
     *
     * @param form
     */
    private void checkRepresentationAttachment(TMDetailsForm form) {
        if (!form.getImported()) {
            if (form.getRepresentationAttachment() != null) {
                String docId = (form.getRepresentationAttachment().getStoredFiles() != null ? (form.getRepresentationAttachment().getStoredFiles().size() > 0 ? "/getDocument.htm?documentId=" + form.getRepresentationAttachment().getStoredFiles().get(0).getDocumentId() : "") : "");
                form.setImageRepresentationURI(docId);
            } else {
                form.setImageRepresentationURI("");
            }
        } else {
            if (form.getImageRepresentationURI() != null && !"".equals(form.getImageRepresentationURI()) &&
                    (form.getRepresentationAttachment() == null || form.getRepresentationAttachment().getStoredFiles().size() == 0)) {
                StoredFile sf = new StoredFile();
                sf.setContentType(EServicesConstants.MIME_IMAGE_PNG);
                if (form.getRepresentationAttachment() == null) {
                    form.setRepresentationAttachment(new FileWrapper());
                }
                form.getRepresentationAttachment().getStoredFiles().add(sf);
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "getNotApplicableGrounds", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public int getNotApplicableGrounds(TMDetailsForm command) {
        return groundsUtil.getNotApplicableOppositionBasis(flowBean.getObsList(),
                groundsUtil.getAvaibleLegalActVersions(command, flowScopeDetails.getFlowModeId())).size();
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Applicant information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @RequestMapping(value = "addTMDetails", method = RequestMethod.POST)
    public ModelAndView onSubmitTMDetails(@ModelAttribute(EServicesConstants.TM_DF) TMDetailsForm command,
    										BindingResult result,
                                            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
    	checkRepresentationAttachment(command);
	    
    	//Add the owners of the mark to the command
    	if(flowBean.getOwners() != null){
    		command.setApplicants(flowBean.getOwners());
    	}
		if (flowBean.getTmpImportRepresentativesList() != null) {
			command.setRepresentatives(flowBean.getTmpImportRepresentativesList());
		}
    	
    	//Add the modified G&S to the application
    	String applicationType = flowScopeDetails.getFlowModeId();
    	List<OppositionBasisForm> removedOBList = new ArrayList<OppositionBasisForm>();
    	
    	if( "tm-opposition".equalsIgnoreCase(applicationType) || "tm-invalidity".equalsIgnoreCase(applicationType) || "tm-revocation".equalsIgnoreCase(applicationType)
				|| "tm-objection".equalsIgnoreCase(applicationType)){
    		flowBean.setAvaibleLegalActVersions(groundsUtil.getAvaibleLegalActVersions(command, applicationType));
    	}

        if( "tm-opposition".equalsIgnoreCase(applicationType) || "tm-invalidity".equalsIgnoreCase(applicationType) || "tm-revocation".equalsIgnoreCase(applicationType)
                || "tm-objection".equalsIgnoreCase(applicationType) || "tm-withdrawal".equalsIgnoreCase(applicationType)){
            removedOBList = groundsUtil.refreshOppositionBasisList(flowBean);
        }

    	command.setGoodsAndServices(flowBean.getGoodsServices());

    	//Add the original G&S of the TM to the application
        command.setOriginalGS(flowBean.getOriginalGS());

    	AddParameters addParameters = new AddParameters(TMDetailsForm.class, EServicesConstants.TM_DF,
                                                        "tm_details/tm_card_list", EServicesConstants.TM_DS, getMaxNumber());

    	if( "tm-opposition".equalsIgnoreCase(applicationType) || "tm-invalidity".equalsIgnoreCase(applicationType) || "tm-revocation".equalsIgnoreCase(applicationType)){
    		if (removedOBList!=null && removedOBList.size()>0){
    			ModelAndView model = addTMCheckMatches(command, result, addParameters, ignoreMatches);
    			model.addObject("removedOBList","true");
    			return model;
    		}
    	}
        return addTMCheckMatches(command, result, addParameters, ignoreMatches);
    }
    
    /**
     * Entry point to get an applicant form for edit.
     * This method figures out the type of the applicant and uses another method that can handle
     * that type of applicant.
     * If the type is not found or there is no such applicant with that id, it returns null.
     * @param id the id of the applicant
     * @return the view containing the applicant form to edit
     */
    @RequestMapping(value = "getTMDetailsForEdit", method = RequestMethod.GET)
    public ModelAndView getTM(@RequestParam(required = true, value = "id") String id)
    {      
        return formBackingTMDetails(id);
    }
    
    /**
     *Recovers the indicated trademark from the TradeMarkService, validated it, 
     *populate the form and defines the view to be show to the user.
     *
     * @param tradeMarkId the trademark id
     * @param languagesFilter the language iso code to filter good and services
     * @param tmDetailsForm the trademark details form
     * @param result manage the validation results of the form object
     * @return Priority object view with the new priority added
     */
    @RequestMapping(value = "importTrademark", method = RequestMethod.GET)
    public ModelAndView importTMDetails(
    	@RequestParam(value = "importId", required = false) String tradeMarkId,
        @RequestParam(value = "languagesFilter", required = false) String languagesFilter,
        @RequestParam(value = "officeImport", required = true) String officeImport,
        @RequestParam(value = "unpublished") boolean unpublished,
    	@ModelAttribute(EServicesConstants.TM_DF) TMDetailsForm tmDetailsForm,
    	BindingResult result) {

    	if (tradeMarkId != null) {

            if (tmDetailsForm == null) {
                // trademark not found or more than one were returned
                return new ModelAndView(EServicesConstants.TM_IW);
            } else {

                TMDetailsForm tmDetailsForm2 = null;
                try {
                    if(unpublished){
                        User user = AuthenticationUtil.getAuthenticatedUser();
                        if (user!=null){
                            String username=user.getUsername();
                            tmDetailsForm2 = backOfficeService.getUnpublishedTradeMark(username, tradeMarkId, flowBean.getIdreserventity());
                        } else {
                            throw new SPException("Unable to import unpublished app without username");
                        }
                    } else {
                        tmDetailsForm2 = esTrademarkService.getTradeMark(tradeMarkId, officeImport);
                    }
                } catch (SPException e) {
                    ModelAndView mv = new ModelAndView(EServicesConstants.TM_IW);
                    Optional<Throwable> cause = Optional.ofNullable(e.getCause());
                    if(!ERROR_IMPORT_TM.equals(cause.isPresent() ? cause.get().getMessage() : UNKNOWN_EXCEPTION)) {
                        mv.addObject("errorCode", "error.importing.trademark.tmview");
                    }
                    return mv;
                }

                if (tmDetailsForm2 != null) {
                    if (languagesFilter != null) {
                        if(tmDetailsForm2.getApplicationLanguage() != null && !tmDetailsForm2.getApplicationLanguage().equals("")){
                            languagesFilter +=","+tmDetailsForm2.getApplicationLanguage();
                        }

                        tmDetailsForm2.setGoodsAndServices(TaxonomyUtil.filterGoodsAndServices(tmDetailsForm2.getGoodsAndServices(), languagesFilter));
                        tmDetailsForm2.setOriginalGS(TaxonomyUtil.filterGoodsAndServices(tmDetailsForm2.getOriginalGS(), languagesFilter));
                    }
                    BeanUtils.copyProperties(tmDetailsForm2, tmDetailsForm);
                } else {
                    ModelAndView model = new ModelAndView(EServicesConstants.TM_IW);
                    model.addObject("errorCode", "error.import.noObjectFound.trademark");
                    return model;
                }

//                tmDetailsForm.setPublicationDate(tmDetailsForm.getOppositionPeriodStartDate());

                flowBean.setTmpImportRepresentativesList(tmDetailsForm.getRepresentatives());
                flowBean.setOwners(tmDetailsForm.getApplicants());

                AddParameters addParameters = new AddParameters(TMDetailsForm.class, EServicesConstants.TM_DF,
                        "tm_details/tm_card_list", EServicesConstants.TM_DS, getMaxNumber());

                return loadImportedTM(tmDetailsForm, result, addParameters);

            }

    	} else {
    		// blank form
            ModelAndView modelAndView = new ModelAndView(EServicesConstants.TM_DS);
			modelAndView.addObject(EServicesConstants.TM_DF, new TMDetailsForm());
            return modelAndView;
    	}

	}
    
    /**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     * 
     * @param id the id of the edited object, or a new object if it is null
     * @param office office code where the mark has to be searched
     * @param previousCTM indicates whether the search is done from the previousCTM section or not
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "autocompleteTrademark", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody public String autocomplete(
    		@RequestParam(required = false, value = "args") String id,
    		@RequestParam(required = true, value = "office") String office,
    		@RequestParam(required = true, value = "previousCTM") Boolean previousCTM) {
		return autocompleteService(id, office, previousCTM, flowScopeDetails.getFlowModeId());
    }

    @RequestMapping(value = "autocompleteEarlierTrademark", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody public String autocompleteEarlier(
            @RequestParam(required = false, value = "args") String id,
            @RequestParam(required = true, value = "office") String office,
            @RequestParam(required = true, value = "previousCTM") Boolean previousCTM) {
        return autocompleteService(id, office, previousCTM, "tm-");
    }
    
	public String autocompleteService(String id, String office, Boolean previousCTM, String flowModeId) {
		logger.info("START search");
        String results = esTrademarkService.getTradeMarkAutocomplete(office, id, 10, previousCTM, flowModeId);
		logger.info("END search");
		return results;		
	}
    
    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Applicant information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @RequestMapping(value = "renewableTrademark", method = RequestMethod.POST)
    public ModelAndView renewableTM(@ModelAttribute(EServicesConstants.TM_DF) TMDetailsForm command,
			BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {			
		//Add the original G&S of the TM to the application
		command.setOriginalGS(flowBean.getOriginalGS());		
		flowBean.setGoodsServices(new TreeSet<GoodAndServiceForm>());
		
		AddParameters addParameters = new AddParameters(TMDetailsForm.class, EServicesConstants.TM_DF,
		                        "tm_details/tm_card_list", EServicesConstants.TM_DS, getMaxNumber());
		return addTMCheckMatches(command, result, addParameters, ignoreMatches);    	
    }    
    
    /**
     *It returns the form to add applicants associated to a trademark
     *
     * @param name the name of the owner
     * @param domicile the domicile of the owner
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addOwnerDetails", method = RequestMethod.GET)
    public ModelAndView formBackiningOwners(
    		@RequestParam(required = false, value = "name") String name,
    		@RequestParam(required = false, value = "domicile") String domicile)
    {
    	ModelAndView modelAndView;
    	
    	if(name != null && !"".equals(name.trim()) && domicile != null){
    		List<ApplicantForm> owners = flowBean.getOwners() == null? new ArrayList<ApplicantForm>() :  flowBean.getOwners();
    		ApplicantForm owner = new ApplicantForm();
    		owner.setName(name);
    		owner.setDomicile(domicile);
    		owners.add(owner);
    		flowBean.setOwners(owners);
    		
    		modelAndView = new ModelAndView("tm_details/tm_details_owner");
    	}
    	else{
    		modelAndView = new ModelAndView("tm_details/tm_owners_warning");
    	}
    	    	
        return modelAndView;
    }
    
    @RequestMapping(value = "cleanOwnersList", method = RequestMethod.GET)
    public ModelAndView cleanOwners()
    {
    	flowBean.setOwners(new ArrayList<ApplicantForm>());
    	ModelAndView modelAndView = new ModelAndView("tm_details/tm_details_owner");
    	
        return modelAndView;
    }
    
    /**
     *It returns the form to add applicants associated to a trademark
     *
     * @param name the name of the owner
     * @param domicile the domicile of the owner
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "removeOwnerDetails", method = RequestMethod.GET)
    public ModelAndView removeOwner(
    		@RequestParam(required = false, value = "name") String name,
    		@RequestParam(required = false, value = "domicile") String domicile)
    {
    	if((name != null && !"".equals(name)) || domicile != null){
    		ApplicantForm ownerForm = flowBean.getApplicant(name, domicile);
    		if(ownerForm!=null) {
    			flowBean.getOwners().remove(ownerForm);
    		}
    	}
    	ModelAndView modelAndView = new ModelAndView("tm_details/tm_details_owner");
    	
        return modelAndView;
    }
    
    /**
     *Auxiliary method to add elements need it in the the @flowBean from the TM imported 
     *and create the ModelAndView to be returned.
     * @return ModelAndView
     */
    private ModelAndView loadImportedTM(TMDetailsForm importedTM, BindingResult result, AddParameters addParameters){
    	importedTM.setCheckBdBlocking(false);
    	importedTM.setImported(true);
    	importedTM.setExtent(true);
    	flowBean.setGoodsServices(importedTM.getGoodsAndServices());
    	flowBean.setOriginalGS(importedTM.getOriginalGS());
    	
    	validateCommand(importedTM, result, addParameters);
		
        ModelAndView modelAndView = new ModelAndView(EServicesConstants.TM_DS);
		modelAndView.addObject(EServicesConstants.TM_DF, result.getTarget());
		
		return modelAndView;		
    }
    
    /**
     * Method that handles the removal of the empty correspondence addresses before adding the applicant to the flowbean.
     * @param command
     * @param result
     * @param addParameters
     * @return
     */
    private ModelAndView addTM(TMDetailsForm command, BindingResult result, AddParameters addParameters)
    {
        return onSubmit(command, flowBean, addParameters, result);
    }
    
    /**
     * Handles the submission of the abstract form. If the given form is not imported,
     * validation for the form is triggered, and if it finds that the form object is not valid,
     * it returns the form view.
     * 
     * If the object is imported and valid or is not imported: it proceeds by adding it to the flow.
     * If it already exists in the flow, it replaces it with the new version.
     * The success view is returned in this case.
     * 
     * @param command
     * @param flowBean
     * @param addParameters
     * @param result
     * @return modelAndView created from command, flowBean, addParameters and result params
     * @throws SPException
     */
    @Override
    public ModelAndView onSubmit(AbstractImportableForm command, FlowBean flowBean, AddParameters addParameters, BindingResult result){
    	
        logger.info("START onSubmit");
        if (command == null){
        	throw new SPException("Command is null", EServicesConstants.ERROR_GE);
        }  
        if (flowBean == null){
        	throw new SPException("Flow is null", EServicesConstants.ERROR_GE);
        }
        if (addParameters == null){
        	throw new SPException("AddParameters is null", EServicesConstants.ERROR_GE);
        }
        if (result == null){
        	 throw new SPException("Binding result is null", EServicesConstants.ERROR_GE);
        }
           

        ModelAndView modelAndView = null;

        //validate trademark independently if is imported or not
        boolean validObject = validateCommand(command, result, addParameters);
        if (!validObject) {
            modelAndView = new ModelAndView(addParameters.getFormView());
            if (flowBean.existsObject(addParameters.getCommandClass(), command.getId())) {
                modelAndView.addObject("entityPosition",
                        1 + flowBean.getIndex(addParameters.getCommandClass(), command.getId()));
            } else {
                modelAndView.addObject("entityPosition", 1 + flowBean
                        .getCollection(addParameters.getCommandClass()).size());
            }
            return modelAndView;
        }

        //if validation result is good, perform validation for warning messages
        command.setCheckBdBlocking(false);
        //change temporary the imported flag to make validation of warning messages
        Boolean isImported = command.getImported();
        command.setImported(true);
        validateCommand(command, result, addParameters);
        command.setImported(isImported);
        
        this.flowBean.setGoodsServices(new TreeSet<GoodAndServiceForm>());
        this.flowBean.setOriginalGS(new TreeSet<GoodAndServiceForm>());
        this.flowBean.setOwners(new ArrayList<ApplicantForm>());
        modelAndView = new ModelAndView(addParameters.getSuccessView());
        logger.debug("add " + addParameters.getCommandClass());

        boolean isEdit = false;
        try {
            String id = command.getId();

            if (!flowBean.existsObject(addParameters.getCommandClass(), id)) {
                flowBean.addObject(command);
            } else {
                isEdit = true;
                flowBean.replaceObject(command, id);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e);
            throw new SPException("Failed to find any object", e, EServicesConstants.ERROR_GE);
        }

        String applicationType = flowScopeDetails.getFlowModeId();
        TMDetailsForm tmCommand = (TMDetailsForm)command;
        if(("tm-renewal".equalsIgnoreCase(applicationType) || "tm-bankruptcy".equalsIgnoreCase(applicationType)
                || "tm-surrender".equalsIgnoreCase(applicationType)|| "tm-withdrawal".equalsIgnoreCase(applicationType)) && !isEdit) {
            AutomaticImportUtils.copyOwnersToApplicants(tmCommand.getApplicants() != null && tmCommand.getApplicants().size() > 0 ? tmCommand.getApplicants() : null, (ESFlowBean) flowBean, personService, flowScopeDetails);
        } else if ("tm-change".equalsIgnoreCase(applicationType) && !isEdit) {
            flowBean.getCollection(HolderForm.class).clear();
            AutomaticImportUtils.copyOwnersToHolders(tmCommand.getApplicants() != null && tmCommand.getApplicants().size() > 0 ? tmCommand.getApplicants() : null, (ESFlowBean) flowBean, flowScopeDetails, personService, holderFactory);
        }

        if("tm-changerep".equalsIgnoreCase(applicationType) && !isEdit){
            AutomaticImportUtils.copyAllRepresentatives(tmCommand.getRepresentatives() != null && tmCommand.getRepresentatives().size() > 0 ? tmCommand.getRepresentatives() : null, (ESFlowBean) flowBean, personService, flowScopeDetails);
        }

        return modelAndView;
    }    

    /**
     * Handles the adding of an applicant to the flow bean.
     * If the ignore matches flag is true, it just adds the applicant to the flow bean (which also triggers validation).
     * Otherwise, it calls the validation before checking for matches.
     * @param command
     * @param result
     * @param addParameters the add parameters
     * @param ignoreMatches boolean indicating whether to call the match service
     * @return
     */
    private ModelAndView addTMCheckMatches(TMDetailsForm command, BindingResult result, AddParameters addParameters,
                                                  Boolean ignoreMatches)
    {
    	command.setCheckBdBlocking(true);    	
        return addTM(command, result, addParameters);
    }
    
    @Override
    protected String[] resolveMaxNumberProperty() {
    	return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT, 
    	                  ConfigurationServiceDelegatorInterface.TM_ADD_MAXNUMBER};
    }

    @RequestMapping(value="refreshCopiedTMOwnersToOther", method = RequestMethod.GET)
    public ModelAndView refreshCopiedOwners(){
        if("tm-renewal".equals(flowScopeDetails.getFlowModeId()) || "tm-bankruptcy".equals(flowScopeDetails.getFlowModeId())
                || "tm-surrender".equals(flowScopeDetails.getFlowModeId()) || "tm-withdrawal".equals(flowScopeDetails.getFlowModeId())){
            return new ModelAndView("applicant/applicant_card_list");
        } else if("tm-change".equals(flowScopeDetails.getFlowModeId())){
            return new ModelAndView("holder/holder_card_list");
        } else {
            return null;
        }
    }

    @RequestMapping(value="refreshCopiedTMRepresentatives", method = RequestMethod.GET)
    public ModelAndView refreshCopiedRepresentatives(){
        return new ModelAndView("representative/representative_card_list");
    }

}
