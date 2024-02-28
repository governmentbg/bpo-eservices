/*******************************************************************************
 * * $Id:: SearchRepresentativeController.java 50771 2012-11-14 15:10:27Z karalc#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

/**
 * 
 * Controller which calls to the searchRepresentative service
 * 
 * @author soriama
 * 
 */
@Controller
public class SearchRepresentativeController extends SearchAbstractController {

	/**
	 * Logger for this class and subclasses
	 */
	private static final Logger logger = Logger.getLogger(SearchRepresentativeController.class);

    @Autowired
    private PersonServiceInterface personService;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

	/**
	 * It returns a new Representative obj if no parameters are passed, so a new
	 * Representative to be created by the user. If a parameter is passed by the
	 * request then the object to be returned will be populated with the values
	 * stored in the session to edit its details.
	 * 
	 * @param id
	 *            the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 * @throws Exception
	 *             if it fails to load the representative
	 */
	@PreAuthorize("hasRole('Representative_Search')")
	@RequestMapping(value = "searchRepresentative", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String autocomplete(@RequestParam(required = false, value = "id") String id) {
    	return super.autocomplete(id);
	}

	@PreAuthorize("hasRole('Representative_Search')")
	@RequestMapping(value = "autocompleteRepresentative", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String autocompleteService(@RequestParam(required = true, value = "args") String args) {
		logger.info("START search");
		int maxNumberOfResults = Integer.parseInt(configurationServiceDelegator
                                                          .getValueFromGeneralComponent(
                                                                  ConfigurationServiceDelegatorInterface
                                                                          .REPRESENTATIVE_AUTOCOMPLETE_MAXRESULTS));
		String results = "";
		String flowModeID = flowScopeDetails.getFlowModeId();
		if (flowModeID!=null && (flowModeID.startsWith("tm-") ||
				flowModeID.startsWith("ds-") || flowModeID.startsWith("pt-") || flowModeID.startsWith("um-") || flowModeID.startsWith("ep-") || flowModeID.startsWith("sv-") || flowModeID.startsWith("spc-") || flowModeID.startsWith("is-") || flowModeID.startsWith("ol-"))) {
			 results = personService.getRepresentativeAutocomplete(args, maxNumberOfResults, flowModeID);
		}
		else{
			 results = personService.getRepresentativeAutocomplete(args, maxNumberOfResults);
		}
			
		logger.info("END search");
		return results;
	}
}
