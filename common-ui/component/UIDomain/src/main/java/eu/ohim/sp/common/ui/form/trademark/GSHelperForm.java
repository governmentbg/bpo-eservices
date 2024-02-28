package eu.ohim.sp.common.ui.form.trademark;

import java.util.Set;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class GSHelperForm extends AbstractImportableForm implements java.io.Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8629120945514718941L;
	private String tmApplicationNumber;
	private boolean extent = true;
	private Set<GoodAndServiceForm> goodsAndServices;
	private String formMessages;
	private String goodsServicesComment;
	/**
	 * This indicator specifies whether on full extent all goods in services are saved.
	 * If it is set to false this means that on full extent - no goods and services are saved. 
	 * If it is set to true - then all gs should be copied from the tm.
	 */
	private boolean inclusive;
	
	
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.GSHELPER;
	}

	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		GSHelperForm gsHelper = new GSHelperForm();
		gsHelper.setId(getId());
		gsHelper.setExtent(extent);
		gsHelper.setInclusive(inclusive);
		gsHelper.setGoodsAndServices(goodsAndServices);
		gsHelper.setTmApplicationNumber(tmApplicationNumber);
		gsHelper.setImported(getImported());
		gsHelper.setGoodsServicesComment(goodsServicesComment);
		return gsHelper;
	}

	public String getTmApplicationNumber() {
		return tmApplicationNumber;
	}

	public void setTmApplicationNumber(String tmApplicationNumber) {
		this.tmApplicationNumber = tmApplicationNumber;
	}

	public boolean getExtent() {
		return extent;
	}

	public void setExtent(boolean extent) {
		this.extent = extent;
	}

	
	public Set<GoodAndServiceForm> getGoodsAndServices() {
		return goodsAndServices;
	}

	public void setGoodsAndServices(Set<GoodAndServiceForm> goodsAndServices) {
		this.goodsAndServices = goodsAndServices;
	}

	public String getFormMessages() {
		return formMessages;
	}

	public void setFormMessages(String formMessages) {
		this.formMessages = formMessages;
	}

	public boolean getInclusive() {
		return inclusive;
	}

	public void setInclusive(boolean inclusive) {
		this.inclusive = inclusive;
	}

	public String getGoodsServicesComment() {
		return goodsServicesComment;
	}

	public void setGoodsServicesComment(String goodsServicesComment) {
		this.goodsServicesComment = goodsServicesComment;
	}
	
	

	
	
}
