package eu.ohim.sp.core.domain.dossier.tasks;

import eu.ohim.sp.common.util.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * This class collects the available parameters to execute a task search
 * 
 * @author masjose
 * 
 */
public class TaskSearchCriteria implements Serializable {

	/** Serial Id **/
	private static final long serialVersionUID = 8800873622332735067L;

	private String dossierId;
	private String dossierType;
	private String taskName;
	private String workflow;
	private String comment;
	private Date deadLineFrom;
	private Date deadLineTo;
	private Date timeLimitFrom;
	private Date timeLimitTo;
	private Date createTimeFrom;
	private Date createTimeTo;
	private Date completeTimeFrom;
	private Date completeTimeTo;
	private String flag;
	private List<String> status;
	private String userOrGroup;
	private String sortProperty;
	private String sortDirection;
	private int pageSize;
	private int pageIndex;
	private String txtname;
	private String bouser;
	private String inboxtype;
	private boolean advancedSearch;
	private boolean fixed;
	private int tabId;

	public String getDossierType() {
		return dossierType;
	}

	public void setDossierType(String dossierType) {
		this.dossierType = dossierType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDeadLineFrom() {
		return DateUtil.cloneDate(deadLineFrom);
	}

	public void setDeadLineFrom(Date deadLineFrom) {
		this.deadLineFrom = DateUtil.cloneDate(deadLineFrom);
	}

	public Date getDeadLineTo() {
		return DateUtil.cloneDate(deadLineTo);
	}

	public void setDeadLineTo(Date deadLineTo) {
		this.deadLineTo = DateUtil.cloneDate(deadLineTo);
	}

	public Date getTimeLimitFrom() {
		return DateUtil.cloneDate(timeLimitFrom);
	}

	public void setTimeLimitFrom(Date timeLimitFrom) {
		this.timeLimitFrom = DateUtil.cloneDate(timeLimitFrom);
	}

	public Date getTimeLimitTo() {
		return DateUtil.cloneDate(timeLimitTo);
	}

	public void setTimeLimitTo(Date timeLimitTo) {
		this.timeLimitTo = DateUtil.cloneDate(timeLimitTo);
	}

	public Date getCreateTimeFrom() {
		return DateUtil.cloneDate(createTimeFrom);
	}

	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = DateUtil.cloneDate(createTimeFrom);
	}

	public Date getCreateTimeTo() {
		return DateUtil.cloneDate(createTimeTo);
	}

	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = DateUtil.cloneDate(createTimeTo);
	}

	public Date getCompleteTimeFrom() {
		return DateUtil.cloneDate(completeTimeFrom);
	}

	public void setCompleteTimeFrom(Date completeTimeFrom) {
		this.completeTimeFrom = DateUtil.cloneDate(completeTimeFrom);
	}

	public Date getCompleteTimeTo() {
		return DateUtil.cloneDate(completeTimeTo);
	}

	public void setCompleteTimeTo(Date completeTimeTo) {
		this.completeTimeTo = DateUtil.cloneDate(completeTimeTo);
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public String getUserOrGroup() {
		return userOrGroup;
	}

	public void setUserOrGroup(String userOrGroup) {
		this.userOrGroup = userOrGroup;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getTxtname() {
		return txtname;
	}

	public void setTxtname(String txtname) {
		this.txtname = txtname;
	}

	public String getBouser() {
		return bouser;
	}

	public void setBouser(String bouser) {
		this.bouser = bouser;
	}

	public String getInboxtype() {
		return inboxtype;
	}

	public void setInboxtype(String inboxtype) {
		this.inboxtype = inboxtype;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public int getTabId() {
		return tabId;
	}

	public void setTabId(int tabId) {
		this.tabId = tabId;
	}

	public String getDossierId() {
		return dossierId;
	}

	public void setDossierId(String dossierId) {
		this.dossierId = dossierId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public boolean isAdvancedSearch() {
		return advancedSearch;
	}

	public void setAdvancedSearch(boolean advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}
}
