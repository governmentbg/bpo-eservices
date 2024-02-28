package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.BackOfficeServiceInterface;
import eu.ohim.sp.eservices.ui.service.interfaces.ESPatentServiceInterface;
import eu.ohim.sp.eservices.ui.util.AutomaticImportUtils;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;
import eu.ohim.sp.eservices.ui.util.GroundsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 12.12.2019
 */
@Controller
public class AddPTController extends AddAbstractController {

    private static final Logger logger = Logger.getLogger(AddPTController.class);

    @Autowired
    private ESPatentServiceInterface esPatentService;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private BackOfficeServiceInterface backOfficeService;

    @Autowired
    private HolderFactory holderFactory;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private ESFlowBean flowBean;

    @Autowired
    private GroundsUtil groundsUtil;

    private static final String PATENT_DETAILS_FORM = "patentDetailsForm";
    private static final String PATENT_DETAILS_SECTION = "pt_details/pt_details_section";
    private static final String PATENT_DETAILS_TABLE = "pt_details/pt_card_list";
    public static final String PATENT_DETAILS_WARNING = "pt_details/pt_import_warning";


    @RequestMapping(value = "addPTDetails", method = RequestMethod.GET)
    public ModelAndView addPTDetails(@RequestParam(required = false, value = "id") String id) {
        return innerFormBackingObject(id, flowBean, new AddParameters(ESPatentDetailsForm.class, PATENT_DETAILS_FORM,
            PATENT_DETAILS_SECTION, PATENT_DETAILS_SECTION, getMaxNumber()));
    }

    @RequestMapping(value = "getPTDetailsForEdit", method = RequestMethod.GET)
    public ModelAndView getPTDetailsForEdit(@RequestParam(required = true, value = "id") String id) {
        ESPatentDetailsForm patentDetailsForm = flowBean.getObject(ESPatentDetailsForm.class, id);
        if(patentDetailsForm != null) {
            flowBean.setOwners(patentDetailsForm.getApplicants());
            flowBean.setTmpImportRepresentativesList(patentDetailsForm.getRepresentatives());
        }
        return addPTDetails(id);
    }

    @RequestMapping(value = "importPatent", method = RequestMethod.GET)
    public ModelAndView importPatent(
        @RequestParam(value = "importId", required = false) String patentId,
        @RequestParam(value = "unpublished") boolean unpublished,
        @ModelAttribute(PATENT_DETAILS_FORM) ESPatentDetailsForm patentDetailsForm,
        BindingResult result) {

        if (patentId != null) {

            if (patentDetailsForm == null) {
                return new ModelAndView(PATENT_DETAILS_WARNING);
            } else {
                ESPatentDetailsForm importedForm =null;
                if(unpublished){
                    User user = AuthenticationUtil.getAuthenticatedUser();
                    if (user!=null) {
                        String username = user.getUsername();
                        importedForm = backOfficeService.getUnpublishedPatent(username, patentId, flowBean.getIdreserventity());
                    }
                } else {
                   importedForm = esPatentService.getPatent(patentId, flowScopeDetails.getFlowModeId());
                }

                if(importedForm == null){
                    ModelAndView model = new ModelAndView(PATENT_DETAILS_WARNING);
                    model.addObject("errorCode", "error.import.noObjectFound.patent");
                    return model;
                }

                flowBean.setTmpImportRepresentativesList(importedForm.getRepresentatives());
                flowBean.setOwners(importedForm.getApplicants());
                BeanUtils.copyProperties(importedForm, patentDetailsForm);
                AddParameters addParameters = new AddParameters(ESPatentDetailsForm.class, PATENT_DETAILS_FORM,
                    PATENT_DETAILS_TABLE, PATENT_DETAILS_SECTION, getMaxNumber());

                return loadImportedPT(importedForm, result, addParameters);
            }
        } else {
            ModelAndView modelAndView = new ModelAndView(PATENT_DETAILS_SECTION);
            modelAndView.addObject(PATENT_DETAILS_FORM, new ESPatentDetailsForm());
            return modelAndView;
        }

    }

    @RequestMapping(value = "autocompletePatent", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String autocomplete(@RequestParam(required = false, value = "args") String id) {
        logger.info("START search");
        String results = esPatentService.autocompletePatent(id, 10, flowScopeDetails.getFlowModeId());
        logger.info("END search");
        return results;
    }

    @RequestMapping(value = "addPTDetails", method = RequestMethod.POST)
    public ModelAndView addPTDetails(@ModelAttribute(PATENT_DETAILS_FORM) ESPatentDetailsForm command,
                                          BindingResult result,
                                          @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {

        if (flowBean.getTmpImportRepresentativesList() != null){
            command.setRepresentatives(flowBean.getTmpImportRepresentativesList());
        }
        if (flowBean.getOwners() != null) {
            command.setApplicants(flowBean.getOwners());
        }

        AddParameters addParameters = new AddParameters(ESPatentDetailsForm.class, PATENT_DETAILS_FORM,
            PATENT_DETAILS_TABLE, PATENT_DETAILS_SECTION, getMaxNumber());

        String applicationType = flowScopeDetails.getFlowModeId();

        if( "pt-invalidity".equalsIgnoreCase(applicationType) || "spc-invalidity".equalsIgnoreCase(applicationType) || "um-invalidity".equalsIgnoreCase(applicationType)){
            flowBean.setAvaibleLegalActVersions(groundsUtil.getAvaibleLegalActVersions(command, applicationType));
        }

        return addPTCheckMatches(command, result, addParameters, ignoreMatches);
    }

    @RequestMapping(value="refreshCopiedPTOwnersToOther", method = RequestMethod.GET)
    public ModelAndView refreshCopiedPTOwnersToOther(){
        if(flowScopeDetails.getFlowModeId().endsWith("-bankruptcy") || flowScopeDetails.getFlowModeId().endsWith("-surrender") || flowScopeDetails.getFlowModeId().endsWith("-withdrawal") || flowScopeDetails.getFlowModeId().endsWith("-appeal")  || flowScopeDetails.getFlowModeId().endsWith("-renewal") || flowScopeDetails.getFlowModeId().endsWith("-docchanges") || flowScopeDetails.getFlowModeId().endsWith("-extendterm")) {
            return new ModelAndView("applicant/applicant_card_list");
        } else if(flowScopeDetails.getFlowModeId().endsWith("-change")){
            return new ModelAndView("holder/holder_card_list");
        } else {
            return null;
        }
    }

    @RequestMapping(value="importRepresentativePTImport", method = RequestMethod.GET)
    public ModelAndView refreshCopiedRepresentatives(){
        return new ModelAndView("representative/representative_card_list");
    }

    private ModelAndView addPTCheckMatches(ESPatentDetailsForm command, BindingResult result, AddParameters addParameters,
                                           Boolean ignoreMatches) {
        command.setCheckBdBlocking(true);
        return addPT(command, result, addParameters);
    }

    private ModelAndView loadImportedPT(ESPatentDetailsForm importedForm, BindingResult result, AddParameters addParameters){
        importedForm.setCheckBdBlocking(false);
        validateCommand(importedForm, result, addParameters);

        ModelAndView modelAndView = new ModelAndView(PATENT_DETAILS_SECTION);
        modelAndView.addObject(PATENT_DETAILS_FORM, result.getTarget());

        return modelAndView;
    }

    private ModelAndView addPT(ESPatentDetailsForm command, BindingResult result, AddParameters addParameters) {
        return onSubmit(command, flowBean, addParameters, result);
    }

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
        ESPatentDetailsForm patentForm = (ESPatentDetailsForm)command;
        if (applicationType.endsWith("-change") && !isEdit) {
            AutomaticImportUtils.copyOwnersToHolders(patentForm.getApplicants() != null && patentForm.getApplicants().size() >0 ? patentForm.getApplicants(): null, (ESFlowBean) flowBean, flowScopeDetails, personService, holderFactory);
        } else if((applicationType.endsWith("-bankruptcy") || applicationType.endsWith("-surrender") || applicationType.endsWith("-withdrawal") || applicationType.endsWith("-appeal") || applicationType.endsWith("-renewal")) && !isEdit) {
            AutomaticImportUtils.copyOwnersToApplicants(patentForm.getApplicants() != null && patentForm.getApplicants().size() >0 ? patentForm.getApplicants(): null, (ESFlowBean) flowBean, personService, flowScopeDetails);
        }

        if(applicationType.endsWith("-changerep") && !isEdit){
            AutomaticImportUtils.copyAllRepresentatives(patentForm.getRepresentatives() != null
                    && patentForm.getRepresentatives().size()>0 ? patentForm.getRepresentatives() :null, (ESFlowBean)flowBean, personService, flowScopeDetails);
        }
        //TODO person copying for other services

        return modelAndView;
    }

    @Override
    protected String[] resolveMaxNumberProperty() {
        String maxNumberSource= ConfigurationServiceDelegatorInterface.PATENT_ADD_MAXNUMBER;
        return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT, maxNumberSource};
    }


}
