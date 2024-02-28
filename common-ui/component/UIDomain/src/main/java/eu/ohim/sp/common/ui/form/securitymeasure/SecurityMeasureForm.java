package eu.ohim.sp.common.ui.form.securitymeasure;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class SecurityMeasureForm extends AbstractImportableForm implements java.io.Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2999709795190469870L;
	
	private boolean forbidUse;
	private boolean forbidManage;
	
	
	public boolean getForbidUse() {
		return forbidUse;
	}
	public void setForbidUse(boolean forbidUse) {
		this.forbidUse = forbidUse;
	}
	public boolean getForbidManage() {
		return forbidManage;
	}
	public void setForbidManage(boolean forbidManage) {
		this.forbidManage = forbidManage;
	}
	
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.SECURITY_MEASURE;
	}
	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		SecurityMeasureForm form = new SecurityMeasureForm();
		form.setId(getId());
		form.setForbidUse(forbidUse);
		form.setForbidManage(forbidManage);
		return form;
	}
}
