package eu.ohim.sp.core.application;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;

public class DraftStatus implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1884402832251284860L;

    private Long id;

    /** The related draft application */
    private DraftApplication draftApplication;
    /** The code representing the status of the draft application */
    private Status code;
    /** The date on which the status was updated */
    private Date modifiedDate;
    /** Message that is related with this status */
    private String message;

    public DraftStatus()
    {
    }

    public DraftStatus(Status code) {
    	this.code = code;
    	this.modifiedDate = new Date();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DraftApplication getDraftApplication() {
        return draftApplication;
    }

    public void setDraftApplication(DraftApplication draftApplication) {
        this.draftApplication = draftApplication;
    }

    public Status getCode() {
        return code;
    }

    public void setCode(Status code) {
        this.code = code;
    }

    public Date getModifiedDate() {
        return DateUtil.cloneDate(modifiedDate);
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = DateUtil.cloneDate(modifiedDate);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
