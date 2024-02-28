package eu.ohim.sp.common.security.esapi;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NamingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EsapiJndiPropertiesLoaderTest {
    final String jndiName = "esapi.path";
    final String propertiesFileName = "my-esapi.properties";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private JndiLookupWrapper jndiLookupWrapper = mock(JndiLookupWrapper.class);

    @Test
    public void should_load_properties() throws Exception {
        // given
        temporaryFileWithPropertiesExists("property1", "value1");
        when(jndiLookupWrapper.lookup(eq(jndiName))).thenReturn(propertiesFileName);

        String propertiesDirectory = temporaryFolder.getRoot().getAbsolutePath();
        EsapiJndiPropertiesLoader esapiJndiPropertiesLoader = new EsapiJndiPropertiesLoader(jndiName, propertiesDirectory, jndiLookupWrapper);

        // when
        Properties properties = esapiJndiPropertiesLoader.loadProperties();

        // then
        assertThat(properties).containsEntry("property1", "value1");
    }

    @Test(expected = EsapiJndiException.class)
    public void should_fail_when_properties_not_found() throws NamingException, EsapiJndiException {
        // given
        when(jndiLookupWrapper.lookup(eq(jndiName))).thenThrow(NamingException.class);

        String propertiesDirectory = temporaryFolder.getRoot().getAbsolutePath();
        EsapiJndiPropertiesLoader esapiJndiPropertiesLoader = new EsapiJndiPropertiesLoader(jndiName, propertiesDirectory, jndiLookupWrapper);

        // when
        esapiJndiPropertiesLoader.loadProperties();
    }

    private void temporaryFileWithPropertiesExists(String key, String value) throws IOException {
        File temporaryFile = temporaryFolder.newFile(propertiesFileName);

        Properties temporaryProperties = new Properties();
        temporaryProperties.put(key, value);
        temporaryProperties.store(new FileWriter(temporaryFile), "comments");
    }
}
