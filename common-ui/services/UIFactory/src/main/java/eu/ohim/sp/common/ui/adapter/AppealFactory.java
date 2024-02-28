package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

@Component
public class AppealFactory implements UIFactory<Appeal, AppealForm> {
	
	@Autowired
	private AttachedDocumentFactory attachedDocumentFactory;

	@Autowired
	private GSHelperFactory gsHelperFactory;

	@Override
	public Appeal convertTo(AppealForm form) {
		Appeal core = new Appeal();
		if(form == null){
			return core;
		}
		core.setAppealKind(EnumAdapter.formAppealKindToCoreAppealKind(form.getAppealKind()));
		core.setDecisionDate(form.getDecisionDate());
		core.setDecisionNumber(form.getDecisionNumber());
		core.setExplanationText(form.getExplanations());
		if(form.getgExplanationGrounds() != null){
			List <AttachedDocument> explanationsDocuments = new ArrayList<AttachedDocument>();
			for(StoredFile storedFile : form.getgExplanationGrounds().getStoredFiles()){
				AttachedDocument explanationAttachment = attachedDocumentFactory.convertTo(storedFile);
				explanationAttachment.setDocumentKind("Explanation Of Grounds");
				explanationsDocuments.add(explanationAttachment);
				
			}
			core.setExplanationsDocuments(explanationsDocuments);			
		}
		core.setOppositionFilingDate(form.getOppositionFilingDate());
		core.setGsHelper(gsHelperFactory.convertTo(form.getGsHelper()));
		return core;
	}

	@Override
	public AppealForm convertFrom(Appeal core) {
		AppealForm form = new AppealForm();
		if(core == null){
			return form;
		}
		form.setAppealKind(EnumAdapter.coreAppealKindToFormAppealKind(core.getAppealKind()));
		form.setDecisionDate(core.getDecisionDate());
		form.setDecisionNumber(core.getDecisionNumber());
		form.setExplanations(core.getExplanationText());
		if (core.getExplanationsDocuments() != null){
			List<StoredFile> storedFiles = new ArrayList<StoredFile>();
			for (AttachedDocument document : core.getExplanationsDocuments()){
				storedFiles.add(attachedDocumentFactory.convertFrom(document));
			}
			FileWrapper gExplanationGrounds = new FileWrapper();
			gExplanationGrounds.setStoredFiles(storedFiles);
			form.setgExplanationGrounds(gExplanationGrounds);
		}
		form.setOppositionFilingDate(core.getOppositionFilingDate());
		form.setGsHelper(gsHelperFactory.convertFrom(core.getGsHelper()));
		return form;
	}

}
