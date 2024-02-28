/*
 *  ResourceService:: ResourceService 14/11/13 15:48 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.resource;

/**
 * The {@code ResourceService} system interface provides User Interface modules
 * ({@code TM e-Filing}, {@code DS e-Filing} and {@code e-Services}) with a method 
 * to retrieve system resources from an external service.
 * <p>
 * The Resource component interfaces with the following SP components:
 * <dl>
 * <dt><b>System:</b>
 * </dl> 
 * <ul>
 * 		<li>{@code ResourceAdapter}: retrieval of system resources from externally.</li>
 * </ul> 
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
public interface ResourceService {

	/**
     * Retrieves message resources from an external location by key.
     *
     * @param messageKey Key of the message resource to be retrieved (i.e. <i>layout.header.applicationTitle</i>).
     * 
     * @return Message ({@code String}).
     */
    String getMessage(String messageKey);
}
