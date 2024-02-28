/*
 *  CoreDomain:: Sequenceable 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.design;

/**
 * @author ionitdi
 */
public interface Sequenceable {
    int getSequenceNumber();
    void setSequenceNumber(int sequenceNumber);
}
