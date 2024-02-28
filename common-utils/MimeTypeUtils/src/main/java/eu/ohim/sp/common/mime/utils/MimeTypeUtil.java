package eu.ohim.sp.common.mime.utils;

import org.apache.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class MimeTypeUtil {

    private final static Logger myLogger = Logger.getLogger(MimeTypeUtil.class);
    
    private final static Set<Set<String>> equivalenceGroups = new HashSet<Set<String>>();
    
    static{
    	Set<String> mp3 = new HashSet<String>();
    	mp3.add("audio/mp3");
    	mp3.add("audio/mpeg");
    	equivalenceGroups.add(mp3);

        Set<String> xml = new HashSet<String>();
        xml.add("application/xml");
        xml.add("text/xml");
        equivalenceGroups.add(xml);
    }

    public static String getMimeType(byte[] bytes, String filename) {
        if (bytes != null) {
            String mimeType = "";
            InputStream bai = new ByteArrayInputStream(bytes);

            TikaConfig config = TikaConfig.getDefaultConfig();
            org.apache.tika.mime.MimeTypes repo = config.getMimeRepository();

            Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, filename);

            try {
                mimeType = repo.detect(bai, metadata).toString();

            } catch (IOException e) {
                myLogger.error(e);
            }
            myLogger.info("Mime: " + mimeType);
            return mimeType;
        }
        return "";
    }

    public static String getFileExtensionFromMimeType(String mimetype) {
        String toReturn = "";
        toReturn = MimeTypes.getExtension(mimetype);
        return toReturn;
    }

    public static String getFileExtensionFromBytes(byte[] bytes, String filename) {
        String toReturn = "";

        String mimetype = getMimeType(bytes, filename);
        if (mimetype != null && !mimetype.equals("")) {
            toReturn = MimeTypes.getExtension(mimetype);
        }

        return toReturn;
    }
    
    public static boolean areEquivalent(String mimetype1, String mimetype2){
    	if(mimetype1.equalsIgnoreCase(mimetype2)){
    		return true;
    	}else{
    		for(Set<String> group: equivalenceGroups){
    			if(group.contains(mimetype1) && group.contains(mimetype2)){
    				return true;
    			}
    		}
    		return false;
    	}
    }
}
