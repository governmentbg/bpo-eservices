package eu.ohim.sp.core.domain.dossier.tasks.enums;

/**
 * 
 * Types of sorting in results of Tasks searching
 * 
 * @author masjose
 * 
 */
public enum SortTaskKind {

	SORT_DEAD_LINE_START("deadlineStart"), SORT_DEAD_LINE_END("deadlineEnd"), SORT_TIME_LIMIT_START(
			"timelimitStart"), SORT_TIME_LIMIT_END("timelimitEnd"), SORT_FLAG(
			"flag"), SORT_DOSSIER_ID("dossierId"), SORT_NAME("taskName"), SORT_ASSIGNEE(
			"assignement");

	private String value;

	private SortTaskKind(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
