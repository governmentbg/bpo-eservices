package eu.ohim.sp.dsefiling.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.ohim.sp.common.ui.controller.AbstractReportController;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.core.report.ReportService;



@Controller
public class DSReportController extends AbstractReportController {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger LOGGER = Logger.getLogger(DSReportController.class);
    
    private static final String MODULE = "dsefiling";
    /**
     * session bean
     */
    @Autowired
    private DSFlowBean flowBean;
    
    @Autowired
    private DSFlowBeanFactory flowBeanFactory;
    
    @Override
	public FlowBean getFlowBean() {
		return flowBean;
	}
	
    @RequestMapping(value = "receipt", method = RequestMethod.GET, produces = "application/pdf")
	@ResponseBody
	public byte[] getReceipt(@SuppressWarnings("unused") HttpServletRequest request, HttpServletResponse response) {
    	
    	if (LOGGER.isDebugEnabled()) {
    		LOGGER.debug("FIRST LANG: " + flowBean.getFirstLang());
    		LOGGER.debug((flowBean.getFirstLang() != null && flowBean.getFirstLang().equals("BG") ? "[X]" : "[ ]") + "BG");
    	}


	    // Set response
	    response.setHeader("Content-Disposition", "attachment; filename=" + ReportService.RECEIPT_REPORT + ".pdf");
	    return super.getReceipt(MODULE, ReportService.RECEIPT_REPORT, flowBeanFactory.convertTo(flowBean), null);

    }
	
}
