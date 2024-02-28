/*******************************************************************************
 * * $Id:: DocumentServiceTest.java 49261 2012-10-29 13:07:33Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.document;

import org.junit.Before;

import eu.ohim.sp.core.domain.resources.Document;


public class DocumentServiceTest {

	DocumentServiceBean documentService;
	Document document;
	
    @Before
    public void setUp() throws Exception {
        // initialize object under test
        documentService = new DocumentServiceBean();
        
        // Prepares the document
        document = new Document();
        document.setName("document1");
    }

	/*
	@Test
    public void testSaveDocument(){
		documentService.saveDocument(commonDocument, "serviceTemporaryFolder", "documents");
	}
*/
}
