/*
 *  CoreDomain:: MarkSoundTest 04/10/13 19:46 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain;

import eu.ohim.sp.core.domain.trademark.SoundSpecification;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class MarkSoundTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(SoundSpecification.class);
    }
}
