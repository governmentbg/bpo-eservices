/*******************************************************************************
 * * $Id:: CheckServletContextListener.java 49264 2012-10-29 13:23:34Z karalch   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package org.jboss.ejb.client.remoting;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Removes remote connections, but not all thread local references
 * @author karalch
 *
 */
public class CheckServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		AutoConnectionCloser.INSTANCE.run();
	}

}
