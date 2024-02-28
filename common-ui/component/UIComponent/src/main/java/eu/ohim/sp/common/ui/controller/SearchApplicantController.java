/*******************************************************************************
 * * $Id:: SearchApplicantController.java 50771 2012-11-14 15:10:27Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which calls to the searchApplicant service
 *
 * @author soriama
 */
@Controller
public class SearchApplicantController extends SearchAbstractController
{

    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(SearchApplicantController.class);

    @Autowired
    private PersonServiceInterface personService;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private FlowScopeDetails flowScopeDetails;
	
	/**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
	@PreAuthorize("hasRole('Applicant_Search')")
    @RequestMapping(value = "searchApplicant", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String autocomplete(@RequestParam(required = false, value = "id") String id) {
        return super.autocomplete(id);
    }

	@PreAuthorize("hasRole('Applicant_Search')")
    @RequestMapping(value = {"autocompleteApplicant"}, headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String autocompleteServiceApp(@RequestParam(required = true, value = "args") String args) {
        return autocompleteService(args);
    }

    @PreAuthorize("hasRole('Assignee_Search')")
    @RequestMapping(value = {"autocompleteAssignee"}, headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String autocompleteServiceAss(@RequestParam(required = true, value = "args") String args) {
        return autocompleteService(args);
    }

    public String autocompleteService(String args) {
        logger.info("START search");
        int maxNumberOfResults = Integer.parseInt(configurationServiceDelegator
                .getValueFromGeneralComponent(
                        ConfigurationServiceDelegatorInterface
                                .APPLICANT_AUTOCOMPLETE_MAXRESULTS));

        String results = "";
        String flowModeID = flowScopeDetails.getFlowModeId();
        if (flowModeID!=null && (flowModeID.startsWith("tm-") ||
                flowModeID.startsWith("ds-") || flowModeID.startsWith("pt-") || flowModeID.startsWith("um-") || flowModeID.startsWith("ep-") || flowModeID.startsWith("sv-") || flowModeID.startsWith("spc-") || flowModeID.startsWith("is-")|| flowModeID.startsWith("ol-"))) {
            results = personService.getApplicantAutocomplete(args, maxNumberOfResults, flowModeID);
        }
        else{
            results = personService.getApplicantAutocomplete(args, maxNumberOfResults);
        }

        logger.info("END search");
        return results;
    }
}
