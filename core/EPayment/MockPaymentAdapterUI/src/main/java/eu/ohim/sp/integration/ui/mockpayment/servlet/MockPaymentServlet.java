package eu.ohim.sp.integration.ui.mockpayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.ohim.sp.integration.ui.mockpayment.util.MockPrintWriterPaymentLoader;


@WebServlet(name = "MockPaymentServlet", urlPatterns = {"/mockpayment"}, loadOnStartup = 1)
public class MockPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger("MockPaymentServlet");

	public void init() {
		System.out.println("init() :: START &END");
	}

	public MockPaymentServlet() throws IOException {
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("service() :: START");

		Map<String, String> params = new HashMap<String, String>();
		params.put("flowKey", request.getParameter("flowKey"));
		params.put("flowMode", request.getParameter("flowMode"));
		params.put("applicationFilingNumber", request.getParameter("applicationFilingNumber"));
		params.put("PAYMENT_ID", request.getParameter("PAYMENT_ID"));


		String url = "paymentCallback.htm";
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		MockPrintWriterPaymentLoader.appendMockPaymentForm(pw, url, params);

		log.info("service() :: END");
	}
}
