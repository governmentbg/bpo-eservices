/*
 *  SystemConfigurationServiceDao:: ConfigParam 04/10/13 19:33 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.configuration.entity;

import eu.ohim.sp.core.domain.configuration.ConfigParamSource;
import eu.ohim.sp.core.domain.configuration.ConfigParamType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ConfigParam.
 */
public class ConfigParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;
	
	private List<ConfigSetting> entries;

	/** The name. */
	private String name;

	/** The component. */
	private Component component;

	// 0: simple, 1: list, 2: xml, 3: object
	/** The paramtype. */
	private ConfigParamType paramtype;

	// 0: path, 1:file_contents, null:path
	/** The param source. */
	private ConfigParamSource paramSource;
	
	/** The hidden. */
	private boolean hidden;

	/** The description. */
	private String description;


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
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Gets the component.
	 * 
	 * @return the component
	 */
	public Component getComponent() {
		return component;
	}

	/**
	 * Sets the component.
	 * 
	 * @param component
	 *            the new component
	 */
	public void setComponent(Component component) {
		this.component = component;
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

	public List<ConfigSetting> getEntries() {
		return entries;
	}

	public void setEntries(List<ConfigSetting> entries) {
		this.entries = entries;
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
