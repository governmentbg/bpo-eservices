package eu.ohim.sp.dsefiling.ui.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.ohim.sp.common.ui.controller.SearchAbstractController;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

/**
 * Controller which calls to the searchDesigner service
 *
 * @author serrajo
 */
@Controller
public class SearchDesignerController extends SearchAbstractController {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger LOGGER = Logger.getLogger(SearchDesignerController.class);

    @Autowired
    private PersonServiceInterface personService;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    /**
     * It returns a new Designer obj if no parameters are passed, so a new
     * Designer to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the designer
     */
	@PreAuthorize("hasRole('Designer_Search')")
    @RequestMapping(value = "searchDesigner", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @Override
    @ResponseBody
    public String autocomplete(@RequestParam(required = false, value = "id") String id) {
        return super.autocomplete(id);
    }

	@PreAuthorize("hasRole('Designer_Search')")
    @RequestMapping(value = "autocompleteDesigner", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @Override
    @ResponseBody
    public String autocompleteService(@RequestParam(required = true, value = "args") String args) {
    	LOGGER.info("START search");
		int maxNumberOfResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent("service.designer.autocomplete.maxResults"));
        String results = personService.getDesignerAutocomplete(args, maxNumberOfResults);
        LOGGER.info("END search");
        return results;
    }
}
