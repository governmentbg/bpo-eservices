package eu.ohim.sp.dsefiling.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.ohim.sp.common.ui.controller.SearchAbstractController;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * Controller which calls to the designService service
 *
 * @author serrajo
 */
@Controller
public class SearchDesignController extends SearchAbstractController {

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Autowired
	private DSDesignsServiceInterface designsService;
    
	@PreAuthorize("hasRole('Design_Search')")
    @RequestMapping(value = "autocompleteDesign", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @Override
    @ResponseBody
    public String autocompleteService(@RequestParam(required = true, value = "args") String args) {
		int maxNumberOfResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent("service.design.autocomplete.maxResults"));
        return designsService.getDesignAutocomplete(args, maxNumberOfResults);
    }

}
