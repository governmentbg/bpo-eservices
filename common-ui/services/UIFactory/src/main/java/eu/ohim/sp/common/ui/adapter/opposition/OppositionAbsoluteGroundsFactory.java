

package eu.ohim.sp.common.ui.adapter.opposition;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.AttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.opposition.AbsoluteGrounds;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.opposition.LawArticle;
import eu.ohim.sp.core.domain.opposition.OppositionAbsoluteGrounds;
import eu.ohim.sp.core.domain.resources.AttachedDocument;



@Component
public class OppositionAbsoluteGroundsFactory implements UIFactory<OppositionAbsoluteGrounds, OppositionBasisForm> {
	

	@Autowired
	private AttachedDocumentFactory attachedDocumentFactory;

	@Autowired
	private LegalActVersionFactory legalActVersionFactory;
	
	@Override
	public OppositionAbsoluteGrounds convertTo(OppositionBasisForm form) {

		if(form == null){
			return new OppositionAbsoluteGrounds();
		}
		
		eu.ohim.sp.core.domain.opposition.LegalActVersion legalActVersion = legalActVersionFactory.convertTo(form.getLegalActVersion());
		
		List<eu.ohim.sp.core.domain.opposition.AbsoluteGroundLawArticle> absoluteGroundLawArticles = new ArrayList<eu.ohim.sp.core.domain.opposition.AbsoluteGroundLawArticle>();  // factoryRelativeGroundsArticles.
		
		
		OppositionAbsoluteGrounds coreObject = new OppositionAbsoluteGrounds();
		List <LawArticle> articles = new ArrayList<LawArticle>();

		
		if (form.getAbsoluteGrounds()!=null){
				if (form.getAbsoluteGrounds().getAbsoluteGroundText()!=null){
					for(int i=0; i<form.getAbsoluteGrounds().getAbsoluteGroundText().size();i++){
						LawArticle article = new LawArticle();
						article.setReference(form.getAbsoluteGrounds().getAbsoluteGroundText().get(i));
						articles.add(article);
					}
					legalActVersion.setArticles(articles);
				}
				coreObject.setLegalActVersion(legalActVersion);
				coreObject.setGroundCategory(eu.ohim.sp.core.domain.opposition.GroundCategoryKind.ABSOLUTE_GROUNDS);
				coreObject.setExplanationText(form.getAbsoluteGrounds().getExplanations());
				coreObject.setAbsoluteGroundLawArticles(absoluteGroundLawArticles); // factoryRelativeGroundsArticles.
				
				if(form.getAbsoluteGrounds().getgExplanationGroundsMarks() != null){
					List <AttachedDocument> explanationsDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getAbsoluteGrounds().getgExplanationGroundsMarks().getStoredFiles()){
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
			
				if(form.getAbsoluteGrounds().getgProposalDecide() != null){
					List <AttachedDocument> proposalDecideDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getAbsoluteGrounds().getgProposalDecide().getStoredFiles()){
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
				
				if(form.getAbsoluteGrounds().getgEvidence() != null){
					List <AttachedDocument> evidenceDocuments = new ArrayList<AttachedDocument>();
					for(StoredFile storedFile : form.getAbsoluteGrounds().getgEvidence().getStoredFiles()){
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
	public OppositionBasisForm convertFrom(OppositionAbsoluteGrounds core) {

		OppositionBasisForm uiObject = new OppositionBasisForm();

		if(core == null){
			return null;
		}
		
		LegalActVersion legalActVersion = legalActVersionFactory.convertFrom(core.getLegalActVersion());
		uiObject.setLegalActVersion(legalActVersion);
		uiObject.setGroundCategory(GroundCategoryKind.ABSOLUTE_GROUNDS);

		AbsoluteGrounds absoluteGrounds = new AbsoluteGrounds();
		absoluteGrounds.setExplanations(core.getExplanationText());
		
		if (core.getLegalActVersion()!=null && core.getLegalActVersion().getArticles()!=null){
			List <String> absoluteGroundText = new ArrayList<String>();
			for (LawArticle article : core.getLegalActVersion().getArticles()){
				absoluteGroundText.add(article.getReference());
			}
			absoluteGrounds.setAbsoluteGroundText(absoluteGroundText);		
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
			absoluteGrounds.setgExplanationGroundsMarks(gExplanationGroundsMarks);
		}
		
		if (core.getProposalDecideDocuments()!= null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getProposalDecideDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gProposalDecideDocuments = new FileWrapper();
			gProposalDecideDocuments.setStoredFiles(storedFiles);
			absoluteGrounds.setgProposalDecide(gProposalDecideDocuments);
		}
		
		if (core.getEvidenceDocuments()!= null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getEvidenceDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gEvidenceDocuments = new FileWrapper();
			gEvidenceDocuments.setStoredFiles(storedFiles);
			absoluteGrounds.setgEvidence(gEvidenceDocuments);
		}

		uiObject.setAbsoluteGrounds(absoluteGrounds);

		return uiObject;
	}

}
