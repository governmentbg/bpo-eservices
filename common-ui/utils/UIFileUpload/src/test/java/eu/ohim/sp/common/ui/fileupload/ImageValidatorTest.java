package eu.ohim.sp.common.ui.fileupload;

import eu.ohim.sp.common.ui.fileupload.exception.ImageValidationException;
import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.common.ui.fileupload.validation.ImageValidator;
import eu.ohim.sp.common.ui.fileupload.validation.SanselanWrapper;
import eu.ohim.sp.common.ui.fileupload.validation.Validator;
import eu.ohim.sp.core.configuration.domain.xsd.*;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ionitdi
 */
public class ImageValidatorTest
{
    private Validator imageValidator;

    @Mock
    MultipartFile fileMock;

    @Mock
    SanselanWrapper sanselan;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        imageValidator = new ImageValidator(sanselan);
    }

    private void initializeWithImageInfo(ImageInfo info)
    {
        sanselan = mock(SanselanWrapper.class);
        imageValidator = new ImageValidator(sanselan);

        try
        {
            byte[] byteArray = new byte[1];
            when(fileMock.getBytes()).thenReturn(byteArray);

            when(sanselan.getImageInfo(any(byte[].class))).thenReturn(info);
        }
        catch (IOException e)
        {
            fail("Exception caught: " + e.getMessage());
        }
        catch (ImageReadException e)
        {
            fail("Exception caught: " + e.getMessage());
        }

    }

    private AllowedFileType initializeAllowedFileType(String contentType, ImageValidationParametersType validationParams)
    {
        AllowedFileType allowedType = new AllowedFileType();
        allowedType.setValue("good content type");
        allowedType.setImage(true);
        allowedType.setImageValidationParameters(validationParams);

        return allowedType;
    }

    @Test
    public void validate_nullInputFilesMap_doesNothing() throws ValidationException
    {
        imageValidator.validate(null, new AllowedFileType());
    }

    @Test
    public void validateMultipartFiles_noValidationSettingsFound_doesNothing() throws ValidationException
    {
        when(fileMock.getContentType()).thenReturn("some content type");

        AllowedFileType allowedType = new AllowedFileType();
        allowedType.setValue("another content type");

        imageValidator.validate(fileMock, allowedType);
    }

    @Test
    public void validate_validationSettingsFoundNoImageValidationParams_doesNothing() throws ValidationException
    {
        /// Arrange
        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = new AllowedFileType();
        allowedType.setValue("good content type");
        allowedType.setImageValidationParameters(null);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }


    @Test
    public void validateImageSize_allSizesCorrespond() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getHeight()).thenReturn(1000);
        when(info.getWidth()).thenReturn(2000);
        when(info.getPhysicalHeightInch()).thenReturn(10F);
        when(info.getPhysicalWidthInch()).thenReturn(10F);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMaxSizePixels(new SizeType(1100, 2100));
        validationParams.setMinSizePixels(new SizeType(800, 600));

        validationParams.setMaxSizeCentimeters(new SizeType(30, 30));
        validationParams.setMinSizeCentimeters(new SizeType(10, 10));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageWidthSmallerThanMinSize_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getHeight()).thenReturn(800);
        when(info.getWidth()).thenReturn(500);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMinSizePixels(new SizeType(800, 600));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageHeightBiggerThanMaxSize_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getHeight()).thenReturn(1000);
        when(info.getWidth()).thenReturn(500);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMaxSizePixels(new SizeType(800, 600));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageWidthBiggerThanMaxSize_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getHeight()).thenReturn(800);
        when(info.getWidth()).thenReturn(700);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMaxSizePixels(new SizeType(800, 600));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageHeightSmallerThanMinSizeCentimeters_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalHeightInch()).thenReturn(8F);
        when(info.getPhysicalWidthInch()).thenReturn(12F);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMinSizeCentimeters(new SizeType(26, 26));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageWidthSmallerThanMinSizeCentimeters_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalHeightInch()).thenReturn(12F);
        when(info.getPhysicalWidthInch()).thenReturn(8F);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMinSizeCentimeters(new SizeType(26, 26));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageHeightBiggerThanMaxSizeCentimeters_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalHeightInch()).thenReturn(30F);
        when(info.getPhysicalWidthInch()).thenReturn(8F);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMaxSizeCentimeters(new SizeType(25, 25));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageWidthBiggerThanMaxSizeCentimeters_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalHeightInch()).thenReturn(8F);
        when(info.getPhysicalWidthInch()).thenReturn(30F);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setMaxSizeCentimeters(new SizeType(25, 25));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test
    public void validateImage_imageDpiCorresponds() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalWidthDpi()).thenReturn(300);
        when(info.getPhysicalHeightDpi()).thenReturn(300);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setExifInfo(new ExifInfoType());
        validationParams.getExifInfo().setMinXDensity(250);
        validationParams.getExifInfo().setMinYDensity(300);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test
    public void validateImage_imageWidthDpiNotSpecifiedInImageInfo() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalWidthDpi()).thenReturn(-1);
        when(info.getPhysicalHeightDpi()).thenReturn(300);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setExifInfo(new ExifInfoType());
        validationParams.getExifInfo().setMinXDensity(250);
        validationParams.getExifInfo().setMinYDensity(300);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test
    public void validateImage_imageHeightDpiNotSpecifiedInImageInfo() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalWidthDpi()).thenReturn(300);
        when(info.getPhysicalHeightDpi()).thenReturn(-1);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setExifInfo(new ExifInfoType());
        validationParams.getExifInfo().setMinXDensity(250);
        validationParams.getExifInfo().setMinYDensity(300);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageHeightDpiSmallerThanMinDpi_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalWidthDpi()).thenReturn(300);
        when(info.getPhysicalHeightDpi()).thenReturn(200);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setExifInfo(new ExifInfoType());
        validationParams.getExifInfo().setMinXDensity(250);
        validationParams.getExifInfo().setMinYDensity(300);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateImage_imageWidthDpiSmallerThanMinDpi_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getPhysicalWidthDpi()).thenReturn(200);
        when(info.getPhysicalHeightDpi()).thenReturn(300);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setExifInfo(new ExifInfoType());
        validationParams.getExifInfo().setMinXDensity(250);
        validationParams.getExifInfo().setMinYDensity(300);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test
    public void validateColorModel_validColorModelRGB() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getColorType()).thenReturn(ImageInfo.COLOR_TYPE_RGB);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setAcceptedColorModels(new ColorModelsType());
        validationParams.getAcceptedColorModels().getColorModel().add(ColorModelType.RGB);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test
    public void validateColorModel_validColorModelCMYK() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getColorType()).thenReturn(ImageInfo.COLOR_TYPE_CMYK);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setAcceptedColorModels(new ColorModelsType());
        validationParams.getAcceptedColorModels().getColorModel().add(ColorModelType.CMYK);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test
    public void validateColorModel_validColorModelGrayscale() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getColorType()).thenReturn(ImageInfo.COLOR_TYPE_GRAYSCALE);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setAcceptedColorModels(new ColorModelsType());
        validationParams.getAcceptedColorModels().getColorModel().add(ColorModelType.GRAYSCALE);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateColorModel_unknownColorModel_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getColorType()).thenReturn(ImageInfo.COLOR_TYPE_UNKNOWN);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setAcceptedColorModels(new ColorModelsType());
        validationParams.getAcceptedColorModels().getColorModel().add(ColorModelType.RGB);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }

    @Test(expected = ImageValidationException.class)
    public void validateColorModel_unacceptedColorModel_throwsException() throws ValidationException
    {
        /// Arrange
        ImageInfo info = mock(ImageInfo.class);
        when(info.getColorType()).thenReturn(ImageInfo.COLOR_TYPE_BW);

        initializeWithImageInfo(info);

        ImageValidationParametersType validationParams = new ImageValidationParametersType();
        validationParams.setAcceptedColorModels(new ColorModelsType());
        validationParams.getAcceptedColorModels().getColorModel().add(ColorModelType.CMYK);

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = initializeAllowedFileType("good content type", validationParams);

        /// Act
        imageValidator.validate(fileMock, allowedType);
    }
}
