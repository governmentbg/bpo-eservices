/*
 *  CoreDomain:: TMOppositionDossierTest 16/10/13 10:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;


/**
 * Dossier of type Trademark representation.
 * 
 * @author masjose
 * 
 */
public class TMOppositionDossierTest extends Dossier {

    @Test
    public void bean_testProperties() throws IntrospectionException {
        JavaBeanTester.test(TMOppositionDossier.class);
    }

}