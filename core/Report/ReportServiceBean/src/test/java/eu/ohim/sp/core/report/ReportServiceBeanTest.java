/*
 *  ReportService:: ReportServiceTest 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.report;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.path.PathResolutionStrategy;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import org.apache.commons.io.FileUtils;
import org.exparity.stub.random.RandomBuilder;
import org.exparity.stub.stub.StubBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: KARALCH
 * Date: 04/10/13
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class ReportServiceBeanTest {

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private DocumentService documentService;

    @Mock
    private PathResolutionStrategy pathResolver;

    @InjectMocks
    private ReportServiceBean reportService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        reportService.init();
    }

    @Test(expected = SPException.class)
    public void testGenerateReportNoTemplate() {
        when(configurationService.getXml(eq("receipt"), eq("eservices"))).thenReturn(null);
        reportService.generateReport("eservices", "receipt", "en", null);
        verify(configurationService, times(1)).getXml(eq("receipt"), eq("eservices"));
    }


    @Test
    public void testGenerateReport() throws IOException {


        when(configurationService.getXml(eq("receipt"), eq("eservices"))).thenReturn(
                new String(falseRead(
                        getClass().getClassLoader().getResourceAsStream("receipt.rptdesign")), "UTF-8"));
//                        new FileInputStream("C:\\Users\\Marcos\\Documents\\Work\\workspaces\\BPO\\conf\\sp-fo\\configuration\\app-server\\tm-efiling\\conf\\receipt.rptdesign")), "UTF-8"));
//        TradeMarkApplication tradeMarkApplication = new TradeMarkApplication();
//        tradeMarkApplication.setApplicationFilingNumber("aaa");
        StubBuilder<TradeMarkApplication> builder = StubBuilder.aRandomStubOf(TradeMarkApplication.class);
        builder.collectionSizeOf(2);
        TradeMarkApplication tradeMarkApplication = builder.build();

        byte[] myByteArray = reportService.
                generateReport("eservices", "receipt", "bg", tradeMarkApplication, null, false, new Date());

//        FileUtils.writeByteArrayToFile(new File("D:\\devel\\tmp\\test.pdf"), myByteArray);

        verify(configurationService, times(1)).getXml(eq("receipt"), eq("eservices"));
    }

    public byte[] falseRead(InputStream inputStream) {
        try {
            byte[] b = new byte[(int) inputStream.available()];
            inputStream.read(b);
            return b;
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return null;
    }

}
