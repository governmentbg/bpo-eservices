/*
 *  CoreDomain:: InboxConfigTest 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.userpref;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 16/10/13
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
public class InboxConfigTest {
    @Test
    public void bean_testInboxConfig() throws IntrospectionException {
        JavaBeanTester.test(InboxConfig.class);
    }

    @Test
    public void bean_testInboxConfigColumn() throws IntrospectionException {
        JavaBeanTester.test(InboxConfigColumn.class);
    }
}
