/*******************************************************************************
 * * $$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/

package eu.ohim.sp.common.ui.form.design;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DesignForm extends AbstractImportableForm implements ContainsLocarnoForm, DSDesignForm {
	private static final long serialVersionUID = 7799371759711916237L;

	Integer number;

	String verbalElements;

    String verbalElementsEn;

    String description;

    String distinctiveFeatures;

    String colours;

    Boolean requestDeferredPublication;

    Date defermentTillDate;

    List<DesignViewForm> views;
    
    List<LocarnoAbstractForm> locarno;

    boolean divisonalApplicationIndicator;
    
    boolean designerWaiverIndicator;

    private List<RepresentativeForm> representatives;
    
	public DesignForm() {
    	views = new ArrayList<DesignViewForm>();
    	locarno = new ArrayList<LocarnoAbstractForm>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.DESIGN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DesignForm clone() throws CloneNotSupportedException {
        DesignForm designForm = new DesignForm();
        clone(designForm, false);
        return designForm;
    }

    /**
     * 
     * @param target
     * @param duplicateMode
     * @throws CloneNotSupportedException
     */
    private void clone(DesignForm target, boolean duplicateMode) throws CloneNotSupportedException {
    	if (!duplicateMode) { // real clone.
    		target.setId(id);
    		target.setImported(getImported());
            for (DesignViewForm designViewForm : views) {
            	target.getViews().add((DesignViewForm) designViewForm.clone());
            }
    	}
    	target.setNumber(number);
    	target.setVerbalElements(verbalElements);
    	target.setVerbalElementsEn(verbalElementsEn);
    	target.setDescription(description);
    	target.setDistinctiveFeatures(distinctiveFeatures);
    	target.setColours(colours);
    	target.setRequestDeferredPublication(requestDeferredPublication);
    	target.setDefermentTillDate(defermentTillDate);
        for (LocarnoAbstractForm locarnoForm : locarno) {
        	target.getLocarno().add((LocarnoAbstractForm) locarnoForm.clone());
        }
    }
     
    /**
     * Duplicate the design, in this mode the views are not cloned.
     * @param target
     * @throws CloneNotSupportedException
     */
    public void duplicate(DesignForm target) throws CloneNotSupportedException {
    	clone(target, true);
    }
    
    /**
     * Get the design number
     *
     * @return the design number
     */
    public Integer getNumber() {
		return number;
	}

    /**
     * Set the design number
     *
     * @param number the design number
     */
	public void setNumber(Integer number) {
		this.number = number;
	}
    
    /**
     * Get the verbal elements
     *
     * @return the verbal elements
     */
    public String getVerbalElements() {
        return verbalElements;
    }

    /**
     * Set the verbal elements
     *
     * @param verbalElements the verbal elements
     */
    public void setVerbalElements(String verbalElements) {
        this.verbalElements = verbalElements;
    }

    /**
     * Get the description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the distinctive features
     *
     * @return the distinctive features
     */
    public String getDistinctiveFeatures() {
        return distinctiveFeatures;
    }

    /**
     * Set the distinctive features
     *
     * @param distinctiveFeatures the distinctive features
     */
    public void setDistinctiveFeatures(String distinctiveFeatures) {
        this.distinctiveFeatures = distinctiveFeatures;
    }

    /**
     * Get the colours
     *
     * @return the colours
     */
    public String getColours() {
        return colours;
    }

    /**
     * Set the colours
     *
     * @param colours the colours
     */
    public void setColours(String colours) {
        this.colours = colours;
    }

    /**
     * Get the request for deferred publication
     *
     * @return the request for deferred publication
     */
    public Boolean getRequestDeferredPublication() {
        return requestDeferredPublication;
    }

    /**
     * Set the request for deferred publication
     *
     * @param requestDeferredPublication the request for deferred publication
     */
    public void setRequestDeferredPublication(Boolean requestDeferredPublication) {
        this.requestDeferredPublication = requestDeferredPublication;
    }

    /**
     * Get the deferment till date
     *
     * @return the deferment till date
     */
    public Date getDefermentTillDate() {
        return defermentTillDate;
    }

    /**
     * SEt the deferment till date
     *
     * @param defermentTillDate the deferment till date
     */
    public void setDefermentTillDate(Date defermentTillDate) {
        this.defermentTillDate = defermentTillDate;
    }
    
    /**
     * Get the views
     *
     * @return the views
     */
    public List<DesignViewForm> getViews() {
        return views;
    }

    /**
     * Set the views
     *
     * @param views the views
     */
    public void setViews(List<DesignViewForm> views) {
        this.views = views;
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

    /**
     * Return all Locarno classifications formatted.
     * @return
     */
    public String getLocarnoClassification() {
    	List<String> toJoin = new ArrayList<String>(locarno.size());
    	String classification;
    	for (LocarnoAbstractForm locarnoAbstractForm : locarno) {
    		classification = locarnoAbstractForm.getLocarnoClassFormatted();
    		if (StringUtils.isNotBlank(classification)) {
    			toJoin.add(classification);
    		}
    	}
    	
    	return StringUtils.join(toJoin, ", ");
    }
    
    /**
     * Return all product indications formatted.
     * @return
     */
    public String getProductIndication() {
    	List<String> toJoin = new ArrayList<String>(locarno.size());
    	String indication;
    	for (LocarnoAbstractForm locarnoAbstractForm : locarno) {
    		indication = locarnoAbstractForm.getLocarnoIndication();
    		if (StringUtils.isNotBlank(indication)) {
    			toJoin.add(indication);
    		}
    	}
    	
    	return StringUtils.join(toJoin, ", ");
    }

    public boolean isDivisonalApplicationIndicator() {
        return divisonalApplicationIndicator;
    }

    public void setDivisonalApplicationIndicator(boolean divisonalApplicationIndicator) {
        this.divisonalApplicationIndicator = divisonalApplicationIndicator;
    }
    
    public boolean isDesignerWaiverIndicator() {
		return designerWaiverIndicator;
	}

	public void setDesignerWaiverIndicator(boolean designerWaiverIndicator) {
		this.designerWaiverIndicator = designerWaiverIndicator;
	}

    public List<RepresentativeForm> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<RepresentativeForm> representatives) {
        this.representatives = representatives;
    }

    public String getVerbalElementsEn() {
        return verbalElementsEn;
    }

    public void setVerbalElementsEn(String verbalElementsEn) {
        this.verbalElementsEn = verbalElementsEn;
    }
}
