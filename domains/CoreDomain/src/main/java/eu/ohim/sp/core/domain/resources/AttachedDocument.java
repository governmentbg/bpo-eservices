package eu.ohim.sp.core.domain.resources;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;

/**
 * The Class AttachedDocument.
 */
public class AttachedDocument extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7506294169682035506L;

	/** The document kind. */
	private String documentKind;
	
	/** The document. */
	private Document document;
	
	/**
	 * Gets the document kind.
	 *
	 * @return the document kind
	 */
	public String getDocumentKind() {
		return documentKind;
	}
	
	/**
	 * Sets the document kind.
	 *
	 * @param documentKind the new document kind
	 */
	public void setDocumentKind(String documentKind) {
		this.documentKind = documentKind;
	}
	
	/**
	 * Gets the document.
	 *
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * Sets the document.
	 *
	 * @param document the new document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	
}
