/*
 *  CoreDomain:: HistoryEntry 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.registry;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;

/**
 * The persistent class for the history entry database table.
 * 
 */
public class HistoryEntry extends Id implements Serializable {

	/** Serial id **/
	private static final long serialVersionUID = 5338659239539007815L;

	private int dossierId;
    /** MODIFIED WITH EVERY MANUAL REVISION */
	private int manualRevision;
    /** MODIFIED WITH EVERY HISTORY ENTRY */
	private int revision;
	private String appVersion;
	private String dataFormat;
	private String data;
	private String eventType;
	private String taskName;
	private String taskResult;
	private String taskStatus;
	private int letterId;
	private Date date;
	private String username;
	private String eventDescription;
	private String comments;

	public int getDossierId() {
		return dossierId;
	}

	public void setDossierId(int dossierId) {
		this.dossierId = dossierId;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public int getManualRevision() {
		return manualRevision;
	}

	public void setManualRevision(int manualRevision) {
		this.manualRevision = manualRevision;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public int getLetterId() {
		return letterId;
	}

	public void setLetterId(int letterId) {
		this.letterId = letterId;
	}

	public Date getDate() {
		return DateUtil.cloneDate(date);
	}

	public void setDate(Date date) {
		this.date = DateUtil.cloneDate(date);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
