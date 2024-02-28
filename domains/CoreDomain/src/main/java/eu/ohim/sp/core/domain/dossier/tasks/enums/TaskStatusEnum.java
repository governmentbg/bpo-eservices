package eu.ohim.sp.core.domain.dossier.tasks.enums;

/**
 * Status of a Task
 * 
 * @author masjose
 * 
 */
public enum TaskStatusEnum {

	PENDING("Pending"), COMPLETED("Completed");

	private String value;

	TaskStatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
