package eu.ohim.sp.core.domain.dossier.tasks;

import java.io.Serializable;

/**
 * Workflow Task definition.
 * @author masjose
 *
 */
public class TaskDefinition implements Serializable {

	/** Serial id **/
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;

	public TaskDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
