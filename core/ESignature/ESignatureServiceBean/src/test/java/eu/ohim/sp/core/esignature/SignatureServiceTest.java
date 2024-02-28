/*
 *  SignatureService:: SignatureServiceTest 08/10/13 12:24 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.esignature;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.resources.Document;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 08/10/13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class SignatureServiceTest {

    @Mock
    ConfigurationService configurationService;

    @InjectMocks
    ESignatureServiceBean signatureService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPrepareSignatureRequest() {
        try {
            Document document = new Document();
            document.setData(new String("test document").getBytes("UTF-8"));
            Document document1 = new Document();
            document1.setData(new String("test 2 document").getBytes("UTF-8"));

            Document[] documents = {document, document1};
            when(configurationService.getValue(anyString(), anyString())).thenReturn("url");
            Map<String, Serializable> map = signatureService.prepareSignatureRequest(documents, "user", "https://whatever.com");

            assertEquals(map.get(ESignatureService.CALLBACK_URL_PARAM), "https://whatever.com");
            assertEquals(map.get(ESignatureService.URL_EXTERNAL_SERVICE_PARAM), "url");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
