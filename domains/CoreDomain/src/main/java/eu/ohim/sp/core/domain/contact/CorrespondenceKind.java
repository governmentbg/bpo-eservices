package eu.ohim.sp.core.domain.contact;

public enum CorrespondenceKind {
	FAX("Fax"),
	POST("Post"),
    EMAIL("Email"),
	OTHER("Other")
	;
	
	private CorrespondenceKind(final String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}
}
