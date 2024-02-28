/*******************************************************************************
 * * $$
 * * . * .
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/

package eu.ohim.sp.common.ui.controller;


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
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;


/**
 * Controller in charge of handling the adding and the editing of the Correspondence Address.
 * @author garjuan
 */
@Controller
public class AddApplicationCAController extends AddAbstractController {
    /**
     * Logger for this class and subclasses
     */
    private static final Logger LOGGER = Logger.getLogger(AddApplicationCAController.class);
    /**
     * session bean
     */
    @Autowired
    private FlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    private Integer maxNumber = -1;

    /**
     * Gets the maximum number of correspondence address entities allowed
     * @return Integer with the maximum number
     */
    public Integer getMaxNumber() {
		if(maxNumber == -1) {
	        maxNumber = getIntegerSetting(configurationServiceDelegator,
	                ConfigurationServiceDelegatorInterface.CORRESPONDENCE_ADDRESS_ADD_MAXNUMBER,
	                ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
		}
		return maxNumber;
    }


    /**
     * It returns a new Correspondence Address obj if no parameters are passed, so a new
     * Design to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the design
     */

    
    @RequestMapping(value = "addApplicationCA", method = RequestMethod.GET)
    public ModelAndView formBackingApplicationCA(@RequestParam(required = false, value = "id") String id)    
    {
    	LOGGER.info("formBackingApplicationCA");
    	
        return innerFormBackingObject(id, flowBean, new AddParameters(ApplicationCAForm.class, "applicationCAForm",
        																			"common/correspondent/applicationCA",
        																			"common/correspondent/applicationCA", getMaxNumber()));
    }

    
 
    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Correspondence Address information.
     * @param result  manage the validation results of the form object
     * @return Correspondence Address object view with the new design added
     */
    @PreAuthorize("hasRole('ApplicationCA_Add')")
    @RequestMapping(value = "addApplicationCA", method = RequestMethod.POST)
    public ModelAndView onSubmitApplicationCA(@ModelAttribute("applicationCAForm") ApplicationCAForm command,
                                            BindingResult result) {
    	
    	AddParameters addParameters = new AddParameters(ApplicationCAForm.class, "applicationCAForm",
                										"common/correspondent/applicationCA_card_list",
                										"common/correspondent/applicationCA", getMaxNumber());
    	return onSubmit(command, flowBean, addParameters, result);
    }

}
