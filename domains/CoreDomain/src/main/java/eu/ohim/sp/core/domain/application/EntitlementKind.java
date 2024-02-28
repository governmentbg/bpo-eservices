package eu.ohim.sp.core.domain.application;

public enum EntitlementKind {
	DESIGN_IS_OFFICIARY("DESIGN_IS_OFFICIARY"),
	DESIGN_IS_NOT_OFFICIARY("DESIGN_IS_NOT_OFFICIARY"),
	NOT_APPLICANTS_WITH_WAIVED("NOT_APPLICANTS_WITH_WAIVED"),
	DUE_TO_SUCESSION("DUE_TO_SUCESSION"),
	ACCORDING_TO_ASSOCIATION_TO_COMPANY("ACCORDING_TO_ASSOCIATION_TO_COMPANY"),
	TRANSFER_OF_RIGHTS("TRANSFER_OF_RIGHTS"),
	OTHER_GROUNDS("OTHER_GROUNDS"),


    PATENT_IS_OFFICIARY("PATENT_IS_OFFICIARY"),
    PATENT_IS_NOT_OFFICIARY("PATENT_IS_NOT_OFFICIARY")
	;

    private EntitlementKind(final String code)
    {
        this.code = code;
    }

    private final String code;



    public String getCode()
    {
        return code;
    }


}