/*******************************************************************************
 * * $Id:: MainForm.java 55958 2013-02-01 12:25:56Z ionitdi $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.util.DateUtil;


public abstract class MainForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
   	private List<ApplicationCAForm> correspondanceAddresses;
    
	// language section
    private Boolean correspondenceLanguageCheckBox;
    
    private Boolean secondLanguageTranslation;
    
    private Boolean correspondenceEmail;
    
    private FileWrapper fileWrapperImage;
    
    // language used in gui
    private String langId = null;
    
    // Signature section
    private Date dateOfSigning;
    
    private SignatureForm signatoryForm;
    
    // Signature section (second)
    private Boolean addSecondSign;
    
    private SignatureForm secondSignatoryForm;
    protected boolean commonApplicationSection;
   
    /**
     * Constructs a new MainForm.
     */
    public MainForm() {
    	correspondanceAddresses = new ArrayList<>();
    	init();
    }
    
    private void init() {
    	correspondenceLanguageCheckBox = Boolean.FALSE;
        secondLanguageTranslation = Boolean.FALSE;
        
        dateOfSigning = new Date();
        signatoryForm = new SignatureForm();
        secondSignatoryForm = new SignatureForm();
        fileWrapperImage = new FileWrapper();
        correspondanceAddresses.clear();
    }
    
    public void clearInformation() {
    	init();
    }
    
   
    /**
     * Getter for property 'secondLanguageTranslation'.
     * 
     * @return Value for property 'secondLanguageTranslation'.
     */
    public Boolean getSecondLanguageTranslation() {
        return secondLanguageTranslation;
    }

    /**
     * Setter for property 'secondLanguageTranslation'.
     * 
     * @param secondLanguageTranslation
     *            Value to set for property 'secondLanguageTranslation'.
     */
    public void setSecondLanguageTranslation(Boolean secondLanguageTranslation) {
        this.secondLanguageTranslation = secondLanguageTranslation;
    }

    public Boolean getCorrespondenceEmail() {
        return correspondenceEmail;
    }

    public void setCorrespondenceEmail(Boolean correspondenceEmail) {
        this.correspondenceEmail = correspondenceEmail;
    }
    
    /**
     * @return the langId
     */
    public String getLangId() {
        return langId;
    }

    /**
     * @param langId
     *            the langId to set
     */
    public void setLangId(String langId) {
        this.langId = langId;
    }
    
    /**
     * Method that returns the secondSignatoryForm
     * 
     * @return the secondSignatoryForm
     */
    public SignatureForm getSecondSignatoryForm() {
        return secondSignatoryForm;
    }

    /**
     * Method that sets the secondSignatoryForm
     * 
     * @param secondSignatoryForm
     *            the secondSignatoryForm to set
     */
    public void setSecondSignatoryForm(SignatureForm secondSignatoryForm) {
        this.secondSignatoryForm = secondSignatoryForm;
    }
    
    public FileWrapper getFileWrapperImage() {
        if (fileWrapperImage == null) {
            fileWrapperImage = new FileWrapper();
        }
        return fileWrapperImage;
    }

    public void setFileWrapperImage(FileWrapper fileWrapperImage) {
        this.fileWrapperImage = fileWrapperImage;
    }
    
    /**
     * @return the dateOfSigning
     */
    public Date getDateOfSigning() {
        return DateUtil.cloneDate(dateOfSigning);
    }

    /**
     * @param dateOfSigning
     *            the dateOfSigning to set
     */
    public void setDateOfSigning(Date dateOfSigning) {
        this.dateOfSigning = DateUtil.cloneDate(dateOfSigning);
    }
    
    /**
     * @return the addSecondSign
     */
    public Boolean getAddSecondSign() {
        return addSecondSign;
    }

    /**
     * @param addSecondSign
     *            the addSecondSign to set
     */
    public void setAddSecondSign(Boolean addSecondSign) {
        this.addSecondSign = addSecondSign;
    }
    
    /**
     * Method that returns the signatoryForm
     * 
     * @return the signatoryForm
     */
    public SignatureForm getSignatoryForm() {
        return signatoryForm;
    }

    /**
     * Method that sets the signatoryForm
     * 
     * @param signatoryForm
     *            the signatoryForm to set
     */
    public void setSignatoryForm(SignatureForm signatoryForm) {
        this.signatoryForm = signatoryForm;
    }
    
    public Boolean getCorrespondenceLanguageCheckBox() {
        return correspondenceLanguageCheckBox;
    }

    public void setCorrespondenceLanguageCheckBox(Boolean correspondenceLanguageCheckBox) {
        this.correspondenceLanguageCheckBox = correspondenceLanguageCheckBox;
    }


	public List<ApplicationCAForm> getCorrespondanceAddresses() {
		return correspondanceAddresses;
	}

    public boolean isCommonApplicationSection() {
        return commonApplicationSection;
    }

    public void setCommonApplicationSection(boolean commonApplicationSection) {
        this.commonApplicationSection = commonApplicationSection;
    }
}
