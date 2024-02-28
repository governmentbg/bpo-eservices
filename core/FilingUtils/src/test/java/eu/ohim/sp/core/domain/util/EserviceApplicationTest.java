/*
 *  FspDomain:: EserviceApplicationTest 15/11/13 15:58 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.util;

import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.design.*;
import eu.ohim.sp.core.domain.payment.*;
import eu.ohim.sp.core.domain.person.*;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.filing.domain.utils.EserviceTransactionUtil;
import eu.ohim.sp.filing.domain.utils.MapperFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 12/11/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class EserviceApplicationTest {

    @Test
    public void testEserviceTrademark() throws IOException {
        EserviceTransactionUtil eserviceTransactionUtil = (EserviceTransactionUtil) new MapperFactory().generateEservicesTU();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns2:Transaction xmlns:ns2=\"http://fsp.ohim.eu/XMLSchema\">\n" +
                "    <TransactionHeader>\n" +
                "        <SenderDetails>\n" +
                "            <RequestXSDVersion>0.01-DRAFT</RequestXSDVersion>\n" +
                "            <LoginInformation>\n" +
                "                <Login>some user</Login>\n" +
                "            </LoginInformation>\n" +
                "        </SenderDetails>\n" +
                "    </TransactionHeader>\n" +
                "    <DesignTransactionBody>\n" +
                "        <TransactionContentDetails>\n" +
                "            <TransactionCode>Design E-Filing</TransactionCode>\n" +
                "            <TransactionData>\n" +
                "                <DesignServicesApplication>\n" +
                "                    <RequestSoftware>\n" +
                "                        <RequestSoftwareName>Design e-Filing</RequestSoftwareName>\n" +
                "                        <RequestSoftwareVersion>0.01</RequestSoftwareVersion>\n" +
                "                    </RequestSoftware>\n" +
                "                    <ApplicationFormName>DS_TRANSFER</ApplicationFormName>\n" +
                "                    <DocumentIncludedDetails>\n" +
                "                        <DocumentIncluded>\n" +
                "                            <DocumentName>name</DocumentName>\n" +
                "                            <DocumentURI>ATTACHMENTS/filename</DocumentURI>\n" +
                "                            <DocumentFilename>filename</DocumentFilename>\n" +
                "                            <DocumentFileFormat>DOC</DocumentFileFormat>\n" +
                "                            <DocumentLanguageCode>en</DocumentLanguageCode>\n" +
                "                            <Comment>comment</Comment>\n" +
                "                            <DocumentKind>Priority Certificate</DocumentKind>\n" +
                "                        </DocumentIncluded>\n" +
                "                    </DocumentIncludedDetails>\n" +
                "                </DesignServicesApplication>\n" +
                "            </TransactionData>\n" +
                "        </TransactionContentDetails>\n" +
                "    </DesignTransactionBody>\n" +
                "</ns2:Transaction>\n";
        IPApplication application = eserviceTransactionUtil.generateIPApplication(xml.getBytes("UTF-8"), "DS_TRANSFER", true);

        Assert.assertTrue(application instanceof DSeServiceApplication);
    }
    
    @Test
    public void testDSserialize() throws UnsupportedEncodingException{
    	EserviceTransactionUtil eserviceTransactionUtil = (EserviceTransactionUtil) new MapperFactory().generateEservicesTU();
    	DSeServiceApplication application = new DSeServiceApplication();
    	application.setApplicationType("DS_CHANGE");
    	application.setApplicationFilingNumber("filing number sds");
    	//signatures
    	List<Signatory> signatures = new ArrayList<Signatory>();
    	Signatory signatory = new Signatory();
    	signatory.setName("John");
    	signatory.setEmail("dasd@dasda.com");
    	signatory.setCapacityText("capacity");
    	signatures.add(signatory);
    	application.setSignatures(signatures);
    	//payment
    	List<PaymentFee> payments = new ArrayList<PaymentFee>();
    	PaymentFee paymentFee = new PaymentFee();
    	paymentFee.setStatus(PaymentStatusCode.TO_FOLLOW);
    	List<MatchedFee> matchedFeeList = new ArrayList<MatchedFee>();
    	MatchedFee matchedFee = new MatchedFee();
    	Fee fee = new Fee();
    	fee.setAmount(23d);
    	FeeType feeType = new FeeType();
    	feeType.setNameKey("dsd");
    	fee.setFeeType(feeType);
    	matchedFee.setFee(fee);
    	matchedFeeList.add(matchedFee);
    	paymentFee.setFees(matchedFeeList);
    	paymentFee.setIdentifier("12id");
    	payments.add(paymentFee);
    	application.setPayments(payments);
    	//applicants
    	List<Applicant> applicants = new ArrayList<Applicant>();
    	Applicant applicant = new Applicant();
    	applicant.setLegalForm("dsfd");
    	applicant.setOwner(true);
    	applicants.add(applicant);
    	application.setApplicants(applicants);
    	//representatives
    	List<Representative> representatives = new ArrayList<Representative>();
    	Representative representative = new Representative();
    	representative.setLegalForm("dsfd");
    	representatives.add(representative);
    	application.setRepresentatives(representatives);
    	//holders
    	List<Holder> holders = new ArrayList<Holder>();
    	Holder holder = new Holder();
    	holder.setLegalForm("dsfd");
    	holders.add(holder);
    	application.setHolders(holders);
    	//assignee
    	List<Assignee> assignees = new ArrayList<Assignee>();
    	Assignee assignee = new Assignee();
    	assignee.setLegalForm("dsfd");
    	assignees.add(assignee);
    	application.setAssignees(assignees);
    	//documents
    	List<AttachedDocument> attachedDocuments = new ArrayList<AttachedDocument>();
    	AttachedDocument attachedDocument = new AttachedDocument();
    	attachedDocument.setId(34);
    	Document document = new Document();
    	document.setDocumentId("2345");
    	document.setComment("dsda");
    	attachedDocument.setDocument(document);
    	attachedDocuments.add(attachedDocument);
    	application.setDocuments(attachedDocuments);
    	//designs
    	List<DesignApplication> designApplications = new ArrayList<DesignApplication>();
    	DesignApplication designApplication = new DesignApplication();
    	designApplication.setApplicationFilingNumber("filing sdf");
    	List<Design> designs = new ArrayList<Design>();
    	Design design = new Design();
    	design.setDescription("desc");
    	design.setDesignIdentifier("designId");
    	design.setRegistrationNumber("regNum");
    	design.setRegistrationDate(new Date());
    	design.setExpiryDate(new Date());
    	design.setCurrentStatus(DesignStatusCode.FILED);
    	design.setNumberOfRenewals(2);
    	List<ProductIndication> productIndications = new ArrayList<ProductIndication>();
    	ProductIndication productInd = new ProductIndication();
    	List<ProductIndicationClass> productIndicationClasses = new ArrayList<ProductIndicationClass>();
    	ProductIndicationClass productIndicationClass = new ProductIndicationClass();
    	List<ProductIndicationTerm> terms = new ArrayList<ProductIndicationTerm>();
    	terms.add(new ProductIndicationTerm());
    	productIndicationClass.setTerms(terms);
    	productIndicationClasses.add(productIndicationClass);		
    	productInd.setClasses(productIndicationClasses);
    	productIndications.add(productInd);
    	design.setProductIndications(productIndications);
    	//design-view
    	List<DesignView> views = new ArrayList<DesignView>();
    	DesignView view = new DesignView();
    	view.setDescription("viewDesc");
    	views.add(view);
    	design.setViews(views);
    	designs.add(design);
    	designApplication.setDesignDetails(designs);
    	designApplication.setApplicationNumber("appNumb");
    	designApplication.setApplicationDate(new Date());
    	designApplication.setPublicationDate(new Date());
    	designApplication.setApplicants(applicants);
    	designApplication.setRepresentatives(representatives);
    	//design-designer
    	List<Designer> designers = new ArrayList<Designer>();
    	Designer designer = new Designer();
    	PersonName designerName = new PersonName();
    	designerName.setFirstName("dfsdf");
    	designer.setName(designerName);
    	designers.add(designer);
    	designApplication.setDesigners(designers);
    	designApplications.add(designApplication);
    	application.setDesignDetails(designApplications);
    	
    	String xml = new String(eserviceTransactionUtil.generateByte(application, application.getApplicationType(), false), "UTF-8");
    	System.out.println(xml);
    	assertNotNull(xml);
    }
    
    

}
