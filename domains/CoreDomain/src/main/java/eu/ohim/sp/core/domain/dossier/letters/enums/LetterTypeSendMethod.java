package eu.ohim.sp.core.domain.dossier.letters.enums;

/**
 * Available Letter send methods
 * 
 * @author masjose
 * 
 */
public enum LetterTypeSendMethod {

	ELECTRONIC("ELECTRONIC"), POST("POST");

	private String value;

	LetterTypeSendMethod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
