package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTPatentServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddPTController extends AddAbstractController {

    private static final Logger logger = Logger.getLogger(AddPTController.class);

    @Autowired
    private PTPatentServiceInterface ptPatentService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private PTFlowBean flowBean;

    private static final String PATENT_DETAILS_FORM = "patentDetailsForm";
    private static final String PATENT_DETAILS_SECTION = "pt_details/pt_details_section";
    private static final String PATENT_DETAILS_TABLE = "pt_details/pt_card_list";
    public static final String PATENT_DETAILS_WARNING = "pt_details/pt_import_warning";


    @RequestMapping(value = "addPTDetails", method = RequestMethod.GET)
    public ModelAndView addPTDetails(@RequestParam(required = false, value = "id") String id) {
        return innerFormBackingObject(id, flowBean, new AddParameters(ESPatentDetailsForm.class, PATENT_DETAILS_FORM,
            PATENT_DETAILS_SECTION, PATENT_DETAILS_SECTION, 1));
    }

    @RequestMapping(value = "getPTDetailsForEdit", method = RequestMethod.GET)
    public ModelAndView getPTDetailsForEdit(@RequestParam(required = true, value = "id") String id) {
        return addPTDetails(id);
    }

    @RequestMapping(value = "importNationalPatent", method = RequestMethod.GET)
    public ModelAndView importNationalPatent(
        @RequestParam(value = "importId", required = false) String patentId,
        @ModelAttribute(PATENT_DETAILS_FORM) ESPatentDetailsForm patentDetailsForm,
        BindingResult result) {

        if (patentId != null) {
            if (patentDetailsForm == null) {
                return new ModelAndView(PATENT_DETAILS_WARNING);
            } else {
                ESPatentDetailsForm importedForm = ptPatentService.getNationalPatent(patentId);
                return importPTCommon(importedForm, patentDetailsForm, result);
            }
        } else {
            return getEmptyPatentDetailsForm();
        }
    }

    @RequestMapping(value = "importEPOPatent", method = RequestMethod.GET)
    public ModelAndView importEPOPatent(
            @RequestParam(value = "importId", required = false) String patentId,
            @ModelAttribute(PATENT_DETAILS_FORM) ESPatentDetailsForm patentDetailsForm,
            BindingResult result) {
        if (patentId != null) {
            if (patentDetailsForm == null) {
                return new ModelAndView(PATENT_DETAILS_WARNING);
            } else {
                ESPatentDetailsForm importedForm = ptPatentService.getEPOPatent(patentId);
                return importPTCommon(importedForm, patentDetailsForm, result);
            }
        } else {
            return getEmptyPatentDetailsForm();
        }
    }

    private ModelAndView importPTCommon(ESPatentDetailsForm importedForm, ESPatentDetailsForm patentDetailsForm, BindingResult result) {
        if(importedForm == null){
            ModelAndView model = new ModelAndView(PATENT_DETAILS_WARNING);
            model.addObject("errorCode", "error.import.noObjectFound.patent");
            return model;
        }
        BeanUtils.copyProperties(importedForm, patentDetailsForm);
        AddParameters addParameters = new AddParameters(ESPatentDetailsForm.class, PATENT_DETAILS_FORM,
                PATENT_DETAILS_TABLE, PATENT_DETAILS_SECTION, 1);

        return loadImportedPT(importedForm, result, addParameters);
    }

    private ModelAndView getEmptyPatentDetailsForm() {
        ModelAndView modelAndView = new ModelAndView(PATENT_DETAILS_SECTION);
        modelAndView.addObject(PATENT_DETAILS_FORM, new ESPatentDetailsForm());
        return modelAndView;
    }

    @RequestMapping(value = "addPTDetails", method = RequestMethod.POST)
    public ModelAndView addPTDetails(@ModelAttribute(PATENT_DETAILS_FORM) ESPatentDetailsForm command,
                                          BindingResult result,
                                          @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ESPatentDetailsForm.class, PATENT_DETAILS_FORM,
            PATENT_DETAILS_TABLE, PATENT_DETAILS_SECTION, 1);
        return addPTCheckMatches(command, result, addParameters, ignoreMatches);
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
            throw new SPException("Command is null", "error.genericError");
        }
        if (flowBean == null){
            throw new SPException("Flow is null", "error.genericError");
        }
        if (addParameters == null){
            throw new SPException("AddParameters is null", "error.genericError");
        }
        if (result == null){
            throw new SPException("Binding result is null", "error.genericError");
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

        command.setCheckBdBlocking(false);
        Boolean isImported = command.getImported();
        command.setImported(true);
        validateCommand(command, result, addParameters);
        command.setImported(isImported);

        if (command.getId() == null && command instanceof ESPatentDetailsForm){
            command.setId(((ESPatentDetailsForm) command).getApplicationNumber());
        }

        modelAndView = new ModelAndView(addParameters.getSuccessView());
        logger.debug("add " + addParameters.getCommandClass());

        try {
            String id = command.getId();

            if (!flowBean.existsObject(addParameters.getCommandClass(), id)) {
                flowBean.addObject(command);
//                this.flowBean.getSpcPatents().add((ESPatentDetailsForm) command);
            } else {
                flowBean.replaceObject(command, id);
//                this.flowBean.setSpcPatents(new ArrayList<>());
//                this.flowBean.getSpcPatents().add((ESPatentDetailsForm) command);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e);
            throw new SPException("Failed to find any object", e, "error.genericError");
        }
        return modelAndView;
    }

    @Override
    protected String[] resolveMaxNumberProperty() {
        String maxNumberSource= ConfigurationServiceDelegatorInterface.PATENT_ADD_MAXNUMBER;
        return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT, maxNumberSource};
    }

}
