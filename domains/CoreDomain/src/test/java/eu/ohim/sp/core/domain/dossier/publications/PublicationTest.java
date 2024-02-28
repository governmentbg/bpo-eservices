/*
 *  CoreDomain:: PublicationTest 16/10/13 10:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.publications;


import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

public class PublicationTest {

    @Test
    public void bean_testProperties() throws IntrospectionException {
        JavaBeanTester.test(Publication.class);
    }

}
