package eu.ohim.sp.integration.adapter.ldap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LdapLogoutSuccessHandler - redirects users to an URL after logout
 *
 * @author simoean
 */
public class LdapLogoutSuccessHandler  extends SimpleUrlLogoutSuccessHandler {

    public static final String DEFAULT_LOGOUT_URL = "/";

    @Value("${ldap.logout.page.url}")
    private String logoutURL;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("LdapLogoutSuccessHandler.onLogoutSuccess :: START");

        logoutURL = StringUtils.isBlank(logoutURL) ? DEFAULT_LOGOUT_URL : logoutURL;
        logger.debug("LdapLogoutSuccessHandler.onLogoutSuccess :: logoutURL - "+logoutURL+" ");
        setDefaultTargetUrl(logoutURL);

        super.onLogoutSuccess(request, response, authentication);

        logger.info("LdapLogoutSuccessHandler.onLogoutSuccess :: END");
    }
}
