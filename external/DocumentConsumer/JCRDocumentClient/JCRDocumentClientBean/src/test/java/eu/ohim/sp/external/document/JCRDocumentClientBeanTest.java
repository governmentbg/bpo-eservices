package eu.ohim.sp.external.document;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.rules.RulesService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class JCRDocumentClientBeanTest {
    @Mock
    private Repository repository;

    @Mock
    private Credentials credentials;

    @Mock
    private Session session;

    @Mock
    private Node node;

    @Mock
    private Property property;

    @Mock
    private ValueFactory valueFactory;

    @Mock
    private PropertyIterator propertyIterator;

    @Mock
    private NodeIterator nodeIterator;

    @Mock
    private Workspace workspace;

    @Mock
    private QueryManager queryManager;

    @Mock
    private Query query;

    @Mock
    private QueryResult queryResult;

    @Mock
    private Calendar calendar;

    @Mock
    private Binary value;

    @Mock
    private RulesService businessRulesService;

    @InjectMocks
    private JCRDocumentClientBean documentClient = new JCRDocumentClientBean();

    @Before
    public void init() throws RepositoryException {
        when(repository.login(any(Credentials.class))).thenReturn(session);
    }

    @Test
    public void testAddDocument() throws RepositoryException {
        Document document = new Document();
        document.setFileName("file.ext");
        document.getCustomProperties().put(FODocument.APPLICATION_TYPE, "TM");
        document.getCustomProperties().put(FODocument.FILING_NUMBER, "EMEF000001");
        document.setData(new byte[10]);
        document.setModule("tmefiling");
		document.getCustomProperties().put("customPath", "somepath/EMEF000001/tmefiling");

        when(session.getNode(any(String.class))).thenReturn(node);
        when(node.getProperty(any(String.class))).thenReturn(property);
        when(node.addNode(any(String.class))).thenReturn(node);
        when(node.addNode(any(String.class), any(String.class))).thenReturn(node);
        when(property.getLong()).thenReturn(new Long(1));
        when(session.getValueFactory()).thenReturn(valueFactory);
        when(valueFactory.createBinary(any(InputStream.class))).thenReturn(value);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("path", "somepath/EMEF000001/tmefiling");

        when(businessRulesService.calculate(eq(document.getModule()), eq(JCRDocumentClientBean.DOCUMENT_SET), Matchers.<List<Object>>anyObject())).thenReturn(resultMap);

        documentClient.addDocument(document);

        verify(session, times(1)).getNode(eq("/"));
        verify(node, times(1)).addNode(eq("tmefiling"), eq("nt:unstructured"));
        verify(node, times(1)).addNode(eq("file.ext"), eq("nt:file"));
        verify(node, times(1)).addNode(eq("jcr:content"), eq("nt:resource"));
        verify(node, times(1)).addNode(eq("ATT0001"));
        verify(node, times(1)).addNode(eq("EMEF000001"), eq("nt:unstructured"));

        verify(node, times(1)).setProperty(FODocument.APPLICATION_TYPE, "TM");
        verify(node, times(1)).setProperty(FODocument.FILING_NUMBER, "EMEF000001");
        verify(node, times(1)).setProperty("fileSize", 10);

        verify(node, times(1)).setProperty("nextValue", 2);

        verify(property, times(1)).getLong();

        //one if done for folders
        verify(session, times(2)).save();
        verify(session, times(1)).logout();

    }

    @Test
    public void testSearchDocument() throws RepositoryException {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("module", "TM");
        properties.put("applicationType", "TMefiling");
        properties.put("filingNumber", "00000000001");
        when(session.getWorkspace()).thenReturn(workspace);
        when(workspace.getQueryManager()).thenReturn(queryManager);
        when(queryManager.createQuery(anyString(), eq(Query.JCR_SQL2))).thenReturn(query);
        when(query.execute()).thenReturn(queryResult);
        when(queryResult.getNodes()).thenReturn(nodeIterator);
        when(nodeIterator.hasNext()).thenReturn(true);
        when(nodeIterator.next()).thenReturn(node);
        when(node.getParent()).thenReturn(node);
        when(node.getIdentifier()).thenReturn("fwefew-fnfnr-dqw3g-rzxc2");
        when(nodeIterator.hasNext()).thenReturn(true, false);

        when(session.getNodeByIdentifier("fwefew-fnfnr-dqw3g-rzxc2")).thenReturn(node);
        when(node.getNode(eq("customProperties"))).thenReturn(node);
        when(node.getProperties()).thenReturn(propertyIterator);
        when(node.getNodes()).thenReturn(nodeIterator);
        when(node.hasProperty("dateCreated")).thenReturn(true);
        when(node.getProperty("dateCreated")).thenReturn(property);
        when(property.getDate()).thenReturn(calendar);

        when(propertyIterator.hasNext()).thenReturn(false);

        List<Document> documents = documentClient.searchDocument(properties, true);

        assertEquals(documents.size(), 1);
        assertEquals(documents.get(0).getDocumentId(), "fwefew-fnfnr-dqw3g-rzxc2");

    }

    @Test
    public void testGetDocument() throws RepositoryException {
        Document document = new Document();
        document.setDocumentId("fwefew-fnfnr-dqw3g-rzxc2");

        when(session.getNodeByIdentifier(eq("fwefew-fnfnr-dqw3g-rzxc2"))).thenReturn(node);
        when(node.getNode(eq("customProperties"))).thenReturn(node);
        when(node.getProperties()).thenReturn(propertyIterator);
        when(node.getNodes()).thenReturn(nodeIterator);

        when(propertyIterator.hasNext()).thenReturn(false);
        when(nodeIterator.hasNext()).thenReturn(false);

        assertNotNull(documentClient.getDocument(document));

        verify(repository, times(1)).login(any(Credentials.class));
        verify(session, times(1)).getNodeByIdentifier(eq("fwefew-fnfnr-dqw3g-rzxc2"));
        verify(node, times(1)).getNode(eq("customProperties"));
        verify(node, times(1)).getProperties();
        verify(node, times(1)).getNodes();
    }

    @Test(expected = SPException.class)
    public void testAddDocumentEmpty() throws RepositoryException {
        Document document = new Document();
        documentClient.addDocument(document);

    }

    @Test
    public void should_not_allow_to_delete_null_document() {
        // given
        final Document document = null;

        try {
            documentClient.deleteDocument(document);
        } catch (Exception e) {
            assertThat(e)
                    .isInstanceOf(SPException.class)
                    .hasMessage("There was an error deleting a document. The document is null");
        }
    }

    @Test
    public void should_not_allow_to_delete_document_with_null_document_id() {
        // given
        final Document document = new Document();
        document.setDocumentId(null);

        try {
            documentClient.deleteDocument(document);
        } catch (Exception e) {
            assertThat(e)
                    .isInstanceOf(SPException.class)
                    .hasMessage("There was an error deleting a document. The document id is mandatory");
        }
    }

    @Test
    public void should_delete_document_from_repository() throws Exception {
        // given
        final Document document = new Document();
        document.setDocumentId("1");

        Session session = mock(Session.class);
        Node node = mock(Node.class);

        when(repository.login(eq(credentials))).thenReturn(session);
        when(session.getNodeByIdentifier(eq(document.getDocumentId()))).thenReturn(node);

        // when
        documentClient.deleteDocument(document);

        // then
        verify(node).remove();
        verify(session).save();
        verify(session).logout();
    }
}