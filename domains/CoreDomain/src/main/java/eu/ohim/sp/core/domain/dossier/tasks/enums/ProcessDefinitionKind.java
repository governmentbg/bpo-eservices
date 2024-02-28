package eu.ohim.sp.core.domain.dossier.tasks.enums;

/**
 * Available workflow process definition kinds in the TM BackOffice.
 * 
 * @author masjose
 * 
 */
public enum ProcessDefinitionKind {

	TRADEMARK_MAIN("trademark-main"), TRADEMARK_ADHOC("trademark-ad-hoc"), TRADEMARK_SUBPROCESS(
			"trademark-subprocess"), TRADEMARK_LETTER("trademark-letter");

	private String value;

	private ProcessDefinitionKind(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
