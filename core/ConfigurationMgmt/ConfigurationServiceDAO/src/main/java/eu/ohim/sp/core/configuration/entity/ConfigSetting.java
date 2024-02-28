/*
 *  SystemConfigurationServiceDao:: ConfigSetting 04/10/13 19:33 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.configuration.entity;

import eu.ohim.sp.common.util.DateUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ConfigSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	/** The validfrom. */
	private Date validfrom;

	/** The validto. */
	private Date validto;

	/** The modified by. */
	private String modifiedBy;
	
	private ConfigParam configParam;
	
	private Date dtCreated;

    private List<ConfigParamValue> values;

    public List<ConfigParamValue> getValues() {
        return values;
    }

    public void setValues(List<ConfigParamValue> values) {
        this.values = values;
    }

    public Date getValidfrom() {
		return DateUtil.cloneDate(validfrom);
	}

	public void setValidfrom(Date validfrom) {
		this.validfrom = DateUtil.cloneDate(validfrom);
	}

	public Date getValidto() {
		return DateUtil.cloneDate(validto);
	}

	public void setValidto(Date validto) {
		this.validto = DateUtil.cloneDate(validto);
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ConfigParam getConfigParam() {
		return configParam;
	}

	public void setConfigParam(ConfigParam configParam) {
		this.configParam = configParam;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtCreated() {
		return DateUtil.cloneDate(dtCreated);
	}

	public void setDtCreated(Date dtCreated) {
		this.dtCreated = DateUtil.cloneDate(dtCreated);
	}

	@Override
	public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
	}


}
