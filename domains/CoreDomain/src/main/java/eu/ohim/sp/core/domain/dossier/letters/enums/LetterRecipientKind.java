package eu.ohim.sp.core.domain.dossier.letters.enums;

/**
 * Available Letter Recipient types
 * 
 * @author masjose
 * 
 */
public enum LetterRecipientKind {

	APPLICANT("APPLICANT"), REPRESENTATIVE("REPRESENTATIVE"), PERSON("PERSON");

	private String value;

	LetterRecipientKind(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
