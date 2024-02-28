package eu.ohim.sp.common.ui.form.application;

import java.util.Date;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class EntitlementForm extends AbstractForm {
	private static final long serialVersionUID = 1L;

	private boolean designOfficiary;
	
	private boolean designNotOfficiary;
	
	private boolean notApplicantsWithWaived;
	
	private boolean dueToSucession;
	
	private boolean accordingToAssociationToCompany;
	
	private boolean transferOfRights;
	
	private boolean otherGrounds;
	
	private String otherGroundsDescription;
	
    private FileWrapper designOfficiaryFiles;
    
    private FileWrapper designNotOfficiaryFiles;
    
    private Date dateOfTransfer;
    
	public EntitlementForm() {
		super();
		designNotOfficiaryFiles=new FileWrapper();
		designOfficiaryFiles=new FileWrapper();
	}

	public boolean isDesignOfficiary() {
		return designOfficiary;
	}

	public void setDesignOfficiary(boolean designOfficiary) {
		this.designOfficiary = designOfficiary;
	}

	public boolean isDesignNotOfficiary() {
		return designNotOfficiary;
	}

	public void setDesignNotOfficiary(boolean designNotOfficiary) {
		this.designNotOfficiary = designNotOfficiary;
	}

	public boolean isNotApplicantsWithWaived() {
		return notApplicantsWithWaived;
	}

	public void setNotApplicantsWithWaived(boolean notApplicantsWithWaived) {
		this.notApplicantsWithWaived = notApplicantsWithWaived;
	}

	public boolean isDueToSucession() {
		return dueToSucession;
	}

	public void setDueToSucession(boolean dueToSucession) {
		this.dueToSucession = dueToSucession;
	}

	public boolean isAccordingToAssociationToCompany() {
		return accordingToAssociationToCompany;
	}

	public void setAccordingToAssociationToCompany(
			boolean accordingToAssociationToCompany) {
		this.accordingToAssociationToCompany = accordingToAssociationToCompany;
	}

	public boolean isTransferOfRights() {
		return transferOfRights;
	}

	public void setTransferOfRights(boolean transferOfRights) {
		this.transferOfRights = transferOfRights;
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

	public FileWrapper getDesignOfficiaryFiles() {
		return designOfficiaryFiles;
	}

	public void setDesignOfficiaryFiles(FileWrapper designOfficiaryFiles) {
		this.designOfficiaryFiles = designOfficiaryFiles;
	}

	public FileWrapper getDesignNotOfficiaryFiles() {
		return designNotOfficiaryFiles;
	}

	public void setDesignNotOfficiaryFiles(FileWrapper designNotOfficiaryFiles) {
		this.designNotOfficiaryFiles = designNotOfficiaryFiles;
	}

	public Date getDateOfTransfer() {
		return dateOfTransfer;
	}

	public void setDateOfTransfer(Date dateOfTransfer) {
		this.dateOfTransfer = dateOfTransfer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.ENTITLEMENT;
	}

	@Override
	public AbstractForm clone() {
		EntitlementForm toReturn=new EntitlementForm();
		
		toReturn.setAccordingToAssociationToCompany(this.isAccordingToAssociationToCompany());
		toReturn.setDateOfTransfer(this.getDateOfTransfer());
		toReturn.setDesignNotOfficiary(this.isDesignNotOfficiary());
		toReturn.setDesignNotOfficiaryFiles(this.getDesignNotOfficiaryFiles());
		toReturn.setDesignOfficiary(this.isDesignOfficiary());
		toReturn.setDesignOfficiaryFiles(this.getDesignOfficiaryFiles());
		toReturn.setDueToSucession(this.isDueToSucession());
		toReturn.setNotApplicantsWithWaived(this.isNotApplicantsWithWaived());
		toReturn.setOtherGrounds(this.isOtherGrounds());
		toReturn.setOtherGroundsDescription(this.getOtherGroundsDescription());
		toReturn.setTransferOfRights(this.isTransferOfRights());
		return toReturn;
	}
    
    
    
}
