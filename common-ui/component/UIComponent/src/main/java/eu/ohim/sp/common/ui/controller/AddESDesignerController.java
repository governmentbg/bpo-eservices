package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
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
import java.util.List;

@Controller
public class AddESDesignerController extends AddAbstractController {
    private static final Logger logger = Logger.getLogger(AddESDesignerController.class);

    @Autowired
    private FlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;


    @Override
    protected String[] resolveMaxNumberProperty() {
        String maxNumberSource = ConfigurationServiceDelegatorInterface.DESIGN_ADD_MAXNUMBER;
        return new String[]{ConfigurationServiceDelegatorInterface.PERSON_COMPONENT, maxNumberSource};
    }

    @RequestMapping(value = "addDesignerNaturalPerson", method = RequestMethod.GET)
    public ModelAndView formBackingNaturalPerson(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean,
                new AddParameters(DesignerForm.class, "designerForm",
                        "designer/yourdata_designer",
                        "designer/designer_naturalperson", getMaxNumber()));

        return model;
    }

    @PreAuthorize("hasRole('Designer_Add')")
    @RequestMapping(value = "addDesignerNaturalPerson", method = RequestMethod.POST)
    public ModelAndView onSubmitNaturalPerson(@ModelAttribute("designerForm") DesignerForm command,
                                              BindingResult result,
                                              @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(DesignerForm.class, "designerForm",
                "designer/designer_card_list", "designer/designer_naturalperson", getMaxNumber());

        ModelAndView model = addDesignerCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors()) {
            model.addObject("formErrors", "true");
            resetCommandImportedDetails(result.getAllErrors(), AvailableSection.DESIGNERS,
                    model, command,
                    ConfigurationServiceDelegatorInterface.RESET_DESIGNER_IMPORTED_KEY);
        }
        return model;
    }

    private ModelAndView addDesigner(DesignerForm command, BindingResult result, AddParameters addParameters) {
        if (command != null) {
            command.removeEmptyCorrespondenceAddresses();
        }
        command.setValidateImported(true);
        return onSubmit(command, flowBean, addParameters, result);
    }

    private ModelAndView addDesignerCheckMatches(DesignerForm command, BindingResult result, AddParameters addParameters,
                                                 Boolean ignoreMatches) {
        // if the ignoreMatches boolean is set to true
        // add the designer without checking for matches
        if (ignoreMatches != null && ignoreMatches) {
            return addDesigner(command, result, addParameters);
        }

        // otherwise, validate the form before calling the match service
        ModelAndView mv = new ModelAndView();
        boolean validObject = validateCommand(command, result, addParameters);
        if (!validObject) {
            mv.setViewName(addParameters.getFormView());
            return mv;
        } else {
            mv = new ModelAndView();

            // when no model errors are found
            // set the validation trigger to false so that validation is not activated twice
            addParameters.setTriggerValidation(false);
        }

        // call the match service
        List<DesignerForm> matches = checkMatches(command);

        // if no matches found, just add the designer
        if (matches.isEmpty()) {
            return addDesigner(command, result, addParameters);
        }

        // otherwise, return the matches view
        mv.setViewName("designer/designerMatches");
        mv.addObject("designerMatches", matches);
        return mv;
    }

    @RequestMapping(value = "getESDesignerForEdit", method = RequestMethod.GET)
    public ModelAndView getDesigner(@RequestParam(required = true, value = "id") String id) {
        ModelAndView model = formBackingNaturalPerson(id);
        model.addObject("formEdit", "true");
        return model;
    }

    private <T extends DesignerForm> List<DesignerForm> checkMatches(T form) {
        if (logger.isDebugEnabled()) {
            logger.debug("form:" + form);
        }
        List<DesignerForm> matchedDesigners = new ArrayList<DesignerForm>();

        // only check for matches if the service is enabled
        if (configurationServiceDelegator.isServiceEnabled(AvailableServices.DESIGNER_MATCH.value())) {
            int maxResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(
                    ConfigurationServiceDelegatorInterface.DESIGNER_MATCH_MAXRESULTS));
            matchedDesigners = personService.matchDesigner(form, maxResults);
        }

        return matchedDesigners;
    }
}
