/*******************************************************************************
 * * $Id:: AddRepresentativeController.java 50771 2012-11-14 15:10:27Z karalch   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.form.person.*;
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

import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;

/**
 * Controller in charge of handling the adding and the editing of the representatives.
 * @author ionitdi
 */
@Controller
public class AddRepresentativeController extends AddAbstractController {

    private static final Logger logger = Logger.getLogger(AddRepresentativeController.class);

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
    private ConfigurationService systemConfigurationService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    /**
     * Gets the maximum number of representative entities allowed
     * @return Integer with the maximum number
     */
    @Override
    protected String[] resolveMaxNumberProperty() {
        String maxNumberSource=ConfigurationServiceDelegatorInterface.REPRESENTATIVE_ADD_MAXNUMBER;
        return new String[]{ConfigurationServiceDelegatorInterface.PERSON_COMPONENT, maxNumberSource};
    }

	/* --------- REPRESENTATIVES ---------- */
	/* ------------------------------------ */

    /**
     * It returns a new Representative obj if no parameters are passed, so a new
     * Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addRepresentativeEmployeeRepresentative", method = RequestMethod.GET)
    public ModelAndView formBackingEmployeeRepresentative(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(EmployeeRepresentativeForm.class,
                "representativeEmployeeRepresentativeForm", "representative/yourdata_representative",
                "representative/representative_employeerepresentative", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Representative object view with the new representative added
     */
    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeEmployeeRepresentative", method = RequestMethod.POST)
    public ModelAndView onSubmitEmployeeRepresentative(
            @ModelAttribute("representativeEmployeeRepresentativeForm") EmployeeRepresentativeForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(EmployeeRepresentativeForm.class,
                "representativeEmployeeRepresentativeForm",
                "representative/representative_card_list",
                "representative/representative_employeerepresentative",
                getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    public ModelAndView formBackingRepIntlPRepresentative(String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(
                IntlPRepresentativeForm.class, "representativeIntlPRepresentativeForm",
                "representative/representative_intlprepresentative", "representative/representative_intlprepresentative", getMaxNumber()));
        return model;
    }

    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeIntlPRepresentative", method = RequestMethod.POST)
    public ModelAndView onSubmitIntlPRepresentative(
            @ModelAttribute("representativeIntlPRepresentativeForm") IntlPRepresentativeForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(EmployeeRepresentativeForm.class,
                "representativeIntlPRepresentativeForm",
                "representative/representative_card_list",
                "representative/representative_intlprepresentative",
                getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Representative obj if no parameters are passed, so a new
     * Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addRepresentativeProfessionalPractitioner", method = RequestMethod.GET)
    public ModelAndView formBackingProfessionalPractitioner(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(ProfessionalPractitionerForm.class,
                "representativeProfessionalPractitionerForm", "representative/yourdata_representative",
                "representative/representative_professionalpractitioner", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Representative object view with the new representative added
     */
    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeProfessionalPractitioner", method = RequestMethod.POST)
    public ModelAndView onSubmitProfessionalPractitioner(
            @ModelAttribute("representativeProfessionalPractitionerForm") ProfessionalPractitionerForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ProfessionalPractitionerForm.class,
                "representativeProfessionalPractitionerForm",
                "representative/representative_card_list",
                "representative/representative_professionalpractitioner",
                getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Representative obj if no parameters are passed, so a new
     * Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addRepresentativeAssociation", method = RequestMethod.GET)
    public ModelAndView formBackingAssociation(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(RepresentativeAssociationForm.class,
                "representativeAssociationForm", "representative/yourdata_representative",
                "representative/representative_association", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Representative object view with the new representative added
     */
    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeAssociation", method = RequestMethod.POST)
    public ModelAndView onSubmitAssociation(
            @ModelAttribute("representativeAssociationForm") RepresentativeAssociationForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(RepresentativeAssociationForm.class,
                "representativeAssociationForm",
                "representative/representative_card_list",
                "representative/representative_association",
                getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Representative obj if no parameters are passed, so a new
     * Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addRepresentativeLegalPractitioner", method = RequestMethod.GET)
    public ModelAndView formBackingLegalPractitioner(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(LegalPractitionerForm.class,
                "representativeLegalPractitionerForm", "representative/yourdata_representative",
                "representative/representative_legalpractitioner", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Representative object view with the new representative added
     */
    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeLegalPractitioner", method = RequestMethod.POST)
    public ModelAndView onSubmitLegalPractitioner(
            @ModelAttribute("representativeLegalPractitionerForm") LegalPractitionerForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(LegalPractitionerForm.class,
                "representativeLegalPractitionerForm",
                "representative/representative_card_list",
                "representative/representative_legalpractitioner",
                getMaxNumber());
        ModelAndView model = addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
        if(result.hasErrors()){
            resetCommandImportedDetails(model, command,
                    ConfigurationServiceDelegatorInterface.RESET_REPRESENTATIVE_IMPORTED_KEY);
        }
        return model;
    }

    /**
     * It returns a new Representative obj if no parameters are passed, so a new
     * Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addRepresentativeLegalEntity", method = RequestMethod.GET)
    public ModelAndView formBackingRepLegalEntity(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(
                RepresentativeLegalEntityForm.class, "representativeLegalEntityForm",
                "representative/yourdata_representative", "representative/representative_legalentity", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Representative object view with the new representative added
     */
    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeLegalEntity", method = RequestMethod.POST)
    public ModelAndView onSubmitRepLegalEntity(
            @ModelAttribute("representativeLegalEntityForm") RepresentativeLegalEntityForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters=new AddParameters(RepresentativeLegalEntityForm.class,
                "representativeLegalEntityForm",
                "representative/representative_card_list",
                "representative/representative_legalentity",
                getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Representative obj if no parameters are passed, so a new
     * Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addRepresentativeNaturalPerson", method = RequestMethod.GET)
    public ModelAndView formBackingRepNaturalPerson(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(
                RepresentativeNaturalPersonForm.class, "representativeNaturalPersonForm",
                "representative/yourdata_representative", "representative/representative_naturalperson", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Representative object view with the new representative added
     */
    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeNaturalPerson", method = RequestMethod.POST)
    public ModelAndView onSubmitRepNaturalPerson(
            @ModelAttribute("representativeNaturalPersonForm") RepresentativeNaturalPersonForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(RepresentativeNaturalPersonForm.class,
                "representativeNaturalPersonForm",
                "representative/representative_card_list",
                "representative/representative_naturalperson",
                getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }


    @RequestMapping(value = "addRepresentativeLawyerCompany", method = RequestMethod.GET)
    public ModelAndView formBackingRepLawyerCompany(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(
            LawyerCompanyForm.class, "representativeLawyerCompanyForm",
            "representative/yourdata_representative", "representative/representative_lawyercompany", getMaxNumber()));
        return model;
    }

    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeLawyerCompany", method = RequestMethod.POST)
    public ModelAndView onSubmitRepLawyerCompany(
        @ModelAttribute("representativeLawyerCompanyForm") LawyerCompanyForm command,
        BindingResult result,
        @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(LawyerCompanyForm.class,
            "representativeLawyerCompanyForm",
            "representative/representative_card_list",
            "representative/representative_lawyercompany",
            getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    @RequestMapping(value = "addRepresentativeLawyerAssociation", method = RequestMethod.GET)
    public ModelAndView formBackingRepLawyerAssociation(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(
            LawyerAssociationForm.class, "representativeLawyerAssociationForm",
            "representative/yourdata_representative", "representative/representative_lawyerassociation", getMaxNumber()));
        return model;
    }

    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeLawyerAssociation", method = RequestMethod.POST)
    public ModelAndView onSubmitRepLawyerAssociation(
        @ModelAttribute("representativeLawyerAssociationForm") LawyerAssociationForm command,
        BindingResult result,
        @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(LawyerAssociationForm.class,
            "representativeLawyerAssociationForm",
            "representative/representative_card_list",
            "representative/representative_lawyerassociation",
            getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    @RequestMapping(value = "addRepresentativeTemporary", method = RequestMethod.GET)
    public ModelAndView formBackingRepTemporary(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(
            RepresentativeTemporaryForm.class, "representativeTemporaryForm",
            "representative/yourdata_representative", "representative/representative_temporary", getMaxNumber()));
        return model;
    }

    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addRepresentativeTemporary", method = RequestMethod.POST)
    public ModelAndView onSubmitRepTemporary(
        @ModelAttribute("representativeTemporaryForm") RepresentativeTemporaryForm command,
        BindingResult result,
        @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(RepresentativeTemporaryForm.class,
            "representativeTemporaryForm",
            "representative/representative_card_list",
            "representative/representative_temporary",
            getMaxNumber());
        ModelAndView model = addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
        if(result.hasErrors()){
            resetCommandImportedDetails(model, command,
                    ConfigurationServiceDelegatorInterface.RESET_REPRESENTATIVE_IMPORTED_KEY);
        }
        return model;
    }


    /**
     * Method that handles the removal of the empty correspondence addresses before adding the representative to the flowbean.
     * @param command
     * @param result
     * @param addParameters
     * @return
     */
    private ModelAndView addRepresentative(RepresentativeForm command, BindingResult result, AddParameters addParameters)
    {
        if(command != null)
        {
            command.removeEmptyCorrespondenceAddresses();
        }
        command.setValidateImported(true);
        return onSubmit(command, flowBean, addParameters, result);
    }

    /**
     * Handles the adding of an representative to the flow bean.
     * If the ignore matches flag is true, it just adds the representative to the flow bean (which also triggers validation).
     * Otherwise, it calls the validation before checking for matches.
     * @param command
     * @param result
     * @param addParameters the add parameters
     * @param ignoreMatches boolean indicating whether to call the match service
     * @return
     */
    private ModelAndView addRepresentativeCheckMatches(RepresentativeForm command, BindingResult result, AddParameters addParameters,
                                                       Boolean ignoreMatches)
    {
        // if the ignoreMatches boolean is set to true
        // add the representative without checking for matches
        if (ignoreMatches != null && ignoreMatches)
        {
            return addRepresentative(command, result, addParameters);
        }

        // otherwise, validate the form before calling the match service
        ModelAndView mv = new ModelAndView();
        boolean validObject = validateCommand(command, result, addParameters);
        if (!validObject)
        {
            mv.setViewName(addParameters.getFormView());
            mv.addObject("formErrors", "true");
            return mv;
        }
        else
        {
            // when no model errors are found
            // set the validation trigger to false so that validation is not activated twice
            addParameters.setTriggerValidation(false);
        }

        // call the match service
        List<RepresentativeForm> matches = checkMatches(command);

        // if no matches found, just add the representative
        if (matches.isEmpty())
        {
            return addRepresentative(command, result, addParameters);
        }

        // otherwise, return the matches view
        mv.setViewName("representative/representativeMatches");
        mv.addObject("representativeMatches", matches);
        return mv;
    }

    /**
     * Returns a view without a model for choosing an representative type
     *
     * @return the view
     */
    @RequestMapping(value = "chooseRepresentativeType", method = RequestMethod.GET)
    public ModelAndView chooseRepresentativeType() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("representative/representative_choosetype");
        return mv;
    }

    /**
     * Entry point to get an representative form for edit.
     * This method figures out the type of the representative and uses another method that can handle
     * that type of representative.
     * If the type is not found or there is no such representative with that id, it returns null.
     * @param id the id of the representative
     * @return the view containing the representative form to edit
     */
    @RequestMapping(value = "getRepresentativeForEdit", method = RequestMethod.GET)
    public ModelAndView getRepresentative(@RequestParam(required = true, value = "id") String id) {
        RepresentativeForm found = flowBean.getObject(RepresentativeForm.class, id);
        ModelAndView model = new ModelAndView();
        if (found instanceof RepresentativeLegalEntityForm) {
            model = formBackingRepLegalEntity(id);
            model.addObject("formEdit", "true");
            return model;
        } else if (found instanceof RepresentativeNaturalPersonForm) {
            model = formBackingRepNaturalPerson(id);
            model.addObject("formEdit", "true");
            return model;

        } else if (found instanceof EmployeeRepresentativeForm) {
            model = formBackingEmployeeRepresentative(id);
            model.addObject("formEdit", "true");
            return model;

        } else if (found instanceof LegalPractitionerForm) {
            model = formBackingLegalPractitioner(id);
            model.addObject("formEdit", "true");
            return model;

        } else if (found instanceof ProfessionalPractitionerForm) {
            model = formBackingProfessionalPractitioner(id);
            model.addObject("formEdit", "true");
            return model;

        } else if (found instanceof RepresentativeAssociationForm) {
            model = formBackingAssociation(id);
            model.addObject("formEdit", "true");
            return model;
        } else if (found instanceof LawyerCompanyForm) {
            model = formBackingRepLawyerCompany(id);
            model.addObject("formEdit", "true");
            return model;

        } else if (found instanceof LawyerAssociationForm) {
            model = formBackingRepLawyerAssociation(id);
            model.addObject("formEdit", "true");
            return model;

        } else if (found instanceof RepresentativeTemporaryForm) {
            model = formBackingRepTemporary(id);
            model.addObject("formEdit", "true");
            return model;
        } else if (found instanceof IntlPRepresentativeForm) {
            model = formBackingRepIntlPRepresentative(id);
            model.addObject("formEdit", "true");
            return model;
        }
        if(logger.isInfoEnabled())
        {
            logger.info("Applicant type not recognized: " + (found == null ? "null" : found.getType()));
        }
        return null;
    }

    /**
     * Method which calls an external service, if it is enabled in the configuration, to get matches for the given representative.
     * @param form
     * @param <T>
     * @return
     */
    private <T extends RepresentativeForm> List<RepresentativeForm> checkMatches(T form) {
        if (logger.isDebugEnabled()) {
            logger.debug("form:" + form);
        }
        List<RepresentativeForm> matchedRepresentatives = new ArrayList<RepresentativeForm>();
        if (configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())) {
            int maxResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(
                    ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS));
            matchedRepresentatives = personService.matchRepresentative(form, maxResults);
        }
        return matchedRepresentatives;
    }
}