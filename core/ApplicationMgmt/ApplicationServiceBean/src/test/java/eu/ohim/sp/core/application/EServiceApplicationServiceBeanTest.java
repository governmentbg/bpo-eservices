/*
 *
 *  *  ApplicationServiceBean:: $EServiceApplicationServiceBeanTest 4/2/2014 9:47 am karalch $
 *  *  * . * .
 *  *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  *  * . RR R . in the Internal Market (trade marks and designs)
 *  *  * * RRR *
 *  *  * . RR RR . ALL RIGHTS RESERVED
 *  *  * * . _ . *
 *
 */

package eu.ohim.sp.core.application;

import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.opposition.OppositionRelativeGrounds;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.report.ReportService;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Test on EServiceApplicationServiceBean
 */
public class EServiceApplicationServiceBeanTest {

    @InjectMocks
    EServiceApplicationServiceBean eServiceApplicationServiceBean;

    @Mock
    private ReportService reportService;

    @Mock
    private DocumentService documentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFillDocuments() {

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        eServiceApplicationServiceBean.fillApplicationDocuments(tMeServiceApplication);
    }

    @Test
    public void testFillDocumentsWithDocumentId() {
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        tMeServiceApplication.setTradeMarks(new ArrayList<LimitedTradeMark>());
        tMeServiceApplication.getTradeMarks().add(new LimitedTradeMark());

        ImageSpecification imageSpecification = new ImageSpecification();
        imageSpecification.setRepresentation(new Document());
        String documentId = UUID.randomUUID().toString();
        imageSpecification.getRepresentation().setDocumentId(documentId);
        imageSpecification.getRepresentation().setUri("sth");
        tMeServiceApplication.getTradeMarks().get(0).setImageSpecifications(new ArrayList<ImageSpecification>());
        tMeServiceApplication.getTradeMarks().get(0).getImageSpecifications().add(imageSpecification);

        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(new OppositionRelativeGrounds());
        ((OppositionRelativeGrounds) tMeServiceApplication.getOppositionGrounds().get(0)).setEarlierTradeMarkDetails(new LimitedTradeMark());
        ((OppositionRelativeGrounds) tMeServiceApplication.getOppositionGrounds().get(0)).getEarlierTradeMarkDetails().setImageSpecifications(new ArrayList<ImageSpecification>());
        ((OppositionRelativeGrounds) tMeServiceApplication.getOppositionGrounds().get(0)).getEarlierTradeMarkDetails().getImageSpecifications().add(new ImageSpecification());
        String oppositionDocumentID = UUID.randomUUID().toString();

        ((OppositionRelativeGrounds) tMeServiceApplication.getOppositionGrounds().get(0)).getEarlierTradeMarkDetails().getImageSpecifications().get(0).setRepresentation(new Document());
        ((OppositionRelativeGrounds) tMeServiceApplication.getOppositionGrounds().get(0)).getEarlierTradeMarkDetails().getImageSpecifications().get(0).getRepresentation().setDocumentId(oppositionDocumentID);

        byte[] data = "ugh".getBytes();
        Document document = new Document();
        document.setDocumentId(documentId);
        document.setData(data);
        Mockito.when(documentService.getDocument(Matchers.eq(documentId))).thenReturn(document);
        Mockito.when(documentService.getData(document)).thenReturn(data);


        byte[] oppData = "ugh2".getBytes();
        Document oppoDocument = new Document();
        oppoDocument.setDocumentId(oppositionDocumentID);
        oppoDocument.setData(oppData);
        Mockito.when(documentService.getDocument(Matchers.eq(oppositionDocumentID))).thenReturn(oppoDocument);
        Mockito.when(documentService.getData(oppoDocument)).thenReturn(oppData);

        IPApplication filledOne = eServiceApplicationServiceBean.fillApplicationDocuments(tMeServiceApplication);

        Assert.assertArrayEquals(((TMeServiceApplication) filledOne).getTradeMarks().get(0).getImageSpecifications().get(0).getRepresentation().getData(), document.getData());
        Assert.assertArrayEquals(((OppositionRelativeGrounds) tMeServiceApplication.getOppositionGrounds().get(0)).getEarlierTradeMarkDetails().getImageSpecifications().get(0).getRepresentation().getData(), oppoDocument.getData());

    }


    @Test
    public void testFillDocumentsDSWithDocumentId() {
        DSeServiceApplication dsApplication = new DSeServiceApplication();
        dsApplication.setDesignDetails(new ArrayList<DesignApplication>());
        dsApplication.getDesignDetails().add(new DesignApplication());
        dsApplication.getDesignDetails().get(0).setDesignDetails(new ArrayList<Design>());
        dsApplication.getDesignDetails().get(0).getDesignDetails().add(new Design());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).setViews(new ArrayList<DesignView>());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().add(new DesignView());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).setView(new AttachedDocument());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).getView().setDocument(new Document());

        String documentId = UUID.randomUUID().toString();

        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).getView().getDocument().setDocumentId(documentId);
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).getView().getDocument().setUri("papares");

        byte[] data = "ugh".getBytes();
        Document document = new Document();
        document.setDocumentId(documentId);
        document.setData(data);
        Mockito.when(documentService.getDocument(Matchers.eq(documentId))).thenReturn(document);
        Mockito.when(documentService.getData(document)).thenReturn(data);

        eServiceApplicationServiceBean.fillApplicationDocuments(dsApplication);

        Assert.assertArrayEquals(dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).getView().getDocument().getData(), document.getData());

    }


    @Test
    @Ignore
    public void testFillDocumentsDSWithUri() throws IOException {
        DSeServiceApplication dsApplication = new DSeServiceApplication();
        dsApplication.setDesignDetails(new ArrayList<DesignApplication>());
        dsApplication.getDesignDetails().add(new DesignApplication());
        dsApplication.getDesignDetails().get(0).setDesignDetails(new ArrayList<Design>());
        dsApplication.getDesignDetails().get(0).getDesignDetails().add(new Design());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).setViews(new ArrayList<DesignView>());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().add(new DesignView());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).setView(new AttachedDocument());
        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).getView().setDocument(new Document());

        dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).getView().getDocument().setUri("http://wp.vcu.edu/sportleadership/files/2013/09/football.jpg");

        eServiceApplicationServiceBean.fillApplicationDocuments(dsApplication);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dsApplication.getDesignDetails().get(0).getDesignDetails().get(0).getViews().get(0).getView().getDocument().getData());
            FileOutputStream fileOutputStream = new FileOutputStream("C:/data/test.jpg");

        IOUtils.copy(byteArrayInputStream, fileOutputStream);


    }
}
