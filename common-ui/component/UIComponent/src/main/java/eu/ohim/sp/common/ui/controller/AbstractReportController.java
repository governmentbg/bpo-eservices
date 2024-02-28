package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class AbstractReportController {

	@Autowired
	private ReportService reportService;

	public byte[] getReceipt(String module, String template, Object application, Map<String, byte[]> attachments) {
		return reportService.generateReport(module, template, this.getFlowBean().getFirstLang(), application,
				attachments, true, null);
	}

	public byte[] getReceipt(String module, String template, Object application, Map<String, byte[]> attachments, boolean draft) {
		return reportService.generateReport(module, template, this.getFlowBean().getFirstLang(), application,
			attachments, draft, null);
	}

	public byte[] getSimilarityReceipt(String module, List<TradeMark> tmList) {
		return reportService.generateSimilaritySpreadSheet(module, tmList);
	}

	abstract public FlowBean getFlowBean();
}
