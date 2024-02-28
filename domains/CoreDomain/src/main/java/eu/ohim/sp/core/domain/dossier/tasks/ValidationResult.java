package eu.ohim.sp.core.domain.dossier.tasks;

import java.io.Serializable;

/**
 * Available validation options for finishing a task. It depends on the task
 * kind and its properties inside the Workflow.
 * 
 * @author masjose
 * 
 */
public class ValidationResult implements Serializable {

	/** Serial Id **/
	private static final long serialVersionUID = 1L;

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}