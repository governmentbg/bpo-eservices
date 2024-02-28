/*
 *  CoreDomain:: TaskTest 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.tasks;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 16/10/13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public class TaskTest {
    @Test
    public void bean_testTaskProperties() throws IntrospectionException {
        JavaBeanTester.test(Task.class);
    }

    @Test
    public void bean_testComment() throws IntrospectionException {
        JavaBeanTester.test(Comment.class);
    }

    @Test
    public void bean_testTaskSearchCriteria() throws IntrospectionException {
        JavaBeanTester.test(TaskSearchCriteria.class);
    }

}
