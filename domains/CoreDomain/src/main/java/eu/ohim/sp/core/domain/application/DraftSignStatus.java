package eu.ohim.sp.core.domain.application;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by georgi.georgiev on 28.07.2015.
 */
public class DraftSignStatus implements Serializable {

    private static final long serialVersionUID = 1884402832251284861L;

    private Long id;

    /** The date on which the sign status was updated */
    private Date modifiedDate;
    /** Message that is related with this sign status */
    private String message;

    /** The related draft application */
    private DraftApplication draftApplication;

    private Long signStatus;

    public DraftSignStatus()
    {
    }

    public DraftSignStatus(Long status) {
        this.signStatus = status;
        this.modifiedDate = new Date();
    }

    public DraftSignStatus(Long status, Date dateModified) {
        this.signStatus = status;
        this.modifiedDate = dateModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public DraftApplication getDraftApplication() {
        return draftApplication;
    }

    public void setDraftApplication(DraftApplication draftApplication) {
        this.draftApplication = draftApplication;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Long signStatus) {
        this.signStatus = signStatus;
    }


}
