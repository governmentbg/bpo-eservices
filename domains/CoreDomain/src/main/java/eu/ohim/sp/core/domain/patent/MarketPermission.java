package eu.ohim.sp.core.domain.patent;

import java.io.Serializable;
import java.util.Date;

public class MarketPermission implements Serializable {
    private String firstPermissionBGNumber;
    private Date firstPermissionBGDate;
    private String firstPermissionEUNumber;
    private Date firstPermissionEUDate;

    public String getFirstPermissionBGNumber() {
        return firstPermissionBGNumber;
    }

    public void setFirstPermissionBGNumber(String firstPermissionBGNumber) {
        this.firstPermissionBGNumber = firstPermissionBGNumber;
    }

    public Date getFirstPermissionBGDate() {
        return firstPermissionBGDate;
    }

    public void setFirstPermissionBGDate(Date firstPermissionBGDate) {
        this.firstPermissionBGDate = firstPermissionBGDate;
    }

    public String getFirstPermissionEUNumber() {
        return firstPermissionEUNumber;
    }

    public void setFirstPermissionEUNumber(String firstPermissionEUNumber) {
        this.firstPermissionEUNumber = firstPermissionEUNumber;
    }

    public Date getFirstPermissionEUDate() {
        return firstPermissionEUDate;
    }

    public void setFirstPermissionEUDate(Date firstPermissionEUDate) {
        this.firstPermissionEUDate = firstPermissionEUDate;
    }
}
