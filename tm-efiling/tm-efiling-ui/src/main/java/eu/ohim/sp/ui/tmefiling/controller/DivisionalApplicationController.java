/*******************************************************************************
 * * $Id:: SearchTrademarkController.java 36713 2014-02-25 14:19:16Z segovro     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.WordSpecification;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 
 * Controller to handle divisional section calls
 * 
 * @author velosma
 * 
 */
@Controller
public class DivisionalApplicationController {

	/** Logger for this class and subclasses */
	private static final Logger logger = Logger.getLogger(DivisionalApplicationController.class);

    /**
     * Custom Date Editor
     */
    @Autowired
    private CustomDateEditor customDateEditor;

    @Autowired
    private TMFlowBean flowBean;

	@Autowired
	private TradeMarkSearchService tradeMarkSearchService;

    @Value("${sp.office}")
    private String office;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, customDateEditor);
    }

    /**
     * It returns imported divisional application view.
     *
     * @param id the id of the trademark
     * @return a modelAndView object with the object
     */
    @PreAuthorize("hasRole('Import_Divisional')")
    @RequestMapping(value = "importDivisionalApplication", method = RequestMethod.POST)
    public ModelAndView importDivisionalApplication(
    		@RequestParam(required = true, value = "id") String id) {
		logger.info("START divisional import");

        ModelAndView modelAndView = new ModelAndView();

		TradeMark tradeMark = tradeMarkSearchService.getTradeMark(office, id);

		if (tradeMark != null) {
            setData(flowBean, tradeMark);
            modelAndView.setViewName("divisional_application_section");
            // This line is necessary for the property editor (date conversion) to work
            modelAndView.addObject("flowBean", flowBean);
        } else {
            modelAndView.setViewName("errors/importError");
            modelAndView.addObject("errorCode", "error.import.noObjectFound.trademark");
        }

        logger.info("END divisional import");
		return modelAndView;
    }

    @PreAuthorize("hasRole('Import_Divisional')")
    @RequestMapping(value = "clearDivisionalApplication", method = RequestMethod.POST)
    public ModelAndView clearDivisionalApplication(){
        clearData(flowBean);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("divisional_application_section");
        // This line is necessary for the property editor (date conversion) to work
        modelAndView.addObject("flowBean", flowBean);
        return modelAndView;
    }

    /**
     * Sets data to flowBean object
     * @param flowBean the flowBean object
     * @param tradeMark the trademark
     */
    private void setData(TMFlowBean flowBean, TradeMark tradeMark) {
        flowBean.getMainForm().setClaimDivisionalApplication(true);
        flowBean.getMainForm().setNumberDivisionalApplication(tradeMark.getApplicationNumber());
        flowBean.getMainForm().setDateDivisionalApplication(tradeMark.getApplicationDate());
        flowBean.getMainForm().setDivisionalApplicationImported(true);
    }

    private void clearData(TMFlowBean flowBean) {
        flowBean.getMainForm().setClaimDivisionalApplication(false);
        flowBean.getMainForm().setNumberDivisionalApplication(null);
        flowBean.getMainForm().setDateDivisionalApplication(null);
        flowBean.getMainForm().setDivisionalApplicationImported(false);
    }
}
