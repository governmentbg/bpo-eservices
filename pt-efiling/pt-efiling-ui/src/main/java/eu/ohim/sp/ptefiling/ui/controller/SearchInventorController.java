package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.SearchAbstractController;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which calls to the searchDesigner service
 *
 * @author serrajo
 */
@Controller
public class SearchInventorController extends SearchAbstractController {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger LOGGER = Logger.getLogger(SearchInventorController.class);

    @Autowired
    private PersonServiceInterface personService;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;


	@PreAuthorize("hasRole('Inventor_Search')")
    @RequestMapping(value = "searchInventor", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @Override
    @ResponseBody
    public String autocomplete(@RequestParam(required = false, value = "id") String id) {
        return super.autocomplete(id);
    }

	@PreAuthorize("hasRole('Inventor_Search')")
    @RequestMapping(value = "autocompleteInventor", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @Override
    @ResponseBody
    public String autocompleteService(@RequestParam(required = true, value = "args") String args) {
    	LOGGER.info("START search");
		int maxNumberOfResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent("service.inventor.autocomplete.maxResults"));
        String results = personService.getInventorAutocomplete(args, maxNumberOfResults);
        LOGGER.info("END search");
        return results;
    }
}
