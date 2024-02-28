package eu.ohim.sp.common.path;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import eu.ohim.sp.common.path.AbsolutePathFileResolutionStrategy;

public class AbsolutePathFileResolutionStrategyTest {
    private AbsolutePathFileResolutionStrategy absolutePathFileResolutionStrategy = new AbsolutePathFileResolutionStrategy();

    @Test
    public void should_return_same_path() {
        final String path = "some/path";

        assertEquals(path, absolutePathFileResolutionStrategy.resolvePath(path));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_path_is_null() {
        absolutePathFileResolutionStrategy.resolvePath(null);
    }
}
