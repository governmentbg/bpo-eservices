/*******************************************************************************
 * * $Id:: AddAssigneeController.java 50771 2012-11-14 15:10:27Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.AssigneeFactory;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.form.person.AssigneeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.AssigneeNaturalPersonForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.configuration.domain.country.xsd.States;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.domain.person.Holder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller in charge of handling the adding and the editing of the assignees.
 * @author ionitdi
 */
@Controller
public class AddAssigneeController extends AddAbstractController
{
    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(AddAssigneeController.class);
    /**
     * session bean
     */
    @Autowired
    private FlowBean flowBean;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private AssigneeFactory assigneeFactory;

    @Autowired
    private ApplicantFactory applicantFactory;

    /**
     * Gets the maximum number of assignee entities allowed
     * @return Integer with the maximun number
     */
    @Override
    protected String[] resolveMaxNumberProperty() {
        String maxNumberSource=ConfigurationServiceDelegatorInterface.ASSIGNEE_ADD_MAXNUMBER;
        return new String[]{ConfigurationServiceDelegatorInterface.PERSON_COMPONENT, maxNumberSource};
    }



    /* --------- ASSIGNEES ---------- */
    /* ------------------------------- */

    /**
     * It returns a new Assignee obj if no parameters are passed, so a new
     * Assignee to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the assignee
     */
    @RequestMapping(value = "addAssigneeLegalEntity", method = RequestMethod.GET)
    public ModelAndView formBackingLegalEntity(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(AssigneeLegalEntityForm.class, "assigneeLegalEntityForm",
                "assignee/yourdata_assignee",
                "assignee/assignee_legalentity", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Assignee information.
     * @param result  manage the validation results of the form object
     * @return Assignee object view with the new assignee added
     */
    @PreAuthorize("hasRole('Assignee_Add')")
    @RequestMapping(value = "addAssigneeLegalEntity", method = RequestMethod.POST)
    public ModelAndView onSubmitLegalEntity(@ModelAttribute("assigneeLegalEntityForm") AssigneeLegalEntityForm command,
                                            BindingResult result,
                                            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(AssigneeLegalEntityForm.class, "assigneeLegalEntityForm",
                "assignee/assignee_card_list", "assignee/assignee_legalentity", getMaxNumber());


        ModelAndView model = addAssigneeCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
            resetCommandImportedDetails(model, command,
                    ConfigurationServiceDelegatorInterface.RESET_ASSIGNEE_IMPORTED_KEY);
        }
        return model;

    }

    /**
     * It returns a new Assignee obj if no parameters are passed, so a new
     * Assignee to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the assignee
     */
    @RequestMapping(value = "addAssigneeNaturalPerson", method = RequestMethod.GET)
    public ModelAndView formBackingNaturalPerson(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean,
                new AddParameters(AssigneeNaturalPersonForm.class, "assigneeNaturalPersonForm",
                        "assignee/yourdata_assignee",
                        "assignee/assignee_naturalperson", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Assignee information.
     * @param result  manage the validation results of the form object
     * @return Assignee object view with the new assignee added
     */
    @PreAuthorize("hasRole('Assignee_Add')")
    @RequestMapping(value = "addAssigneeNaturalPerson", method = RequestMethod.POST)
    public ModelAndView onSubmitNaturalPerson(@ModelAttribute("assigneeNaturalPersonForm") AssigneeNaturalPersonForm command,
                                              BindingResult result,
                                              @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(AssigneeNaturalPersonForm.class, "assigneeNaturalPersonForm",
                "assignee/assignee_card_list", "assignee/assignee_naturalperson", getMaxNumber());

        ModelAndView model = addAssigneeCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
            resetCommandImportedDetails(model, command,
                    ConfigurationServiceDelegatorInterface.RESET_ASSIGNEE_IMPORTED_KEY);
        }
        return model;

    }

    /**
     * It returns a new Assignee obj if no parameters are passed, so a new
     * Assignee to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the assignee
     */
    /*@RequestMapping(value = "addAssigneeNaturalPersonSpecial", method = RequestMethod.GET)
    public ModelAndView formBackingNaturalPersonSpecial(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(AssigneeNaturalPersonSpecialForm.class,
                                                                                    "assigneeNaturalPersonSpecialForm",
                                                                                    "assignee/yourdata_assignee",
                                                                                    "assignee/assignee_naturalpersonspecial", getMaxNumber()));
        return model;
    }*/

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Assignee information.
     * @param result  manage the validation results of the form object
     * @return Assignee object view with the new assignee added
     */
    /*@RequestMapping(value = "addAssigneeNaturalPersonSpecial", method = RequestMethod.POST)
    public ModelAndView onSubmitNaturalPersonSpecial(
            @ModelAttribute("assigneeNaturalPersonSpecialForm") NaturalPersonSpecialForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(NaturalPersonSpecialForm.class, "assigneeNaturalPersonSpecialForm",
                                                        "assignee/assignee_card_list", "assignee/assignee_naturalpersonspecial",
                                                        getMaxNumber());
        return addAssigneeCheckMatches(command, result, addParameters, ignoreMatches);
    }*/

    /**
     * Method that handles the removal of the empty correspondence addresses before adding the assignee to the flowbean.
     * @param command
     * @param result
     * @param addParameters
     * @return
     */
    private ModelAndView addAssignee(AssigneeForm command, BindingResult result, AddParameters addParameters)
    {
        if(command != null)
        {
            command.removeEmptyCorrespondenceAddresses();
        }
        command.setValidateImported(true);
        return onSubmit(command, flowBean, addParameters, result);
    }

    /**
     * Handles the adding of an assignee to the flow bean.
     * If the ignore matches flag is true, it just adds the assignee to the flow bean (which also triggers validation).
     * Otherwise, it calls the validation before checking for matches.
     * @param command
     * @param result
     * @param addParameters the add parameters
     * @param ignoreMatches boolean indicating whether to call the match service
     * @return
     */
    private ModelAndView addAssigneeCheckMatches(AssigneeForm command, BindingResult result, AddParameters addParameters,
                                                 Boolean ignoreMatches)
    {
        // if the ignoreMatches boolean is set to true
        // add the assignee without checking for matches
        if (ignoreMatches != null && ignoreMatches)
        {
            return addAssignee(command, result, addParameters);
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
        List<AssigneeForm> matches = checkMatches(command);

        // if no matches found, just add the assignee
        if (matches.isEmpty())
        {
            return addAssignee(command, result, addParameters);
        }

        // otherwise, return the matches view
        mv.setViewName("assignee/assigneeMatches");
        mv.addObject("assigneeMatches", matches);
        return mv;
    }

    /**
     * Returns a view without a model for choosing an assignee type
     *
     * @return the view
     */
    @RequestMapping(value = "chooseAssigneeType", method = RequestMethod.GET)
    public ModelAndView chooseAssigneeType()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("assignee/assignee_choosetype");
        return mv;
    }

    /**
     * Entry point to get an assignee form for edit.
     * This method figures out the type of the assignee and uses another method that can handle
     * that type of assignee.
     * If the type is not found or there is no such assignee with that id, it returns null.
     * @param id the id of the assignee
     * @return the view containing the assignee form to edit
     */
    @RequestMapping(value = "getAssigneeForEdit", method = RequestMethod.GET)
    public ModelAndView getAssignee(@RequestParam(required = true, value = "id") String id)
    {
        AssigneeForm found = flowBean.getObject(AssigneeForm.class, id);

        if (found instanceof AssigneeLegalEntityForm)
        {
            ModelAndView model = formBackingLegalEntity(id);
            model.addObject("formEdit", "true");
            return model;
        }
        else if (found instanceof AssigneeNaturalPersonForm)
        {
            ModelAndView model= formBackingNaturalPerson(id);
            model.addObject("formEdit", "true");
            return model;
        }
        if(logger.isInfoEnabled())
        {
            logger.info("Assignee type not recognized: " + (found == null ? "null" : found.getType()));
        }
        return null;
    }

    @RequestMapping(value = "getAssigneeStates", headers = "Accept=*/*", method = RequestMethod.GET)
    public @ResponseBody byte[] getStates(@RequestParam(required = true, value="country") String countryCode)
    {
        List<States.Country.State> states = configurationServiceDelegator.getStates(countryCode);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        //		mapper.getSerializationConfig().setSerializationInclusion(JsonS)
        try {
            mapper.writeValue(baos, states);
        } catch (JsonGenerationException e) {
            logger.error(e);
        } catch (JsonMappingException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }

        return baos.toByteArray();
    }

    /**
     * Method which calls an external service, if it is enabled in the configuration,
     * to get matches for the given assignee.
     * @param form
     * @param <T>
     * @return
     */
    private <T extends AssigneeForm> List<AssigneeForm> checkMatches(T form)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("form:" + form);
        }
        List<AssigneeForm> matchedAssignees = new ArrayList<AssigneeForm>();

        // only check for matches if the service is enabled
        if (configurationServiceDelegator.isServiceEnabled(AvailableServices.ASSIGNEE_MATCH.value())) {
            int maxResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(
                    ConfigurationServiceDelegatorInterface.ASSIGNEE_MATCH_MAXRESULTS));
            List<ApplicantForm> matchedApplicantForms = personService.matchApplicant(applicantFactory.convertFromAssigneeForm(form), maxResults);
            if(matchedApplicantForms != null && matchedApplicantForms.size()>0){
                matchedApplicantForms.stream().forEach(applicantForm -> matchedAssignees.add(
                    assigneeFactory.convertFromApplicantForm(applicantForm)));
            }
        }

        return matchedAssignees;
    }
}
