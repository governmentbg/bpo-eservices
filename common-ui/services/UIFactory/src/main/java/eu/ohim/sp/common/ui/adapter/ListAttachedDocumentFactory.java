package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

@Component
public class ListAttachedDocumentFactory implements UIFactory<List<AttachedDocument>, FileWrapper> {

	@Autowired
	private AttachedDocumentFactory fileFactory;
	
	@Override
	public List<AttachedDocument> convertTo(FileWrapper form) {
        List<AttachedDocument> documents = new ArrayList<AttachedDocument>();
        
        /*if (form.getAttachment()!=null && form.getAttachment().equals(Boolean.TRUE)) {
        	if (form.getStoredFiles().isEmpty()) {
        		AttachedDocument document = new AttachedDocument();
        		Document commonDocument = new Document();
        		document.setDocument(commonDocument);
        		document.setDocumentKind(documentKind.value());
        		
        		documents.add(document);
        	}
        }*/

        if (form != null) {
	        for (StoredFile storedFile : form.getStoredFiles()) {
	            documents.add(fileFactory.convertTo(storedFile));
	        }
        }
        
        return documents;
	}

	@Override
	public FileWrapper convertFrom(List<AttachedDocument> core) {
		FileWrapper form = new FileWrapper();
		
		if (CollectionUtils.isNotEmpty(core)) {
			for (AttachedDocument doc : core) {
				form.getStoredFiles().add(fileFactory.convertFrom(doc));
//			StoredFile storedFile = new StoredFile();
//			storedFile.setContentType("application/pdf"); 
//			
//			if (doc.getDocument() != null) {
//				Document commonDocument = doc.getDocument();
//				storedFile.setFilename(StringUtils.isNotEmpty(commonDocument.getFileName()) ? commonDocument.getFileName() : "");
//				storedFile.setOriginalFilename(StringUtils.isNotEmpty(commonDocument.getFileName()) ? commonDocument.getFileName() : "");
//				storedFile.setFileSize(commonDocument.getFileSize() > 0 ? commonDocument.getFileSize() : 0);
//			}
			}
		}
		
		return form;
	}

}
