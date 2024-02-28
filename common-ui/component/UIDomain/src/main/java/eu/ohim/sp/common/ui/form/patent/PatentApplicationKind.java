package eu.ohim.sp.common.ui.form.patent;

/**
 * Created by Raya
 * 21.10.2019
 */
public enum  PatentApplicationKind {
    EP_VALIDATION_REQUEST("ep.validation.request", "EP Validation Request"),
    EP_TEMPORARY_PROTECTION("ep.temporary.protection", "EP Temporary Protection"),
    EP_VALIDATION_REQUEST_CHANGED_PATENT("ep.validation.request.changed.patent", "EP Validation Request for Changed Patent");

    private String key;
    private String value;

    PatentApplicationKind(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getName(){
        return name();
    }

    public static PatentApplicationKind fromValue(String value){
        if(value != null) {
            for (PatentApplicationKind kind : values()) {
                if (kind.getValue().equals(value)) {
                    return kind;
                }
            }
        }
        return null;
    }
}
