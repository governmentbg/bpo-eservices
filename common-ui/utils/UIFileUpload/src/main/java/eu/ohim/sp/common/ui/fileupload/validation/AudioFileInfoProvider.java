package eu.ohim.sp.common.ui.fileupload.validation;

import eu.ohim.sp.common.ui.fileupload.exception.SoundValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author ionitdi
 */
public interface AudioFileInfoProvider
{
    /**
     * Loads information from given file.
     * @param file
     */
    public void loadFile(MultipartFile file) throws SoundValidationException;

    /**
     * Loads information from given input stream.
     * @param inputStream
     */
    public void loadFile(InputStream inputStream) throws SoundValidationException;

    /**
     * Returns mp3 sampling rate in hz
     * @return
     */
    public Integer getSamplingRate() throws SoundValidationException;

    /**
     * Returns number of channels: 1 - mono, 2 - stereo
     * @return
     */
    public Integer getChannels() throws SoundValidationException;
}
