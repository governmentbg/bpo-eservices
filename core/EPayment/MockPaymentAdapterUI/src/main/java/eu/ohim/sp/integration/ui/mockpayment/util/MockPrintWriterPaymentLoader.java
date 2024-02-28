package eu.ohim.sp.integration.ui.mockpayment.util;

import java.io.PrintWriter;
import java.util.Map;

public class MockPrintWriterPaymentLoader {

	public static void appendMockPaymentForm(PrintWriter pw, String url, Map<String, String> parameterMap) {
		String PAY = "PAY";
		String PAY_OK = "PAYMENT_OK";
		String PAYMENT_ERROR = "PAYMENT_ERROR";
		String PAYMENT_CANCELLED = "PAYMENT_CANCELLED";
		String CANCEL = "CANCEL";
		String ERROR = "ERROR";

		pw.println("<html>");
		pw.println("<head><title>SP Mock e-Payment Server</title></title>");
		pw.println("<body>");
		pw.println("<h1>SP Mock Payment Server</h1>");
		pw.println("</br>");
		pw.println("<p>This is an implementation of a Payment plattform that fits the SP integration contract</p>");
		pw.println("<p>See [https://svn.oami.europa.eu/svn/emrepo/softwarepackage/doc/integration/fo/ohim] for further details</p>");
		pw.println("</br>");
		pw.println("<h2>Payment details:</h2>");

		pw.println("<p>Click in the button to pick an action</p>");

		//Pay form-------------------------------------------------------
		pw.println("<form id=\"pay\" action=\"" + url + "\" method=\"POST\" >");
		if (parameterMap != null) {
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				pw.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + value + "\" />");
			}
		}

		pw.println("<input type=\"hidden\" name=\"STATUS\" value=\"" + PAY_OK + "\" />");
		pw.println("<input type=\"submit\" value=\"" + PAY + "\" />");

		pw.println("</form>");

		//Cancel form-------------------------------------------------------
		pw.println("<form id=\"cancel\" action=\"" + url + "\" method=\"POST\" >");
		if (parameterMap != null) {
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				pw.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + value + "\" />");
			}
		}
		pw.println("<input type=\"hidden\" name=\"STATUS\" value=\"" + PAYMENT_CANCELLED + "\" />");
		pw.println("<input type=\"submit\" value=\"" + CANCEL + "\" />");
		pw.println("</form>");

		//Error form-------------------------------------------------------

		pw.println("<form id=\"error\" action=\"" + url + "\" method=\"POST\" >");
		if (parameterMap != null) {
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				pw.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + value + "\" />");
			}
		}
		//does not transmit payment cancel aprameter
		pw.println("<input type=\"hidden\" name=\"STATUS\" value=\"" + PAYMENT_ERROR + "\" />");
		pw.println("<input type=\"submit\" value=\"" + ERROR + "\" />");
		pw.println("</form>");
		
		pw.println("</body>");
		pw.println("<script type=\"text/javascript\">");
		pw.println("    (function() { ");
		pw.println("    	if (window.parent.document.getElementById('_csrf')) { ");
		pw.println("            var token = window.parent.document.getElementById('_csrf').content; ");
		pw.println("            document.getElementById('pay').innerHTML += '<input type=\"hidden\" name=\"_csrf\" value=\"\'+token+\'\" />'");
		pw.println("            document.getElementById('cancel').innerHTML += '<input type=\"hidden\" name=\"_csrf\" value=\"\'+token+\'\" />'");
		pw.println("            document.getElementById('error').innerHTML += '<input type=\"hidden\" name=\"_csrf\" value=\"\'+token+\'\" />'");
		pw.println("        } ");
		pw.println("    })() ");
		pw.println("</script>");

		pw.println("</html>");

	}

}
