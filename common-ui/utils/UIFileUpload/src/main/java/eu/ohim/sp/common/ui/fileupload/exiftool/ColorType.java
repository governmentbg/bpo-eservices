package eu.ohim.sp.common.ui.fileupload.exiftool;

/**
 * https://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/PNG.html
 */
public enum ColorType {
    GRAYSCALE("0"), RGB("2"), PALETTE("3"), GRAYSCALE_WITH_ALPHA("4"), RGB_WITH_ALPHA("6");

    private String type;

    private ColorType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static ColorType getFromString(String typeVal){
        if(typeVal == null){
            return null;
        }
        String typeTrimmed = typeVal.trim();
        for(ColorType ctype: ColorType.values()){
            if(ctype.getType().equals(typeTrimmed)){
                return ctype;
            }
        }
        return null;
    }


}
