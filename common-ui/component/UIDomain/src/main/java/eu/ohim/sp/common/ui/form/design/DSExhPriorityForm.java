package eu.ohim.sp.common.ui.form.design;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;

public class DSExhPriorityForm extends ExhPriorityForm implements ContainsDesignsLinkForm {

	private static final long serialVersionUID = -1216953792416861935L;

	// Designs linked and not linked to the designer:
	@SuppressWarnings(value="unchecked")
	private List<DesignForm> designsLinked  = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
	@SuppressWarnings(value="unchecked")
	private List<DesignForm> designsNotLinked  = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
	
	private Integer designSequenceNumber;
	
	public DSExhPriorityForm() {
		super();
		// By default selected as "Now" 
		this.getFileWrapper().setAttachment(Boolean.TRUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()c
	 */
	@Override
	public DSExhPriorityForm clone() throws CloneNotSupportedException {
		DSExhPriorityForm exhPriorityForm = new DSExhPriorityForm();
		exhPriorityForm.setFirstDate(super.getFirstDate());
		exhPriorityForm.setId(id);
		exhPriorityForm.setExhibitionName(super.getExhibitionName());
		exhPriorityForm.setFileWrapper(super.getFileWrapper());
		exhPriorityForm.setExhibitionDescription(super.getExhibitionDescription());
        if (designsLinked != null) {
			for (DesignForm designLinkedForm : designsLinked) {
				exhPriorityForm.getDesignsLinked().add(designLinkedForm.clone());
			}
		}
		if (designsNotLinked != null) {
			for (DesignForm designNotLinkedForm : designsNotLinked) {
				exhPriorityForm.getDesignsNotLinked().add(designNotLinkedForm.clone());
			}
		}
		return exhPriorityForm;

	}

	
	@Override
	public List<DesignForm> getDesignsLinked() {
		return designsLinked;
	}


	@Override
	public void setDesignsLinked(List<DesignForm> designsLinked) {
		this.designsLinked = designsLinked;
	}


	@Override
	public List<DesignForm> getDesignsNotLinked() {
		return designsNotLinked;
	}


	@Override
	public void setDesignsNotLinked(List<DesignForm> designsNotLinked) {
		this.designsNotLinked = designsNotLinked;
	}


	public Integer getDesignSequenceNumber() {
		return designSequenceNumber;
	}


	public void setDesignSequenceNumber(Integer designSequenceNumber) {
		this.designSequenceNumber = designSequenceNumber;
	}
	


}
