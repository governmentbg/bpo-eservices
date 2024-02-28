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

import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.getViewByFormClass;

import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.PROFESSIONAL_PRACTITIONER_VIEW_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.LEGAL_PRACTITIONER_VIEW_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.EMPLOYEE_REPRESENTATIVE_VIEW_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.NATURAL_PERSON_VIEW_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.LEGAL_ENTITY_VIEW_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.ASSOCIATION_VIEW_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.ADDRESS_VIEW_NAME;

import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.PROFESSIONAL_PRACTITIONER_FORM_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.LEGAL_PRACTITIONER_FORM_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.EMPLOYEE_REPRESENTATIVE_FORM_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.NATURAL_PERSON_FORM_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.LEGAL_ENTITY_FORM_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.ASSOCIATION_FORM_NAME;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.ADDRESS_FORM_NAME;


import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.util.PersonChangeControllerHelper;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import org.apache.commons.lang.StringUtils;
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

/**
 * Controller in charge of handling the adding and the editing of the change of persons.
 *
 * @author velosma
 */
@Controller
public class AddPersonChangeController extends AddAbstractController {

    private static final Logger logger = Logger.getLogger(AddPersonChangeController.class);

    private static final String YOURDATA_NAME = "personChange/yourdata_personChange";
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
     * Gets the maximum number of person changes entities allowed
     * @return Integer with the maximum number
     */
    @Override
    protected String[] resolveMaxNumberProperty() {
        String flowModeId = flowScopeDetails.getFlowModeId();

        String maxNumberSource=ConfigurationServiceDelegatorInterface.PERSONCHANGE_ADD_MAXNUMBER;
        if (flowModeId.equals("tm-change")){
            maxNumberSource =   ConfigurationServiceDelegatorInterface.PERSONCHANGE_ADD_MAXNUMBER_CHANGE;
        }
        if (flowModeId.equals("ds-change")){
            maxNumberSource =   ConfigurationServiceDelegatorInterface.PERSONCHANGE_ADD_MAXNUMBER_DSCHANGE ;
        }

        return new String[]{ConfigurationServiceDelegatorInterface.PERSON_COMPONENT, maxNumberSource};
    }


    /* ------------------------------- */

    @RequestMapping(value = "addPersonChange", method = RequestMethod.GET)
    public ModelAndView formBackingChange(@RequestParam ChangePersonType changeType)
    {
        String view = null;
        String formName = "personChangeForm";
        switch (changeType) {
            case ADD_NEW_REPRESENTATIVE:
            case ADD_NEW_CORRESPONDENT:
                view = "personChange/personChange_addNewRepresentative";
                break;
            case REPLACE_REPRESENTATIVE:
            case REPLACE_CORRESPONDENT:
                view = "personChange/personChange_replaceRepresentative";
                break;
            case REMOVE_REPRESENTATIVE:
            case REMOVE_CORRESPONDENT:
                view = "personChange/personChange_removeRepresentative";
                formName = NATURAL_PERSON_FORM_NAME;
                break;
            case CHANGE_REPRESENTATIVE_ADDRESS:
            case CHANGE_CORRESPONDENT_ADDRESS:
            case CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS:
                view = "personChange/personChange_address";
                break;
        }

        ChangeRepresentativeNaturalPersonForm form = new ChangeRepresentativeNaturalPersonForm();
        form.setChangeType(changeType);

        ModelAndView model = new ModelAndView(view);
        model.addObject(formName, form);
        return model;
    }

	/* --------- REPRESENTATIVES ---------- */
	/* ------------------------------------ */

    /**
     * It returns a new change of Representative obj if no parameters are passed, so a new
     * Change of Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addChangeEmployeeRepresentative", method = RequestMethod.GET)
    public ModelAndView formBackingChangeEmployeeRepresentative(@RequestParam(required = false, value = "id") String id,
                @RequestParam(required = false, value = "changeType") ChangePersonType changeType) {
        return innerFormBackingObjectType(id, changeType,new AddParameters(ChangeEmployeeRepresentativeForm.class,
                EMPLOYEE_REPRESENTATIVE_FORM_NAME, YOURDATA_NAME,
                EMPLOYEE_REPRESENTATIVE_VIEW_NAME, getMaxNumber()));
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Change of Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Change of Representative object view with the new change of representative added
     */
    @PreAuthorize("hasRole('PersonChange_Add')")
    @RequestMapping(value = "addChangeEmployeeRepresentative", method = RequestMethod.POST)
    public ModelAndView onSubmitChangeEmployeeRepresentative(
            @ModelAttribute("representativeEmployeeRepresentativeForm") ChangeEmployeeRepresentativeForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ChangeEmployeeRepresentativeForm.class,
                EMPLOYEE_REPRESENTATIVE_FORM_NAME, "personChange/personChange_card_list",
                EMPLOYEE_REPRESENTATIVE_VIEW_NAME, getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Change of Representative obj if no parameters are passed, so a new
     * Change of Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addChangeProfessionalPractitioner", method = RequestMethod.GET)
    public ModelAndView formBackingChangeProfessionalPractitioner(@RequestParam(required = false, value = "id") String id,
                @RequestParam(required = false, value = "changeType") ChangePersonType changeType) {
        return innerFormBackingObjectType(id, changeType, new AddParameters(ChangeProfessionalPractitionerForm.class,
                PROFESSIONAL_PRACTITIONER_FORM_NAME, YOURDATA_NAME,
                PROFESSIONAL_PRACTITIONER_VIEW_NAME, getMaxNumber()));
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Change of Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Change of Representative object view with the new change of representative added
     */
    @PreAuthorize("hasRole('PersonChange_Add')")
    @RequestMapping(value = "addChangeProfessionalPractitioner", method = RequestMethod.POST)
    public ModelAndView onSubmitChangeProfessionalPractitioner(
            @ModelAttribute("representativeProfessionalPractitionerForm") ChangeProfessionalPractitionerForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ChangeProfessionalPractitionerForm.class,
                PROFESSIONAL_PRACTITIONER_FORM_NAME, "personChange/personChange_card_list",
                PROFESSIONAL_PRACTITIONER_VIEW_NAME, getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Change of Representative obj if no parameters are passed, so a new
     * Change of Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addChangeRepresentativeAssociation", method = RequestMethod.GET)
    public ModelAndView formBackingChangeRepresentativeAssociation(@RequestParam(required = false, value = "id") String id,
                 @RequestParam(required = false, value = "changeType") ChangePersonType changeType) {
        return innerFormBackingObjectType(id, changeType, new AddParameters(ChangeRepresentativeAssociationForm.class,
                ASSOCIATION_FORM_NAME, YOURDATA_NAME,
                ASSOCIATION_VIEW_NAME, getMaxNumber()));
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Change of Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Change of Representative object view with the new change of representative added
     */
    @PreAuthorize("hasRole('PersonChange_Add')")
    @RequestMapping(value = "addChangeRepresentativeAssociation", method = RequestMethod.POST)
    public ModelAndView onSubmitChangeRepresentativeAssociation(
            @ModelAttribute("representativeAssociationForm") ChangeRepresentativeAssociationForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ChangeRepresentativeAssociationForm.class,
                ASSOCIATION_FORM_NAME, "personChange/personChange_card_list",
                ASSOCIATION_VIEW_NAME, getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Change of Representative obj if no parameters are passed, so a new
     * Change of Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addChangeLegalPractitioner", method = RequestMethod.GET)
    public ModelAndView formBackingChangeLegalPractitioner(@RequestParam(required = false, value = "id") String id,
                @RequestParam(required = false, value = "changeType") ChangePersonType changeType) {
        return innerFormBackingObjectType(id, changeType, new AddParameters(ChangeLegalPractitionerForm.class,
                LEGAL_PRACTITIONER_FORM_NAME, YOURDATA_NAME,
                LEGAL_PRACTITIONER_VIEW_NAME, getMaxNumber()));
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Change of Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Change of Representative object view with the new representative added
     */
    @PreAuthorize("hasRole('PersonChange_Add')")
    @RequestMapping(value = "addChangeLegalPractitioner", method = RequestMethod.POST)
    public ModelAndView onSubmitChangeLegalPractitioner(
            @ModelAttribute("representativeLegalPractitionerForm") ChangeLegalPractitionerForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ChangeLegalPractitionerForm.class,
                LEGAL_PRACTITIONER_FORM_NAME, "personChange/personChange_card_list",
                LEGAL_PRACTITIONER_VIEW_NAME, getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Change of Representative obj if no parameters are passed, so a new
     * Change of Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addChangeRepresentativeLegalEntity", method = RequestMethod.GET)
    public ModelAndView formBackingChangeRepresentativeLegalEntity(@RequestParam(required = false, value = "id") String id,
                @RequestParam(required = false, value = "changeType") ChangePersonType changeType) {
        return innerFormBackingObjectType(id, changeType, new AddParameters(ChangeRepresentativeLegalEntityForm.class,
                LEGAL_ENTITY_FORM_NAME, YOURDATA_NAME,
                LEGAL_ENTITY_VIEW_NAME, getMaxNumber()));
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Change of Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Change of Representative object view with the new change of representative added
     */
    @PreAuthorize("hasRole('PersonChange_Add')")
    @RequestMapping(value = "addChangeRepresentativeLegalEntity", method = RequestMethod.POST)
    public ModelAndView onSubmitChangeRepresentativeLegalEntity(
            @ModelAttribute("representativeLegalEntityForm") ChangeRepresentativeLegalEntityForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters=new AddParameters(ChangeRepresentativeLegalEntityForm.class,
                LEGAL_ENTITY_FORM_NAME, "personChange/personChange_card_list",
                LEGAL_ENTITY_VIEW_NAME, getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Change of Representative obj if no parameters are passed, so a new
     * Change of Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "addChangeRepresentativeNaturalPerson", method = RequestMethod.GET)
    public ModelAndView formBackingChangeRepresentativeNaturalPerson(@RequestParam(required = false, value = "id") String id,
                @RequestParam(required = false, value = "changeType") ChangePersonType changeType) {
        return innerFormBackingObjectType(id, changeType, new AddParameters(ChangeRepresentativeNaturalPersonForm.class,
                NATURAL_PERSON_FORM_NAME, YOURDATA_NAME,
                NATURAL_PERSON_VIEW_NAME, getMaxNumber()));
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Change of Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Change of Representative object view with the new change of representative added
     */
    @PreAuthorize("hasRole('PersonChange_Add')")
    @RequestMapping(value = "addChangeRepresentativeNaturalPerson", method = RequestMethod.POST)
    public ModelAndView onSubmitRepresentativeNaturalPerson(
            @ModelAttribute("representativeNaturalPersonForm") ChangeRepresentativeNaturalPersonForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ChangeRepresentativeNaturalPersonForm.class,
                NATURAL_PERSON_FORM_NAME, "personChange/personChange_card_list",
                NATURAL_PERSON_VIEW_NAME, getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
    }

    /**
     * It returns a new Change of Representative obj if no parameters are passed, so a new
     * Change of Representative to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    private ModelAndView formBackingChangeRepresentativeAddress(@RequestParam(required = false, value = "id") String id,
                @RequestParam(required = false, value = "changeType") ChangePersonType changeType) {
        return innerFormBackingObjectType(id, changeType, new AddParameters(ChangeRepresentativeAddressForm.class,
                ADDRESS_FORM_NAME, YOURDATA_NAME,
                ADDRESS_VIEW_NAME, getMaxNumber()));
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command
     *            object that contains the Change of Representative information.
     * @param result
     *            manage the validation results of the form object
     * @return Change of Representative object view with the new change of representative added
     */
    @PreAuthorize("hasRole('PersonChange_Add')")
    @RequestMapping(value = "addChangeAddress", method = RequestMethod.POST)
    public ModelAndView onSubmitAddressPerson(
            @ModelAttribute("personChangeForm") ChangeRepresentativeAddressForm command,
            BindingResult result,
            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
        AddParameters addParameters = new AddParameters(ChangeRepresentativeAddressForm.class,
                ADDRESS_FORM_NAME, "personChange/personChange_card_list",
                ADDRESS_VIEW_NAME, getMaxNumber());
        return addRepresentativeCheckMatches(command, result, addParameters, ignoreMatches);
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
        mv.setViewName("personChange/personChangeMatches");
        mv.addObject("personChangeMatches", matches);
        return mv;
    }

    /**
     * Returns a view without a model for choosing an representative type
     *
     * @return the view
     */
    @RequestMapping(value = "chooseChangePersonType", method = RequestMethod.GET)
    public ModelAndView chooseChangePersonType() {
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
    @RequestMapping(value = "getChangePersonForEdit", method = RequestMethod.GET)
    public ModelAndView getChangePerson(@RequestParam(required = true, value = "id") String id) {
        RepresentativeForm found = flowBean.getObject(ChangeRepresentativeNaturalPersonForm.class, id);

        if (found != null) {

            PersonChangeControllerHelper.ViewInfo viewInfo = getViewByFormClass(found.getClass());

            ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(viewInfo.getFormClass(),
                    viewInfo.getFormName(), YOURDATA_NAME, viewInfo.getViewName(), getMaxNumber()));
            model.addObject("formEdit", "true");

            return model;
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

    /**
     * Prepare the inner form backing object and sets the change type if necessary
     * @param id the id of the form
     * @param changeType the change type
     * @param addParameters list of parameters
     * @param <T> Form type
     * @return the model and view
     * @throws SPException if error occurs
     */
    private <T extends AbstractForm> ModelAndView innerFormBackingObjectType(String id,
                ChangePersonType changeType, AddParameters addParameters) throws SPException {
        ModelAndView model = innerFormBackingObject(id, flowBean, addParameters);

        if (StringUtils.isEmpty(id) && changeType != null && addParameters != null) {
            RepresentativeForm form = (RepresentativeForm) model.getModel().get(addParameters.getCommandName());
            if (form != null) {
                form.setChangeType(changeType);
            }
        }

        return model;
    }
}