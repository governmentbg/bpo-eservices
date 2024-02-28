package eu.ohim.sp.common.path;

import javax.enterprise.inject.Default;


/**
 * Uses ${sp.config.dir} environment property as prefix directory for resolving file's path
 *
 * @author Maciej Walkowiak
 */
@Default
public class SpConfigDirPathResolutionStrategy implements PathResolutionStrategy {
    public static final String SP_CONFIG_DIR_PROPERTY = "sp.core.config.dir";
    private String spConfigDir;

    public SpConfigDirPathResolutionStrategy() {
        spConfigDir = System.getProperty(SP_CONFIG_DIR_PROPERTY);

        if (spConfigDir == null || spConfigDir.isEmpty()) {
            throw new IllegalStateException(SP_CONFIG_DIR_PROPERTY + " not defined");
        }
    }

    @Override
    public String resolvePath(String path) {
        return spConfigDir + "/" + path;
    }
}
