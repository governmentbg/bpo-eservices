package eu.ohim.sp.common.ui.fileupload.validation;

import eu.ohim.sp.common.ui.fileupload.exception.SoundValidationException;
import org.springframework.web.multipart.MultipartFile;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author ionitdi
 */
public class AudioFileInfoProviderImpl implements AudioFileInfoProvider
{
    private Map properties;

    public void loadFile(MultipartFile file) throws SoundValidationException
    {
        try
        {
            loadFile(file.getInputStream());
        }
        catch (IOException e)
        {
            throw new SoundValidationException("An error occurred while processing the file.", null, "error.fileupload.validation.sound.infoprovider.io");
        }
    }

    public void loadFile(InputStream inputStream) throws SoundValidationException
    {
        AudioFileFormat baseFileFormat = null;
        try
        {
            baseFileFormat = AudioSystem.getAudioFileFormat(inputStream);
        }
        catch (UnsupportedAudioFileException e)
        {
            throw new SoundValidationException("An error occurred while processing the file.", null, "error.fileupload.validation.sound.infoprovider.unsupportedfile");
        }
        catch (IOException e)
        {
            throw new SoundValidationException("An error occurred while processing the file.", null, "error.fileupload.validation.sound.infoprovider.io");
        }

        if(baseFileFormat instanceof TAudioFileFormat)
        {
            properties = ((TAudioFileFormat) baseFileFormat).properties();
        }
        else
        {
            throw new SoundValidationException("An error occurred while processing the file.", null, "error.fileupload.validation.sound.infoprovider.initialization");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSamplingRate() throws SoundValidationException
    {
        if(properties == null)
        {
            throw new SoundValidationException("An error occurred while processing the file.", null, "error.fileupload.validation.sound.infoprovider.mapNotInitialized");
        }
        Integer val = (Integer)properties.get("mp3.frequency.hz");
        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getChannels() throws SoundValidationException
    {
        if(properties == null)
        {
            throw new SoundValidationException("An error occurred while processing the file.", null, "error.fileupload.validation.sound.infoprovider.mapNotInitialized");
        }
        Integer val = (Integer)properties.get("mp3.channels");
        return val;
    }
}
