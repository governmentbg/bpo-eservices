/*******************************************************************************
 * * $Id:: FileMapValidatorImplTest.java 113489 2013-04-22 14:59:26Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload;

import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.common.ui.fileupload.validation.FileMapValidator;
import eu.ohim.sp.common.ui.fileupload.validation.FileMapValidatorImpl;
import eu.ohim.sp.common.ui.fileupload.validation.ImageValidator;
import eu.ohim.sp.common.ui.fileupload.validation.SanselanWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.*;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO This test should be refactored
 * to not use the FileMapValidatorImpl as a wrapper for ImageValidator.
 * This is an old implementation.
 *
 * @author ionitdi
 */
public class FileMapValidatorImplTest
{
    @InjectMocks
    FileMapValidator fileMapImageValidator = new FileMapValidatorImpl(true);

    Map<String, FileUploadType> defaultConfigMap;
    Map<String, MultipartFile> defaultFileMap;
    MultipartFile fileMock;
    FileUploadType defaultFileConfig;

    @Mock
    SanselanWrapper sanselan;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        fileMapImageValidator = new FileMapValidatorImpl(true);

        defaultConfigMap = new HashMap<String, FileUploadType>();
        defaultFileMap = new HashMap<String, MultipartFile>();

        defaultFileConfig = new FileUploadType();
        defaultFileConfig.setFileUploadInfo(new FileUploadInfoType());
        AllowedFileTypes allowedTypes = new AllowedFileTypes();
        defaultFileConfig.getFileUploadInfo().setAllowedTypes(allowedTypes);

        fileMock = mock(MultipartFile.class);
        defaultFileMap.put("fileToCheck", fileMock);
    }

    private void initializeWithImageInfo(ImageInfo info)
    {
        sanselan = mock(SanselanWrapper.class);
        fileMapImageValidator = new FileMapValidatorImpl(false);
        fileMapImageValidator.addValidator(new ImageValidator(sanselan));

        when(fileMock.getContentType()).thenReturn("good content type");

        AllowedFileType allowedType = new AllowedFileType();
        allowedType.setValue("good content type");
        allowedType.setImageValidationParameters(new ImageValidationParametersType());
        allowedType.setImage(true);

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

        defaultFileConfig.getFileUploadInfo().getAllowedTypes().getAllowedFileType().add(allowedType);
        defaultConfigMap.put("fileToCheck", defaultFileConfig);
    }

    @Test
    public void validateMultipartFiles_nullInputFilesMap_doesNothing() throws ValidationException
    {
        fileMapImageValidator.validateMultipartFiles(null, defaultConfigMap);
    }

    @Test
    public void validateMultipartFiles_noValidationSettingsFound_doesNothing() throws ValidationException
    {
        when(fileMock.getContentType()).thenReturn("some content type");

        AllowedFileType allowedType = new AllowedFileType();
        allowedType.setValue("another content type");

        defaultFileConfig.getFileUploadInfo().getAllowedTypes().getAllowedFileType().add(allowedType);
        defaultConfigMap.put("fileToCheck", defaultFileConfig);

        fileMapImageValidator.validateMultipartFiles(defaultFileMap, defaultConfigMap);
    }

//    @Test
//    public void validateMultipartFiles_validationSettingsFoundNoImageValidationParams_doesNothing()
//    {
//        /// Arrange
//        when(fileMock.getContentType()).thenReturn("good content type");
//
//        AllowedFileType allowedType = new AllowedFileType();
//        allowedType.setValue("good content type");
//        allowedType.setImageValidationParameters(null);
//
//        defaultFileConfig.getFileUploadInfo().getAllowedTypes().getAllowedFileType().add(allowedType);
//        defaultConfigMap.put("fileToCheck", defaultFileConfig);
//
//        /// Act
//        fileMapImageValidator.validateMultipartFiles(defaultFileMap, defaultConfigMap);
//    }

//    @Test
//    public void validateMultipartFiles_validationSettingsFoundNotImageType_doesNothing()
//    {
//        /// Arrange
//        when(fileMock.getContentType()).thenReturn("good content type");
//
//        AllowedFileType allowedType = new AllowedFileType();
//        allowedType.setValue("good content type");
//        allowedType.setImageValidationParameters(new ImageValidationParametersType());
//        allowedType.setImage(false);
//
//        defaultFileConfig.getFileUploadInfo().getAllowedTypes().getAllowedFileType().add(allowedType);
//        defaultConfigMap.put("fileToCheck", defaultFileConfig);
//
//        /// Act
//        fileMapImageValidator.validateMultipartFiles(defaultFileMap, defaultConfigMap);
//    }

    @Test
    public void validateMultipartFiles_noFileFound_throwsException() throws ValidationException
    {
        /// Arrange
        initializeWithImageInfo(null);

        /// Act
        fileMapImageValidator.validateMultipartFiles(defaultFileMap, defaultConfigMap);
    }
}
