package eu.ohim.sp.common.ui.form.design;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import eu.ohim.sp.common.ui.form.claim.PriorityForm;

public class DSPriorityForm extends PriorityForm implements ContainsDesignsLinkForm {

    /**
     * This variable is created with the purpose to sehow in an user friendly way the date. The date of the PriorityForm
     * is an object Date, that is not very nice to show to the user. To avoid do a lot of changes, i add this new variable
     * and a new field to the form that is going to load the value of this variable instead of modify the type of this
     * variable. When the priority is imported then the field with the date is going to be updated with the value in
     * dateStringFormat
     */
    private String dateStringFormat;

    public String getDateStringFormat() {
        return dateStringFormat;
    }

    public void setDateStringFormat(String dateStringFormat) {
        this.dateStringFormat = dateStringFormat;
    }

	private static final long serialVersionUID = 5381527829165594172L;

	/**
	 * Applicant name mentioned on the priority claim.
	 */
	private String applicantName;
	
	/**
	 * Priority type.
	 */
	private String type;
	
	@SuppressWarnings(value="unchecked")
	private List<DesignForm> designsLinked  = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
	@SuppressWarnings(value="unchecked")
	private List<DesignForm> designsNotLinked  = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
	
	private Integer designSequenceNumber;
	
	public DSPriorityForm() {
		super();
		// By default selected as "Now" 
		this.getFileWrapperCopy().setAttachment(Boolean.TRUE);
		this.getFileWrapperTranslation().setAttachment(Boolean.FALSE);
		this.getFilePriorityCertificate().setAttachment(Boolean.TRUE);
	}
	
	@Override
	public DSPriorityForm clone() throws CloneNotSupportedException {
		DSPriorityForm priorityForm = new DSPriorityForm();
		priorityForm.setCountry(super.getCountry());
		priorityForm.setDate(super.getDate());
		priorityForm.setId(id);
		priorityForm.setNumber(super.getNumber());
		priorityForm.setImported(getImported());
		priorityForm.setFileWrapperCopy(super.getFileWrapperCopy());
		priorityForm.setFileWrapperTranslation(super.getFileWrapperTranslation());
		priorityForm.setFilePriorityCertificate(super.getFilePriorityCertificate());
        priorityForm.setPartialPriority(super.getPartialPriority());
        priorityForm.setApplicantName(applicantName);
        priorityForm.setType(type);
        if (designsLinked != null) {
			for (DesignForm designLinkedForm : designsLinked) {
				priorityForm.getDesignsLinked().add(designLinkedForm.clone());
			}
		}
		if (designsNotLinked != null) {
			for (DesignForm designNotLinkedForm : designsNotLinked) {
				priorityForm.getDesignsNotLinked().add(designNotLinkedForm.clone());
			}
		}
		return priorityForm;

	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
