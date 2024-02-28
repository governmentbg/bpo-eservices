package eu.ohim.sp.common.ui.form.resources;

public enum FileAttachmentStatus {

    ATTACHED("Attached"), NOT_ATTACHED("Not Attached"), PROVIDED("Provided") , TO_FOLLOW("To Follow"), EXPLANATIONS("Explanations");

    private String value;

    FileAttachmentStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FileAttachmentStatus fromValue(String value){
        for(FileAttachmentStatus option: values()){
            if(option.getValue().equalsIgnoreCase(value)){
                return option;
            }
        }

        return null;
    }
}
