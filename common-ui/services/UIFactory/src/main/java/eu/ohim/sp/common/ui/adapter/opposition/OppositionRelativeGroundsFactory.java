package eu.ohim.sp.common.ui.adapter.opposition;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.opposition.*;
import eu.ohim.sp.core.domain.design.DesignApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.AddressFactory;
import eu.ohim.sp.common.ui.adapter.AttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.LimitedTrademarkFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.opposition.EarlierEntitlementRightKind;
import eu.ohim.sp.core.domain.opposition.LawArticle;
import eu.ohim.sp.core.domain.opposition.OppositionRelativeGrounds;
import eu.ohim.sp.core.domain.opposition.RelativeGroundLawArticle;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;


@Component
public class OppositionRelativeGroundsFactory implements UIFactory<OppositionRelativeGrounds, OppositionBasisForm> {
	

	@Autowired
	private AttachedDocumentFactory attachedDocumentFactory;
	
	@Autowired
	private LimitedTrademarkFactory limitedTradeMarkFactory;
	
	@Autowired
	private LegalActVersionFactory legalActVersionFactory;
	
	@Autowired
	private AddressFactory addressFactory;

	@Autowired
	private ESDesignFactory esDesignFactory;
	
	@Override
	public OppositionRelativeGrounds convertTo(OppositionBasisForm form) {

		if(form == null){
			return new OppositionRelativeGrounds();
		}
		
		eu.ohim.sp.core.domain.opposition.LegalActVersion legalActVersion = legalActVersionFactory.convertTo(form.getLegalActVersion());
		
		List<eu.ohim.sp.core.domain.opposition.RelativeGroundLawArticle> relativeGroundLawArticles = new ArrayList<RelativeGroundLawArticle>();  // factoryRelativeGroundsArticles.
		
		
		OppositionRelativeGrounds coreObject = new OppositionRelativeGrounds();
		List <LawArticle> articles = new ArrayList<LawArticle>();

		
		if (form.getRelativeGrounds()!=null && form.getRelativeGrounds().getEarlierEntitlementRightInf() != null){
			if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition()!=null){
				if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getRelativeGroundText()!=null){
					for(int i=0; i<form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getRelativeGroundText().size();i++){
						LawArticle article = new LawArticle();
						article.setReference(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getRelativeGroundText().get(i));
						articles.add(article);
					}
					legalActVersion.setArticles(articles);
				}
				coreObject.setLegalActVersion(legalActVersion);
				coreObject.setGroundCategory(eu.ohim.sp.core.domain.opposition.GroundCategoryKind.RELATIVE_GROUNDS);
				coreObject.setExplanationText(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getExplanations());
				coreObject.setRelativeGroundLawArticles(relativeGroundLawArticles); // factoryRelativeGroundsArticles.
				
				if(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgExplanationGroundsMarks() != null){
					List <AttachedDocument> explanationsDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgExplanationGroundsMarks().getStoredFiles()){
						AttachedDocument explanationAttachment = new AttachedDocument();
						explanationAttachment = attachedDocumentFactory.convertTo(storedFile);
						explanationAttachment.setDocumentKind("Explanation Of Grounds");
						explanationsDocuments.add(explanationAttachment);
						
					}
					coreObject.setExplanationsDocuments(explanationsDocuments);
					if (coreObject.getGlobalDocuments() == null || coreObject.getGlobalDocuments().size()==0) 
						coreObject.setGlobalDocuments(explanationsDocuments);
					else
						coreObject.getGlobalDocuments().addAll(explanationsDocuments);
				}
			
				if(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgProposalDecide() != null){
					List <AttachedDocument> proposalDecideDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgProposalDecide().getStoredFiles()){
						AttachedDocument proposalDecideAttachment = new AttachedDocument();
						proposalDecideAttachment = attachedDocumentFactory.convertTo(storedFile);
						proposalDecideAttachment.setDocumentKind("Proposal As To Decide");
						proposalDecideDocuments.add(proposalDecideAttachment);
						
					}
					coreObject.setProposalDecideDocuments(proposalDecideDocuments);
					if (coreObject.getGlobalDocuments() == null || coreObject.getGlobalDocuments().size()==0) 
						coreObject.setGlobalDocuments(proposalDecideDocuments);
					else
						coreObject.getGlobalDocuments().addAll(proposalDecideDocuments);
					
				}
				
				if(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgEvidence() != null){
					List <AttachedDocument> evidenceDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgEvidence().getStoredFiles()){
						AttachedDocument evidenceAttachment = new AttachedDocument();
						evidenceAttachment = attachedDocumentFactory.convertTo(storedFile);
						evidenceAttachment.setDocumentKind("Grounds Evidence");
						evidenceDocuments.add(evidenceAttachment);
					}
					coreObject.setEvidenceDocuments(evidenceDocuments);
					if (coreObject.getGlobalDocuments() == null || coreObject.getGlobalDocuments().size()==0) 
						coreObject.setGlobalDocuments(evidenceDocuments);
					else
						coreObject.getGlobalDocuments().addAll(evidenceDocuments);
				}
				
				if(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgReputationClaimDetails() != null){
					
					if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgReputationClaimDetails().getAttachments() != null){
						List <AttachedDocument> reputationClaimDocuments = new ArrayList<AttachedDocument>();
						for(StoredFile storedFile : form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgReputationClaimDetails().getAttachments().getStoredFiles()){
							AttachedDocument reputationClaimAttachment = new AttachedDocument();
							reputationClaimAttachment = attachedDocumentFactory.convertTo(storedFile);
							reputationClaimAttachment.setDocumentKind("Reputation Claim");
							reputationClaimDocuments.add(reputationClaimAttachment);
						}
						coreObject.setReputationClaimDocuments(reputationClaimDocuments);
					}
					
					coreObject.setReputationClaimExplanation(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgReputationClaimDetails().getExplanationClaim());
					
					
				}
				
				if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgReputationClaimed()!=null)
					coreObject.setReputationClaimed(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgReputationClaimed().getReputationClaimed());
				
				coreObject.setReputationClaimCountries(form.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgROCountryReputationClaimed());
	
				
			}	
			if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getEntitlementOpponent()!=null){
				coreObject.setOpponentEntitlementText(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEntitlementOpponent().getDetails());
				coreObject.setOpponentEntitlementKind(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEntitlementOpponent().getEntitlement());
			}
			
			if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails() !=null){
				coreObject.setEarlierTradeMarkCategory(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getCategoryTradeMark());
				coreObject.setDateOfFame(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getDateOfFame());
				coreObject.setRelatedApplicationsNumbers(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getRelatedApplicationsNumbers());
				coreObject.setTypeRight(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getTypeRight());
				coreObject.setTypeRightDetails(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getTypeRightDetails());
				coreObject.setAreaActivity(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getAreaActivity());
				coreObject.setRegistrationCountry(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getCountry());
				coreObject.setEarlierRightDescription(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierRightDescription());
				if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails()!=null){
					
					LimitedTradeMark earlierTradeMarkDetails = limitedTradeMarkFactory.convertTo(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails()); //factory Limited.
					if (form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getApplicantHolder()!=null ){
						Applicant owner = new Applicant();
						ApplicantForm applicantForm = form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getApplicantHolder();
						PersonName name = new PersonName();
						List <Address> listAddress = new ArrayList<Address>();
						Address address = addressFactory.convertFrom(applicantForm.getAddress());
						listAddress.add(address);
						name.setOrganizationName(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getApplicantHolder().getName());
						owner.setName(name);
						owner.setAddresses(listAddress);
						owner.setKind(PersonKind.LEGAL_ENTITY);
						List<Applicant> applicants = new ArrayList<Applicant>();
						applicants.add(owner);
						earlierTradeMarkDetails.setApplicants(applicants);
					}
					coreObject.setEarlierTradeMarkDetails(earlierTradeMarkDetails);
				}
                if(form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierDesigns() != null && form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierDesigns().size() >0){
                    List<DesignApplication> dsApps = new ArrayList<>();
                    for(ESDesignDetailsForm dsForm: form.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierDesigns()){
                        DesignApplication dsApp = esDesignFactory.convertTo(dsForm);
                        if(dsApp != null){
                            dsApps.add(dsApp);
                        }
                    }
                    coreObject.setEarlierDesignDetails(dsApps);
                }
			}
		    if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.EARLIER_TRADE_MARK.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.EARLIER_TRADE_MARK);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.BUSINESS_NAME.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.BUSINESS_NAME);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.CONTESTED_APPLICATION.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.CONTESTED_APPLICATION);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.FOREIGN_TRADEMARK.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.FOREIGN_TRADEMARK);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.INDUSTRIAL_PROPERTY_RIGHT.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.INDUSTRIAL_PROPERTY_RIGHT);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.NON_REGISTERED.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.NON_REGISTERED);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.OTHER_RIGHTS.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.OTHER_RIGHTS);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.PRIVACY_RIGHT.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.PRIVACY_RIGHT);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.PROPRIETARY_MEDICINAL_PRODUCT.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.PROPRIETARY_MEDICINAL_PRODUCT);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.RIGHTS_AUTOR.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.RIGHTS_AUTOR);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.RIGHTS_PLANT_VARIATION.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.RIGHTS_PLANT_VARIATION);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.RIGHTS_PORTRAIT_NAME.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.RIGHTS_PORTRAIT_NAME);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.EARLIER_GI.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.EARLIER_GI);
		    else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.WELL_KNOW.toString()))
		    	coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.WELL_KNOW);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.AGENT_APPLIED.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.AGENT_APPLIED);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.REPUTATION.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.REPUTATION);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.BAD_FAITH.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.BAD_FAITH);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.EVERY_PERSON.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.EVERY_PERSON);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.PERSON_LEGAL_INTEREST.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.PERSON_LEGAL_INTEREST);
			else if (form.getRelativeGrounds().getEarlierEntitlementRightTypeCode().equalsIgnoreCase(EarlierEntitlementRightKind.COURT_ADMITTED_OWNER.toString()))
				coreObject.setEarlierEntitlementRightType(EarlierEntitlementRightKind.COURT_ADMITTED_OWNER);
		}
				
		return coreObject;
	}

	@Override
	public OppositionBasisForm convertFrom(OppositionRelativeGrounds core) {
		

		OppositionBasisForm uiObject = new OppositionBasisForm();

		if(core == null){
			return null;
		}
		
		LegalActVersion legalActVersion = legalActVersionFactory.convertFrom(core.getLegalActVersion());
		uiObject.setLegalActVersion(legalActVersion);
		uiObject.setGroundCategory(GroundCategoryKind.RELATIVE_GROUNDS);
		RelativeGrounds relativeGrounds = new RelativeGrounds();
		EarlierEntitlementRightInf earlierEntitlementRightInf = new EarlierEntitlementRightInf();
		EarlierEntitlementRightDetails earlierEntitlementRightDetails = new EarlierEntitlementRightDetails();
		earlierEntitlementRightDetails.setAreaActivity(core.getAreaActivity());
		earlierEntitlementRightDetails.setCategoryTradeMark(core.getEarlierTradeMarkCategory());
		earlierEntitlementRightDetails.setDateOfFame(core.getDateOfFame());
		earlierEntitlementRightDetails.setRelatedApplicationsNumbers(core.getRelatedApplicationsNumbers());
		earlierEntitlementRightDetails.setCountry(core.getRegistrationCountry());
		earlierEntitlementRightDetails.setTypeRight(core.getTypeRight());
		earlierEntitlementRightDetails.setTypeRightDetails(core.getTypeRightDetails());
		earlierEntitlementRightDetails.setEarlierRightDescription(core.getEarlierRightDescription());
		if(core.getEarlierTradeMarkDetails() != null){
			TMDetailsForm earlierTradeMarkDetails = limitedTradeMarkFactory.convertFrom(core.getEarlierTradeMarkDetails());
			earlierEntitlementRightDetails.setEarlierTradeMarkDetails(earlierTradeMarkDetails);

			if(earlierTradeMarkDetails.getApplicants() != null && !earlierTradeMarkDetails.getApplicants().isEmpty()){
				earlierEntitlementRightDetails.setApplicantHolder(earlierTradeMarkDetails.getApplicants().get(0));
			}
		}

		if(core.getEarlierDesignDetails() != null){
			List<ESDesignDetailsForm> dsForms = new ArrayList<>();
			for(DesignApplication dsApp: core.getEarlierDesignDetails()){
				ESDesignDetailsForm dsForm = esDesignFactory.convertFrom(dsApp);
				if(dsForm != null){
					dsForms.add(dsForm);
				}
			}
			earlierEntitlementRightDetails.setEarlierDesigns(dsForms);
		}
		
		earlierEntitlementRightInf.setEarlierEntitlementRightDetails(earlierEntitlementRightDetails);
		
		EntitlementOpponent entitlementOpponent = new EntitlementOpponent();
		entitlementOpponent.setDetails(core.getOpponentEntitlementText());
		entitlementOpponent.setEntitlement(core.getOpponentEntitlementKind());
		
		earlierEntitlementRightInf.setEntitlementOpponent(entitlementOpponent);
		
		GroundsRelativeForOpposition groundsRelativeForOpposition = new GroundsRelativeForOpposition();
		groundsRelativeForOpposition.setExplanations(core.getExplanationText());
		
		if (core.getLegalActVersion()!=null && core.getLegalActVersion().getArticles()!=null){
			List <String> relativeGroundText = new ArrayList<String>();
			for (LawArticle article : core.getLegalActVersion().getArticles()){
				relativeGroundText.add(article.getReference());
			}
			groundsRelativeForOpposition.setRelativeGroundText(relativeGroundText);		
		}
		
		
		if (core.getGlobalDocuments() != null) {
			for (AttachedDocument documentGlobal : core.getGlobalDocuments()){
				if (documentGlobal.getDocumentKind().equalsIgnoreCase("Explanation Of Grounds")){
					if (core.getExplanationsDocuments()==null || core.getExplanationsDocuments().size()==0)
						core.setExplanationsDocuments(new ArrayList<AttachedDocument>());
					core.getExplanationsDocuments().add(documentGlobal);
				}
				if (documentGlobal.getDocumentKind().equalsIgnoreCase("Proposal As To Decide")){
					if (core.getProposalDecideDocuments()==null || core.getProposalDecideDocuments().size()==0)
						core.setProposalDecideDocuments(new ArrayList<AttachedDocument>());
					core.getProposalDecideDocuments().add(documentGlobal);
				}
				if (documentGlobal.getDocumentKind().equalsIgnoreCase("Grounds Evidence")){
					if (core.getEvidenceDocuments()==null || core.getEvidenceDocuments().size()==0)
						core.setEvidenceDocuments(new ArrayList<AttachedDocument>());
					core.getEvidenceDocuments().add(documentGlobal);
				}
			}
		}
		
		if (core.getExplanationsDocuments() != null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getExplanationsDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gExplanationGroundsMarks = new FileWrapper();
			gExplanationGroundsMarks.setStoredFiles(storedFiles);
			groundsRelativeForOpposition.setgExplanationGroundsMarks(gExplanationGroundsMarks);
		}
		
		if (core.getProposalDecideDocuments()!= null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getProposalDecideDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gProposalDecideDocuments = new FileWrapper();
			gProposalDecideDocuments.setStoredFiles(storedFiles);
			groundsRelativeForOpposition.setgProposalDecide(gProposalDecideDocuments);
		}
		
		if (core.getEvidenceDocuments()!= null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getEvidenceDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gEvidenceDocuments = new FileWrapper();
			gEvidenceDocuments.setStoredFiles(storedFiles);
			groundsRelativeForOpposition.setgEvidence(gEvidenceDocuments);
		}
		
		GReputationClaimed gReputationClaimed = new GReputationClaimed();
		GReputationClaimDetails gReputationClaimDetails = new GReputationClaimDetails();
		gReputationClaimed.setReputationClaimed(core.getReputationClaimed());
		gReputationClaimDetails.setExplanationClaim(core.getReputationClaimExplanation());
		if (core.getReputationClaimDocuments()!= null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getReputationClaimDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper attachments = new FileWrapper();
			attachments.setStoredFiles(storedFiles);
			gReputationClaimDetails.setAttachments(attachments);
		}
		groundsRelativeForOpposition.setgReputationClaimed(gReputationClaimed);
		groundsRelativeForOpposition.setgReputationClaimDetails(gReputationClaimDetails);
		groundsRelativeForOpposition.setgROCountryReputationClaimed(core.getReputationClaimCountries());
		
		earlierEntitlementRightInf.setGroundsRelativeForOpposition(groundsRelativeForOpposition);
		relativeGrounds.setEarlierEntitlementRightInf(earlierEntitlementRightInf);
		uiObject.setRelativeGrounds(relativeGrounds);
		relativeGrounds.setEarlierEntitlementRightTypeCode(core.getEarlierEntitlementRightType().toString());

		return uiObject;
	}

}
