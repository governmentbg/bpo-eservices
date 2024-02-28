package eu.ohim.sp.common.ui.fileupload.validation;

import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import org.springframework.web.multipart.MultipartFile;

import eu.ohim.sp.common.ui.fileupload.exception.SoundValidationException;
import eu.ohim.sp.core.configuration.domain.xsd.AllowedFileType;

/**
 * @author ionitdi
 */
public class SoundValidator implements Validator {
    private AudioFileInfoProvider audioFileInfo;

    public SoundValidator() {
        audioFileInfo = new AudioFileInfoProviderImpl();
    }

    public SoundValidator(AudioFileInfoProvider audioFileInfo) {
        this.audioFileInfo = audioFileInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(MultipartFile file, AllowedFileType allowedFileType) throws ValidationException
    {
        if (file == null || allowedFileType == null) {
            return;
        }
        if (allowedFileType.isSound() == null || !allowedFileType.isSound()) {
            return;
        }

        if (allowedFileType.getSoundValidationParameters() == null) {
            return;
        }

        audioFileInfo.loadFile(file);
        Integer sampleRate = audioFileInfo.getSamplingRate();
        Integer channels = audioFileInfo.getChannels();

        validateChannels(allowedFileType, channels);

        validateSampleRate(allowedFileType, sampleRate);
    }

    private void validateChannels(AllowedFileType allowedFileType, Integer channels) throws SoundValidationException
    {
        boolean found = false;
        if (allowedFileType.getSoundValidationParameters().getChannels() != null
                && allowedFileType.getSoundValidationParameters().getChannels().getChannel().size() > 0) {
            for (int chan : allowedFileType.getSoundValidationParameters().getChannels().getChannel()) {
                if (chan == channels) {
                    found = true;
                    continue;
                }
            }
            if (!found) {
                throw new SoundValidationException(
                        "Number of channels not supported. Uploaded file's number of channels: " + channels, null,
                        "error.fileUpload.validation.sound.channels");
            }
        }
    }

    private void validateSampleRate(AllowedFileType allowedFileType, Integer sampleRate) throws SoundValidationException
    {
        if (allowedFileType.getSoundValidationParameters().getMinSampleRate() != null) {
            if (sampleRate < allowedFileType.getSoundValidationParameters().getMinSampleRate()) {
                throw new SoundValidationException("Minimum sample rate not met. This file's sample rate: "
                        + sampleRate, null, "error.fileUpload.validation.sound.minimumSampleRate");
            }
        }

        boolean found = false;
        if (allowedFileType.getSoundValidationParameters().getAllowedSampleRates() != null
                && allowedFileType.getSoundValidationParameters().getAllowedSampleRates().getSampleRates().size() > 0) {
            found = false;
            for (int sr : allowedFileType.getSoundValidationParameters().getAllowedSampleRates().getSampleRates()) {
                if (sampleRate == sr) {
                    found = true;
                    continue;
                }
            }
            if (!found) {
                throw new SoundValidationException(
                        "File's sample rate not part of allowed sample rates. Uploaded file's sample rate: "
                                + sampleRate, null, "error.fileUpload.validation.sound.allowedSampleRates");
            }
        }
    }
}
