package eu.ohim.sp.common.ui.fileupload.exiftool;

/**
 * More metadata tags available here:
 * https://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/
 */
public enum ExifToolTag {
    COLOR_TYPE("ColorType"),
    COLOR_SPACE_DATA("ColorSpaceData"),
    IMAGE_WIDTH("ImageWidth"),
    IMAGE_HEIGHT("ImageHeight"),
    X_RESOLUTION("XResolution"),
    Y_RESOLUTION("YResolution"),
    FILE_TYPE("FileType"),
    MIME_TYPE("MIMEType");


    private String name;

    public String getName() {
        return name;
    }

    private ExifToolTag(String name){
        this.name = name;
    }

    public static ExifToolTag getByName(String name){
        if(name == null){
            return null;
        }
        for(ExifToolTag tag: values()){
            if(tag.getName().equals(name.trim())){
                return tag;
            }
        }
        return null;

    }
}
