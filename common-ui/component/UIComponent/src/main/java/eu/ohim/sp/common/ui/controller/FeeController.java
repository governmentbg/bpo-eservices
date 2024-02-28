/*******************************************************************************
 * * $Id:: FeeController.java 50925 2012-11-15 17:10:35Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.service.interfaces.FeeServiceInterface;

@Controller
public class FeeController {

	/**
	 * Logger for this class and subclasses
	 */
	private static final Logger logger = Logger.getLogger(FeeController.class);

	@Autowired
	private CommonSpFlowBean flowBean;
	
	@Autowired
	private FeeServiceInterface feeService;
	
	@RequestMapping(value = "getFeesInformation", method = RequestMethod.GET)
	public ModelAndView getFeesInformation() {
		ModelAndView model = new ModelAndView("right_cart");
		feeService.getFeesInformation(flowBean);
		return model;
	}
	
	@RequestMapping(value = "updateFeesInformation", method = RequestMethod.GET)
	public ModelAndView updateFees() {
		ModelAndView model = new ModelAndView("right_cart");
		feeService.updateFeesInformation(flowBean);
		return model;
	}
}
