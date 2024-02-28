package eu.ohim.sp.eservices.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsListForm;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.eservices.ui.service.interfaces.BackOfficeServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.design.DesignStatusCode;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESDesignService;
import eu.ohim.sp.eservices.ui.util.AutomaticImportUtils;
import eu.ohim.sp.eservices.ui.util.ESDesignDetailsComparator;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;

/**
 * Controller in charge of handling the adding and the editing of the applicants.
 * @author ionitdi
 */
@Controller
public class AddDSController extends AddAbstractController{

    private static final Logger logger = Logger.getLogger(AddDSController.class);

    /**
     * session bean
     */
    @Autowired
    private ESFlowBean flowBean;
    
    @Autowired
    private ESDesignService esDesignService;

    @Autowired
    private BackOfficeServiceInterface backOfficeService;
    
    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationService;

    @Autowired
    private PersonServiceInterface personService;
    
    @Autowired
	private HolderFactory holderFactory;

    @Autowired
    private LegalActVersionFactory legalActVersionFactory;

    @Value("${sp.office}")
    private String office;

    private static final List<DesignStatusCode> DS_STATUSES_ALLOWED = Arrays.asList(new DesignStatusCode[]{DesignStatusCode.FILED, DesignStatusCode.APPLICATION_PUBLISHED, DesignStatusCode.REGISTERED, DesignStatusCode.REGISTERED_AND_FULLY_PUBLISHED, DesignStatusCode.EXPIRING, DesignStatusCode.INVALIDITY_PROCEDURE_PENDING, DesignStatusCode.DESIGN_LAPSED});
    private static final List<DesignStatusCode> DS_STATUSES_ALLOWED_EXTENDED = Arrays.asList(new DesignStatusCode[]{DesignStatusCode.REGISTERED, DesignStatusCode.REGISTERED_AND_FULLY_PUBLISHED, DesignStatusCode.EXPIRING, DesignStatusCode.INVALIDITY_PROCEDURE_PENDING, DesignStatusCode.DESIGN_LAPSED, DesignStatusCode.DESIGN_SURRENDERED});
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
    @RequestMapping(value = "addDSDetails", method = RequestMethod.GET)
    public ModelAndView formBackingDSDetails(@RequestParam(required = false, value = "id") String id)
    {

    	flowBean.clearLocarno();
    	flowBean.clearDesignRepresentationAttachment();
        flowBean.setOwners(new ArrayList<ApplicantForm>());

        if(!"".equals(id) && id != null){
            ESDesignDetailsForm command = flowBean.getObject(ESDesignDetailsForm.class, id);
            if(command!=null){
                List<ApplicantForm> owners = new ArrayList<ApplicantForm>();
                if(command.geteSDesignApplicationData() != null && command.geteSDesignApplicationData().getHolders() !=  null){
                    owners.addAll(command.geteSDesignApplicationData().getHolders());
                }
                flowBean.setOwners(owners);
            }
        }

    	ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(ESDesignDetailsForm.class, EServicesConstants.DS_DF,
                                                                                    EServicesConstants.DS_DS,
                                                                                    EServicesConstants.DS_DS, getMaxNumber()));
    	
        return model;
    }
    
    @RequestMapping(value = "addDSDetails", method = RequestMethod.POST)
    public ModelAndView onSubmitDSDetails(@ModelAttribute(EServicesConstants.DS_DF) ESDesignDetailsForm command,
    										BindingResult result,
                                            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
	    
    	//Add the owners of the mark to the command
    	if(flowBean.getOwners() != null && !flowBean.getOwners().isEmpty()){
    		command.geteSDesignApplicationData().setHolders(flowBean.getOwners());
    	}
    	
    	command.getLocarno().addAll(flowBean.getLocarno());
    	if (command.getImported()){
    		command.setRepresentationAttachment(flowBean.getDesignRepresentationAttachment());
            command.setRepresentatives(flowBean.getTmpImportRepresentativesList());
    	}

    	AddParameters addParameters = new AddParameters(ESDesignDetailsForm.class, EServicesConstants.DS_DF,
                                                        EServicesConstants.DS_CL, EServicesConstants.DS_DS, getMaxNumber());
    	
    	if (command.getRenewalIndicator()== null){
    		command.setRenewalIndicator(Boolean.FALSE);
    	}
    	
        return addDSCheckMatches(command, result, addParameters, ignoreMatches);
    }
    
    /**
     *Recovers the indicated trademark from the TradeMarkService, validated it, 
     *populate the form and defines the view to be show to the user.
     *
     *            object that contains the Priority information.
     * @param result
     *            manage the validation results of the form object
     * @return Priority object view with the new priority added
     */
    @RequestMapping(value = "importDesign", method = RequestMethod.GET)
    public ModelAndView importDSDetails(
    		@RequestParam(value = "importId", required = false) String designId,
            @RequestParam(value = "unpublished") boolean unpublished,
    		@ModelAttribute(EServicesConstants.DS_DF) ESDesignDetailsForm dsDetailsForm,
    		BindingResult result) {

    	ModelAndView modelAndView;
    	AddParameters addParameters = new AddParameters(ESDesignDetailsForm.class, EServicesConstants.DS_DF, EServicesConstants.DS_CL, EServicesConstants.DS_CL, getMaxNumber());
    	if (designId != null) {   		
    		designId = designId.substring(0, designId.lastIndexOf('-') != -1 ? designId.lastIndexOf('-') : designId.length());
    		try{
                List<ESDesignDetailsForm> dsDetailsForms = null;
                if(unpublished){
                    User user = AuthenticationUtil.getAuthenticatedUser();
                    if (user!=null) {
                        String username = user.getUsername();
                        dsDetailsForms = backOfficeService.getUnpublishedDesignApplication(username, designId, flowBean.getIdreserventity());
                    }
                } else {
                    ESDesignDetailsListForm designDetailsListForm = esDesignService.getDesignApplication(designId);
                    if(designDetailsListForm == null) {
                        dsDetailsForms = null;
                    } else {
                        if(designDetailsListForm.containsApplications() && designDetailsListForm.hasMultipleApplications()){
                            modelAndView = new ModelAndView("designs/ds_choose_application");
                            modelAndView.addObject("applicationsList", designDetailsListForm.getListApplications());
                            modelAndView.addObject("registrationNumber", designId);
                            return modelAndView;
                        } else {
                            dsDetailsForms = designDetailsListForm.getDesignDetailsFormList();
                        }
                    }
                }
    			modelAndView = commonPostImportProcessing(dsDetailsForms, addParameters, result);
    		}
    		catch (SPException e) {
    			modelAndView = new ModelAndView(EServicesConstants.DS_IW);
    		}

    	} else {
    		// blank form
    		modelAndView = new ModelAndView("designs/ds_import");
    		modelAndView.addObject("dsDetailsForm", new ESDesignDetailsForm());
    	}

    	return modelAndView;
    }

    @RequestMapping(value = "importDesignByApplicationId", method = RequestMethod.GET)
    public ModelAndView importDesignByApplicationId(
            @RequestParam(value = "applicationId") String applicationId,
            @ModelAttribute(EServicesConstants.DS_DF) ESDesignDetailsForm dsDetailsForm,
            BindingResult result) {
        List<ESDesignDetailsForm> dsDetailsForms = null;
        AddParameters addParameters = new AddParameters(ESDesignDetailsForm.class, EServicesConstants.DS_DF, EServicesConstants.DS_CL, EServicesConstants.DS_CL, getMaxNumber());
        ESDesignDetailsListForm designDetailsListForm = esDesignService.getDesignApplicationByApplicationId(applicationId);
        if(designDetailsListForm != null && designDetailsListForm.containsApplications() && !designDetailsListForm.hasMultipleApplications()) {
            dsDetailsForms = designDetailsListForm.getDesignDetailsFormList();
        } else {
            return new ModelAndView(EServicesConstants.DS_IW);
        }
        ModelAndView modelAndView = commonPostImportProcessing(dsDetailsForms, addParameters, result);
        return modelAndView;
    }

    @RequestMapping(value = "autocompleteDesign", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String autocomplete(
            @RequestParam(required = false, value = "args") String id) {
        logger.info("START search");
        String results = esDesignService.getDesignAutocomplete(this.office, id, 10, flowScopeDetails.getFlowModeId());
        logger.info("END search");
        return results;
    }

    @RequestMapping(value = "autocompleteEarlierDesign", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String autocompleteEarlier(
            @RequestParam(required = false, value = "args") String id) {
        logger.info("START search");
        String results = esDesignService.getDesignAutocomplete(this.office, id, 10, "ds-");
        logger.info("END search");
        return results;
    }

    private ModelAndView commonPostImportProcessing(List<ESDesignDetailsForm> dsDetailsForms, AddParameters addParameters, BindingResult result){
        ModelAndView modelAndView;
        if(dsDetailsForms != null && dsDetailsForms.size()>0){
            String applicationType = flowScopeDetails.getFlowModeId();
            for(ESDesignDetailsForm dsForm: dsDetailsForms){
                if("ds-invalidity".equalsIgnoreCase(applicationType) && !DS_STATUSES_ALLOWED_EXTENDED.contains(dsForm.getDsStatus())){
                    dsForm.setSelected(false);
                } else if(!"ds-invalidity".equalsIgnoreCase(applicationType) && !("ds-generic").equalsIgnoreCase(applicationType) &&
                        !("ds-changerep").equalsIgnoreCase(applicationType) && !("ds-changeca").equalsIgnoreCase(applicationType) && !DS_STATUSES_ALLOWED.contains(dsForm.getDsStatus())) {
                    dsForm.setSelected(false);
                }
                dsForm.setImported(true);
                flowBean.addObject(dsForm);
                // add representatives
                        /*for (ESDesignDetailsForm detailsForm : flowBean.getDssList()) {
                            if (detailsForm.getDesignIdentifier().equals(dsForm.getDesignIdentifier())) {
                                detailsForm.setRepresentatives(dsForm.getRepresentatives());
                            }
                        }*/
            }
            if(flowBean.getDssList().size() >0  && flowBean.getDssList().get(0) != null){
                validateCommand(flowBean.getDssList().get(0), result, addParameters);
            }

            Collections.sort(flowBean.getDssList(), new ESDesignDetailsComparator());

            if( "ds-invalidity".equalsIgnoreCase(applicationType)){
                flowBean.refreshRelativeGroundsNoFilter(configurationService, applicationType, legalActVersionFactory);
            }

            if(dsDetailsForms.get(0) != null){
                applicationType = flowScopeDetails.getFlowModeId();
                ESDesignDetailsForm dsCommand = dsDetailsForms.get(0);
                boolean isEdit = false;
                if(("ds-renewal".equalsIgnoreCase(applicationType) || "ds-bankruptcy".equalsIgnoreCase(applicationType) || "ds-surrender".equalsIgnoreCase(applicationType) || "ds-appeal".equalsIgnoreCase(applicationType) || "ds-withdrawal".equalsIgnoreCase(applicationType)) && !isEdit) {
                    AutomaticImportUtils.copyOwnersToApplicants(dsCommand.geteSDesignApplicationData().getHolders() != null && dsCommand.geteSDesignApplicationData().getHolders().size() >0 ? dsCommand.geteSDesignApplicationData().getHolders(): null, (ESFlowBean) flowBean, personService, flowScopeDetails);
                } else if ("ds-change".equalsIgnoreCase(applicationType) && !isEdit) {
                    AutomaticImportUtils.copyOwnersToHolders(dsCommand.geteSDesignApplicationData().getHolders() != null && dsCommand.geteSDesignApplicationData().getHolders().size() >0 ? dsCommand.geteSDesignApplicationData().getHolders(): null, (ESFlowBean) flowBean, flowScopeDetails, personService, holderFactory);
                }

                if("ds-changerep".equalsIgnoreCase(applicationType) && !isEdit){
                    AutomaticImportUtils.copyAllRepresentatives(dsCommand.geteSDesignApplicationData() != null && dsCommand.geteSDesignApplicationData().getRepresentatives() != null
                            && dsCommand.geteSDesignApplicationData().getRepresentatives().size()>0 ? dsCommand.geteSDesignApplicationData().getRepresentatives():null, (ESFlowBean)flowBean, personService, flowScopeDetails);
                }
            }

            modelAndView = new ModelAndView(EServicesConstants.DS_CL);
        } else {
            modelAndView = new ModelAndView(EServicesConstants.DS_IW);
        }
        return modelAndView;
    }
   
    /**
     *Auxiliary method to add elements need it in the the @flowBean from the DS imported 
     *and create the ModelAndView to be returned.
     * @return ModelAndView
     */
    private ModelAndView loadImportedDS(ESDesignDetailsForm importedDS, BindingResult result, AddParameters addParameters){
    	importedDS.setCheckBdBlocking(false);
    	importedDS.setImported(true);    	
    	
    	if(importedDS.geteSDesignApplicationData().getHolders() != null && importedDS.geteSDesignApplicationData().getHolders().size() >0){
    		for(ApplicantForm app: importedDS.geteSDesignApplicationData().getHolders()){
    			app.setImported(true);
    		}
    	}

    	//boolean validObject = validateCommand(importedTM, result, addParameters)
		
        ModelAndView modelAndView = new ModelAndView(EServicesConstants.DS_DS);
		modelAndView.addObject("dsDetailsForm", result.getTarget());
		
		return modelAndView;		
    }    
    
    
    @RequestMapping(value = "addHolderDetails", method = RequestMethod.GET)
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
    		
    		modelAndView = new ModelAndView(EServicesConstants.DS_DH);
    	}
    	else{
    		modelAndView = new ModelAndView(EServicesConstants.DS_HW);
    	}
    	    	
        return modelAndView;
    }
    
    
    @RequestMapping(value = "removeHolderDSDetails", method = RequestMethod.GET)
    public ModelAndView removeHolder(
    		@RequestParam(required = false, value = "name") String name,
    		@RequestParam(required = false, value = "domicile") String domicile)
    {
    	if((name != null && !"".equals(name)) || domicile != null){
    		ApplicantForm holderForm = flowBean.getApplicant(name, domicile);
    		if(holderForm!=null) {
    			flowBean.getOwners().remove(holderForm);
    		}
    	}
    	ModelAndView modelAndView = new ModelAndView(EServicesConstants.DS_DH);
    	
        return modelAndView;
    }
    
    @RequestMapping(value = "getDSDetailsForEdit", method = RequestMethod.GET)
    public ModelAndView getDS(@RequestParam(required = true, value = "id") String id)
    {
        flowBean.clearLocarno();
    	ModelAndView model = formBackingDSDetails(id);
    	ESDesignDetailsForm eSD =  (ESDesignDetailsForm) model.getModel().get(EServicesConstants.DS_DF);
    	flowBean.getLocarno().addAll(eSD.getLocarno());
    	flowBean.setDesignRepresentationAttachment(eSD.getRepresentationAttachment());
    	return model;
    }
    
    
    private ModelAndView addDSCheckMatches(ESDesignDetailsForm command, BindingResult result, AddParameters addParameters,
            Boolean ignoreMatches)
	{
		command.setCheckBdBlocking(true);    	
		return addDS(command, result, addParameters);
	}
    
    private ModelAndView addDS(ESDesignDetailsForm command, BindingResult result, AddParameters addParameters)
    {

        return onSubmit(command, flowBean, addParameters, result);
    }
    
    @RequestMapping(value = "cleanHoldersDSList", method = RequestMethod.GET)
    public ModelAndView cleanOwners()
    {
    	flowBean.setOwners(new ArrayList<ApplicantForm>());
    	ModelAndView modelAndView = new ModelAndView(EServicesConstants.DS_DH);
    	
        return modelAndView;
    }
    
    @Override
    public ModelAndView onSubmit(AbstractImportableForm command, FlowBean flowBean, AddParameters addParameters,
            BindingResult result) throws SPException {
        
        if (command == null){
            throw new SPException("Command is null", "error.genericError");
        }if (flowBean == null){
            throw new SPException("Flow is null", "error.genericError");
        }if (addParameters == null){
            throw new SPException("AddParameters is null", "error.genericError");
        }if (result == null){
            throw new SPException("Binding result is null", "error.genericError");
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
        
       
        String applicationType = flowScopeDetails.getFlowModeId();
        ESDesignDetailsForm dsCommand = (ESDesignDetailsForm)command;
        boolean isEdit = flowBean.existsObject(addParameters.getCommandClass(), command.getId());
        if(("ds-renewal".equalsIgnoreCase(applicationType) || "ds-bankruptcy".equalsIgnoreCase(applicationType) || "ds-surrender".equalsIgnoreCase(applicationType)  || "ds-appeal".equalsIgnoreCase(applicationType) || "ds-withdrawal".equalsIgnoreCase(applicationType)) && !isEdit) {
    		AutomaticImportUtils.copyOwnersToApplicants(dsCommand.geteSDesignApplicationData().getHolders() != null && dsCommand.geteSDesignApplicationData().getHolders().size() >0 ? dsCommand.geteSDesignApplicationData().getHolders(): null, (ESFlowBean) flowBean, personService, flowScopeDetails);
    	} else if ("ds-change".equalsIgnoreCase(applicationType) && !isEdit) {
    		AutomaticImportUtils.copyOwnersToHolders(dsCommand.geteSDesignApplicationData().getHolders() != null && dsCommand.geteSDesignApplicationData().getHolders().size() >0 ? dsCommand.geteSDesignApplicationData().getHolders(): null, (ESFlowBean) flowBean, flowScopeDetails, personService, holderFactory);
    	}

        if("ds-changerep".equalsIgnoreCase(applicationType) && !isEdit){
            AutomaticImportUtils.copyAllRepresentatives(dsCommand.geteSDesignApplicationData() != null && dsCommand.geteSDesignApplicationData().getRepresentatives() != null
                    && dsCommand.geteSDesignApplicationData().getRepresentatives().size()>0 ? dsCommand.geteSDesignApplicationData().getRepresentatives():null, (ESFlowBean)flowBean, personService, flowScopeDetails);
        }
        
        this.flowBean.setOwners(new ArrayList<ApplicantForm>());
        modelAndView = new ModelAndView(addParameters.getSuccessView());
        

        try {
            String id = command.getId();

            if (!flowBean.existsObject(addParameters.getCommandClass(), id)) {
                flowBean.addObject(command);
            } else {
                flowBean.replaceObject(command, id);
            }
        } catch (IllegalArgumentException e) {
            
            throw new SPException("Failed to find any object", e, "error.genericError");
        }

        return modelAndView;
    }    
    
    @Override
    protected String[] resolveMaxNumberProperty() {
    	return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT, 
    	                  ConfigurationServiceDelegatorInterface.DS_ADD_MAXNUMBER};
    }
    
    @RequestMapping(value="refreshCopiedDSOwnersToOther", method = RequestMethod.GET)
    public ModelAndView refreshCopiedOwnerrs(){
    	if("ds-renewal".equals(flowScopeDetails.getFlowModeId())  
    			 || "ds-bankruptcy".equals(flowScopeDetails.getFlowModeId()) || "ds-withdrawal".equals(flowScopeDetails.getFlowModeId()) || "ds-surrender".equals(flowScopeDetails.getFlowModeId())  || "ds-appeal".equals(flowScopeDetails.getFlowModeId())){
    		return new ModelAndView("applicant/applicant_card_list");
    	} else if("ds-change".equals(flowScopeDetails.getFlowModeId())){
    		return new ModelAndView("holder/holder_card_list");
    	} else {
    		return null;
    	}
    }

    @RequestMapping(value="importRepresentativeDSImport", method = RequestMethod.GET)
    public ModelAndView refreshCopiedRepresentatives(){
        return new ModelAndView("representative/representative_card_list");
    }
    
    @RequestMapping(value="changeDesignChecked", method = RequestMethod.POST)
    public ModelAndView changeDesignChecked(@RequestParam(required = true, value="selected")Boolean selected, @RequestParam(required = true, value = "id") String id){
    	ESDesignDetailsForm design = flowBean.getObject(ESDesignDetailsForm.class, id);
    	if(design!= null){
    		design.setSelected(selected);
    		if(design.getSelected()){
    			if("ds-invalidity".equals(flowScopeDetails.getFlowModeId()) && !DS_STATUSES_ALLOWED_EXTENDED.contains(design.getDsStatus())){
    				return new ModelAndView("designs/ds_select_warning");
    			} else if(!"ds-invalidity".equals(flowScopeDetails.getFlowModeId()) && !"ds-generic".equals(flowScopeDetails.getFlowModeId()) &&
                        !"ds-withdrawal".equals(flowScopeDetails.getFlowModeId()) && !"ds-appeal".equals(flowScopeDetails.getFlowModeId()) &&
                        !"ds-changerep".equals(flowScopeDetails.getFlowModeId())&& !"ds-changeca".equals(flowScopeDetails.getFlowModeId())&& !DS_STATUSES_ALLOWED.contains(design.getDsStatus())){
    				return new ModelAndView("designs/ds_select_warning");
    			}
    		}
    	}
    	return null;
    }

}
