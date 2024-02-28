package eu.ohim.sp.eservices.ui.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.flow.section.*;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import eu.ohim.sp.common.ui.form.opposition.OpposedTradeMarkForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.patent.MarketPermissionForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.securitymeasure.SecurityMeasureForm;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.form.userdoc.UserdocForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;
import eu.ohim.sp.eservices.ui.util.GroundsUtil;

public interface ESFlowBean extends FlowBean, FeesFlowBean,
FileUploadFlowBean,LanguagesFlowBean,
PaymentFlowBean,PersonFlowBean, SignaturesFlowBean {
	
	FileWrapper getOtherAttachments();
	
	void setOtherAttachments(FileWrapper otherAttachments);
	
	List<TMDetailsForm> getTmsList();

	void setTmsList(List<TMDetailsForm> tmsList);
	
	List<String> getReputationCountries();
	
	void setReputationCountries (List<String> reputationCountry);
	
	void addReputationCountry (String reputationCountry);
	void removeReputationCountry (String reputationCountry);
	

	
	/**
    * Retrieves stored goods and services
    * 
    * @return List of GoodAndServiceForm
    */
   Set<GoodAndServiceForm> getGoodsAndServices();

   /**
    * Retrieves goods and services
    * 
    * @param langId
    * @param classId
    * @return goods and services form object
    */
   GoodAndServiceForm getGoodsAndService(String langId, String classId);
   
   /**
    * Method to add each of G&S to the Set of G&S to be displayed.
    * @param gs
    */
   void addGoodAndService(GoodAndServiceForm gs);
   
   /**
    * Method to recover the original G&S
    * @return
    */
   Set<GoodAndServiceForm> getOriginalGS();

   /**
    * Method to modify the original G&S
    * @return
    */
   void setOriginalGS(Set<GoodAndServiceForm> originalGS);

   /**
    * Method related to the applicants/owners part of trademark
    */
   List<ApplicantForm> getOwners();
   
   void setOwners(List<ApplicantForm> owners);
	
   ApplicantForm getApplicant(String name, String domicile);
   
   /**
    * Gets number of classes
    * 
    * @return int classes number
    */
   int getNumberofClasses();
   
   /**
    * Clear the Goods and Services
    */
   void clearGandS();
   
   Set<GoodAndServiceForm> getGoodsServices();
   
   void setGoodsServices(Set<GoodAndServiceForm> goodsServices);
   
   OpposedTradeMarkForm getOpposedTradeMark();

   void setOpposedTradeMark(OpposedTradeMarkForm opposedTradeMark);
	
   List<OpposedTradeMarkForm> getTmosList();

   void setTmosList(List<OpposedTradeMarkForm> tmosList);
	
   OppositionBasisForm getOppositionBasis();

   void setOppositionBasis(OppositionBasisForm oppositionBasis);
   
   List<HolderForm> getHolders();
   
   public List<HolderForm> getUserDataHolders();
   
   public String getComment();

   void setComment(String comment);

   public Integer getPagesCountAttachment();

   void setPagesCountAttachment(Integer pagesCountAttachment);

   public Boolean getHolderIsInventor();

   void setHolderIsInventor(Boolean holderIsInventor);

   public Boolean getPartialInvalidity();

   void setPartialInvalidity(Boolean partialInvalidity);

   String getReference();

   void setReference(String reference);
   
   List<LegalActVersion> getAvaibleLegalActVersions();
   
   void setAvaibleLegalActVersions(List<LegalActVersion> avaibleLegalActVersions);
   
   int getAbsoluteGroundsCount();
   
   int getRelativeGroundsCount();
   
   int getRevocationGroundsCount();

   List<OppositionBasisForm> getObsList();
   
   void setObsList(List<OppositionBasisForm> obsList);

   boolean isReadOnly();
   
   void setReadOnly(boolean readOnly);
   
   boolean isExistsNiceClass(String originalGSClassId, Set<GoodAndServiceForm> limitedGS);
   
   boolean isExistsTerm(String originalGSClassId, String originalGSTerm, Set<GoodAndServiceForm> limitedGS);
   
   boolean isContainsRemovedTerm(String originalGSClassId, Set<GoodAndServiceForm> originalGS, Set<GoodAndServiceForm> limitedGS);
   
   void setIdReservePayment(String idReservePayment);
   
   String getIdReservePayment();
   
   List<LocarnoAbstractForm> getLocarno();
   
   void setLocarno(List<LocarnoAbstractForm> locarno);
   
   List<LocarnoClass> getLocarnoClasses();
   
   void setLocarnoClasses(List<LocarnoClass> locarnoClasses);
   
   List<LocarnoClass> getLocarnoSubclasses();
   
   void setLocarnoSubclasses(List<LocarnoClass> locarnoSubclasses);
   
   void setLocarnoClassifications(Map<String, LocarnoClassification> locarnoClassifications);
   
   Map<String, LocarnoClassification> getLocarnoClassifications();
   
   Collection<LocarnoClassification> getLocarnoClassificationsCollection();
   
   void clearLocarno();

   List<AssigneeForm> getUserAssigneesForm();

   void setUserAssigneesForm(List<AssigneeForm> userAssigneesForm);

   List<HolderForm> getUserHoldersForm();

   void setUserHoldersForm(List<HolderForm> userHoldersForm);

   List<ESDesignDetailsForm> getDssList();

   FileWrapper getDesignRepresentationAttachment();
   
   void clearDesignRepresentationAttachment();
   
   void setDesignRepresentationAttachment(FileWrapper file);

   SecurityMeasureForm getSecurityMeasure();

   void setSecurityMeasure(SecurityMeasureForm securityMeasure);

   List<AppealForm> getAppeals();

   void setAppeals(List<AppealForm> appeals);

   List<GSHelperForm> getGsHelpers();

   void setGsHelpers(List<GSHelperForm> gsHelpers);

   List<LicenceForm> getLicences();

   void setLicences(List<LicenceForm> licences);

   String getChangeType();

   void setChangeType(String changeType);

   List<RepresentativeForm> getPersonChanges();

   void setPersonChanges(List<RepresentativeForm> personChanges);

   List<ApplicantForm> getTmOwners();

   List<RepresentativeForm> getTmpImportRepresentativesList();

   void setTmpImportRepresentativesList(List<RepresentativeForm> representatives);

   RepresentativeForm getImportedRepresentative(String id);

   List<ApplicationCAForm> getCorrespondanceAddresses();

   void setCorrespondanceAddresses(List<ApplicationCAForm> correspondanceAddresses);

   List<PersonForm> getPersons();

   int getApplicantsCount();

   void refreshRelativeGroundsNoFilter(ConfigurationServiceDelegatorInterface configurationService, String applicationType, LegalActVersionFactory legalActVersionFactory);

   List<ESDesignDetailsForm> getDssTempList();

   void setDssTempList(List<ESDesignDetailsForm> dssTempList);

   Integer countOfRemainRepresentatives(String representativeId);

   List<PersonForm> getTmOwnersAndRepresentatives();

   List<PersonForm> getDsOwnersAndRepresentatives();

   List<PersonForm> getPtOwnersAndRepresentatives();

   List<PersonForm> getChangeCAPersons();

   List<ESPatentDetailsForm> getPatentsList();

   void setPatentsList(List<ESPatentDetailsForm> patentsList);

   Boolean getProcessInitiatedBeforePublication();

   void setProcessInitiatedBeforePublication(Boolean processInitiatedBeforePublication);

   boolean getUnpublishedAppImported();

   MarketPermissionForm getMarketPermission();

   void setMarketPermission(MarketPermissionForm marketPermissionForm);

   List<UserdocForm> getFetchedUserdocs();

  boolean getFetchedUserdocsError();

   void setFetchedUserdocsError(boolean fetchedUserdocsError);

   UserdocForm getSelectedUserdoc();

   void setSelectedUserdoc(UserdocForm selectedUserdoc);

   String getUserdocRelationRestriction();

   void setUserdocRelationRestriction(String userdocRelationRestriction);

   Boolean getRelateRequestToObject();

   void setRelateRequestToObject(Boolean relateRequestToObject);

   String getUserdocMainObject();

   void setUserdocMainObject(String userdocMainObject);

   boolean getObjectIsNational();
}
