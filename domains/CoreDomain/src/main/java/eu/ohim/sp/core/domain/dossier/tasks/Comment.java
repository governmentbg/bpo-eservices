/*
 *  CoreDomain:: Comment 09/08/13 16:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.tasks;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import eu.ohim.sp.core.domain.id.Id;

/**
 * User comment on Tasks.
 * 
 */
public class Comment extends Id implements Serializable {

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = 1L;

	private String commentText;
	private Date creationDate;
	private Date lastUpdateDate;
	private String status;
	private String user;
	private String taskId;
	private String boUserId;

	public Comment() {
	}

	public Date getCreationDate() {
		return DateUtil.cloneDate(this.creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = DateUtil.cloneDate(creationDate);
	}

	public Date getLastUpdateDate() {
		return DateUtil.cloneDate(this.lastUpdateDate);
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = DateUtil.cloneDate(lastUpdateDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return super.equals(obj);
		}

		Comment actual = (Comment) obj;

		return EqualsBuilder.reflectionEquals(this, actual);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBoUserId() {
		return boUserId;
	}

	public void setBoUserId(String boUserId) {
		this.boUserId = boUserId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}