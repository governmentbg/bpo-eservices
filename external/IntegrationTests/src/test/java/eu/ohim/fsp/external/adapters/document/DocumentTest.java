package eu.ohim.sp.external.adapters.document;

import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import eu.ohim.sp.core.document.DocumentServiceRemote;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/06/13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class DocumentTest {

    //UserAdapterServiceInterface userAdapterServiceInterface = null;

    @Before
    public void setup() {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            final Context context = new InitialContext(jndiProperties);
            //userAdapterServiceInterface = (UserAdapterServiceInterface) context.lookup("ejb:external-user-management/UserAdapterService//UserAdapterService!eu.ohim.sp.external.adapters.user.UserAdapterServiceInterface");
            //classificationAdapterServiceInterface = (ClassificationAdapterServiceInterface) context.lookup("ejb:external-classification-management/ClassificationAdapterService//ClassificationAdapterService!eu.ohim.sp.external.adapters.classification.ClassificationAdapterServiceInterface");
        } catch (NamingException e) {

        }
    }


    public void testUpdateDocument() {

        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        DocumentServiceRemote documentServiceRemote = null;
        try {
            final Context context = new InitialContext(jndiProperties);
            documentServiceRemote = (DocumentServiceRemote) context.lookup("ejb:sp-core-document/DocumentServiceBean//DocumentServiceBean!eu.ohim.sp.core.document.DocumentServiceRemote");
        } catch (NamingException e) {
            e.printStackTrace();
        }



    }


    @Test
    public void testDocument() {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            final Context context = new InitialContext(jndiProperties);

            Runnable[] documentAccesses = new Runnable[1];
            Thread[] myThreads = new Thread[1];


            for (int threads = 0;threads<1;threads++) {
                final int threadsId = threads;
                documentAccesses[threads] = new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<1;i++) {
                            FODocument document = new FODocument();
                            document.setName("test");
                            document.setFileName("filename");
                            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("a.out");
                            try {
                                byte[] data = new byte[inputStream.available()];
                                inputStream.read(data);
                                document.setData(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            document.setModule("applicationType");
                            document.setApplicationType("module");
                            document.setFilingNumber("EMEF000079"+threadsId);

                            DocumentServiceRemote documentServiceRemote = null;
                            try {
                                documentServiceRemote = (DocumentServiceRemote) context.lookup("ejb:core-document-management/DocumentService//DocumentService!eu.ohim.sp.core.document.DocumentServiceRemote");
                            } catch (NamingException e) {
                                e.printStackTrace();
                            }
                            long startTime = new Date().getTime();
                            Document newDocument = documentServiceRemote.saveDocument(document);
                            long endTime = new Date().getTime();
                            System.out.println("Time spent " + threadsId + " : "+ (endTime-startTime) + " : " + newDocument.getDocumentId());

                            newDocument.setDateReceived(new Date());
                            startTime = new Date().getTime();
                            //documentServiceRemote.updateDocument(newDocument);
                            endTime = new Date().getTime();
                            System.out.println("Time spent updating " + threadsId + " : "+ (endTime-startTime) + " : " + newDocument.getDocumentId());



                        }
                    }
                };
                myThreads[threads] = new Thread(documentAccesses[threads]);
                myThreads[threads].setDaemon(false);
                myThreads[threads].start();
            }

            for (int threads = 0;threads<1;threads++) {
                try {
                    myThreads[threads].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //userAdapterServiceInterface = (UserAdapterServiceInterface) context.lookup("ejb:external-user-management/UserAdapterService//UserAdapterService!eu.ohim.sp.external.adapters.user.UserAdapterServiceInterface");
            //classificationAdapterServiceInterface = (ClassificationAdapterServiceInterface) context.lookup("ejb:external-classification-management/ClassificationAdapterService//ClassificationAdapterService!eu.ohim.sp.external.adapters.classification.ClassificationAdapterServiceInterface");
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }


    public void testGetUser() {
        //System.out.println(((eu.ohim.sp.core.domain.user.User) userAdapterServiceInterface.getUser("TM", "EM", "abril23")).getUserName());
    }

    public void testValidate() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("test", "test");
        //System.out.println( ( (eu.ohim.sp.core.domain.user.User) userAdapterServiceInterface.searchUser("TM", "EM", map).get(0)).getUserName());
    }
}
