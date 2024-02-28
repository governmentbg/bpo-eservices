package eu.ohim.sp.external.document;

import eu.ohim.sp.core.domain.resources.Document;

import java.util.List;
import java.util.Map;

/**
 * User: jaraful
 * Date: 02/08/13
 * Time: 14:52
 */
public interface DocumentClient {
	Document addDocument(Document document);

	Document getDocument(Document document);

	Document updateDocument(Document document);

	List<Document> searchDocument(Map<String, String> searchFilter, boolean lazy);

	boolean deleteDocument(Document document);
}
