package eu.ohim.sp.core.domain.dossier.tasks;

import java.io.Serializable;
import java.util.List;

/**
 * Workflow process definition
 * @author masjose
 *
 */
public class ProcessDefinition implements Serializable {

	/** Serial id **/
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private List<TaskDefinition> taskDefinitions;
    private List<String> candidatesGroups;

    public ProcessDefinition() {
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

    public List<TaskDefinition> getTaskDefinitions() {
        return taskDefinitions;
    }

    public void setTaskDefinitions(List<TaskDefinition> taskDefinitions) {
        this.taskDefinitions = taskDefinitions;
    }

    public List<String> getCandidatesGroups() {
        return candidatesGroups;
    }

    public void setCandidatesGroups(List<String> candidatesGroups) {
        this.candidatesGroups = candidatesGroups;
    }

}
