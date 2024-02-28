package eu.ohim.sp.core.domain.licence;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.trademark.GSHelperDetails;


public class Licence extends Id implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4652825071390393882L;
	
	
	private LicenceKind licenceKind;
	private Boolean subLicenceIndicator;
	private Boolean territoryLimitationIndicator;
	private String territoryLimitationText;
	private Date periodLimitationEndDate;
	private Boolean periodLimitationIndicator;
	private GSHelperDetails gsHelper;
	
	
	public GSHelperDetails getGsHelper() {
		return gsHelper;
	}
	public void setGsHelper(GSHelperDetails gsHelper) {
		this.gsHelper = gsHelper;
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
	public Boolean getPeriodLimitationIndicator() {
		return periodLimitationIndicator;
	}
	public void setPeriodLimitationIndicator(Boolean periodLimitationIndicator) {
		this.periodLimitationIndicator = periodLimitationIndicator;
	}
	
	
	
}
