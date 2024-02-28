/*******************************************************************************
 * * $Id:: ConfigurationParameter.java 140658 2013-09-18 14:50:41Z velasca       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.configuration;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;

/**
 * The Class ConfigurationParameter.
 */
public abstract class ConfigurationParameter implements Serializable {

	/** The system component. */
	private String component;

	/** The Parameter name. */
	private String name;

	/** The description. */
	private String description;

	/** The hidden. */
	private boolean hidden;

	/** The modified by. */
	private String modifiedBy;

	/** The paramtype. */
	private ConfigParamType paramtype;

	private Long configSettingId;

	/** The validfrom. */
	private Date validfrom;

	/** The validto. */
	private Date validto;

	/** The param source. */
	private ConfigParamSource paramSource;

	/**
	 * Instantiates a new configuration parameter.
	 */
	public ConfigurationParameter() {
	}

    public ConfigurationParameter(String name, String component, ConfigParamType configParamType) {
        this.name = name;
        this.component = component;
        this.paramtype = configParamType;
    }

	/**
	 * Instantiates a new configuration parameter.
	 * 
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param hidden
	 *            the hidden
	 * @param modifiedBy
	 *            the modified by
	 * @param paramtype
	 *            the paramtype
	 * @param validfrom
	 *            the validfrom
	 * @param validto
	 *            the validto
	 */
	public ConfigurationParameter(String name, String description,
			boolean hidden, String modifiedBy, ConfigParamType paramtype, Date validfrom,
			Date validto, String component, ConfigParamSource paramSource) {
		this.name = name;
		this.description = description;
		this.hidden = hidden;
		this.modifiedBy = modifiedBy;
		this.paramtype = paramtype;
		this.validfrom = DateUtil.cloneDate(validfrom);
		this.validto = DateUtil.cloneDate(validto);
		this.component = component;
		this.paramSource = paramSource;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if is hidden.
	 * 
	 * @return true, if is hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Sets the hidden.
	 * 
	 * @param hidden
	 *            the new hidden
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * Gets the modified by.
	 * 
	 * @return the modified by
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the modified by.
	 * 
	 * @param modifiedBy
	 *            the new modified by
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Gets the paramtype.
	 * 
	 * @return the paramtype
	 */
	public ConfigParamType getParamtype() {
		return paramtype;
	}

	/**
	 * Sets the paramtype.
	 * 
	 * @param paramtype
	 *            the new paramtype
	 */
	public void setParamtype(ConfigParamType paramtype) {
		this.paramtype = paramtype;
	}

	/**
	 * Gets the validfrom.
	 * 
	 * @return the validfrom
	 */
	public Date getValidfrom() {
		return DateUtil.cloneDate(validfrom);
	}

	/**
	 * Sets the validfrom.
	 * 
	 * @param validfrom
	 *            the new validfrom
	 */
	public void setValidfrom(Date validfrom) {
		this.validfrom = DateUtil.cloneDate(validfrom);
	}

	/**
	 * Gets the validto.
	 * 
	 * @return the validto
	 */
	public Date getValidto() {
		return DateUtil.cloneDate(validto);
	}

	/**
	 * Sets the validto.
	 * 
	 * @param validto
	 *            the new validto
	 */
	public void setValidto(Date validto) {
		this.validto = DateUtil.cloneDate(validto);
	}

	/**
	 * Gets the component.
	 * 
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}

	/**
	 * Sets the component.
	 * 
	 * @param component
	 *            the new component
	 */
	public void setComponent(String component) {
		this.component = component;
	}

	/**
	 * Gets the config setting id.
	 * 
	 * @return the config setting id
	 */
	public Long getConfigSettingId() {
		return configSettingId;
	}

	/**
	 * Sets the config setting id.
	 * 
	 * @param configSettingId
	 *            the new config setting id
	 */
	public void setConfigSettingId(Long configSettingId) {
		this.configSettingId = configSettingId;
	}

	/**
	 * Gets the param source.
	 * 
	 * @return the param source
	 */
	public ConfigParamSource getParamSource() {
		return paramSource;
	}

	/**
	 * Sets the param source.
	 * 
	 * @param paramSource
	 *            the new param source
	 */
	public void setParamSource(ConfigParamSource paramSource) {
		this.paramSource = paramSource;
	}

}
