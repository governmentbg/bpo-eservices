package eu.ohim.sp.core.domain.application;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

public class SecurityMeasure extends Id implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -658240809226584197L;
	private boolean securityMeasureForbidRightsUse;
	private boolean securityMeasureForbidRightsManage;
	
	
	public boolean getSecurityMeasureForbidRightsUse() {
		return securityMeasureForbidRightsUse;
	}
	public void setSecurityMeasureForbidRightsUse(
			boolean securityMeasureForbidRightsUse) {
		this.securityMeasureForbidRightsUse = securityMeasureForbidRightsUse;
	}
	public boolean getSecurityMeasureForbidRightsManage() {
		return securityMeasureForbidRightsManage;
	}
	public void setSecurityMeasureForbidRightsManage(
			boolean securityMeasureForbidRightsManage) {
		this.securityMeasureForbidRightsManage = securityMeasureForbidRightsManage;
	}
	
	
	
}
