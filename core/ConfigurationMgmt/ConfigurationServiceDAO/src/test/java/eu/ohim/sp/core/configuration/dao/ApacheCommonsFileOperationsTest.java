package eu.ohim.sp.core.configuration.dao;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ohim.sp.common.path.PathResolutionStrategy;


import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApacheCommonsFileOperationsTest {
    @InjectMocks
    private ApacheCommonsFileOperations fileOperations;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private PathResolutionStrategy pathResolutionStrategy;

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_to_load_when_path_is_null() throws Exception {
        fileOperations.loadFileContent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_to_save_when_path_is_null() throws Exception {
        fileOperations.saveFileContent(null, "any content");
    }

    @Test
    public void should_be_able_to_load_file() throws Exception {
        // given
        final String path = "some-path";
        final File file = temporaryFolder.newFile();

        FileUtils.writeStringToFile(file, "content");

        final String resolvedPath = file.getAbsolutePath();

        when(pathResolutionStrategy.resolvePath(eq(path))).thenReturn(resolvedPath);

        // when
        String result = fileOperations.loadFileContent(path);

        // then
        verify(pathResolutionStrategy).resolvePath(eq(path));
        assertThat(result, is("content"));
    }

    @Test
    public void should_be_able_to_save_file() throws Exception {
        // given
        final String path = "some-path";
        final File file = temporaryFolder.newFile();
        final String resolvedPath = file.getAbsolutePath();
        when(pathResolutionStrategy.resolvePath(eq(path))).thenReturn(resolvedPath);

        // when
        fileOperations.saveFileContent(path, "any string");

        // then
        verify(pathResolutionStrategy).resolvePath(eq(path));
        assertThat(FileUtils.readFileToString(file), is("any string"));
    }
}
