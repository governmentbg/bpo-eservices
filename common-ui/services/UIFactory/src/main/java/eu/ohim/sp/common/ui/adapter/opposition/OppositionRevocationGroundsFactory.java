

package eu.ohim.sp.common.ui.adapter.opposition;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.AttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.opposition.RevocationGrounds;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.opposition.LawArticle;
import eu.ohim.sp.core.domain.opposition.OppositionRevocationGrounds;
import eu.ohim.sp.core.domain.resources.AttachedDocument;


@Component
public class OppositionRevocationGroundsFactory implements UIFactory<OppositionRevocationGrounds, OppositionBasisForm> {
	

	@Autowired
	private AttachedDocumentFactory attachedDocumentFactory;

	@Autowired
	private LegalActVersionFactory legalActVersionFactory;
	
	@Override
	public OppositionRevocationGrounds convertTo(OppositionBasisForm form) {

		if(form == null){
			return new OppositionRevocationGrounds();
		}
		
		eu.ohim.sp.core.domain.opposition.LegalActVersion legalActVersion = legalActVersionFactory.convertTo(form.getLegalActVersion());
		
		List<eu.ohim.sp.core.domain.opposition.RevocationGroundLawArticle> revocationGroundLawArticles = new ArrayList<eu.ohim.sp.core.domain.opposition.RevocationGroundLawArticle>();  // factoryRelativeGroundsArticles.
		
		
		OppositionRevocationGrounds coreObject = new OppositionRevocationGrounds();
		List <LawArticle> articles = new ArrayList<LawArticle>();

		
		if (form.getRevocationGrounds()!=null){
				if (form.getRevocationGrounds().getrevocationGroundText()!=null){
					for(int i=0; i<form.getRevocationGrounds().getrevocationGroundText().size();i++){
						LawArticle article = new LawArticle();
						article.setReference(form.getRevocationGrounds().getrevocationGroundText().get(i));
						articles.add(article);
					}
					legalActVersion.setArticles(articles);
				}
				coreObject.setLegalActVersion(legalActVersion);
				coreObject.setGroundCategory(eu.ohim.sp.core.domain.opposition.GroundCategoryKind.REVOCATION_GROUNDS);
				coreObject.setExplanationText(form.getRevocationGrounds().getExplanations());
				coreObject.setRevocationGroundLawArticles(revocationGroundLawArticles); // factoryRelativeGroundsArticles.
				
				if(form.getRevocationGrounds().getgExplanationGroundsMarks() != null){
					List <AttachedDocument> explanationsDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getRevocationGrounds().getgExplanationGroundsMarks().getStoredFiles()){
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
			
				if(form.getRevocationGrounds().getgProposalDecide() != null){
					List <AttachedDocument> proposalDecideDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getRevocationGrounds().getgProposalDecide().getStoredFiles()){
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
				
				if(form.getRevocationGrounds().getgEvidence() != null){
					List <AttachedDocument> evidenceDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getRevocationGrounds().getgEvidence().getStoredFiles()){
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
				
		}
				
		return coreObject;
	}

	@Override
	public OppositionBasisForm convertFrom(OppositionRevocationGrounds core) {
		
		OppositionBasisForm uiObject = new OppositionBasisForm();

		if(core == null){
			return null;
		}
		
		LegalActVersion legalActVersion = legalActVersionFactory.convertFrom(core.getLegalActVersion());
		uiObject.setLegalActVersion(legalActVersion);
		uiObject.setGroundCategory(GroundCategoryKind.REVOCATION_GROUNDS);

		RevocationGrounds revocationGrounds = new RevocationGrounds();
		revocationGrounds.setExplanations(core.getExplanationText());
		
		if (core.getLegalActVersion()!=null && core.getLegalActVersion().getArticles()!=null){
			List <String> revocationGroundText = new ArrayList<String>();
			for (LawArticle article : core.getLegalActVersion().getArticles()){
				revocationGroundText.add(article.getReference());
			}
			revocationGrounds.setrevocationGroundText(revocationGroundText);		
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
			revocationGrounds.setgExplanationGroundsMarks(gExplanationGroundsMarks);
		}
		
		if (core.getProposalDecideDocuments()!= null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getProposalDecideDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gProposalDecideDocuments = new FileWrapper();
			gProposalDecideDocuments.setStoredFiles(storedFiles);
			revocationGrounds.setgProposalDecide(gProposalDecideDocuments);
		}
		
		if (core.getEvidenceDocuments()!= null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getEvidenceDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gEvidenceDocuments = new FileWrapper();
			gEvidenceDocuments.setStoredFiles(storedFiles);
			revocationGrounds.setgEvidence(gEvidenceDocuments);
		}

		uiObject.setRevocationGrounds(revocationGrounds);

		return uiObject;
	}

}
