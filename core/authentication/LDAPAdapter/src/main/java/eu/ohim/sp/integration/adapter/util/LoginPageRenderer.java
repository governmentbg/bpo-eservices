package eu.ohim.sp.integration.adapter.util;

import org.springframework.util.StringUtils;

import java.io.PrintWriter;
import java.util.Map;

/**
 * LoginPageRenderer - constructs a default simple login form
 *
 * @author simoean
 */
public class LoginPageRenderer {

    public static void render (PrintWriter pw, String url, Map<String, String[]> parameterMap){

        pw.println("<html>");
        pw.println("<head><title>User authentication</title></title>");
        pw.println("<body>");
        pw.println("<h1>Login form</h1>");
        pw.println("<form action=\""+url+"\" method=\"GET\" >");

        if (parameterMap != null) {
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String [] values = entry.getValue();
                String key = entry.getKey();
                for (String value : values) {
                    if (StringUtils.hasText(value)){
                        pw.println("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\" />");
                    }
                }
            }
        }

        pw.println("Username: <input type=\"text\" name=\"username\" />");
        pw.println("<br>");
        pw.println("Password: <input type=\"password\" name=\"password\" />");
        pw.println("<br>");
        pw.println("<br>");
        pw.println("<input type=\"submit\" name=\"login\" value=\"Login\" />");
        pw.println("</form>");
        pw.println("</body></html>");

    }

}
