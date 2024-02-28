package eu.ohim.sp.core.domain.dossier.tasks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.dossier.tasks.enums.TaskStatusEnum;

/**
 * Task representation class. It's used in the workflow of a Dossier It
 * represents a task human or not that must be completed to finish the dossier
 * management
 * 
 * @author masjose
 * 
 */
public class Task implements Serializable {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = -206995864091288084L;

	private String id;
	private String dossierId;
	private String name;
	private String description;
	private Date createTime;
	private Date completionDate;
	private Date dueDate;
	private String owner;
	private String assignee;
	private int numComment;
	private Date deadLineStartDate;
	private Date deadLineEndDate;
	private Date timeLimitStartDate;
	private Date timeLimitEndDate;
	private String flag;
	private TaskStatusEnum status;

	private List<ValidationResult> results = new ArrayList<ValidationResult>();
	private List<String> candidatesGroups = new ArrayList<String>();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the dossierId
	 */
	public String getDossierId() {
		return dossierId;
	}

	/**
	 * @param dossierId
	 *            the dossierId to set
	 */
	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return DateUtil.cloneDate(createTime);
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = DateUtil.cloneDate(createTime);
	}

	/**
	 * @return the completionDate
	 */
	public Date getCompletionDate() {
		return DateUtil.cloneDate(completionDate);
	}

	/**
	 * @param completionDate
	 *            the completionDate to set
	 */
	public void setCompletionDate(Date completionDate) {
		this.completionDate = DateUtil.cloneDate(completionDate);
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return DateUtil.cloneDate(dueDate);
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = DateUtil.cloneDate(dueDate);
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the numComment
	 */
	public int getNumComment() {
		return numComment;
	}

	/**
	 * @param numComment
	 *            the numComment to set
	 */
	public void setNumComment(int numComment) {
		this.numComment = numComment;
	}

	/**
	 * @return the deadLineStartDate
	 */
	public Date getDeadLineStartDate() {
		return DateUtil.cloneDate(deadLineStartDate);
	}

	/**
	 * @param deadLineStartDate
	 *            the deadLineStartDate to set
	 */
	public void setDeadLineStartDate(Date deadLineStartDate) {
		this.deadLineStartDate = DateUtil.cloneDate(deadLineStartDate);
	}

	/**
	 * @return the deadLineEndDate
	 */
	public Date getDeadLineEndDate() {
		return DateUtil.cloneDate(deadLineEndDate);
	}

	/**
	 * @param deadLineEndDate
	 *            the deadLineEndDate to set
	 */
	public void setDeadLineEndDate(Date deadLineEndDate) {
		this.deadLineEndDate = DateUtil.cloneDate(deadLineEndDate);
	}

	/**
	 * @return the timeLimitStartDate
	 */
	public Date getTimeLimitStartDate() {
		return DateUtil.cloneDate(timeLimitStartDate);
	}

	/**
	 * @param timeLimitStartDate
	 *            the timeLimitStartDate to set
	 */
	public void setTimeLimitStartDate(Date timeLimitStartDate) {
		this.timeLimitStartDate = DateUtil.cloneDate(timeLimitStartDate);
	}

	/**
	 * @return the timeLimitEndDate
	 */
	public Date getTimeLimitEndDate() {
		return DateUtil.cloneDate(timeLimitEndDate);
	}

	/**
	 * @param timeLimitEndDate
	 *            the timeLimitEndDate to set
	 */
	public void setTimeLimitEndDate(Date timeLimitEndDate) {
		this.timeLimitEndDate = DateUtil.cloneDate(timeLimitEndDate);
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the status
	 */
	public TaskStatusEnum getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(TaskStatusEnum status) {
		this.status = status;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the results
	 */
	public List<ValidationResult> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<ValidationResult> results) {
		this.results = results;
	}

	/**
	 * @return the candidatesGroups
	 */
	public List<String> getCandidatesGroups() {
		return candidatesGroups;
	}

	/**
	 * @param candidatesGroups
	 *            the candidatesGroups to set
	 */
	public void setCandidatesGroups(List<String> candidatesGroups) {
		this.candidatesGroups = candidatesGroups;
	}

}
