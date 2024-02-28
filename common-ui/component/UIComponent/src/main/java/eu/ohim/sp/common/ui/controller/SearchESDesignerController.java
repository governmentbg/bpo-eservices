package eu.ohim.sp.common.ui.controller;

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

@Controller
public class SearchESDesignerController extends SearchAbstractController
{
    private static final Logger logger = Logger.getLogger(SearchESDesignerController.class);

    @Autowired
    private PersonServiceInterface personService;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@PreAuthorize("hasRole('Designer_Search')")
    @RequestMapping(value = "searchESDesigner", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String autocomplete(@RequestParam(required = false, value = "id") String id) {
        return super.autocomplete(id);
    }

	@PreAuthorize("hasRole('Designer_Search')")
    @RequestMapping(value = {"autocompleteESDesigner"}, headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String autocompleteServiceApp(@RequestParam(required = true, value = "args") String args) {
        return autocompleteService(args);
    }

    public String autocompleteService(String args) {
        logger.info("START search");
        int maxNumberOfResults = Integer.parseInt(configurationServiceDelegator
                .getValueFromGeneralComponent(
                        ConfigurationServiceDelegatorInterface
                                .DESIGNER_AUTOCOMPLETE_MAXRESULTS));
        String results = personService.getDesignerAutocomplete(args, maxNumberOfResults);
        logger.info("END search");
        return results;
    }
}
