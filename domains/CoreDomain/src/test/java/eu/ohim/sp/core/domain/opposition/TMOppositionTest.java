/*
 *  CoreDomain:: TMOppositionTest 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
/**
 * 
 */
package eu.ohim.sp.core.domain.opposition;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

public class TMOppositionTest {
    @Test
    public void bean_testTMOpposition() throws IntrospectionException {
        JavaBeanTester.test(TMOpposition.class);
    }

    @Test
    public void bean_testOppositionGround() throws IntrospectionException {
        JavaBeanTester.test(OppositionGround.class);
    }

    @Test
    public void bean_testOppositionRelativeGrounds() throws IntrospectionException {
        JavaBeanTester.test(OppositionRelativeGrounds.class);
    }

    @Test
    public void bean_testOppositionAbsoluteGrounds() throws IntrospectionException {
        JavaBeanTester.test(OppositionAbsoluteGrounds.class);
    }

    @Test
    public void bean_testLegalActVersion() throws IntrospectionException {
        JavaBeanTester.test(LegalActVersion.class);
    }

    @Test
    public void bean_testLawArticle() throws IntrospectionException {
        JavaBeanTester.test(LawArticle.class);
    }

}
