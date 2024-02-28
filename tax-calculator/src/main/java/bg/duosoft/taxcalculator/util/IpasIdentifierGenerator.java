package bg.duosoft.taxcalculator.util;

import bg.duosoft.taxcalculator.model.IpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpasIdentifierGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpasIdentifierGenerator.class);

    public static String generateStringIdentifier(IpFile ipFile) {
        StringBuilder builder = new StringBuilder();
        builder.append(ipFile.getIpFileId().getFileSeq());
        builder.append("/");
        builder.append(ipFile.getIpFileId().getFileType());
        builder.append("/");
        builder.append(ipFile.getIpFileId().getFileSer());
        builder.append("/");
        builder.append(ipFile.getIpFileId().getFileNum());
        return builder.toString();
    }

}
