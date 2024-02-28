package eu.ohim.sp.core.domain.configuration;

public class SimpleConfigurationParameter extends ConfigurationParameter {

	/** The values. */
	private String value;

    public SimpleConfigurationParameter() {
        setParamtype(ConfigParamType.SIMPLE);
    }

    public SimpleConfigurationParameter(String name, String component, String value) {
        super(name, component, ConfigParamType.SIMPLE);
        this.value = value;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
