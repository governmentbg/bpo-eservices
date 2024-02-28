package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controller in charge of handling the adding and the editing of the inventors.
 * 
 * @author raya copied from serrajo's AddDesignerController.java
 */
@Controller
public class AddInventorController extends AddAbstractController {

    private static final String INVENTOR_INVENTOR_WAIVER = "inventor/inventor_waiver";
    private static final String INVENTOR_INVENTOR_NOTAGROUP = "inventor/inventor_notagroup";
    private static final String INVENTOR_INVENTOR_GROUP = "inventor/inventor_group";
    private static final String MODEL_INVENTOR_FORM = "inventorForm";
    private static final String VIEW_INVENTOR_TABLE = "inventor/inventor_card_list";

    private static Logger logger = Logger.getLogger(AddInventorController.class);

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private PTFlowBean flowBean;

    @Autowired
    private PersonServiceInterface personService;

    private Integer maxNumber = -1;


    @Override
    public Integer getMaxNumber() {
        if (maxNumber == -1) {
            maxNumber = getIntegerSetting(configurationServiceDelegator,
                    ConfigurationServiceDelegatorInterface.INVENTOR_ADD_MAXNUMBER,
                    ConfigurationServiceDelegatorInterface.PERSON_COMPONENT);
        }
        return maxNumber;
    }

    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "displayInventorWaiver", method = RequestMethod.GET)
    public ModelAndView formWaiver(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = getModelAndView(id, INVENTOR_INVENTOR_WAIVER);
        InventorForm inventorForm = getInventorFromModel(model);
        inventorForm.setWaiver(true);
        return model;
    }

    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "displayInventorGroup", method = RequestMethod.GET)
    public ModelAndView formBelongsToAGroup(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = getModelAndView(id, INVENTOR_INVENTOR_GROUP);
        InventorForm inventorForm = getInventorFromModel(model);
        inventorForm.setBelongsToAGroup(true);
        return model;
    }


    @RequestMapping(value = "displayInventorNotAGroup", method = RequestMethod.GET)
    public ModelAndView formNotBelongsToAGroup(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = getModelAndView(id, INVENTOR_INVENTOR_NOTAGROUP);
        return model;
    }


    private ModelAndView getModelAndView(String id, String formView) {
        return innerFormBackingObject(id, flowBean, new AddParameters(InventorForm.class, MODEL_INVENTOR_FORM, null,
                formView, getMaxNumber()));
    }


    private InventorForm getInventorFromModel(ModelAndView modelAndView) {
        return (InventorForm) modelAndView.getModelMap().get(MODEL_INVENTOR_FORM);
    }


    @RequestMapping(value = "getInventorForEdit", method = RequestMethod.GET)
    public ModelAndView getInventor(@RequestParam(required = true, value = "id") String id) {
        InventorForm found = flowBean.getObject(InventorForm.class, id);
        ModelAndView mav = null;
        if (found != null) {
            found.setMayWaiver(flowBean.getInventors().stream().anyMatch(e -> e.isWaiver()));
            if (found.isBelongsToAGroup()) {
                mav = getModelAndView(id, INVENTOR_INVENTOR_GROUP);
            } else {
                mav = getModelAndView(id, INVENTOR_INVENTOR_NOTAGROUP);
            }
        }
        return mav;
    }


    @RequestMapping(value = "addInventor", method = RequestMethod.POST)
    @PreAuthorize("hasRole('Inventor_Add')")
    public ModelAndView onSubmitInventor(@ModelAttribute(MODEL_INVENTOR_FORM) InventorForm command, BindingResult result,
                                         @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        // modified, the inventor waiver is not admitted now. just group or not group. waiver goes to a group view
        String formView = this.setInventorFormGroup(command);
        AddParameters addParameters = new AddParameters(InventorForm.class, MODEL_INVENTOR_FORM, VIEW_INVENTOR_TABLE,
                formView, getMaxNumber());

        // just in case the inventor hasnt checked the waiver but has files attached, they will be deleted
        this.checkInventorAttachmentsToDelete(command);

        ModelAndView mv = addInventorCheckMatches(command, result, addParameters, ignoreMatches);

        if(result.hasErrors()){
            if(result.hasErrors()){
                resetCommandImportedDetails(mv, command,
                        ConfigurationServiceDelegatorInterface.RESET_INVENTOR_IMPORTED_KEY);
            }
        }
        return mv;
    }

    private void checkInventorAttachmentsToDelete(InventorForm found) {
        if (!found.isWaiver() && found.getInventorAttachment() != null) {
            Iterator<StoredFile> it = found.getInventorAttachment().getStoredFiles().iterator();
            while (it.hasNext()) {
                it.next();
                it.remove();
                break;
            }

            if (found.getInventorAttachment().getStoredFiles().size() == 0) {
                found.getInventorAttachment().setAttachment(false);
            }

        }
    }

    private String setInventorFormGroup(InventorForm command) {
        String formView = INVENTOR_INVENTOR_GROUP;
        if (!command.isBelongsToAGroup()) {
            formView = INVENTOR_INVENTOR_NOTAGROUP;
        }
        return formView;
    }


    private ModelAndView addInventor(InventorForm command, BindingResult result, AddParameters addParameters) {
        if (command != null) {
            command.removeEmptyCorrespondenceAddresses();
        }
        return onSubmit(command, flowBean, addParameters, result);
    }

    private ModelAndView addInventorCheckMatches(InventorForm command, BindingResult result, AddParameters addParameters,
                                                 Boolean ignoreMatches)
    {
        // if the ignoreMatches boolean is set to true
        // add the inventor without checking for matches
        if (ignoreMatches != null && ignoreMatches)
        {
            return addInventor(command, result, addParameters);
        }

        // otherwise, validate the form before calling the match service
        ModelAndView mv = new ModelAndView();
        boolean validObject = validateCommand(command, result, addParameters);
        if (!validObject)
        {
            mv.setViewName(addParameters.getFormView());
            return mv;
        }
        else
        {
            mv = new ModelAndView();

            // when no model errors are found
            // set the validation trigger to false so that validation is not activated twice
            addParameters.setTriggerValidation(false);
        }

        // call the match service
        List<InventorForm> matches = checkMatches(command);

        // if no matches found, just add the inventor
        if (matches.isEmpty())
        {
            return addInventor(command, result, addParameters);
        }

        // otherwise, return the matches view
        mv.setViewName("inventor/inventorMatches");
        mv.addObject("inventorMatches", matches);
        return mv;
    }

    private <T extends InventorForm> List<InventorForm> checkMatches(T form)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("form:" + form);
        }
        List<InventorForm> inventorMatches = new ArrayList<InventorForm>();

        // only check for matches if the service is enabled
        if (configurationServiceDelegator.isServiceEnabled(AvailableServices.INVENTOR_MATCH.value())) {
            int maxResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.INVENTOR_MATCH_MAXRESULTS));
            inventorMatches = personService.matchInventor(form, maxResults);
        }

        return inventorMatches;
    }

}
