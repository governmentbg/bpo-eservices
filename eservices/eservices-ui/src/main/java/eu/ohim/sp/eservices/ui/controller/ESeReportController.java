package eu.ohim.sp.eservices.ui.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.ohim.sp.common.ui.controller.AbstractReportController;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.ApplicationServiceInterface;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.core.report.ReportServiceRemote;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.FormUtil;

@Controller
public class ESeReportController extends AbstractReportController {

	public static final String ERROR_COPY_BYTE_ARRAY = "error.common.copybytearray";

	/**
	 * session bean
	 */
	@Autowired
	private ESFlowBean flowBean;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ESFlowBeanFactory flowBeanFactory;

	@Autowired
	private FileServiceInterface fileService;
	
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private FormUtil formUtil;
	
	private static final String PACKAGE = "eu.ohim.sp.core.rules";
	
    /** Logger for this class. */
    private static final Logger logger = Logger.getLogger(ESeReportController.class);

	@RequestMapping(value = "receiptServ", method = RequestMethod.GET, produces = "application/pdf")
	@ResponseBody
	public byte[] getReceipt(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute(value = "template") Template template) {

		String module = PACKAGE+"."+ formUtil.getModule();
		
		
		// // Get the attachments for the trade mark (if more attachments are
		// needed to pass, add them here. eg priorities
		// // attachments...)
		Map<String, byte[]> attachments = new HashMap<String, byte[]>();
		if (flowBean.getMainForm() != null && flowBean.getMainForm().getFileWrapperImage() != null && flowBean.getMainForm().getFileWrapperImage().getStoredFiles() != null) {
          for (StoredFile file : flowBean.getMainForm().getFileWrapperImage().getStoredFiles()) {
		    byte[] attachmentByteArray;
			
		    attachmentByteArray = fileService.getFileStream(flowBean.getIdreserventity()).getContent();
		    attachments.put(file.getOriginalFilename(), attachmentByteArray);
			}
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=receipt.pdf");
		EServiceApplication eServiceApplication = flowBeanFactory.convertTo(flowBean);
		
		IPApplication iPApplication = applicationService.fillApplicationDocuments(eServiceApplication);
		eServiceApplication=(EServiceApplication)iPApplication;
		
		return reportService.generateReport(module,"receipt", "en",	eServiceApplication, null, Boolean.TRUE.toString(), new Date(), true);

	}
	
   

	@Override
	public FlowBean getFlowBean() {
		return flowBean;
	}

}
