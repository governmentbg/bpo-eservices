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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * Controller to make a mock COPATO environment
 * 
 * @author soriama
 * 
 */
@Controller
public class CopatoMockupController {
    
    @RequestMapping(value = "copato", method = RequestMethod.POST)
    public ModelAndView paymentIFrame() {    	    	
    	return new ModelAndView("payment/copato_mock_window");
    }        
}
