package eu.ohim.sp.core.domain.design;

public enum ClassificationKind {
	LOCARNO("Locarno"),
	;
	
	private ClassificationKind(final String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}
}
