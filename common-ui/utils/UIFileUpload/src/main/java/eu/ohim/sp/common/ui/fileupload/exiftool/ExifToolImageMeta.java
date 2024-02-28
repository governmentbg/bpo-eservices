package eu.ohim.sp.common.ui.fileupload.exiftool;

/**
 * More metadata tags available here:
 * https://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/
 */
public class ExifToolImageMeta {

    protected Integer imageWidth;
    protected Integer imageHeight;
    protected String mimeType;
    protected String fileType;
    protected Double xResolution;
    protected Double yResolution;
    protected ColorType colorType;
    protected ColorSpaceData colorSpaceData;

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Double getxResolution() {
        return xResolution;
    }

    public void setxResolution(Double xResolution) {
        this.xResolution = xResolution;
    }

    public Double getyResolution() {
        return yResolution;
    }

    public void setyResolution(Double yResolution) {
        this.yResolution = yResolution;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public void setColorType(ColorType colorType) {
        this.colorType = colorType;
    }

    public ColorSpaceData getColorSpaceData() {
        return colorSpaceData;
    }

    public void setColorSpaceData(ColorSpaceData colorSpaceData) {
        this.colorSpaceData = colorSpaceData;
    }

    public void setImageMetaSingle(String[] metaComponents){
        if(metaComponents == null || metaComponents.length < 2){
            return;
        }

        String metaKey = metaComponents[0];
        String metaVal = metaComponents[1].trim();

        ExifToolTag tag = ExifToolTag.getByName(metaKey);
        if(tag != null) {
            switch (tag) {
                case FILE_TYPE:
                    this.fileType = metaVal;
                    break;
                case COLOR_TYPE:
                    this.colorType = ColorType.getFromString(metaVal);
                    break;
                case MIME_TYPE:
                    this.mimeType = metaVal;
                    break;
                case IMAGE_WIDTH:
                    this.imageWidth = Integer.parseInt(metaVal);
                    break;
                case IMAGE_HEIGHT:
                    this.imageHeight = Integer.parseInt(metaVal);
                    break;
                case X_RESOLUTION:
                    this.xResolution = Double.parseDouble(metaVal);
                    break;
                case Y_RESOLUTION:
                    this.yResolution = Double.parseDouble(metaVal);
                    break;
                case COLOR_SPACE_DATA:
                    this.colorSpaceData = ColorSpaceData.getFromString(metaVal);
                    break;
            }
        }
    }

}
