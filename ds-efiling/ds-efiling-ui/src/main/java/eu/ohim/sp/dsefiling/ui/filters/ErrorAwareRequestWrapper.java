package eu.ohim.sp.dsefiling.ui.filters;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import eu.ohim.sp.dsefiling.ui.exceptions.InternalServerErrorException;
import eu.ohim.sp.dsefiling.ui.exceptions.NoSuchConversationException;
import eu.ohim.sp.dsefiling.ui.exceptions.PageNotFoundException;

/**
 * 
 * @author serrajo
 *
 */
public class ErrorAwareRequestWrapper extends HttpServletResponseWrapper {

	private static final int SC_NO_SUCH_CONVERSATION = 902;
	
	public ErrorAwareRequestWrapper(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
	}

	@Override
	public void sendError(int errorCode) throws IOException {
		switch (errorCode) {
			case HttpServletResponse.SC_NOT_FOUND:
				throw new PageNotFoundException();
			case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
				throw new InternalServerErrorException();
			case SC_NO_SUCH_CONVERSATION:
				throw new NoSuchConversationException();
			default:
				super.sendError(errorCode);
				break;
		}
	}

}
