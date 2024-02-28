/*******************************************************************************
 * * $Id:: AddApplicantController.java 50771 2012-11-14 15:10:27Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright Â© 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.exception.MaximumEntitiesException;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.form.person.HolderLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.HolderNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Holder;

/**
 * Controller in charge of handling the adding and the editing of the holders.
 * @author ionitdi
 */
@Controller
public class AddHolderController extends AddAbstractController
{
    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(AddHolderController.class);
    /**
     * session bean
     */
    @Autowired
    private FlowBean flowBean;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private HolderFactory holderFactory;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    /**
     * Gets the maximum number of holder entities allowed
     * @return Integer with the maximun number
     */
    @Override
    protected String[] resolveMaxNumberProperty() {
        String maxNumberSource=ConfigurationServiceDelegatorInterface.HOLDER_ADD_MAXNUMBER;
        return new String[]{ConfigurationServiceDelegatorInterface.PERSON_COMPONENT, maxNumberSource};
    }



    /* --------- HOLDERS ---------- */
    /* ------------------------------- */

    /**
     * It returns a new Holder obj if no parameters are passed, so a new
     * Holder to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the holder
     */
    @RequestMapping(value = "addHolderLegalEntity", method = RequestMethod.GET)
    public ModelAndView formBackingLegalEntity(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(HolderLegalEntityForm.class, "holderLegalEntityForm",
                "holder/yourdata_holder",
                "holder/holder_legalentity", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Holder information.
     * @param result  manage the validation results of the form object
     * @return Holder object view with the new holder added
     */
    @PreAuthorize("hasRole('Holder_Add')")
    @RequestMapping(value = "addHolderLegalEntity", method = RequestMethod.POST)
    public ModelAndView onSubmitLegalEntity(@ModelAttribute("holderLegalEntityForm") HolderLegalEntityForm command,
                                            BindingResult result,
                                            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(HolderLegalEntityForm.class, "holderLegalEntityForm",
                "holder/holder_card_list", "holder/holder_legalentity", getMaxNumber());


        ModelAndView model = addHolderCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
        }
        return model;

    }

    /**
     * It returns a new Holder obj if no parameters are passed, so a new
     * Holder to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the holder
     */
    @RequestMapping(value = "addHolderNaturalPerson", method = RequestMethod.GET)
    public ModelAndView formBackingNaturalPerson(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean,
                new AddParameters(HolderNaturalPersonForm.class, "holderNaturalPersonForm",
                        "holder/yourdata_holder",
                        "holder/holder_naturalperson", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Holder information.
     * @param result  manage the validation results of the form object
     * @return Holder object view with the new holder added
     */
    @PreAuthorize("hasRole('Holder_Add')")
    @RequestMapping(value = "addHolderNaturalPerson", method = RequestMethod.POST)
    public ModelAndView onSubmitNaturalPerson(@ModelAttribute("holderNaturalPersonForm") HolderNaturalPersonForm command,
                                              BindingResult result,
                                              @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
    {
        AddParameters addParameters = new AddParameters(HolderNaturalPersonForm.class, "holderNaturalPersonForm",
                "holder/holder_card_list", "holder/holder_naturalperson", getMaxNumber());


        ModelAndView model = addHolderCheckMatches(command, result, addParameters, ignoreMatches);
        if (result.hasErrors())
        {
            model.addObject("formErrors", "true");
        }
        return model;

    }


    /**
     * Method that handles the removal of the empty correspondence addresses before adding the holder to the flowbean.
     * @param command
     * @param result
     * @param addParameters
     * @return
     */
    private ModelAndView addHolder(HolderForm command, BindingResult result, AddParameters addParameters)
    {
        if(command != null)
        {
            command.removeEmptyCorrespondenceAddresses();
        }
        return onSubmit(command, flowBean, addParameters, result);
    }

    /**
     * Handles the adding of an holder to the flow bean.
     * If the ignore matches flag is true, it just adds the holder to the flow bean (which also triggers validation).
     * Otherwise, it calls the validation before checking for matches.
     * @param command
     * @param result
     * @param addParameters the add parameters
     * @param ignoreMatches boolean indicating whether to call the match service
     * @return
     */
    private ModelAndView addHolderCheckMatches(HolderForm command, BindingResult result, AddParameters addParameters,
                                               Boolean ignoreMatches)
    {
        // if the ignoreMatches boolean is set to true
        // add the holder without checking for matches
        if (ignoreMatches != null && ignoreMatches)
        {
            return addHolder(command, result, addParameters);
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
        List<HolderForm> matches = checkMatches(command);

        // if no matches found, just add the holder
        if (matches.isEmpty())
        {
            return addHolder(command, result, addParameters);
        }

        // otherwise, return the matches view
        mv.setViewName("holder/holderMatches");
        mv.addObject("holderMatches", matches);
        return mv;
    }

    /**
     * Returns a view without a model for choosing a Holder type
     *
     * @return the view
     */
    @RequestMapping(value = "chooseHolderType", method = RequestMethod.GET)
    public ModelAndView chooseApplicantType()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("holder/holder_choosetype");
        return mv;
    }

    /**
     * Entry point to get a Holder form for edit.
     * This method figures out the type of the Holder and uses another method that can handle
     * that type of applicant.
     * If the type is not found or there is no such applicant with that id, it returns null.
     * @param id the id of the Holder
     * @return the view containing the applicant form to edit
     */
    @RequestMapping(value = "getHolderForEdit", method = RequestMethod.GET)
    public ModelAndView getHolder(@RequestParam(required = true, value = "id") String id)
    {
        HolderForm found = flowBean.getObject(HolderForm.class, id);

        if (found instanceof HolderLegalEntityForm)
        {
            ModelAndView model = formBackingLegalEntity(id);
            model.addObject("formEdit", "true");
            return model;

        }
        else if (found instanceof HolderNaturalPersonForm)
        {
            ModelAndView model = formBackingNaturalPerson(id);
            model.addObject("formEdit", "true");
            return model;
        }
        if(logger.isInfoEnabled())
        {
            logger.info("Holder type not recognized: " + (found == null ? "null" : found.getType()));
        }
        return null;
    }

    /**
     * Method which calls an external service, if it is enabled in the configuration,
     * to get matches for the given applicant.
     * @param form
     * @param <T>
     * @return
     */
    private <T extends HolderForm> List<HolderForm> checkMatches(T form)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("form:" + form);
        }
        List<HolderForm> matchedApplicants = new ArrayList<HolderForm>();

        // only check for matches if the service is enabled
        /*if (configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value())) {
            int maxResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(
                    ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS));
            matchedApplicants = personService.matchApplicant(form, maxResults);
        }*/

        return matchedApplicants;
    }

    @PreAuthorize("hasRole('Holder_Add')")
    @RequestMapping(value = "addApplicantAsHolder", method = RequestMethod.POST)
    public ModelAndView addApplicantAsHolder(@RequestParam(required = true, value = "id") String id) throws CloneNotSupportedException
    {

        //validate max holders
        if (flowBean.getCollection(HolderForm.class) != null
                && flowBean.getCollection(HolderForm.class).size() >= getMaxNumber()){
            throw new MaximumEntitiesException("Maximum entities reached", new Exception(), "error.ef.max."
                    + HolderForm.class.getSimpleName(), getMaxNumber().toString());
        }


        ApplicantForm found = flowBean.getObject(ApplicantForm.class, id);
        if (found instanceof LegalEntityForm)
        {
            HolderLegalEntityForm newHolder = found.copy(HolderLegalEntityForm.class); //copy personForm data
            newHolder.setId(null);
            //copy applicant data
            newHolder.setName(found.getName());
            newHolder.setDomicile(found.getDomicile());
            newHolder.setDomicileCountry(found.getDomicileCountry());
            newHolder.setContactPerson(found.getContactPerson());
            //copy LegalEntityForm data
            newHolder.setStateOfIncorporation(((LegalEntityForm)found).getStateOfIncorporation());
            newHolder.setLegalForm(((LegalEntityForm)found).getLegalForm());
            newHolder.setBusinessVatNumber(((LegalEntityForm)found).getBusinessVatNumber());
            newHolder.setCountryOfRegistration(((LegalEntityForm)found).getCountryOfRegistration());

            BindingResult errors = new BeanPropertyBindingResult(newHolder, "newHolder");

            return onSubmitLegalEntity(newHolder, errors, true);
        }
        else if (found instanceof NaturalPersonForm)
        {
            HolderNaturalPersonForm newHolder = found.copy(HolderNaturalPersonForm.class); //copy personForm data
            newHolder.setId(null);
            //copy applicant data
            newHolder.setName(found.getName());
            newHolder.setDomicile(found.getDomicile());
            newHolder.setDomicileCountry(found.getDomicileCountry());
            newHolder.setContactPerson(found.getContactPerson());
            //copy NaturalPersonForm data
            newHolder.setNationality(((NaturalPersonForm)found).getNationality());
            newHolder.setFirstName(((NaturalPersonForm)found).getFirstName());
            newHolder.setSurname(((NaturalPersonForm)found).getSurname());

            BindingResult errors = new BeanPropertyBindingResult(newHolder, "newHolder");
            return onSubmitNaturalPerson(newHolder, errors, true);
        }
        else
        {
            logger.info("Holder type not recognized: " + (found == null ? "null" : found.getType()));
            return null;
        }
    }
}
