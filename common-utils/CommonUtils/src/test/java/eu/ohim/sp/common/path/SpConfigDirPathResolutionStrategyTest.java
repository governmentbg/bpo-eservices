package eu.ohim.sp.common.path;

import org.junit.Test;

import eu.ohim.sp.common.path.PathResolutionStrategy;
import eu.ohim.sp.common.path.SpConfigDirPathResolutionStrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SpConfigDirPathResolutionStrategyTest {

    @Test(expected = IllegalStateException.class)
    public void should_fail_to_create_instance_when_property_not_found() {
        // given
        System.setProperty(SpConfigDirPathResolutionStrategy.SP_CONFIG_DIR_PROPERTY, "");

        // when
        new SpConfigDirPathResolutionStrategy();

        // then
        // exception is thrown
    }

    @Test
    public void should_create_instance_when_property_set() {
        // given
        System.setProperty(SpConfigDirPathResolutionStrategy.SP_CONFIG_DIR_PROPERTY, "some-dir");

        // when
        PathResolutionStrategy strategy = new SpConfigDirPathResolutionStrategy();

        // then
        assertNotNull(strategy);
    }

    @Test
    public void should_return_path_relative_to_property() {
        final String spConfigDir = "some-dir";
        // given
        System.setProperty(SpConfigDirPathResolutionStrategy.SP_CONFIG_DIR_PROPERTY, spConfigDir);

        // when
        PathResolutionStrategy strategy = new SpConfigDirPathResolutionStrategy();

        // then
        String path = strategy.resolvePath("my-path");

        assertEquals(spConfigDir + "/my-path", path);
    }

}
