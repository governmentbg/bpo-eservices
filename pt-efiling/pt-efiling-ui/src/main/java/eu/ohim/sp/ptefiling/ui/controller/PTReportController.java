package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AbstractReportController;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.ptefiling.ui.adapter.PTFlowBeanFactory;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.service.FormUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class PTReportController extends AbstractReportController {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger LOGGER = Logger.getLogger(PTReportController.class);
    /**
     * session bean
     */
    @Autowired
    private PTFlowBean flowBean;
    
    @Autowired
    private PTFlowBeanFactory flowBeanFactory;

    @Autowired
    private FormUtil formUtil;

    private static final String PACKAGE = "eu.ohim.sp.core.rules";
    
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
	    return super.getReceipt(PACKAGE+"."+formUtil.getModule(), ReportService.RECEIPT_REPORT, flowBeanFactory.convertTo(flowBean), null);

    }
	
}
