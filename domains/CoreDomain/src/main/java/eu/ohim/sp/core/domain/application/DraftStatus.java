package eu.ohim.sp.core.domain.application;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;

public class DraftStatus implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1884402832251284860L;

    /** The code representing the status of the draft application */
    private ApplicationStatus status;
    /** The date on which the status was updated */
    private Date modifiedDate;
    /** Message that is related with this status */
    private String message;

    public DraftStatus()
    {
    }

    public DraftStatus(ApplicationStatus status) {
    	this.status = status;
    	this.modifiedDate = new Date();
    }
    
    public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
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
