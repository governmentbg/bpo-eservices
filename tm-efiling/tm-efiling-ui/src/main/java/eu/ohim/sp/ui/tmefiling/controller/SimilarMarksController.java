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

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.flow.section.TrademarkFlowBean;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.service.interfaces.ImportServiceInterface;

/**
 * @author ionitdi
 */
@Controller
public class SimilarMarksController {
	@Autowired
	private TMFlowBean flowBean;
	
	@Autowired
	private TradeMarkSearchService tradeMarkSearchService;

    @Autowired
    private TrademarkFlowBean trademarkFlowBean;
    
    @Autowired
	private FlowBeanFactory flowBeanFactory;

	@Autowired
	private ImportServiceInterface importService;

	@Value("${tmefiling.office}")
	private String officeCode;

	@RequestMapping(value = "getSimilarMarks", method = RequestMethod.GET)
	public ModelAndView getSimilarMarks(@RequestParam(required = false, value = "markDenomination")
											String markDenomination) {
		if (StringUtils.isBlank(markDenomination)) {
			if (StringUtils.isBlank(flowBean.getMainForm().getWordRepresentation())) {
				// no mark word representation
				// no need to call service
				return new ModelAndView();
			}
			markDenomination = flowBean.getMainForm().getWordRepresentation();
		}

		flowBean.getMainForm().setWordRepresentation(markDenomination);

		ModelAndView modelAndView = new ModelAndView("similar_marks/similarmarks");
		
		trademarkFlowBean.setSimilarMarks(importService.getSimilarTradeMarks(officeCode, (FlowBeanImpl) flowBean));
        if(trademarkFlowBean.getSimilarMarks() == null || trademarkFlowBean.getSimilarMarks().size() == 0)
        {
            modelAndView = new ModelAndView("errors/errorTemplate");
            modelAndView.addObject("errorCode", "general.messages.similarMarks.noResultsFound");
            return modelAndView;
        }
		modelAndView.addObject("similarMarks", trademarkFlowBean.getSimilarMarks());
		return modelAndView;
	}

    @RequestMapping(value = "getSimilarMarksList", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody String getSimilarMarksList(@RequestParam(required = false, value = "markDenomination") String markDenomination)
    {
        if (StringUtils.isBlank(markDenomination)) {
            if (StringUtils.isBlank(flowBean.getMainForm().getWordRepresentation())) {
                // no mark word representation
                // no need to call service
                return null;
            }
            markDenomination = flowBean.getMainForm().getWordRepresentation();
        }
        flowBean.getMainForm().setWordRepresentation(markDenomination);
        try{
            trademarkFlowBean.setSimilarMarks(importService.getSimilarTradeMarks(officeCode, (FlowBeanImpl) flowBean));
        }catch (Exception ex){
            return("\"error \": \"Error connecting Similar Marks Service\"");
        }

        if(trademarkFlowBean.getSimilarMarks() == null || trademarkFlowBean.getSimilarMarks().size() == 0)
        {
            return null;
        }

        String json = null;
        try
        {
             json = new ObjectMapper().writeValueAsString(trademarkFlowBean.getSimilarMarks());
        }
        catch (Exception e)
        {
            throw new SPException("Cannot convert to json the list of SimilarMarkForm objects.", e);
        }
        return json;
    }
    
}
