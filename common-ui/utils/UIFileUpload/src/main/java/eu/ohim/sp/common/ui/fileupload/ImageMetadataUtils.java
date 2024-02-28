package eu.ohim.sp.common.ui.fileupload;

import eu.ohim.sp.common.ui.fileupload.exception.ExifToolException;
import eu.ohim.sp.common.ui.fileupload.exception.GeneralUploadException;
import eu.ohim.sp.common.ui.fileupload.exiftool.ColorType;
import eu.ohim.sp.common.ui.fileupload.exiftool.ExifToolCaller;
import eu.ohim.sp.common.ui.fileupload.exiftool.ExifToolImageMeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageMetadataUtils {

    public static byte[] removeMetadataAndFixResolution(byte[] fileBytes, Integer minXResolution, Integer minYResolution) {
        File inputFile = null;
        try {
            inputFile = File.createTempFile("exif", null);
        } catch (IOException e) {
            throw new GeneralUploadException("Could not create temp file to clear image metadata", e, "error.fileupload.image.metadata.clear.general.error");
        }
        File outputFile = new File(inputFile.getAbsolutePath()+"out");
        try(FileOutputStream fileOutputStream  = new FileOutputStream(inputFile);){
            fileOutputStream.write(fileBytes);
            fileOutputStream.flush();
        } catch (IOException e){
            inputFile.deleteOnExit();
            if(outputFile.exists()) {
                outputFile.deleteOnExit();
            }
            throw new GeneralUploadException("Could not write bytes in temp file to clear metadata", e, "error.fileupload.image.metadata.clear.general.error");
        }

        try {
            Map<String, Boolean> fixItMap = getFixResolution(fileBytes, minXResolution, minYResolution);
            boolean fixX = fixItMap.get("x");
            boolean fixY = fixItMap.get("y");
            ExifToolCaller.clearImageMetaAndFixResolution(inputFile, outputFile, fixX ? minXResolution : null, fixY ? minYResolution : null);
        } catch (ExifToolException e) {
            inputFile.deleteOnExit();
            if(outputFile.exists()) {
                outputFile.deleteOnExit();
            }
            throw new GeneralUploadException("exiftool failed to clear metadata", e, "error.fileupload.image.metadata.clear.general.error");
        }

        byte[] resultBytes = new byte[(int)outputFile.length()];
        try(FileInputStream fileInputStream  = new FileInputStream(outputFile);){
            fileInputStream.read(resultBytes);
        } catch (IOException e){
            throw new GeneralUploadException("failed to read back the cleared image's bytes", e, "error.fileupload.image.metadata.clear.general.error");
        } finally {
            inputFile.deleteOnExit();
            outputFile.deleteOnExit();
        }

        return resultBytes;
    }

    private static Map<String, Boolean> getFixResolution(byte[] fileBytes, Integer minXResolution, Integer minYResolution ){
        ExifToolImageMeta meta = getExifImageMetaFromExiftool(fileBytes);
        Map<String, Boolean> fixIt = new HashMap<>();

        if(minXResolution != null && meta != null && meta.getxResolution() != null) {
            if (meta.getxResolution() < minXResolution) {
                fixIt.put("x", true);
            } else {
                fixIt.put("x", false);
            }
        } else {
            fixIt.put("x", false);
        }
        if(minYResolution != null && meta != null && meta.getyResolution() != null) {
            if(meta.getyResolution() <minYResolution){
                fixIt.put("y", true);
            } else {
                fixIt.put("y", false);
            }
        } else {
            fixIt.put("y", false);
        }

        return fixIt;
    }

    public static boolean imageHasTransparentBackground(byte[] fileBytes){
        ExifToolImageMeta meta = getExifImageMetaFromExiftool(fileBytes);
        if(meta != null && meta.getColorType() != null && (meta.getColorType().equals(ColorType.GRAYSCALE_WITH_ALPHA) || meta.getColorType().equals(ColorType.RGB_WITH_ALPHA))){
            return true;
        } else {
            return false;
        }
    }

    public static boolean imageHasTransparentBackground(ExifToolImageMeta meta){
        if(meta != null && meta.getColorType() != null && (meta.getColorType().equals(ColorType.GRAYSCALE_WITH_ALPHA) || meta.getColorType().equals(ColorType.RGB_WITH_ALPHA))){
            return true;
        } else {
            return false;
        }
    }


    public static ExifToolImageMeta getExifImageMetaFromExiftool(byte[] fileBytes){
        File inputFile = null;
        try {
            inputFile = File.createTempFile("exif", null);
        } catch (IOException e) {
            throw new GeneralUploadException("Could not create temp file for exiftool", e, "error.fileupload.image.metadata.clear.general.error");
        }
        try(FileOutputStream fileOutputStream  = new FileOutputStream(inputFile);){
            fileOutputStream.write(fileBytes);
            fileOutputStream.flush();
        } catch (IOException e){
            inputFile.deleteOnExit();
            throw new GeneralUploadException("Could not write bytes in temp file to read metadata with exif tool", e, "error.fileupload.image.metadata.clear.general.error");
        }
        ExifToolImageMeta meta = null;
        try {
            meta = ExifToolCaller.getExifToolImageMeta(inputFile);
            return meta;
        } catch (ExifToolException e) {
            throw new GeneralUploadException("exiftool failed to read metadata", e, "error.fileupload.image.metadata.clear.general.error");
        } finally {
            inputFile.deleteOnExit();
        }
    }
}
