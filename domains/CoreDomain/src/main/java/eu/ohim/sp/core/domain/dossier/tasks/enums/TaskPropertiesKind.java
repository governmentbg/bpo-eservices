package eu.ohim.sp.core.domain.dossier.tasks.enums;

/**
 * Available kinds of properties sent to the Workflow process engine when an
 * action over a task is called.
 * 
 * @author masjose
 * 
 */
public enum TaskPropertiesKind {

	VALIDATION_RESULT("validation_result"), DEAD_LINE_START("dead_line_start"), DEAD_LINE_END(
			"dead_line_end"), TIME_LIMIT_START("time_limit_start"), TIME_LIMIT_END(
			"time_limit_end"), FLAG("flag"), DOSSIER_ID("dossierId"), DOSSIER_TYPE(
			"dossierType"), DEFAULT_USER("default_user");

	private String value;

	private TaskPropertiesKind(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
