package eu.ohim.sp.integration.adapter.mockticket.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import eu.ohim.sp.integration.adapter.mockticket.authentication.exception.MockAuthenticationException;
import eu.ohim.sp.integration.adapter.mockticket.authentication.util.MockPrintWriterLoader;
import eu.ohim.sp.integration.adapter.mockticket.authentication.util.PropertyUtil;

/**
 * @author simonjo
 * 
 */
public class MockAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private Properties usersProp;
	
	@Value("${mock.adapter.ticket.name}")
	private String ticketName;

	/**
     * Redirects to MOCK login page
     * 
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
    	logger.info("commence ::START ");
    	logger.info("commence :: TICKETNAME ["+ticketName+"]");
    	
    	//service that requested the authentication
    	final String service = request.getRequestURI();
    	Map<String, String[]> parameterMap = request.getParameterMap();
    	
    	response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        response.setContentType("text/html");

        PrintWriter pw = response.getWriter(); 
        MockPrintWriterLoader.appendLogiFormnrequest(pw, service, parameterMap, usersProp, ticketName);
        response.flushBuffer();
        pw.close();
        logger.info("commence :: END");
    }

    @PostConstruct
    private void initialize() {
        try {
        	//this property file must be set in the webapp
        	usersProp = PropertyUtil.readProperties(PropertyUtil.MOCK_USERS_PROPERTIES_NAME);
        
        } catch (IOException e) {
            throw new MockAuthenticationException("An error has ocurred while validating token against mock");
        }
    }
   
  
}
