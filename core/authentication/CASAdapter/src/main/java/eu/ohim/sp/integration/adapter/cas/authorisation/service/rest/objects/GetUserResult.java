package eu.ohim.sp.integration.adapter.cas.authorisation.service.rest.objects;

public class GetUserResult extends BaseResult {
    private boolean agreedToTermsOfUse;
    private Integer companyId;
    private Integer contactId;
    private String emalAddress;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer userId;

    public boolean isAgreedToTermsOfUse() {
        return agreedToTermsOfUse;
    }

    public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
        this.agreedToTermsOfUse = agreedToTermsOfUse;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getEmalAddress() {
        return emalAddress;
    }

    public void setEmalAddress(String emalAddress) {
        this.emalAddress = emalAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
