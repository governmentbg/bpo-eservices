/*
 *  CoreDomain:: AnnotationTest 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.annotations;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 16/10/13
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationTest {
    @Test
    public void bean_testProperties() throws IntrospectionException {
        JavaBeanTester.test(Annotation.class);
    }
}
