/*******************************************************************************
 * * $Id::                                                          $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.adapter.SimilarMarkFactory;
import eu.ohim.sp.common.ui.controller.AbstractReportController;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.service.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ReportController extends AbstractReportController {

    public static final String ERROR_COPY_BYTE_ARRAY = "error.common.copybytearray";

    public static final String MODULE = "tmefiling";
    
    /**
     * session bean
     */
    @Autowired
    private TMFlowBean flowBean;

    @Autowired
    private TradeMarkSearchService tradeMarkSearchService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private FlowBeanFactory flowBeanFactory;

    @Autowired
    private SimilarMarkFactory similarMarkFactory;

    @Autowired
    private FormUtil formUtil;

    @RequestMapping(value = "receipt", method = RequestMethod.GET, produces = "application/pdf")
	@ResponseBody
	public byte[] getReceipt(HttpServletRequest request, HttpServletResponse response) {
    	
	    // Set response
	    response.setHeader("Content-Disposition", "attachment; filename=" + ReportService.RECEIPT_REPORT + ".pdf");

        TradeMarkApplication tradeMarkApplication = flowBeanFactory.convertToTradeMarkApplication((FlowBeanImpl) flowBean);

        tradeMarkApplication = (TradeMarkApplication) applicationService.fillApplicationDocuments(tradeMarkApplication);

	    return super.getReceipt(formUtil.getModule(), ReportService.RECEIPT_REPORT, tradeMarkApplication, null);
    }    
    
    /**
    	Just in case at the end they want the language of the receipt to be the UI language, put the following lines:
        String langCode = ((LanguagesFlowBean) flowBean).getFirstLang();    	
    	langCode = localeComponent.getLocale(request).getValue();
     */
    @RequestMapping(value = "similarTMReport", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public byte[] getSimilarTMReport(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Set response
        response.setHeader("Content-Disposition", "attachment; filename="
                + ReportService.SIMILAR_TRADE_MARK_REPORT + ".pdf");

        TradeMark tm = flowBeanFactory.convertToTradeMarkApplication((FlowBeanImpl) flowBean).getTradeMark();
        return this.getReceipt(MODULE, ReportService.SIMILAR_TRADE_MARK_REPORT, tradeMarkSearchService.getPreclearanceReport(tm.getReceivingOffice(), tm), null, false);
    }

    @RequestMapping(value = "similarTMReportXLSX", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public byte[] getSimilarTMReportXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Set response
        response.setHeader("Content-Disposition", "attachment; filename="
                + ReportService.SIMILAR_TRADE_MARK_REPORT + ".xlsx");

        TradeMark tm = flowBeanFactory.convertToTradeMarkApplication((FlowBeanImpl) flowBean).getTradeMark();
        return this.getSimilarityReceipt(MODULE, tradeMarkSearchService.getPreclearanceReport(tm.getReceivingOffice(), tm));
    }

	@Override
	public FlowBean getFlowBean() {
		return (FlowBean) flowBean;
	}
}
