/*
 *  DocumentService:: EPUBUtilTest 21/10/13 20:57 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.document.utils;

import eu.ohim.sp.external.document.DocumentClient;
import eu.ohim.sp.core.domain.resources.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/08/13
 * Time: 20:09
 * To change this template use File | Settings | File Templates.
 */
public class EPUBUtilTest {
    @Mock
    DocumentClient documentClientLocal;

    @InjectMocks
    EPUBUtil epubUtil;

    @InjectMocks
    ZipUtil zipUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private Map<String, Document> prepareDocuments() {
        Map<String, Document> documents = new HashMap<String, Document>();

        Document document1 = new Document();
        document1.setFileName("container.xml");
        document1.setData(stupidRead("./target/test-classes/container.xml"));
        document1.setDocumentId("test1");
        documents.put("container.xml", document1);
        Document document2 = new Document();
        document2.setFileName("application.xml");
        document2.setData(stupidRead("./target/test-classes/application.xml"));
        document2.setDocumentId("test2");
        documents.put("application.xml", document2);

        Document document3 = new Document();
        document3.setFileName("ATTACHMENTS/Att001.pdf");
        document3.setDocumentId("test3");
        document3.setData(stupidRead("./target/test-classes/ATTACHMENTS/Att001.pdf"));

        documents.put("ATTACHMENTS/Att001.pdf", document3);

        when(documentClientLocal.getDocument(refEq(document1, "documentId", "data"))).thenReturn(document1);
        when(documentClientLocal.getDocument(refEq(document2, "documentId", "data"))).thenReturn(document2);
        when(documentClientLocal.getDocument(refEq(document3, "documentId", "data"))).thenReturn(document3);

        return documents;
    }

    @Test
    public void testArchiveEPUB() {
        stupidWrite("application.epub", epubUtil.createArchiveDocument(prepareDocuments(), false).getData());
    }


    @Test
    public void testArchiveZIP() {
        stupidWrite("application.zip", zipUtil.createArchiveDocument(prepareDocuments(), false).getData());
    }

    public void testUnarchiveEPUB() {
        Document document3 = new Document();
        document3.setFileName("application.epub");
        document3.setDocumentId("test3");
        document3.setData(stupidRead("./application.epub"));

        Map<String, Document> documents = epubUtil.unarchiveDocuments(document3, false);
        for (Map.Entry entry : documents.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println(documents.size());
    }

    @Test
    public void testUnarchiveZIP() throws IOException {
        Document document3 = new Document();
        document3.setFileName("application.epub");

        byte[] bytes = new byte[getClass().getClassLoader().getResourceAsStream("application.zip").available()];
        getClass().getClassLoader().getResourceAsStream("application.zip").read(bytes);

        document3.setData(bytes);

        Map<String, Document> documents = zipUtil.unarchiveDocuments(document3, false);
        for (Map.Entry entry : documents.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        assertEquals(3, documents.size());
    }


    public byte[] stupidRead(String filename) {
        File file = new File(filename);

        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
        catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return b;
    }

    public void stupidWrite(String filename, byte[] bytes) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(bytes);
            fos.close();
        }
        catch(FileNotFoundException ex)   {
            System.out.println("FileNotFoundException : " + ex);
        }
        catch(IOException ioe)  {
            System.out.println("IOException : " + ioe);
        }

    }
}
