package eu.ohim.sp.core.domain.dossier;

/**
 * Kinds of Dossier managed by TM BackOffice module
 * 
 * @author masjose
 * 
 */
public enum DossierKind {
	TRADEMARK_APPLICATION("TrademarkApplication"), TRADEMARK_OPPOSITION(
			"TrademarkOpposition"), TRADEMARK_CANCELLATION(
			"TrademarkCancellation"), TRADEMARK_RECORDAL("TrademarkRecordal"), DESIGN_APPLICATION(
			"DesignApplication"), DESIGN_INVALIDITY("DesignInvalidity"), DESIGN_RECORDAL(
			"DesignRecordal");

	private final String value;

	DossierKind(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}