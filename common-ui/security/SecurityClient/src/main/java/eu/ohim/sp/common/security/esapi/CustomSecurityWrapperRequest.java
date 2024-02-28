package eu.ohim.sp.common.security.esapi;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.filters.SecurityWrapperRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSecurityWrapperRequest extends SecurityWrapperRequest {
	
	
    private Integer secureParameterLength;
	
	private final Logger logger = ESAPI.getLogger("CustomSecurityWrapperRequest");
	
	public CustomSecurityWrapperRequest(HttpServletRequest request, Integer secureParameterLength) {
		super(request);
		this.secureParameterLength = secureParameterLength;
	}
	
	@Override
	public String getParameter(String name, boolean allowNull) {
        return getParameter(name, allowNull, getSecureParameterLength(), "HTTPParameterValue");
    }

	@Override
	public Map getParameterMap() {
        @SuppressWarnings({"unchecked"})
        Map<String,String[]> map = getHttpServletRequest().getParameterMap();
        Map<String,String[]> cleanMap = new HashMap<String,String[]>();
        for (Object o : map.entrySet()) {
            try {
                Map.Entry e = (Map.Entry) o;
                String name = (String) e.getKey();
                String cleanName = ESAPI.validator().getValidInput("HTTP parameter name: " + name, name, "HTTPParameterName", 100, true);

                String[] value = (String[]) e.getValue();
                String[] cleanValues = new String[value.length];
                for (int j = 0; j < value.length; j++) {
                    String cleanValue = ESAPI.validator().getValidInput("HTTP parameter value: " + value[j], value[j], "HTTPParameterValue", getSecureParameterLength(), true);
                    cleanValues[j] = cleanValue;
                }
                cleanMap.put(cleanName, cleanValues);
            } catch (ValidationException e) {
                // already logged
            }
        }
        return cleanMap;
    }
	
	 public String[] getParameterValues(String name) {
	        String[] values = getHttpServletRequest().getParameterValues(name);
	        List<String> newValues;

		if(values == null)
			return null;
	        newValues = new ArrayList<String>();
	        for (String value : values) {
	            try {
	                String cleanValue = ESAPI.validator().getValidInput("HTTP parameter value: " + value, value, "HTTPParameterValue", getSecureParameterLength(), true);
	                newValues.add(cleanValue);
	            } catch (ValidationException e) {
	                logger.warning(Logger.SECURITY_FAILURE, "Skipping bad parameter");
	            }
	        }
	        return newValues.toArray(new String[newValues.size()]);
	    }
	
	private HttpServletRequest getHttpServletRequest() {
    	return (HttpServletRequest)super.getRequest();
    }

	private Integer getSecureParameterLength() {
		if(secureParameterLength == null){
			secureParameterLength = 2000;
		}
		return secureParameterLength;
	}
	
}
