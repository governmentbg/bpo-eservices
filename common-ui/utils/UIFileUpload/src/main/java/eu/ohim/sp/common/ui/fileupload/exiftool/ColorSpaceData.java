package eu.ohim.sp.common.ui.fileupload.exiftool;

public enum ColorSpaceData {
    BILEVEL("Bi-level"), YCBCR_709_VIDEO("YCbCr, ITU-R BT 709, video"), NONE_SPECIFIED("No color space specified"), YCBCR_601_RGB("YCbCr, ITU-R BT 601-1, RGB"),
    YCBCR_601_VIDEO("YCbCr, ITU-R BT 601-1, video"),
    GRAYSCALE("Gray-scale"), PHOTO_YCC("PhotoYCC"),
    RGB("RGB"), CMY("CMY"), CMYK("CMYK"), YCCK("YCCK"), CIELAB("CIELab");

    private String type;

    private ColorSpaceData(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static ColorSpaceData getFromString(String typeVal){
        if(typeVal == null){
            return NONE_SPECIFIED;
        }
        String typeTrimmed = typeVal.trim();
        for(ColorSpaceData space: ColorSpaceData.values()){
            if(space.getType().equals(typeTrimmed)){
                return space;
            }
        }
        return NONE_SPECIFIED;
    }
}
