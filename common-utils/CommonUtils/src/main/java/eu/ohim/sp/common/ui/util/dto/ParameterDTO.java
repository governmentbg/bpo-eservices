/*******************************************************************************
 * * $Id:: ParameterDTO.java 49260 2012-10-29 13:02:02Z karalch                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util.dto;

import java.io.Serializable;

public class ParameterDTO  implements Serializable{
    private static final long serialVersionUID = 1L;
    private String code;
    private String value;
    private String description;
    private String order;
    
    /**
     * Get the code
     * 
     * @return code
     */
    public String getCode() {
        return code;
    }
    /**
     * Set the code
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Return the value
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }
    /**
     * Set the value
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * Get the description
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set the description
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Get the order
     * 
     * @return order
     */
    public String getOrder() {
        return order;
    }
    /**
     * Set the order
     * 
     * @param order
     */
    public void setOrder(String order) {
        this.order = order;
    }


    /**
     * Get the String representation of this object
     *
     * @return String representation of this object
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("code: ").append(code).append(" ").
            append("value: ").append(value).append(" ").
            append("description: ").append(description).append("\n");
        return sb.toString();
    }
}
