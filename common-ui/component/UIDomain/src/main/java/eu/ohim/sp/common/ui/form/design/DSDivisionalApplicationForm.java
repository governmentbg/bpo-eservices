package eu.ohim.sp.common.ui.form.design;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DSDivisionalApplicationForm extends AbstractImportableForm implements ContainsDesignsLinkForm, ContainsLocarnoForm {

    private static final long serialVersionUID = -7617819423074884892L;
	
    // Divisional application details
	private Boolean claimDivisionalApplication;
	private String infoDivisionalApplication;
	private String numberDivisionalApplication;
    private Date dateDivisionalApplication;
    private FileWrapper fileDivisionalApplication;
 
	// Designs linked and not linked to the divisional application
	private List<DesignForm> designsLinked;
	private List<DesignForm> designsNotLinked;
	
	private List<LocarnoAbstractForm> locarno;
	
	@SuppressWarnings(value="unchecked")
	public DSDivisionalApplicationForm() {
		claimDivisionalApplication = false;
		fileDivisionalApplication = new FileWrapper();
		locarno = new ArrayList<LocarnoAbstractForm>();
		designsLinked = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
		designsNotLinked = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
	}
	
	@Override
	public List<DesignForm> getDesignsLinked() {
		return designsLinked;
	}

	@Override
	public void setDesignsLinked(List<DesignForm> designsLinked) {
		this.designsLinked.clear();
        if (designsLinked != null) {
            for(DesignForm design : designsLinked) {
                design.setDivisonalApplicationIndicator(true);
            }
            this.designsLinked.addAll(designsLinked);
        }
	}

	@Override
	public List<DesignForm> getDesignsNotLinked() {
		return designsNotLinked;
	}

	@Override
	public void setDesignsNotLinked(List<DesignForm> designsNotLinked) {
		this.designsNotLinked.clear();
        if (designsNotLinked != null) {
            for(DesignForm design : designsNotLinked) {
                design.setDivisonalApplicationIndicator(false);
            }
            this.designsNotLinked.addAll(designsNotLinked);
        }
	}
	
	/**
	 * 
	 * @return
	 */
	public String getInfoDivisionalApplication() {
		return infoDivisionalApplication;
	}

	/**
	 * 
	 * @param info
	 */
	public void setInfoDivisionalApplication(String infoDivisionalApplication) {
		this.infoDivisionalApplication = infoDivisionalApplication;
	}
	
	/**
     * @return the claimDivisionalApplication
     */
    public Boolean getClaimDivisionalApplication() {
        return claimDivisionalApplication;
    }

    /**
     * @param claimDivisionalApplication
     *            the claimDivisionalApplication to set
     */
    public void setClaimDivisionalApplication(Boolean claimDivisionalApplication) {
        this.claimDivisionalApplication = claimDivisionalApplication;
    }

    /**
     * @return the numberDivisionalApplication
     */
    public String getNumberDivisionalApplication() {
        return numberDivisionalApplication;
    }

    /**
     * @param numberDivisionalApplication
     *            the numberDivisionalApplication to set
     */
    public void setNumberDivisionalApplication(String numberDivisionalApplication) {
        this.numberDivisionalApplication = numberDivisionalApplication;
    }

    /**
     * @return the dateDivisionalApplication
     */
    public Date getDateDivisionalApplication() {
        return DateUtil.cloneDate(dateDivisionalApplication);
    }

    /**
     * @param dateDivisionalApplication
     *            the dateDivisionalApplication to set
     */
    public void setDateDivisionalApplication(Date dateDivisionalApplication) {
        this.dateDivisionalApplication = DateUtil.cloneDate(dateDivisionalApplication);
    }
    
    public FileWrapper getFileDivisionalApplication() {
		return fileDivisionalApplication;
	}

	public void setFileDivisionalApplication(FileWrapper fileDivisionalApplication) {
		this.fileDivisionalApplication = fileDivisionalApplication;
	}

	 /**
     * {@inheritDoc}
     */
    @Override
    public List<LocarnoAbstractForm> getLocarno() {
    	return locarno;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocarno(List<LocarnoAbstractForm> locarno) {
    	this.locarno = locarno;
    }
	
    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DSDivisionalApplicationForm clone() throws CloneNotSupportedException {
		DSDivisionalApplicationForm daForm = new DSDivisionalApplicationForm();
		daForm.setId(this.getId());
		daForm.setImported(getImported());
		daForm.setInfoDivisionalApplication(this.getInfoDivisionalApplication());
		daForm.setClaimDivisionalApplication(this.getClaimDivisionalApplication());
		daForm.setDateDivisionalApplication(this.getDateDivisionalApplication());
		daForm.setNumberDivisionalApplication(this.getNumberDivisionalApplication());
		daForm.setFileDivisionalApplication(this.getFileDivisionalApplication());
		
        if (designsLinked != null) {
			for (DesignForm designLinkedForm : designsLinked) {
				daForm.getDesignsLinked().add(designLinkedForm.clone());
			}
		}
		if (designsNotLinked != null) {
			for (DesignForm designNotLinkedForm : designsNotLinked) {
				daForm.getDesignsNotLinked().add(designNotLinkedForm.clone());
			}
		}
		
        for (LocarnoAbstractForm locarnoForm : locarno) {
        	daForm.getLocarno().add((LocarnoAbstractForm) locarnoForm.clone());
        }

        return daForm;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.DIVISIONAL_APPLICATION_SECTION;
	}

}
