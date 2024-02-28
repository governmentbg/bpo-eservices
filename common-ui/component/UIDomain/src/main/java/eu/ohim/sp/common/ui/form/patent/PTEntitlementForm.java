package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PTEntitlementForm extends AbstractForm {

    private boolean patentOfficiary;

    private boolean patentNotOfficiary;

    private boolean transferContract;

    private boolean otherGrounds;

    private String otherGroundsDescription;

    private FileWrapper patentOfficiaryFiles;

    private FileWrapper patentNotOfficiaryFiles;

    private FileWrapper transferContractFiles;

    public PTEntitlementForm() {
        super();
        patentNotOfficiaryFiles = new FileWrapper();
        patentOfficiaryFiles = new FileWrapper();
        transferContractFiles = new FileWrapper();
    }

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PTEntitlementForm form = new PTEntitlementForm();
        form.setOtherGrounds(this.otherGrounds);
        form.setOtherGroundsDescription(this.otherGroundsDescription);
        form.setPatentNotOfficiary(this.patentNotOfficiary);
        form.setPatentOfficiary(this.patentOfficiary);
        form.setTransferContract(this.transferContract);
        form.setPatentNotOfficiaryFiles(this.patentNotOfficiaryFiles);
        form.setPatentOfficiaryFiles(this.patentOfficiaryFiles);
        form.setTransferContractFiles(this.transferContractFiles);
        return form;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.ENTITLEMENT;
    }

    public boolean isPatentOfficiary() {
        return patentOfficiary;
    }

    public void setPatentOfficiary(boolean patentOfficiary) {
        this.patentOfficiary = patentOfficiary;
    }

    public boolean isPatentNotOfficiary() {
        return patentNotOfficiary;
    }

    public void setPatentNotOfficiary(boolean patentNotOfficiary) {
        this.patentNotOfficiary = patentNotOfficiary;
    }

    public boolean isTransferContract() {
        return transferContract;
    }

    public void setTransferContract(boolean transferContract) {
        this.transferContract = transferContract;
    }

    public boolean isOtherGrounds() {
        return otherGrounds;
    }

    public void setOtherGrounds(boolean otherGrounds) {
        this.otherGrounds = otherGrounds;
    }

    public String getOtherGroundsDescription() {
        return otherGroundsDescription;
    }

    public void setOtherGroundsDescription(String otherGroundsDescription) {
        this.otherGroundsDescription = otherGroundsDescription;
    }

    public FileWrapper getPatentOfficiaryFiles() {
        return patentOfficiaryFiles;
    }

    public void setPatentOfficiaryFiles(FileWrapper patentOfficiaryFiles) {
        this.patentOfficiaryFiles = patentOfficiaryFiles;
    }

    public FileWrapper getPatentNotOfficiaryFiles() {
        return patentNotOfficiaryFiles;
    }

    public void setPatentNotOfficiaryFiles(FileWrapper patentNotOfficiaryFiles) {
        this.patentNotOfficiaryFiles = patentNotOfficiaryFiles;
    }

    public FileWrapper getTransferContractFiles() {
        return transferContractFiles;
    }

    public void setTransferContractFiles(FileWrapper transferContractFiles) {
        this.transferContractFiles = transferContractFiles;
    }
}
