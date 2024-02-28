package eu.ohim.sp.common.logging.model;

/**
 * @author ionitdi
 */
public class StatisticalBasics
{
    private String timestamp;
    private String eFilingId;
    private String userLanguage;
    private String IP;
    private String externalOrInternal;
    private String userGroup;
    private String username;

    private ApplicationType applicationType;
    private ActionType action;

    private EntityStatus entityStatus;

    private EntityType entityType;

    // TODO: what are these three ?
    private String entityAffected;
    private String groundOfContactForm;
    private boolean entityBlockedUnderRenewalProcess;

    private InspectionRequestType inspectionRequestType;
    private PaymentType paymentType;

    /**
     *
     * @return
     */
    public ActionType getAction()
    {
        return action;
    }

    /**
     *
     * @param action
     */
    public void setAction(ActionType action)
    {
        this.action = action;
    }

    /**
     *
     * @return
     */
    public ApplicationType getApplicationType()
    {
        return applicationType;
    }

    /**
     *
     * @param applicationType
     */
    public void setApplicationType(ApplicationType applicationType)
    {
        this.applicationType = applicationType;
    }

    /**
     *
     * @return
     */
    public String getEFilingId()
    {
        return eFilingId;
    }

    /**
     *
     * @param eFilingId
     */
    public void setEFilingId(String eFilingId)
    {
        this.eFilingId = eFilingId;
    }

    /**
     *
     * @return
     */
    public String getEntityAffected()
    {
        return entityAffected;
    }

    /**
     *
     * @param entityAffected
     */
    public void setEntityAffected(String entityAffected)
    {
        this.entityAffected = entityAffected;
    }

    /**
     *
     * @return
     */
    public boolean isEntityBlockedUnderRenewalProcess()
    {
        return entityBlockedUnderRenewalProcess;
    }

    /**
     *
     * @param entityBlockedUnderRenewalProcess
     */
    public void setEntityBlockedUnderRenewalProcess(boolean entityBlockedUnderRenewalProcess)
    {
        this.entityBlockedUnderRenewalProcess = entityBlockedUnderRenewalProcess;
    }

    /**
     *
     * @return
     */
    public EntityStatus getEntityStatus()
    {
        return entityStatus;
    }

    /**
     *
     * @param entityStatus
     */
    public void setEntityStatus(EntityStatus entityStatus)
    {
        this.entityStatus = entityStatus;
    }

    /**
     *
     * @return
     */
    public EntityType getEntityType()
    {
        return entityType;
    }

    /**
     *
     * @param entityType
     */
    public void setEntityType(EntityType entityType)
    {
        this.entityType = entityType;
    }

    /**
     *
     * @return
     */
    public String getGroundOfContactForm()
    {
        return groundOfContactForm;
    }

    /**
     *
     * @param groundOfContactForm
     */
    public void setGroundOfContactForm(String groundOfContactForm)
    {
        this.groundOfContactForm = groundOfContactForm;
    }

    /**
     *
     * @return
     */
    public InspectionRequestType getInspectionRequestType()
    {
        return inspectionRequestType;
    }

    /**
     *
     * @param inspectionRequestType
     */
    public void setInspectionRequestType(InspectionRequestType inspectionRequestType)
    {
        this.inspectionRequestType = inspectionRequestType;
    }

    /**
     *
     * @return
     */
    public String getIP()
    {
        return IP;
    }

    /**
     *
     * @param IP
     */
    public void setIP(String IP)
    {
        this.IP = IP;
    }

    /**
     *
     * @return
     */
    public PaymentType getPaymentType()
    {
        return paymentType;
    }

    /**
     *
     * @param paymentType
     */
    public void setPaymentType(PaymentType paymentType)
    {
        this.paymentType = paymentType;
    }

    /**
     *
     * @return
     */
    public String getTimestamp()
    {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     */
    public String getUserGroup()
    {
        return userGroup;
    }

    /**
     *
     * @param userGroup
     */
    public void setUserGroup(String userGroup)
    {
        this.userGroup = userGroup;
    }

    /**
     *
     * @return
     */
    public String getUserLanguage()
    {
        return userLanguage;
    }

    /**
     *
     * @param userLanguage
     */
    public void setUserLanguage(String userLanguage)
    {
        this.userLanguage = userLanguage;
    }

    /**
     *
     * @return
     */
    public String getUsername()
    {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getExternalOrInternal()
    {
        return externalOrInternal;
    }

    public void setExternalOrInternal(String externalOrInternal)
    {
        this.externalOrInternal = externalOrInternal;
    }
}