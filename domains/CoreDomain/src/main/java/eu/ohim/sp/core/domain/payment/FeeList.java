/*
 *  CoreDomain:: FeeList 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.payment;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Fee list.
 */
public class FeeList extends Id implements Serializable
{

    private static final long serialVersionUID = 1L;
	/**
	 * The Fee type list.
	 */
    private List<FeeType> feeTypeList;

	/**
	 * Instantiates a new Fee list.
	 */
	public FeeList() {
        super();
    }

	/**
	 * Instantiates a new Fee list.
	 *
	 * @param feeTypeList the fee type list
	 */
	public FeeList(final List<FeeType> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }

	/**
	 * Gets fee type list.
	 *
	 * @return the fee type list
	 */
	public List<FeeType> getFeeTypeList() {
        if (feeTypeList == null) {
            feeTypeList = new ArrayList<FeeType>();
        }
        return this.feeTypeList;
    }

}
