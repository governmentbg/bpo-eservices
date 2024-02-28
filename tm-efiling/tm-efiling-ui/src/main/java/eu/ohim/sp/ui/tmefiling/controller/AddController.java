/*******************************************************************************
 * * $Id::                                                       $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddController extends AddAbstractController {
    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(AddController.class);

    /**
     * session bean
     */
    @Autowired
    private FlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    /* --------- SENIORITIES ---------- */
    /* -------------------------------- */

    /**
     * It returns a new Seniority obj if no parameters are passed, so a new
     * Seniority to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the seniority
     */
    @RequestMapping(value = "addSeniority", method = RequestMethod.GET)
    public ModelAndView formBackingSeniority(@RequestParam(required = false, value = "id") String id,
            @RequestParam(required = true, value = "detailsView") String detailsView) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_SENIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = innerFormBackingObject(id, flowBean, new AddParameters(SeniorityForm.class,
                "seniorityForm", "claim_seniority", detailsView, maxNumber));
        return modelAndView;
    }

    /**
     * Adds or edits on the collection stored in the session
     * 
     * @param command
     *            object that contains the Seniority information.
     * @param result
     *            manage the validation results of the form object
     * @return Seniority object view with the new seniority added
     */
    @RequestMapping(value = "addSeniority", method = RequestMethod.POST)
    public ModelAndView onSubmitSeniority(@ModelAttribute("seniorityForm") SeniorityForm command, BindingResult result,
            @RequestParam(required = true, value = "detailsView") String detailsView,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_SENIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = onSubmit(command, flowBean, new AddParameters(SeniorityForm.class, "seniorityForm",
                claimTable, detailsView, maxNumber), result);
        return modelAndView;
    }


    /* --------- IR TRANSFORMATION---- */
    /* ------------------------------- */

    /**
     * It returns a new IR Transformation obj if no parameters are passed, so a
     * new IR Transformation to be created by the user. If a parameter is passed
     * by the request then the object to be returned will be populated with the
     * values stored in the session to edit its details.
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR Transformation object
     */
    @RequestMapping(value = "addTransformation", method = RequestMethod.GET)
    public ModelAndView formBackingTransformation(@RequestParam(required = false, value = "id") String id,
            @RequestParam(required = true, value = "detailsView") String detailsView) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_TRANSFORMATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = innerFormBackingObject(id, flowBean, new AddParameters(TransformationForm.class,
                "transformationForm", "claim_transformation", detailsView, maxNumber));
        return modelAndView;
    }

    /**
     * Adds or edits on the collection stored in the session
     * 
     * @param command
     *            object that contains the IR Transformation information.
     * @param result
     *            manage the validation results of the form object
     * @return IR Transformation object view with the new IR Transformation
     *         added
     */
    @RequestMapping(value = "addTransformation", method = RequestMethod.POST)
    public ModelAndView onSubmitTransformation(@ModelAttribute("transformationForm") TransformationForm command,
            BindingResult result, @RequestParam(required = true, value = "detailsView") String detailsView,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_TRANSFORMATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = onSubmit(command, flowBean, new AddParameters(TransformationForm.class,
                "transformationForm", claimTable, detailsView, maxNumber), result);
        return modelAndView;
    }

}
