/*******************************************************************************
 * * $Id:: ParameterControllerDTO.java 49260 2012-10-29 13:02:02Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util.dto;

public class ParameterControllerDTO {
    private String code;
    private String value;

    /**
	 * Method that returns the code
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Method that sets the code
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * Method that returns the value
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Method that sets the value
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
