/*******************************************************************************
 * * $Id:: ClassificationErrorEnum.java 121785 2013-06-06 18:45:12Z karalch      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.trademark;

public enum ClassificationErrorEnum {
	HINT("Hint"), NOT_OK("Not OK"), NONE("None");
    
	private String value;

    private ClassificationErrorEnum(String value) {
		this.value = value;
	}
	
	/**
	 * Method that returns the value
	 * @return the value
	 */
	public String value() {
		return value;
	}
}
