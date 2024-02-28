/*
 *  CoreDomain:: Designer 19/08/13 11:10 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.design;

import eu.ohim.sp.core.domain.person.PersonRole;

public class Designer extends PersonRole implements Sequenceable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1947132009156589112L;
	
	private boolean waiver;
	
	private boolean belongsToAGroup;
	
	// If designer does not waive and belongs to a group:
	private String groupName;

    private Boolean designsAssociatedIndicator;

    private int sequenceNumber;
    
    public boolean isWaiver() {
		return waiver;
	}

	public void setWaiver(boolean waiver) {
		this.waiver = waiver;
	}

	public boolean isBelongsToAGroup() {
		return belongsToAGroup;
	}

	public void setBelongsToAGroup(boolean belongsToAGroup) {
		this.belongsToAGroup = belongsToAGroup;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

    public Boolean getDesignsAssociatedIndicator() {
        return designsAssociatedIndicator;
    }

    public void setDesignsAssociatedIndicator(Boolean designsAssociatedIndicator) {
        this.designsAssociatedIndicator = designsAssociatedIndicator;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
}
