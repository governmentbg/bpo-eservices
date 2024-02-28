package eu.ohim.sp.core.domain.dossier.publications;

/**
 * Status of the Publication
 * 
 * @author garciac
 * 
 */
public enum PublicationStatus {

	ERROR("Error"), PENDING("Pending"), PUBLISHED("Published");

	private String value;

	PublicationStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
