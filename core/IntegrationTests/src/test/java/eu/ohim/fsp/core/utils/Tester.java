package eu.ohim.sp.core.utils;

import eu.ohim.sp.core.document.DocumentServiceRemote;
import eu.ohim.sp.core.domain.resources.Document;

/**
 * User: jaraful
 * Date: 18/10/13
 * Time: 11:52
 */
public class Tester extends Thread {
	private DocumentServiceRemote documentService = null;
	private Document document;
	private String results = "";
	private String name;

	private Tester(){}

	public Tester(String name, DocumentServiceRemote documentService, final Document document){
		this.documentService = documentService;
		this.document = document;
		this.name = name;
	}

	public void run(){
		for(int i = 0; i < 10 ; i++){
			document.getCustomProperties().put("filingNumber", name+i);
			Document persistedDocument = documentService.saveDocument(document);
			if(persistedDocument != null && persistedDocument.getDocumentId() != null){
				results = results + "1";
			} else{
				results = results + "0";
			}
		}
	}

	public String getResults(){
		return results;
	}
}
