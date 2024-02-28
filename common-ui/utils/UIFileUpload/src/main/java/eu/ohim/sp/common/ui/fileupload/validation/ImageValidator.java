/*******************************************************************************
 * * $Id:: ImageValidator.java 113489 2013-04-22 14:59:26Z karalch               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload.validation;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;


import eu.ohim.sp.common.ui.fileupload.ImageMetadataUtils;
import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.common.ui.fileupload.exiftool.ColorSpaceData;
import eu.ohim.sp.common.ui.fileupload.exiftool.ExifToolImageMeta;
import org.apache.log4j.Logger;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import eu.ohim.sp.common.ui.fileupload.exception.ImageValidationException;
import eu.ohim.sp.core.configuration.domain.xsd.AllowedFileType;
import eu.ohim.sp.core.configuration.domain.xsd.ColorModelType;
import eu.ohim.sp.core.configuration.domain.xsd.ImageValidationParametersType;

/**
 * @author ionitdi
 */
@Component
public class ImageValidator implements Validator
{
    private final Logger log = Logger.getLogger(ImageValidator.class);

    private static final double cm_inches = 2.54;

    /**
     * This wrapper helps with the unit testing,
     * as it is not possible to mock the static
     * methods that the Sanselan library uses
     */
    SanselanWrapper sanselan;

    public ImageValidator()
    {
        sanselan = new SanselanWrapper();
    }

    public ImageValidator(SanselanWrapper sanselan)
    {
        this.sanselan = sanselan;
    }



    /**
     * Method which uses the bytes of the given MultipartFile
     * object to instantiate, if the file is an image,
     * an ImageInfo object containing information about the image.
     * This information is compared to the
     * validation parameters.
     *
     * @param file
     * @param allowedFileType
     */
    @Override
    public void validate(MultipartFile file, AllowedFileType allowedFileType) throws ValidationException
    {
        if (allowedFileType == null || allowedFileType.getImageValidationParameters() == null)
            return;

        if (!isImage(allowedFileType))
            return;

        ImageValidationParametersType validationParams = allowedFileType.getImageValidationParameters();

        byte[] imageBytes;

        try
        {
            imageBytes = file.getBytes();
        }
        catch (IOException ex)
        {
            // should not get here
            throw new ImageValidationException("error.fileUpload.validation.imageInfo.fileNotFound", ex);
        }

        ImageInfo imageInfo = readImageInfo(imageBytes);

        if (log.isDebugEnabled())
        {
            log.debug("Successfully read image info from file bytes. Proceeding to validation of image properties.");
        }

        if(imageInfo != null)
        {
            validateImageSizePx(validationParams, imageBytes, imageInfo);

            validateExifResolutionUnit(validationParams, imageBytes, imageInfo);

            if(imageBytes != null) {
                ExifToolImageMeta exifToolImageMeta = ImageMetadataUtils.getExifImageMetaFromExiftool(imageBytes);

                validateBackground(exifToolImageMeta);
                validateColorModel(validationParams, imageInfo, exifToolImageMeta);
            }
        }
    }

    private ImageInfo readImageInfo(byte[] imageBytes) throws ImageValidationException {
        ImageInfo imageInfo;

        try {
            imageInfo = sanselan.getImageInfo(imageBytes);
            return imageInfo;
        } catch (ImageReadException ex)
        {
            throw new ImageValidationException("error.fileUpload.validation.imageInfo.readError", ex);
        }
        catch (IOException ex)
        {
            // should not get here
            throw new ImageValidationException("error.fileUpload.validation.imageInfo.fileNotFound", ex);
        }
    }

    /**
     * Using the validation parameters and the image bytes given,
     * this method checks whether the Exif resolution unit
     * of the image is the accepted list of values.
     *
     * IMPORTANT: this method only knows how to process the following
     * content types:
     *  - image/jpeg
     *  - image/pjpeg
     *
     * @param validationParams
     * @param imageBytes
     * @param imageInfo
     */
    private void validateExifResolutionUnit(ImageValidationParametersType validationParams, byte[] imageBytes, ImageInfo imageInfo) throws
                                                                                                                                    ImageValidationException
    {
        // stores image metadata (exif)
        IImageMetadata imageMeta;
        try
        {
            imageMeta = sanselan.getMetadata(imageBytes);
        }
        catch (IOException e)
        {
            // should not get here
//            throw new ImageValidationException("error.fileUpload.validation.imageMetadata.fileNotFound", e);
            log.info("Problem reading image metadata when validation exif resolution unit", e);
            return;
        }
        catch (ImageReadException e)
        {
            log.info("Problem reading image metadata when validation exif resolution unit", e);
            return;
        }

        if (validationParams.getExifInfo() != null && validationParams.getExifInfo().getValidResolutionUnits() != null && !validationParams.getExifInfo().getValidResolutionUnits().getValidByte().isEmpty())
        {
            if (log.isDebugEnabled())
            {
                log.debug("Validation parameters contain Exif valid byte information.");
            }
            // currently using only jpeg content type
            if (imageInfo.getMimeType().equals("image/jpeg") || imageInfo.getMimeType().equals("image/pjpeg"))
            {
                JpegImageMetadata jpegMeta;
                jpegMeta = (JpegImageMetadata) imageMeta;
                if (jpegMeta != null && jpegMeta.getExif() != null)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("Jpeg metadata contains Exif info.");
                    }
                    try
                    {
                        TiffDirectory dir = jpegMeta.getExif().findDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT);
                        for (Object entry : dir.getDirectoryEntrys())
                        {
                            TiffField tiff = (TiffField) entry;
                            // looking for a tag info with the name Resolution Unit
                            if (tiff.tagInfo.name.equals("Resolution Unit"))
                            {
                                boolean resUnitMatch = false;
                                int exifResolutionByte = Integer.parseInt(tiff.getValue().toString());
                                if (log.isDebugEnabled())
                                {
                                    log.debug("Uploaded file's Exif resolution byte: " + exifResolutionByte);
                                }
                                /* iterates through the valid bytes
                                defined in the validation parameters and
                                checks whether any of them match the
                                given image's resolution unit byte*/
                                for (Integer validByte : validationParams.getExifInfo().getValidResolutionUnits().getValidByte())
                                {
                                    if (exifResolutionByte == validByte.intValue())
                                    {
                                        resUnitMatch = true;
                                    }
                                }
                                if (!resUnitMatch)
                                    throw new ImageValidationException("error.fileUpload.validation.exif.resolutionUnit");
                                break;
                            }
                        }
                    }
                    catch (ImageReadException e)
                    {
                        log.info("Problem reading image metadata when validation exif resolution unit", e);
                        return;
                    }
                }
            }
        }
    }

    /**
     * This method validated the image's color model
     * using the validation parameters.
     *
     * @param validationParams
     * @param imageInfo
     * @param exifToolImageMeta
     */
    private void validateColorModel(ImageValidationParametersType validationParams, ImageInfo imageInfo, ExifToolImageMeta exifToolImageMeta) throws
                                                                                                         ImageValidationException
    {
        ColorModelType imageColorModel = getImageColorModelType(imageInfo.getColorType(), exifToolImageMeta.getColorSpaceData());

        // check accepted color model
        if (validationParams.getAcceptedColorModels() != null && !validationParams.getAcceptedColorModels().getColorModel().isEmpty())
        {
            if (imageColorModel == null)
                throw new ImageValidationException("error.fileUpload.validation.imageInfo.colorModel.unknown");
            boolean acceptedColorModel = false;
            for (ColorModelType colorModel : validationParams.getAcceptedColorModels().getColorModel())
            {
                if (colorModel == imageColorModel)
                {
                    acceptedColorModel = true;
                }
            }
            if (!acceptedColorModel)
                throw new ImageValidationException("error.fileUpload.validation.imageInfo.colorModel");
        }
    }

    /**
     * This method validated the image's size
     * using the validation parameters.
     *
     * @param validationParams
     * @param imageInfo
     */
    private void validateImageSizePx(ImageValidationParametersType validationParams, byte[] imageBytes, ImageInfo imageInfo) throws ImageValidationException
    {
        // check minimum size of image in pixels
        if (validationParams.getMinSizePixels() != null)
        {
            if (imageInfo.getHeight() < validationParams.getMinSizePixels().getHeight())
                throw new ImageValidationException("error.fileUpload.validation.minSizePixels.height");
            if (imageInfo.getWidth() < validationParams.getMinSizePixels().getWidth())
                throw new ImageValidationException("error.fileUpload.validation.minSizePixels.width");
        }

        // check maximum size of image in pixels
        if (validationParams.getMaxSizePixels() != null)
        {
            if (imageInfo.getHeight() > validationParams.getMaxSizePixels().getHeight())
                throw new ImageValidationException("error.fileUpload.validation.maxSizePixels.height");
            if (imageInfo.getWidth() > validationParams.getMaxSizePixels().getWidth())
                throw new ImageValidationException("error.fileUpload.validation.maxSizePixels.width");
        }

        // check minimum (physical) size of image in centimeters

    }

    public void validateResolutionAndSizeCm(ImageValidationParametersType validationParams, byte[] imageBytes) throws ImageValidationException {
        ImageInfo imageInfo = readImageInfo(imageBytes);

        if(validationParams != null) {
            if (validationParams.getMinSizeCentimeters() != null) {
                if (imageInfo.getPhysicalHeightInch() > 0) {
                    if (fromInchToCm(imageInfo.getPhysicalHeightInch()) < validationParams.getMinSizeCentimeters().getHeight())
                        throw new ImageValidationException("error.fileUpload.validation.minSizeCentimetres.height");
                }
                if (imageInfo.getPhysicalWidthInch() > 0) {
                    if (fromInchToCm(imageInfo.getPhysicalWidthInch()) < validationParams.getMinSizeCentimeters().getWidth())
                        throw new ImageValidationException("error.fileUpload.validation.minSizeCentimetres.width");
                }
            }

            // check maximum (physical) size of image in centimeters
            if (validationParams.getMaxSizeCentimeters() != null) {
                if (imageInfo.getPhysicalHeightInch() > 0) {
                    if (fromInchToCm(imageInfo.getPhysicalHeightInch()) > validationParams.getMaxSizeCentimeters().getHeight())
                        throw new ImageValidationException("error.fileUpload.validation.maxSizeCentimetres.height");
                }
                if (imageInfo.getPhysicalWidthInch() > 0) {
                    if (fromInchToCm(imageInfo.getPhysicalWidthInch()) > validationParams.getMaxSizeCentimeters().getWidth())
                        throw new ImageValidationException("error.fileUpload.validation.maxSizeCentimetres.width");
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Image size corresponds to given parameters: height " + imageInfo.getHeight()
                    + "px; width " + imageInfo.getWidth() + "px.");
            }

            // check exif information
            if (validationParams.getExifInfo() != null) {
                ExifToolImageMeta exifToolImageMeta = ImageMetadataUtils.getExifImageMetaFromExiftool(imageBytes);
                if (exifToolImageMeta != null) {
                    if (exifToolImageMeta.getxResolution() == null || exifToolImageMeta.getxResolution() == -1) {
                        if (log.isInfoEnabled()) {
                            log.info("Image X-Density not specified.");
                        }
                    }
                    // check image physical X-density (DPI)
                    if (exifToolImageMeta.getxResolution() != null && exifToolImageMeta.getxResolution() != -1 &&
                        validationParams.getExifInfo().getMinXDensity() != null && exifToolImageMeta.getxResolution() < validationParams.getExifInfo().getMinXDensity())
                        throw new ImageValidationException("error.fileUpload.validation.xDensity");

                    if (exifToolImageMeta.getyResolution() == null || exifToolImageMeta.getyResolution() == -1) {
                        if (log.isInfoEnabled()) {
                            log.info("Image Y-Density not specified.");
                        }
                    }
                    // check image physical Y-density (DPI)
                    if (exifToolImageMeta.getyResolution() != null && exifToolImageMeta.getyResolution() != -1 &&
                        validationParams.getExifInfo().getMinYDensity() != null && exifToolImageMeta.getyResolution() < validationParams.getExifInfo().getMinYDensity())
                        throw new ImageValidationException("error.fileUpload.validation.yDensity");
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("No exif metadata could be read by exiftool");
                    }
                }
            }
        }
    }

    private void validateBackground(ExifToolImageMeta imageMeta) throws ImageValidationException {
        if(ImageMetadataUtils.imageHasTransparentBackground(imageMeta)) {
            throw new ImageValidationException("error.fileUpload.validation.background.transparent");
        }

    }

    /**
     * Converts an integer to
     * a ColorModelType enumeration value.
     *
     * @param imageInfoColor
     * @param colorSpace
     * @return a ColorModelType object
     */
    private ColorModelType getImageColorModelType(int imageInfoColor, ColorSpaceData colorSpace)
    {
        ColorModelType colorModel = null;
        if(colorSpace != null) {
            switch (colorSpace) {
                case RGB:
                    colorModel = ColorModelType.RGB;
                    break;
                case BILEVEL:
                    colorModel = ColorModelType.BW;
                    break;
                case CMY:
                case CMYK:
                    colorModel =  ColorModelType.CMYK;
                    break;
                case GRAYSCALE:
                    colorModel = ColorModelType.GRAYSCALE;
                    break;
            }
        }
        if(colorModel == null){
            switch (imageInfoColor)
            {
                case ImageInfo.COLOR_TYPE_RGB:
                    colorModel = ColorModelType.RGB;
                    break;
                case ImageInfo.COLOR_TYPE_BW:
                    colorModel = ColorModelType.BW;
                    break;
                case ImageInfo.COLOR_TYPE_CMYK:
                    colorModel = ColorModelType.CMYK;
                    break;
                case ImageInfo.COLOR_TYPE_GRAYSCALE:
                    colorModel = ColorModelType.GRAYSCALE;
                    break;
                case ImageInfo.COLOR_TYPE_OTHER:
                case ImageInfo.COLOR_TYPE_UNKNOWN:
                default:
                    colorModel = null;
                    break;
            }
        }
        return colorModel;
    }

    /**
     * Converts inches to centimeters.
     *
     * @param inches the value to convert
     * @return the value in centimeters
     */
    private double fromInchToCm(double inches)
    {
        double centimeters = cm_inches * inches;
        BigDecimal decimal = new BigDecimal(centimeters);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        if (log.isDebugEnabled())
        {
            log.debug("Converted and rounded inches to centimeters: " + inches + "in -> " + decimal.doubleValue() + "cm");
        }
        return decimal.doubleValue();
    }

    /**
     * Checks whether a file is an image.
     *
     * Current implementation uses the defined configuration
     * parameters which can contain a boolean specifying
     * whether the file is an image.
     *
     * @param fileType
     * @return
     */
    private boolean isImage(AllowedFileType fileType)
    {
        if (fileType.isImage() != null && fileType.isImage())
            return true;
        return false;
    }
}
