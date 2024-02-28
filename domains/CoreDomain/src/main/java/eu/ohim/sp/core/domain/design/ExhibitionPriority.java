/*
 *  CoreDomain:: ExhibitionPriority 19/08/13 10:57 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.design;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 19/08/13
 * Time: 10:12
 * To change this template use File | Settings | File Templates.
 */
public class ExhibitionPriority extends eu.ohim.sp.core.domain.claim.ExhibitionPriority implements Sequenceable {

    private int sequenceNumber;

    public int getSequenceNumber()
    {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber)
    {
        this.sequenceNumber = sequenceNumber;
    }

    private List<Design> designs;

	public List<Design> getDesigns() {
		return designs;
	}

	public void setDesigns(List<Design> designs) {
		this.designs = designs;
	}


}
