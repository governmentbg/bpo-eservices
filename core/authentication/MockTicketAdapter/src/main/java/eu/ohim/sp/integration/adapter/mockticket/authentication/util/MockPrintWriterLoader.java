package eu.ohim.sp.integration.adapter.mockticket.authentication.util;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.StringUtils;



public class MockPrintWriterLoader {


	public static void appendLogiFormnrequest(PrintWriter pw, String url, Map<String, String[]> parameterMap, Properties properties, String ticketName){
		 
		Enumeration enuKeys = properties.keys();
		pw.println("<html>");
		pw.println("<head><title>SP Mock Authentication Server</title></title>");
		pw.println("<body>");
		pw.println("<h1>SP Mock Authentication Server</h1>");
		pw.println("</br>");
		pw.println("<p> This is an implementation of the Authentication adapter that fits the SP authentication integration contract</p>");
		pw.println("<p> See [https://svn.oami.europa.eu/svn/emrepo/softwarepackage/doc/integration/generic/authentication] for further details</p>");
		pw.println("</br>");
		pw.println("<h2>Login with user:</h2>");
		pw.println("<p> Click in the button and you will be loggued as the username</p>");
		
		pw.println("<form action=\""+url+"\" method=\"GET\" >");
		
		if (parameterMap != null) {
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				String [] values = entry.getValue();
				String key = entry.getKey();
				for (String value : values) {
					//avoid ESAPI escaping to null is parameter values are empty
					if (StringUtils.hasText(value)){
						pw.println("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\" />");
					}
				}
			}
		}
		
		while (enuKeys.hasMoreElements()) {
			String key = (String) enuKeys.nextElement();
			String value = properties.getProperty(key);
			pw.println("</br>");
			pw.println("<input type=\"submit\" name=\""+ticketName+"\" value=\""+key+"\" /> Has role  ["+value+"]  ");
			pw.println("</br>");
		}
		pw.println("</form>");

		pw.println("</body></html>");

	}

	
}
