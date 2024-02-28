package eu.ohim.sp.core.configuration.dao;

import eu.ohim.sp.common.path.PathResolutionStrategy;
import eu.ohim.sp.common.path.SpConfigDirPathResolutionStrategy;
import eu.ohim.sp.core.configuration.converter.ConfigParamConverter;
import eu.ohim.sp.core.configuration.entity.ConfigSetting;
import eu.ohim.sp.core.domain.configuration.ConfigParamType;
import eu.ohim.sp.core.domain.configuration.ConfigurationParameter;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

@ConfigFromFile
public class ConfigParamFileDAO implements ConfigParamDAO {

    private static final Logger LOGGER = Logger.getLogger(ConfigParamFileDAO.class.getName());

    private final static String CONFIG_FILE = "fo-config.properties";
    private final static char LIST_DELIMETER = ',';

    private Properties properties = null;

    private PathResolutionStrategy pathResolutionStrategy = new SpConfigDirPathResolutionStrategy();

    @Inject
    private FileOperations fileOperations;

    public ConfigParamFileDAO() throws IOException {
        this.properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(pathResolutionStrategy.resolvePath(CONFIG_FILE));
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            this.properties.load(reader);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.warning(e.getMessage());
                }
            }
        }

    }

    public String findConfigParamValue(String name, String component) {
        return properties.getProperty(composeKey(component, name));
    }

    public List<String> findConfigParamValueList(String name, String component) {
        String paramValue = findConfigParamValue(name, component);
        if (paramValue != null) {
            return Arrays.asList(paramValue.trim().split("\\s*(,\\s*)+"));
        } else {
            return null;
        }
    }

    public String findConfigParamXML(String name, String component) {
        final String xmlFile = findConfigParamValue(name, component);
        try {
            final String xmlFileContent = fileOperations.loadFileContent(xmlFile);
            if (xmlFileContent != null) return xmlFileContent;
            return xmlFile;
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

    public String findConfigParamObject(String name, String component) {
        return findConfigParamXML(name, component);
    }

    public <T> T getObject(String name, String component, Class<T> clazz) {
        return ConfigParamConverter.convertXML2Object(
                findConfigParamXML(name, component),
                clazz);
    }

    public Map<String, ConfigurationParameter> getConfigParamByModule(String module) {
        throw new RuntimeException("getConfigParamByModule not available with file based property approach");
    }

    public boolean setConfigParam(ConfigurationParameter configurationParameter) {
        throw new RuntimeException("setConfigParam not available with file based property approach");
    }

    public boolean setConfigParamList(Collection<ConfigurationParameter> configurationParameter) {
        throw new RuntimeException("setConfigParamList not available with file based property approach");
    }

    public Collection<ConfigurationParameter> getConfigParamHistory(String name, String component) {
        throw new RuntimeException("getConfigParamHistory not available with file based property approach");
    }

    @Override
    public ConfigSetting findActiveEntry(String name, String comp, ConfigParamType configParamType) {
        throw new RuntimeException("findActiveEntry not available with file based property approach");
    }

    private String composeKey(final String component, final String name) {
        return component + "." + name;
    }

    public String getFileContent(String name, String component) {
        final String file = findConfigParamValue(name, component);
        try {
            final String fileContent = fileOperations.loadFileContent(file);
            return fileContent;
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

}
