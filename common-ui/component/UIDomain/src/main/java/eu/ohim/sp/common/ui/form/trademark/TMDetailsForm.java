/*******************************************************************************
 * * $$
 * * . * .
 * * * RRRR * Copyright Â© 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/

package eu.ohim.sp.common.ui.form.trademark;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import eu.ohim.sp.common.ui.form.NotAllowedCharsForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;

/**
 * @author Medinju and DURAISA
 *
 *Encapsulate the information related to the Trade Mark used for the different eServices available in SP
 */
public class TMDetailsForm extends AbstractImportableForm implements Cloneable, Serializable {

	private static final long serialVersionUID = 5677881790792941446L;
	
	private static transient final Logger logger = Logger.getLogger(TMDetailsForm.class);
	
	private String applicationNumber;
	
	private String registrationNumber;
	
	private String registrationOffice;

	private String applicationLanguage;

	private List<ApplicantForm> applicants;

	private List<RepresentativeForm> representatives;
	
	private String applicant;
	
	private String domicile;
	
	private Date applicationDate;
	
	private Date publicationDate;
	
	private Date registrationDate;
	
	private Date expiryDate;
	
	private Date priorityDate;
	
	private String applicationStatus;

	private Date oppositionPeriodStartDate;

	private Date oppositionPeriodEndDate;

	private String applicationRepresentation;

	private NotAllowedCharsForm applicationRepresentationAlternative;

	private String markDisclaimer;
	
	private String tradeMarkStatus;
	
	private TradeMarkKind tradeMarkKind;

	private String tradeMarkType;
	
	private String tradeMarkName; //get the same of applicationRepresentation for opposition
	
	private Set<GoodAndServiceForm> originalGS;
	
	private Set<GoodAndServiceForm> goodsAndServices;
	
	private FileWrapper representationAttachment;

	private FileWrapper multimediaAttachment;

	private String formMessages;
	
	private String formWarnings;
	
	private String imageRepresentationURI;

	private String multimediaURI;

	private boolean extent;

	private String goodsServicesComment;

	private Boolean unpublished;

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Date getOppositionPeriodStartDate() {
		return oppositionPeriodStartDate;
	}

	public void setOppositionPeriodStartDate(Date oppositionPeriodStartDate) {
		this.oppositionPeriodStartDate = oppositionPeriodStartDate;
	}

	public Date getOppositionPeriodEndDate() {
		return oppositionPeriodEndDate;
	}

	public void setOppositionPeriodEndDate(Date oppositionPeriodEndDate) {
		this.oppositionPeriodEndDate = oppositionPeriodEndDate;
	}

	public String getApplicationRepresentation() {
		return applicationRepresentation;
	}

	public void setApplicationRepresentation(String applicationRepresentation) {
		this.applicationRepresentation = applicationRepresentation;
		this.applicationRepresentationAlternative = NotAllowedCharsForm.fromOriginalValue(applicationRepresentation);
	}

	public void setApplicationRepresentationOnly(String applicationRepresentation) {
		this.applicationRepresentation = applicationRepresentation;
	}

	public String getMarkDisclaimer() {
		return markDisclaimer;
	}

	public void setMarkDisclaimer(String markDisclaimer) {
		this.markDisclaimer = markDisclaimer;
	}

	public String getTradeMarkStatus() {
		return tradeMarkStatus;
	}

	public void setTradeMarkStatus(String tradeMarkStatus) {
		this.tradeMarkStatus = tradeMarkStatus;
	}

	public String getTradeMarkName() {
		return tradeMarkName;
	}

	public void setTradeMarkName(String tradeMarkName) {
		this.tradeMarkName = tradeMarkName;
	}

	public Date getPriorityDate() {
		return priorityDate;
	}

	public void setPriorityDate(Date priorityDate) {
		this.priorityDate = priorityDate;
	}

	public String getRegistrationOffice() {
		return registrationOffice;
	}

	public void setRegistrationOffice(String registrationOffice) {
		this.registrationOffice = registrationOffice;
	}

	public String getApplicationLanguage() {
		return applicationLanguage;
	}

	public void setApplicationLanguage(String applicationLanguage) {
		this.applicationLanguage = applicationLanguage;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	public List<ApplicantForm> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<ApplicantForm> applicants) {
		this.applicants = applicants;
	}

	public TradeMarkKind getTradeMarkKind() {
		return tradeMarkKind;
	}

	public void setTradeMarkKind(TradeMarkKind tradeMarkKind) {
		this.tradeMarkKind = tradeMarkKind;
	}

	public String getTradeMarkType() {
		return tradeMarkType;
	}

	public void setTradeMarkType(String tradeMarkType) {
		this.tradeMarkType = tradeMarkType;
	}
	

	public Set<GoodAndServiceForm> getGoodsAndServices() {
		return goodsAndServices;
	}

	public void setGoodsAndServices(Set<GoodAndServiceForm> goodsAndServices) {
		this.goodsAndServices = goodsAndServices;
	}

	public Set<GoodAndServiceForm> getOriginalGS() {
		return originalGS;
	}

	public void setOriginalGS(Set<GoodAndServiceForm> originalGS) {
		this.originalGS = originalGS;
	}
	
	public FileWrapper getRepresentationAttachment() {
		return representationAttachment;
	}

	public void setRepresentationAttachment(FileWrapper representationAttachment) {
		this.representationAttachment = representationAttachment;
	}

	public String getFormMessages() {
		return formMessages;
	}

	public void setFormMessages(String formMessages) {
		this.formMessages = formMessages;
	}			
	
	public String getFormWarnings() {
		return formWarnings;
	}

	public void setFormWarnings(String formWarnings) {
		this.formWarnings = formWarnings;
	}

	public boolean isExtent() {
		return extent;
	}

	public void setExtent(boolean extent) {
		this.extent = extent;
	}

	public String getGoodsServicesComment() {
		return goodsServicesComment;
	}

	public void setGoodsServicesComment(String goodsServicesComment) {
		this.goodsServicesComment = goodsServicesComment;
	}

	public String getImageRepresentationURI() {
		if(unpublished != null && unpublished){
			if(representationAttachment != null && representationAttachment.getStoredFiles()!= null && representationAttachment.getStoredFiles().size()>0 &&
			representationAttachment.getStoredFiles().get(0).getDocumentId() != null){
				return "getDocument.htm?documentId="+representationAttachment.getStoredFiles().get(0).getDocumentId();
			}
		}
		return imageRepresentationURI;
	}

	public void setImageRepresentationURI(String imageRepresentationURI) {
		this.imageRepresentationURI = imageRepresentationURI;
	}

	public String getMultimediaURI() {
		return multimediaURI;
	}

	public void setMultimediaURI(String multimediaURI) {
		this.multimediaURI = multimediaURI;
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	public List<RepresentativeForm> getRepresentatives() {
		return representatives;
	}

	public void setRepresentatives(List<RepresentativeForm> representatives) {
		this.representatives = representatives;
	}

	public void addGoodAndService(GoodAndServiceForm gs) {
        try {
            if (goodsAndServices.contains(gs)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The goodsService collection contains key : " + gs);
                }
                Iterator<GoodAndServiceForm> iterator = goodsAndServices.iterator();
                while (iterator.hasNext()) {
                    GoodAndServiceForm goodsAndServiceForm = iterator.next();
                    if (goodsAndServiceForm.equals(gs)) {
                        for (TermForm termForm : gs.getTermForms()) {
                            goodsAndServiceForm.addTermForm(termForm);
                        }
                    }
                }
            } else {
            	goodsAndServices.add(gs);
            }
        } catch (Exception e) {
            logger.error("An exception occured in FlowBean.addGoodAndService method");
            throw new SPException(e);
        }
    }

	public FileWrapper getMultimediaAttachment() {
		return multimediaAttachment;
	}

	public void setMultimediaAttachment(FileWrapper multimediaAttachment) {
		this.multimediaAttachment = multimediaAttachment;
	}

	public Boolean getUnpublished() {
		return unpublished;
	}

	public void setUnpublished(Boolean unpublished) {
		this.unpublished = unpublished;
	}

	public NotAllowedCharsForm getApplicationRepresentationAlternative() {
		return applicationRepresentationAlternative;
	}

	public void setApplicationRepresentationAlternative(NotAllowedCharsForm applicationRepresentationAlternative) {
		this.applicationRepresentationAlternative = applicationRepresentationAlternative;
		this.setApplicationRepresentation(applicationRepresentationAlternative != null ? applicationRepresentationAlternative.getOriginalValue() : null);
	}

	public void setApplicationRepresentationAlternativeOnly(NotAllowedCharsForm applicationRepresentationAlternative) {
		this.applicationRepresentationAlternative = applicationRepresentationAlternative;
	}

	/**
	 * Creates a clone of the original object
	 */
	@Override
	public TMDetailsForm clone() throws CloneNotSupportedException{
		TMDetailsForm tmDetailsForm = new TMDetailsForm();
		tmDetailsForm.setId(id);
		tmDetailsForm.setApplicationDate(applicationDate);
		tmDetailsForm.setRegistrationOffice(registrationOffice);
		tmDetailsForm.setApplicationLanguage(applicationLanguage);
		tmDetailsForm.setApplicationNumber(applicationNumber);
		tmDetailsForm.setApplicant(applicant);
		tmDetailsForm.setApplicationRepresentationOnly(applicationRepresentation);
		tmDetailsForm.setApplicationStatus(applicationStatus);
		tmDetailsForm.setExpiryDate(expiryDate);
		tmDetailsForm.setMarkDisclaimer(markDisclaimer);
		tmDetailsForm.setOppositionPeriodEndDate(oppositionPeriodEndDate);
		tmDetailsForm.setOppositionPeriodStartDate(oppositionPeriodStartDate);
		tmDetailsForm.setPublicationDate(publicationDate);
		tmDetailsForm.setRegistrationDate(registrationDate);
		tmDetailsForm.setRegistrationNumber(registrationNumber);
		tmDetailsForm.setTradeMarkStatus(tradeMarkStatus);
		tmDetailsForm.setTradeMarkType(tradeMarkType);
		tmDetailsForm.setTradeMarkKind(tradeMarkKind);
		tmDetailsForm.setRepresentationAttachment(representationAttachment);
		tmDetailsForm.setMultimediaAttachment(multimediaAttachment);
		tmDetailsForm.setGoodsAndServices(goodsAndServices);
		tmDetailsForm.setOriginalGS(originalGS);
		tmDetailsForm.setImported(getImported());
		tmDetailsForm.setTradeMarkName(tradeMarkName);
		tmDetailsForm.setPriorityDate(priorityDate);
		tmDetailsForm.setRegistrationDate(registrationDate);
		tmDetailsForm.setExtent(extent);
		tmDetailsForm.setGoodsServicesComment(goodsServicesComment);
		tmDetailsForm.setApplicants(applicants);
		tmDetailsForm.setRepresentatives(representatives);
		tmDetailsForm.setFormMessages(formMessages);
		tmDetailsForm.setFormWarnings(formWarnings);
		tmDetailsForm.setImageRepresentationURI(imageRepresentationURI);
		tmDetailsForm.setMultimediaURI(multimediaURI);
		tmDetailsForm.setTradeMarkStatus(tradeMarkStatus);
		tmDetailsForm.setRegistrationOffice(registrationOffice);
		tmDetailsForm.setUnpublished(unpublished);
		tmDetailsForm.setApplicationRepresentationAlternativeOnly(applicationRepresentationAlternative);
		return tmDetailsForm;
	}
	
}
