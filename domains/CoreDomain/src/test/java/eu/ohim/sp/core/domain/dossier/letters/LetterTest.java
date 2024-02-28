/*
 *  CoreDomain:: LetterTest 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.letters;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 16/10/13
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public class LetterTest {
    @Test
    public void bean_testProperties() throws IntrospectionException {
        JavaBeanTester.test(Letter.class);
    }

    @Test
    public void bean_testClient() throws IntrospectionException {
        JavaBeanTester.test(LetterClient.class);
    }

    @Test
    public void bean_testLetterSearch() throws IntrospectionException {
        JavaBeanTester.test(LetterSearchCriteria.class);
    }

    @Test
    public void bean_testLetterTemplate() throws IntrospectionException {
        JavaBeanTester.test(LetterTemplate.class);
    }

    @Test
    public void bean_testLetterTemplateSearchCriteria() throws IntrospectionException {
        JavaBeanTester.test(LetterTemplateSearchCriteria.class);
    }

    @Test
    public void bean_testRecipient() throws IntrospectionException {
        JavaBeanTester.test(Recipient.class);
    }
}
