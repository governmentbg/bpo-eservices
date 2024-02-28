package eu.ohim.sp.common.security.authentication.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Listener to detect when an HTTP session is destroyed and call to the
 * delegated SSO clients that may need it for Single Sign Out feature.
 * <p>
 * 
 * @author OHIM
 * @since 1.0.0
 */
public final class SPSingleSignOutHttpSessionListener implements
		HttpSessionListener, ApplicationContextAware {

	ApplicationContext applicationContext = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(final HttpSessionEvent event) {
		// DO NOTHING
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(final HttpSessionEvent event) {

		// Configuration service - authentication.client.ssout.enabled
//		if (true) {
			HttpSessionListener spDelegateSingleSignOutHttpSessionListener = applicationContext
					.getBean("spDelegateSingleSignOutHttpSessionListener",
							HttpSessionListener.class);
			spDelegateSingleSignOutHttpSessionListener.sessionDestroyed(event);
//		}

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
