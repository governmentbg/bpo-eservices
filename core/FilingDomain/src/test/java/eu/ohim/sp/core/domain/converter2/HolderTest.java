/*
 *  FspDomain:: HolderTest 15/11/13 15:58 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter2;

import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;

import eu.ohim.sp.filing.domain.tm.Applicant;
import eu.ohim.sp.filing.domain.tm.Transaction;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 31/10/13
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */
public class HolderTest {

    @Test
    public void testHolder() throws InstantiationException, IllegalAccessException {


        Holder holder = PersonConverterTest.fillPerson(Holder.class);

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();


        tMeServiceApplication.setHolders(new ArrayList<Holder>());
        tMeServiceApplication.getHolders().add(holder);

        Transaction transaction = CommonSetup.getMapper().map(tMeServiceApplication, Transaction.class);

        PersonConverterTest.validatePerson(holder, transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getHolderDetails().getPreviousHolder().get(0), Applicant.class);

    }

}
