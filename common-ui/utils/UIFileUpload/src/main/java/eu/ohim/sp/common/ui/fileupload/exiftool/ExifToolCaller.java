package eu.ohim.sp.common.ui.fileupload.exiftool;


import eu.ohim.sp.common.ui.fileupload.exception.ExifToolException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Used as example Riyad Kalla's ExifTool
 * https://github.com/rkalla/exiftool
 * exiftool page: https://www.sno.phy.queensu.ca/~phil/exiftool/
 */
public class ExifToolCaller {

    private static final List<String> baseMetaClearCallArgs = Arrays.asList("-S", "-All=", "--jfif:all", "-ColorSpaceTags");

    private static final String EXIF_TOOL_PATH = System.getProperty(
            "exiftool.path", "exiftool");

    private static final Logger log = Logger.getLogger(ExifToolCaller.class);

    public static Process startExifToolProcess(String[] args) throws ExifToolException {
        Process exifToolProc = null;
        try {
            exifToolProc = new ProcessBuilder(args).start();
        } catch (IOException e) {
            log.error("Unable to start exiftool process", e);
            throw new ExifToolException("Unable to start exiftool process", e);
        }

        return exifToolProc;
    }

    public static void clearImageMeta(File image, File outputImage) throws ExifToolException {
        callForMetaProcessing(image, outputImage, baseMetaClearCallArgs);
    }

    public static void clearImageMetaAndFixResolution(File image, File outputImage, Integer minXResolution, Integer minYResolution) throws ExifToolException {
        List<String> commands = new ArrayList<>();
        commands.addAll(baseMetaClearCallArgs);
        if(minXResolution != null || minYResolution != null) {
            commands.add("-ResolutionUnit=inches");
            if (minXResolution != null) {
                commands.add("-XResolution=" + minXResolution);
            }
            if (minYResolution != null) {
                commands.add("-YResolution=" + minYResolution);
            }
        }
        callForMetaProcessing(image, outputImage, commands);
    }

    private static void callForMetaProcessing(File image, File outputImage, List<String> commands) throws ExifToolException {
        List<String> args = new ArrayList<>();
        args.add(EXIF_TOOL_PATH);
        args.addAll(commands);
        args.addAll(Arrays.asList(image.getAbsolutePath(), "-o", outputImage.getAbsolutePath()));
        Process exifToolProc = startExifToolProcess(args.toArray(new String[0]));
        try(BufferedReader errorReader = new BufferedReader(new InputStreamReader(exifToolProc.getErrorStream()))){
            String line = null;
            String error = "";
            while((line = errorReader.readLine()) != null){
                error += line;
            }

            if(!error.isEmpty()){
                log.warn(error);
                throw new ExifToolException(error);
            }

        } catch (IOException e){
            log.error("Failed to process metadata with exiftool", e);
            throw new ExifToolException("Failed to process metadata with exiftool", e);
        } finally {
            if(exifToolProc.isAlive()) {
                exifToolProc.destroy();
            }
        }
    }

    public static ExifToolImageMeta getExifToolImageMeta(File image) throws ExifToolException {
        List<String> args  = new ArrayList<>();
        args.add(EXIF_TOOL_PATH);
        args.add("-n"); // numeric output
        args.add("-S"); // compact output
        args.add(image.getAbsolutePath());
        Process exifToolProc = startExifToolProcess(args.toArray(new String[0]));
        ExifToolImageMeta meta = new ExifToolImageMeta();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(exifToolProc.getInputStream()))){
            String line = null;
            while((line = reader.readLine()) != null){
                String[] lineComponents = line.split(":");
                meta.setImageMetaSingle(lineComponents);
            }
        } catch (IOException e){
            log.error("Failed to fetch metadata with exiftool", e);
            throw new ExifToolException("Failed to fetch metadata with exiftool", e);
        } finally {
            if(exifToolProc.isAlive()) {
                exifToolProc.destroy();
            }
        }


        return meta;
    }

}
