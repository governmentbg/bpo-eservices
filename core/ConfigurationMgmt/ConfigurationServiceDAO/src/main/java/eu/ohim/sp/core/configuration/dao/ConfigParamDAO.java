package eu.ohim.sp.core.configuration.dao;

import eu.ohim.sp.core.configuration.entity.ConfigSetting;
import eu.ohim.sp.core.domain.configuration.ConfigParamType;
import eu.ohim.sp.core.domain.configuration.ConfigurationParameter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ConfigParamDAO {

    Map<String, ConfigurationParameter> getConfigParamByModule(String module);

    String findConfigParamValue(String name, String component);

    List<String> findConfigParamValueList(String name, String component);

    String findConfigParamXML(String name, String component);

    <T> T getObject(String name, String component, Class<T> clazz);

    boolean setConfigParam(ConfigurationParameter configurationParameter);

    boolean setConfigParamList(Collection<ConfigurationParameter> configurationParameter);

    Collection<ConfigurationParameter> getConfigParamHistory(String name, String component);

    String findConfigParamObject(String name, String component);

    ConfigSetting findActiveEntry(String name, String comp, ConfigParamType configParamType);

    String getFileContent(String name, String component);
}
