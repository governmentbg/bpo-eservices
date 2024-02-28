/*
 *  PersonAdapterServiceInterface:: DesignerAdapterServiceInterfaceLocal 06/09/13 19:36 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.external.person;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link DesignerClient} business interface.
 * 
 * @see DesignerClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface DesignerClientRemote extends DesignerClient {
}