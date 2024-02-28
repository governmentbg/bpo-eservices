package eu.ohim.sp.common.ui;

import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadInfoType;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface.FILE_UPLOAD_CONFIGURATION;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadConfigurationFactoryTest {
	@InjectMocks
	private FileUploadConfigurationFactory factory;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;

	private final String component = "component";

	@Test
	public void should_return_file_upload_configuration() {
		// given
		final FileUploadConfiguration fileUploadConfiguration = mock(FileUploadConfiguration.class);
		final List<FileUploadType> fileUploadTypes = new ArrayList<FileUploadType>();

		fileUploadTypes.add(new FileUploadType(mock(FileUploadInfoType.class), "field1", false));
		fileUploadTypes.add(new FileUploadType(mock(FileUploadInfoType.class), "field2", false));
		fileUploadTypes.add(new FileUploadType(mock(FileUploadInfoType.class), "field3", false));

		when(fileUploadConfiguration.getFileUpload()).thenReturn(fileUploadTypes);

		when(configurationService.getObjectFromComponent(eq(FILE_UPLOAD_CONFIGURATION), eq(component), eq(FileUploadConfiguration.class))).thenReturn(fileUploadConfiguration);

		// when
		Map<String, FileUploadType> map = factory.getFileuploadConfiguration(component);

		// then
		assertThat(map.size(), is(fileUploadTypes.size()));
	}

	@Test
	public void should_return_empty_map_when_configuration_not_found() {
		// given
		when(configurationService.getObjectFromComponent(eq(FILE_UPLOAD_CONFIGURATION), eq(component), eq(FileUploadConfiguration.class))).thenReturn(null);

		// when
		Map<String, FileUploadType> map = factory.getFileuploadConfiguration(component);

		// then
		assertThat(map.size(), is(0));
	}
}
