/*
 *  SystemConfigurationServiceDao:: ConfigParamValue 09/08/13 16:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.configuration.entity;

import eu.ohim.sp.common.util.DateUtil;

import java.util.Date;

/**
 * The Class ConfigParamValue.
 */
public class ConfigParamValue {

	/** The id. */
	private Long id;

    /** The paramvalue. */
    private String paramvalue;

    /** The configparam. */
    private ConfigSetting configSetting;

    private Date dtCreated;

    public ConfigParamValue() {
    }

    public ConfigParamValue(String paramvalue, Date dtCreated) {
        this.paramvalue = paramvalue;
        this.dtCreated = DateUtil.cloneDate(dtCreated);
    }

    /**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the paramvalue.
	 *
	 * @return the paramvalue
	 */
	public String getParamvalue() {
		return paramvalue;
	}

	/**
	 * Sets the paramvalue.
	 *
	 * @param paramvalue the new paramvalue
	 */
	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	/**
	 * Gets the configparam.
	 *
	 * @return the configparam
	 */
	public ConfigSetting getConfigSetting() {
		return configSetting;
	}

	/**
	 * Sets the configparam.
	 *
	 * @param configSetting the new configSetting
	 */
	public void setConfigSetting(ConfigSetting configSetting) {
		this.configSetting = configSetting;
	}

	public Date getDtCreated() {
		return DateUtil.cloneDate(dtCreated);
	}

	public void setDtCreated(Date dtCreated) {
		this.dtCreated = DateUtil.cloneDate(dtCreated);
	}

}
