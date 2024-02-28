package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

public class MarketPermissionForm extends AbstractImportableForm {

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

    @Override
    public MarketPermissionForm clone() throws CloneNotSupportedException {
        MarketPermissionForm marketPermissionForm = new MarketPermissionForm();
        marketPermissionForm.setFirstPermissionBGNumber(firstPermissionBGNumber);
        marketPermissionForm.setFirstPermissionBGDate(firstPermissionBGDate);
        marketPermissionForm.setFirstPermissionEUNumber(firstPermissionEUNumber);
        marketPermissionForm.setFirstPermissionEUDate(firstPermissionEUDate);
        return marketPermissionForm;
    }

    public AvailableSection getAvailableSectionName() {
        return AvailableSection.MARKET_PERMISSION;
    }

}
