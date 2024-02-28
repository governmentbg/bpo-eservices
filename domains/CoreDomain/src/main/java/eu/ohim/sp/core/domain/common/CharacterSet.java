package eu.ohim.sp.core.domain.common;

public enum CharacterSet {
	ARABIC("Arabic"),
	ARMENIAN("Armenian"),
	CHINESE("Chinese"),
	CYRILLIC("Cyrillic"),
	GEORGIAN("Georgian"),
	GREEK("Greek"),
	HEBREW("Hebrew"),
	INDIC("Indic"),
	JAPANESE("Japanese"),
	KOREAN("Korean"),
	THAI("Thai"),
    LATIN("Latin");
	
	private final String text;

	private CharacterSet(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
