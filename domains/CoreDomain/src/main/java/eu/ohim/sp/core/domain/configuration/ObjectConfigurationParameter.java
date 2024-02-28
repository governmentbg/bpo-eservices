/*
 *  SystemConfigurationServiceDomain:: ObjectConfigurationParameter 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.configuration;

public class ObjectConfigurationParameter extends ConfigurationParameter {

	private Object value;
    private Class clazz;
    private String xml;

    public ObjectConfigurationParameter(String name, String component, String xml) {
        super(name, component, ConfigParamType.XML);
        this.xml = xml;
    }

    public ObjectConfigurationParameter(String name, String component, Object value, Class clazz) {
        super(name, component, ConfigParamType.OBJECT);
        this.value = value;
        this.clazz = clazz;
    }

    public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}


}
