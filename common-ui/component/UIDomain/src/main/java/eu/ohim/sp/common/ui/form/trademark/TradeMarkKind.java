/*******************************************************************************
	 * * $Id:: TradeMarkKind.java 121785 2013-06-06 18:45:12Z medinju                     $
	 * *       . * .
	 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
	 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
	 * *   *   RRR     *
	 * *    .  RR RR  .   ALL RIGHTS RESERVED
	 * *     * . _ . *
	 ******************************************************************************/
	/**
	 * 
	 */
package eu.ohim.sp.common.ui.form.trademark;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.core.configuration.domain.country.xsd.Countries;

public enum TradeMarkKind {

	INDIVIDUAL("Individual"),
	COLLECTIVE("Collective"),
	CERTIFICATE("Certificate"),
	CERTIFICATION("Certification"),
	GUARANTEE("Guarantee"),
	DEFENSIVE("Defensive"),
	OTHER("Other");

	private final String value;

	private TradeMarkKind(final String value) {
		this.value = value;
	}

	public String value(){
		return value;
	}
	
	
    public List<TradeMarkKind> getTradeMarkKind() {
    	ArrayList<TradeMarkKind> values = new ArrayList<TradeMarkKind>();
    	for(TradeMarkKind value : TradeMarkKind.values()) {
			values.add(value);
    	}
    	return values;
    }
	
	public List<String> defaultValues() {
		ArrayList<String> values = new ArrayList<String>();
		/*for(TradeMarkKind value : TradeMarkKind.values()) {
			values.add(value.toString());
		}*/
		values.add("hola");
		values.add("adios");
		return values;
	}

	@Override
	public String toString() {
		return value;
	}
}

