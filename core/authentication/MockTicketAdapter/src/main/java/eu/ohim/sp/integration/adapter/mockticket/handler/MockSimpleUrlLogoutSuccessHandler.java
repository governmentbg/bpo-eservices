package eu.ohim.sp.integration.adapter.mockticket.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class MockSimpleUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {


	@Value("${mock.adapter.logout.url}")
	private String logoutURL;

//  Discarded: conflicts with ESAPI, throws intrusion detector as false positive	
//	private static final int COOKIES_TIME_TO_LIVE_REMOVE = 0;
//	private static final String JSESSIONID_COOKIE = "JSESSIONID";
//	private static final String MOCK_AUTHENTICATION_ADAPTER_COOKIE = "SPNET";


	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("onLogoutSuccess :: START ");

		logger.debug("onLogoutSuccesS :: setDefaultTargetUrl ["+logoutURL+"] ");
		setDefaultTargetUrl(logoutURL);

		//kill cookies jSessionID, SPNET (Discarded: conflicts with ESAPI, throws intrusion detector as false positive	)
		//leaveCookies(response, "", "", COOKIES_TIME_TO_LIVE_REMOVE);


		super.onLogoutSuccess(request, response, authentication);

		logger.info("onLogoutSuccess :: END ");


	}
//	Discarded: conflicts with ESAPI, throws intrusion detector as false positive	
//	private final static void leaveCookies(
//			HttpServletResponse response, String jSessionID, 
//			String sPNET, int timeToLive) {
//
//		/* Create cookies. */
//		Cookie jSessionIDCookie = new Cookie(JSESSIONID_COOKIE,jSessionID);
//		Cookie sPNETCookie = new Cookie(MOCK_AUTHENTICATION_ADAPTER_COOKIE, sPNET);
//
//		/* Set maximum age to cookies. */
//		jSessionIDCookie.setMaxAge(timeToLive);
//		sPNETCookie.setMaxAge(timeToLive);
//
//		/* Add cookies to response. */
//		response.addCookie(jSessionIDCookie);
//		response.addCookie(sPNETCookie);
//	}

}
