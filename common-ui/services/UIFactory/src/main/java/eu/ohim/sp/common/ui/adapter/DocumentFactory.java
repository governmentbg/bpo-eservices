package eu.ohim.sp.common.ui.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.common.ui.form.resources.FileContent;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.core.domain.resources.Document;

@Component
public class DocumentFactory implements UIFactory<Document, StoredFile>{
	
	@Autowired
	private FileServiceInterface fileService;

	@Override
	public Document convertTo(StoredFile storedFile) {
		Document commonDocument = new Document();
        if(storedFile == null)
        {
            return commonDocument;
        }
        commonDocument.setFileFormat(storedFile.getContentType());
        commonDocument.setDocumentId(storedFile.getDocumentId());
        commonDocument.setUri(storedFile.getOriginalFilename());
        commonDocument.setComment(storedFile.getDescription());
        
        if(storedFile.getFileSize() != null){
        	commonDocument.setFileSize(storedFile.getFileSize().longValue());
        }
        
        // Original doc name
        commonDocument.setFileName(storedFile.getOriginalFilename());
        // Name given by documentService        
        commonDocument.setName(storedFile.getFilename());
        
        if (storedFile.getThumbnail()!=null) {
        	FileContent content = fileService.getFileStream(storedFile.getThumbnail().getDocumentId());        
        	commonDocument.setData(content.getContent());
        }
        
        return commonDocument;
	}

	@Override
	public StoredFile convertFrom(Document core) {
		String originalFilename = core.getFileName();
		if(originalFilename == null){
			originalFilename = core.getName();
		}
        String description = core.getComment();
		StoredFile storedFile= new StoredFile(core.getName(), originalFilename, core.getFileFormat(), description);
		storedFile.setFileSize(core.getFileSize());
		storedFile.setDocumentId(core.getDocumentId());

		if(core.getCustomProperties().get("documentType")!=null){
			storedFile.setDocType(AttachmentDocumentType.valueOf(core.getCustomProperties().get("documentType")));
		}
		
		return storedFile;
	}

}
