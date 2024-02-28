package eu.ohim.sp.core.domain.dossier.letters.enums;

/**
 * Available Letter statuses
 * 
 * @author masjose
 * 
 */
public enum LetterStatusKind {

	DRAFT("DRAFT"), NEW("NEW"), READ("READ"), CREATE("CREATE"), SENT_POST(
			"SENTPOST"), SENT_ELECTRONICALLY("SENTELECTRONICALLY");

	private String value;

	LetterStatusKind(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
