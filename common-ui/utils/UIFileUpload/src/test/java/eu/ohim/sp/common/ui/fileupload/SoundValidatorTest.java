package eu.ohim.sp.common.ui.fileupload;

import eu.ohim.sp.common.ui.fileupload.exception.SoundValidationException;
import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.common.ui.fileupload.validation.AudioFileInfoProvider;
import eu.ohim.sp.common.ui.fileupload.validation.SoundValidator;
import eu.ohim.sp.core.configuration.domain.xsd.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ionitdi
 */
public class SoundValidatorTest
{
    @Mock
    private AudioFileInfoProvider audioFileInfoProvider;

    @InjectMocks
    private SoundValidator soundValidator = new SoundValidator();

    MultipartFile fileMock;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        fileMock = mock(MultipartFile.class);
    }

    @Test
    public void validate_nullFile_doesNothing() throws ValidationException
    {
        soundValidator.validate(null, new AllowedFileType());
    }

    @Test
    public void validate_nullConfig_doesNothing() throws ValidationException
    {
        soundValidator.validate(fileMock, null);
    }

    @Test
    public void validate_isNotSound_doesNothing() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSoundValidationParameters(new SoundValidationParametersType());
        allowedFileType.setSound(false);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }

    @Test
    public void validate_doesNotHaveSoundValidationParams_doesNothing() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSound(true);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }

    @Test
    public void validate_hasValidNumberOfChannels_doesNothing() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSound(true);
        allowedFileType.setSoundValidationParameters(new SoundValidationParametersType());
        allowedFileType.getSoundValidationParameters().setChannels(new ChannelsType());
        allowedFileType.getSoundValidationParameters().getChannels().getChannel().add(1);

        when(audioFileInfoProvider.getChannels()).thenReturn(1);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }

    @Test(expected = SoundValidationException.class)
    public void validate_doesNotHaveValidNumberOfChannels_throwsException() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSound(true);
        allowedFileType.setSoundValidationParameters(new SoundValidationParametersType());
        allowedFileType.getSoundValidationParameters().setChannels(new ChannelsType());
        allowedFileType.getSoundValidationParameters().getChannels().getChannel().add(2);

        when(audioFileInfoProvider.getChannels()).thenReturn(1);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }

    @Test
    public void validate_hasAllowedSamplingRate_doesNothing() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSound(true);
        allowedFileType.setSoundValidationParameters(new SoundValidationParametersType());
        allowedFileType.getSoundValidationParameters().setAllowedSampleRates(new SampleRatesType());
        allowedFileType.getSoundValidationParameters().getAllowedSampleRates().getSampleRates().add(44100);

        when(audioFileInfoProvider.getSamplingRate()).thenReturn(44100);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }

    @Test(expected = SoundValidationException.class)
    public void validate_doesNotHaveAllowedSamplingRate_doesNothing() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSound(true);
        allowedFileType.setSoundValidationParameters(new SoundValidationParametersType());
        allowedFileType.getSoundValidationParameters().setAllowedSampleRates(new SampleRatesType());
        allowedFileType.getSoundValidationParameters().getAllowedSampleRates().getSampleRates().add(44100);

        when(audioFileInfoProvider.getSamplingRate()).thenReturn(11025);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }

    @Test
    public void validate_hasGreaterThanMinSamplingRate_doesNothing() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSound(true);
        allowedFileType.setSoundValidationParameters(new SoundValidationParametersType());
        allowedFileType.getSoundValidationParameters().setMinSampleRate(44100);

        when(audioFileInfoProvider.getSamplingRate()).thenReturn(48000);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }

    @Test(expected = SoundValidationException.class)
    public void validate_hasLessThanMinSamplingRate_throwsException() throws ValidationException
    {
        /// Arrange
        AllowedFileType allowedFileType = new AllowedFileType();
        allowedFileType.setSound(true);
        allowedFileType.setSoundValidationParameters(new SoundValidationParametersType());
        allowedFileType.getSoundValidationParameters().setMinSampleRate(44100);

        when(audioFileInfoProvider.getSamplingRate()).thenReturn(11025);

        /// Act
        soundValidator.validate(fileMock, allowedFileType);
    }
}