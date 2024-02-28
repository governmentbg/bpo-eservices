/*******************************************************************************
 * * $Id:: AddApplicantController.java 50771 2012-11-14 15:10:27Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.form.person.UniversityForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.configuration.domain.country.xsd.States;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;

/**
 * Controller in charge of handling the adding and the editing of the applicants.
 * @author ionitdi
 */
@Controller
public class AddApplicantController extends AddAbstractController
{
    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(AddApplicantController.class);
    /**
     * session bean
     */
    @Autowired
    private FlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    /**
     * Gets the maximum number of applicant entities allowed
     * @return Integer with the maximun number
     */
    @Override
    protected String[] resolveMaxNumberProperty() {
        String maxNumberSource=ConfigurationServiceDelegatorInterface.APPLICANT_ADD_MAXNUMBER;
        if(flowScopeDetails != null && flowScopeDetails.getFlowModeId() != null && flowScopeDetails.getFlowModeId().equals("gi-efiling")){
            maxNumberSource=ConfigurationServiceDelegatorInterface.APPLICANT_ADD_MAXNUMBER_GI_EFILING;
        }
        return new String[]{ConfigurationServiceDelegatorInterface.PERSON_COMPONENT, maxNumberSource};
    }
    



    /* --------- APPLICANTS ---------- */
    /* ------------------------------- */

    /**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
    @RequestMapping(value = "addApplicantLegalEntity", method = RequestMethod.GET)
    public ModelAndView formBackingLegalEntity(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(LegalEntityForm.class, "applicantLegalEntityForm",
                "applicant/yourdata_applicant",
                "applicant/applicant_legalentity", getMaxNumber()));

        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Applicant information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @PreAuthorize("hasRole('Applicant_Add')")
    @RequestMapping(value = "addApplicantLegalEntity", method = RequestMethod.POST)
    public ModelAndView onSubmitLegalEntity(@ModelAttribute("applicantLegalEntityForm") LegalEntityForm command,
                                            BindingResult result,
                                            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(LegalEntityForm.class, "applicantLegalEntityForm",
                "applicant/applicant_card_list", "applicant/applicant_legalentity", getMaxNumber());
        ModelAndView model = addApplicantCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
            resetCommandImportedDetails(model, command,
                    ConfigurationServiceDelegatorInterface.RESET_APPLICANT_IMPORTED_KEY);
        }
        return model;
    }

    /**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
    @RequestMapping(value = "addApplicantNaturalPerson", method = RequestMethod.GET)
    public ModelAndView formBackingNaturalPerson(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean,
                new AddParameters(NaturalPersonForm.class, "applicantNaturalPersonForm",
                        "applicant/yourdata_applicant",
                        "applicant/applicant_naturalperson", getMaxNumber()));

        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Applicant information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @PreAuthorize("hasRole('Applicant_Add')")
    @RequestMapping(value = "addApplicantNaturalPerson", method = RequestMethod.POST)
    public ModelAndView onSubmitNaturalPerson(@ModelAttribute("applicantNaturalPersonForm") NaturalPersonForm command,
                                              BindingResult result,
                                              @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(NaturalPersonForm.class, "applicantNaturalPersonForm",
                "applicant/applicant_card_list", "applicant/applicant_naturalperson", getMaxNumber());

        ModelAndView model = addApplicantCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
            resetCommandImportedDetails(result.getAllErrors(), AvailableSection.APPLICANT_NATURALPERSON,
                    model, command,
                    ConfigurationServiceDelegatorInterface.RESET_APPLICANT_IMPORTED_KEY);
        }
        return model;
    }


    /**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
    @RequestMapping(value = "addApplicantNaturalPersonSpecial", method = RequestMethod.GET)
    public ModelAndView formBackingNaturalPersonSpecial(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(NaturalPersonSpecialForm.class,
                "applicantNaturalPersonSpecialForm",
                "applicant/yourdata_applicant",
                "applicant/applicant_naturalpersonspecial", getMaxNumber()));

        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Applicant information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @PreAuthorize("hasRole('Applicant_Add')")
    @RequestMapping(value = "addApplicantNaturalPersonSpecial", method = RequestMethod.POST)
    public ModelAndView onSubmitNaturalPersonSpecial(
            @ModelAttribute("applicantNaturalPersonSpecialForm") NaturalPersonSpecialForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(NaturalPersonSpecialForm.class, "applicantNaturalPersonSpecialForm",
                "applicant/applicant_card_list", "applicant/applicant_naturalpersonspecial",
                getMaxNumber());
        ModelAndView model = addApplicantCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
        }
        return model;
    }

    /**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
    @RequestMapping(value = "addApplicantUniversity", method = RequestMethod.GET)
    public ModelAndView formBackingUniversity(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(UniversityForm.class,
                "applicantUniversityForm",
                "applicant/yourdata_applicant",
                "applicant/applicant_university", getMaxNumber()));

        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Applicant information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @PreAuthorize("hasRole('Applicant_Add')")
    @RequestMapping(value = "addApplicantUniversity", method = RequestMethod.POST)
    public ModelAndView onSubmitUniversity(
            @ModelAttribute("applicantUniversityForm") UniversityForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(UniversityForm.class, "applicantUniversityForm",
                "applicant/applicant_card_list", "applicant/applicant_university",
                getMaxNumber());
        ModelAndView model = addApplicantCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
        }
        return model;
    }

    /**
     * Method that handles the removal of the empty correspondence addresses before adding the applicant to the flowbean.
     * @param command
     * @param result
     * @param addParameters
     * @return
     */
    private ModelAndView addApplicant(ApplicantForm command, BindingResult result, AddParameters addParameters)
    {
        if(command != null)
        {
            command.removeEmptyCorrespondenceAddresses();
        }
        command.setValidateImported(true);
        return onSubmit(command, flowBean, addParameters, result);
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
    private ModelAndView addApplicantCheckMatches(ApplicantForm command, BindingResult result, AddParameters addParameters,
                                                  Boolean ignoreMatches)
    {
        // if the ignoreMatches boolean is set to true
        // add the applicant without checking for matches
        if (ignoreMatches != null && ignoreMatches)
        {
            return addApplicant(command, result, addParameters);
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
        List<ApplicantForm> matches = checkMatches(command);

        // if no matches found, just add the applicant
        if (matches.isEmpty())
        {
            return addApplicant(command, result, addParameters);
        }

        // otherwise, return the matches view
        mv.setViewName("applicant/applicantMatches");
        mv.addObject("applicantMatches", matches);
        return mv;
    }

    /**
     * Returns a view without a model for choosing an applicant type
     *
     * @return the view
     */
    @RequestMapping(value = "chooseApplicantType", method = RequestMethod.GET)
    public ModelAndView chooseApplicantType()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("applicant/applicant_choosetype");
        return mv;
    }

    /**
     * Entry point to get an applicant form for edit.
     * This method figures out the type of the applicant and uses another method that can handle
     * that type of applicant.
     * If the type is not found or there is no such applicant with that id, it returns null.
     * @param id the id of the applicant
     * @return the view containing the applicant form to edit
     */
    @RequestMapping(value = "getApplicantForEdit", method = RequestMethod.GET)
    public ModelAndView getApplicant(@RequestParam(required = true, value = "id") String id)
    {
        ApplicantForm found = flowBean.getObject(ApplicantForm.class, id);
        ModelAndView model = new ModelAndView();
        if (found instanceof LegalEntityForm)
        {
            model = formBackingLegalEntity(id);
            model.addObject("formEdit", "true");
            return model;
        }
        else if (found instanceof NaturalPersonForm)
        {
            model= formBackingNaturalPerson(id);
            model.addObject("formEdit", "true");
            return model;
        }
        else if (found instanceof UniversityForm)
        {
            model=  formBackingUniversity(id);
            model.addObject("formEdit", "true");
            return model;
        }
        else if (found instanceof NaturalPersonSpecialForm)
        {
            model=  formBackingNaturalPersonSpecial(id);
            model.addObject("formEdit", "true");
            return model;
        }
        if(logger.isInfoEnabled())
        {
            logger.info("Applicant type not recognized: " + (found == null ? "null" : found.getType()));
        }
        return null;
    }

    @RequestMapping(value = "getStates", headers = "Accept=*/*", method = RequestMethod.GET)
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
     * to get matches for the given applicant.
     * @param form
     * @param <T>
     * @return
     */
    private <T extends ApplicantForm> List<ApplicantForm> checkMatches(T form)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("form:" + form);
        }
        List<ApplicantForm> matchedApplicants = new ArrayList<ApplicantForm>();

        // only check for matches if the service is enabled
        if (configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value())) {
            int maxResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(
                    ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS));
            matchedApplicants = personService.matchApplicant(form, maxResults);
        }

        return matchedApplicants;
    }
}
