/*
 *  CoreDomain:: TMOpposition 04/10/13 19:33 KARALCH $
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

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMark;

public class TMOpposition extends TradeMark implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4717490525974661249L;

    /** the opposedTradeMark */
    private LimitedTradeMark opposedTradeMark;

    /** the opposition grounds */
    private List<OppositionGround> oppositionGrounds;
    
    /** The opposition documents. */
    private List<AttachedDocument> oppositionDocuments;

	/**
	 * @return the opposedTradeMark
	 */
	public LimitedTradeMark getOpposedTradeMark() {
		return opposedTradeMark;
	}

	/**
	 * @param opposedTradeMark the opposedTradeMark to set
	 */
	public void setOpposedTradeMark(LimitedTradeMark opposedTradeMark) {
		this.opposedTradeMark = opposedTradeMark;
	}

	/**
	 * @return the oppositionGrounds
	 */
	public List<OppositionGround> getOppositionGrounds() {
		return oppositionGrounds;
	}

	/**
	 * @param oppositionGrounds the oppositionGrounds to set
	 */
	public void setOppositionGrounds(List<OppositionGround> oppositionGrounds) {
		this.oppositionGrounds = oppositionGrounds;
	}

	/**
	 * @return the oppositionDocuments
	 */
	public List<AttachedDocument> getOppositionDocuments() {
		return oppositionDocuments;
	}

	/**
	 * @param oppositionDocuments the oppositionDocuments to set
	 */
	public void setOppositionDocuments(List<AttachedDocument> oppositionDocuments) {
		this.oppositionDocuments = oppositionDocuments;
	}


    
	    
}
