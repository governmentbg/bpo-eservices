/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.core.register.IPOAutocompleteSearchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.ohim.sp.core.register.TradeMarkSearchService;

/**
 * 
 * Controller which calls to the searchTrademark service
 * 
 * @author soriama
 * 
 */
@Controller
public class SearchTrademarkController {

	/** Logger for this class and subclasses */
	private static final Logger logger = Logger.getLogger(SearchTrademarkController.class);	
	
	@Autowired
	private TradeMarkSearchService tradeMarkSearchService;

	@Autowired
	private IPOAutocompleteSearchService ipoAutocompleteSearchService;

	@Value("${sp.office}")
	private String office;
	
	/**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     * 
     * @param id the id of the edited object, or a new object if it is null
     * @param office office code where the mark has to be searched
     * @param previousCTM indicates whether the search is done from the previousCTM section or not
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "autocompleteTrademark", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String autocomplete(
    		@RequestParam(required = false, value = "args") String id,
    		@RequestParam(required = true, value = "office") String office,
    		@RequestParam(required = true, value = "previousCTM") Boolean previousCTM) {
		return autocompleteService(id, office, previousCTM);
    }
    
	public String autocompleteService(String id, String office, Boolean previousCTM) {
		logger.info("START autocompleteService search");
		String results = "";
		try {
			if(office.equals(this.office)){
				results = ipoAutocompleteSearchService.ipoAutocomplete(id, 50, "tm-efiling");
			} else {
				results = tradeMarkSearchService.getTradeMarkAutocomplete(office, id, 50);
			}
		} catch (Exception e) {
			logger.error("SearchTrademarkController autocompleteService ERROR: " + e.getMessage());
		}
		
		logger.info("END autocompleteService search");
		return results;		
	}
}
