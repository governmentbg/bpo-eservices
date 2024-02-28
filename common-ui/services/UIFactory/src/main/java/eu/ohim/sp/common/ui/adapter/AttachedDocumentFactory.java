/*******************************************************************************
 * * $Id:: FileFactory.java 113496 2013-04-22 15:03:04Z karalch                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;


@Component
public class AttachedDocumentFactory implements UIFactory<AttachedDocument, StoredFile>{
	
	@Autowired
	DocumentFactory documentFactory;
	
	@Override
	public AttachedDocument convertTo(StoredFile storedFile) {
		AttachedDocument doc = new AttachedDocument();
        if(storedFile == null)
        {
            return doc;
        }
        Document commonDocument = documentFactory.convertTo(storedFile);
        doc.setDocument(commonDocument);

        if (storedFile.getDocType() != null) {
        	doc.setDocumentKind(storedFile.getDocType().value());
        }
        
        return doc;
	}

	@Override
	public StoredFile convertFrom(AttachedDocument core) {
		StoredFile storedFile = documentFactory.convertFrom(core.getDocument());	
		
		if (core.getDocumentKind() != null) {
			
			storedFile.setDocType(AttachmentDocumentType.fromValue(core.getDocumentKind()));
		}
		
		return storedFile;
	}

}
