package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 05.05.2022
 * Time: 18:10
 */
public class ForeignRegistration implements Serializable {

    private String registrationNumber;
    private Date registrationDate;
    private String registrationCountry;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationCountry() {
        return registrationCountry;
    }

    public void setRegistrationCountry(String registrationCountry) {
        this.registrationCountry = registrationCountry;
    }

    @Override
    public String toString() {
        return "ForeignRegistration{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", registrationDate=" + registrationDate +
                ", registrationCountry='" + registrationCountry + '\'' +
                '}';
    }
}
