package eu.ohim.sp.common.ui.form.licence;

import java.util.Date;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class LicenceForm extends AbstractImportableForm implements java.io.Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2118624831448748444L;
	
	private LicenceKind licenceKind;
	private Boolean subLicenceIndicator;
	private Boolean territoryLimitationIndicator;
	private String territoryLimitationText;
	private Date periodLimitationEndDate;
	private Boolean periodLimitationIndicator;
	private GSHelperForm gsHelper;
	private Boolean licenceIsCompulsory;
	
	public LicenceForm(){
		gsHelper = new GSHelperForm();
	}
	
	public LicenceKind getLicenceKind() {
		return licenceKind;
	}
	public void setLicenceKind(LicenceKind licenceKind) {
		this.licenceKind = licenceKind;
	}
	public Boolean getSubLicenceIndicator() {
		return subLicenceIndicator;
	}
	public void setSubLicenceIndicator(Boolean subLicenceIndicator) {
		this.subLicenceIndicator = subLicenceIndicator;
	}
	public Boolean getTerritoryLimitationIndicator() {
		return territoryLimitationIndicator;
	}
	public void setTerritoryLimitationIndicator(Boolean territoryLimitationIndicator) {
		this.territoryLimitationIndicator = territoryLimitationIndicator;
	}
	public String getTerritoryLimitationText() {
		return territoryLimitationText;
	}
	public void setTerritoryLimitationText(String territoryLimitationText) {
		this.territoryLimitationText = territoryLimitationText;
	}
	public Date getPeriodLimitationEndDate() {
		return periodLimitationEndDate;
	}
	public void setPeriodLimitationEndDate(Date periodLimitationEndDate) {
		this.periodLimitationEndDate = periodLimitationEndDate;
	}
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.LICENCE;
	}
	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		LicenceForm licenceForm = new LicenceForm();
		licenceForm.setId(id);
		licenceForm.setLicenceKind(licenceKind);
		licenceForm.setPeriodLimitationEndDate(periodLimitationEndDate);
		licenceForm.setSubLicenceIndicator(subLicenceIndicator);
		licenceForm.setTerritoryLimitationIndicator(territoryLimitationIndicator);
		licenceForm.setTerritoryLimitationText(territoryLimitationText);
		licenceForm.setPeriodLimitationIndicator(periodLimitationIndicator);
		if(gsHelper != null){
			licenceForm.setGsHelper((GSHelperForm)gsHelper.clone());
		}
		licenceForm.setLicenceIsCompulsory(licenceIsCompulsory);
		return licenceForm;
	}
	
	public Boolean getPeriodLimitationIndicator() {
		return periodLimitationIndicator;
	}
	public void setPeriodLimitationIndicator(Boolean periodLimitationIndicator) {
		this.periodLimitationIndicator = periodLimitationIndicator;
	}
	public GSHelperForm getGsHelper() {
		return gsHelper;
	}
	public void setGsHelper(GSHelperForm gsHelper) {
		this.gsHelper = gsHelper;
	}

	public Boolean getLicenceIsCompulsory() {
		return licenceIsCompulsory;
	}

	public void setLicenceIsCompulsory(Boolean licenceIsCompulsory) {
		this.licenceIsCompulsory = licenceIsCompulsory;
	}
}
