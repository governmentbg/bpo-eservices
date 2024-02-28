package eu.ohim.sp.core.domain.application;

public enum ApplicationBasicKind {

    TRADEMARK_EFILING("Trade Mark E-Filing"), 
    TRADEMARK_SERVICES_EFILING("Trade Mark Services E-Filing"), 
    DESIGN_EFILING("Design E-Filing"), 
    DESIGN_SERVICES_EFILING("Design Services E-filing"),
    PATENT_EFILING("Patent E-Filing"),
    PATENT_SERVICES_EFILING("Patent Services E-Filing"),
    OBJECTLESS_SERVICES_EFILING("Objectless Services E-Filing");

    private final String value;

    ApplicationBasicKind(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
