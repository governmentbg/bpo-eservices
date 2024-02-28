package eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetUserResult extends BaseResult {
    private boolean agreedToTermsOfUse;
    private Integer companyId;
    private Integer contactId;
    private String emailAddress;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
