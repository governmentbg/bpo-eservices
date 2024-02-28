/*
 *  FspDomain:: TMeServiceApplicationConverterTest 17/12/13 13:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.InternationalTradeMarkCode;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.payment.BankTransfer;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeType;
import eu.ohim.sp.core.domain.payment.MatchedFee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.payment.PaymentKind;
import eu.ohim.sp.core.domain.payment.PaymentStatusCode;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKind;
import eu.ohim.sp.core.domain.resources.Image;
import eu.ohim.sp.core.domain.resources.Sound;
import eu.ohim.sp.core.domain.resources.SoundFileFormat;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.MarkDescription;
import eu.ohim.sp.core.domain.trademark.MarkDisclaimer;
import eu.ohim.sp.core.domain.trademark.MarkFeature;
import eu.ohim.sp.core.domain.trademark.MarkKind;
import eu.ohim.sp.core.domain.trademark.SoundSpecification;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.domain.trademark.WordSpecification;
import eu.ohim.sp.filing.domain.tm.AddressBook;
import eu.ohim.sp.filing.domain.tm.ApplicantDetails;
import eu.ohim.sp.filing.domain.tm.ClassDescriptionDetails;
import eu.ohim.sp.filing.domain.tm.ClassNumber;
import eu.ohim.sp.filing.domain.tm.ClassificationTermDetails;
import eu.ohim.sp.filing.domain.tm.ClassificationTermType;
import eu.ohim.sp.filing.domain.tm.ColourCodeFormatType;
import eu.ohim.sp.filing.domain.tm.ColourCodeType;
import eu.ohim.sp.filing.domain.tm.ColourDetailsType;
import eu.ohim.sp.filing.domain.tm.ColourType;
import eu.ohim.sp.filing.domain.tm.ContactInformationDetails;
import eu.ohim.sp.filing.domain.tm.DocumentIncludedDetails;
import eu.ohim.sp.filing.domain.tm.DocumentStatusCode;
import eu.ohim.sp.filing.domain.tm.ExhibitionPriorityDetails;
import eu.ohim.sp.filing.domain.tm.ExhibitionStatusCodeType;
import eu.ohim.sp.filing.domain.tm.ExtendedISOLanguageCode;
import eu.ohim.sp.filing.domain.tm.ExtendedWIPOST3Code;
import eu.ohim.sp.filing.domain.tm.FormattedAddress;
import eu.ohim.sp.filing.domain.tm.FormattedName;
import eu.ohim.sp.filing.domain.tm.FormattedNameAddress;
import eu.ohim.sp.filing.domain.tm.FreeFormatNameDetailsType;
import eu.ohim.sp.filing.domain.tm.FreeFormatNameType;
import eu.ohim.sp.filing.domain.tm.GoodsServices;
import eu.ohim.sp.filing.domain.tm.GoodsServicesDetails;
import eu.ohim.sp.filing.domain.tm.HolderDetailsType;
import eu.ohim.sp.filing.domain.tm.ISOCountryCode;
import eu.ohim.sp.filing.domain.tm.ISOLanguageCode;
import eu.ohim.sp.filing.domain.tm.Identifier;
import eu.ohim.sp.filing.domain.tm.MarkDescriptionDetails;
import eu.ohim.sp.filing.domain.tm.MarkDisclaimerDetails;
import eu.ohim.sp.filing.domain.tm.MarkImage;
import eu.ohim.sp.filing.domain.tm.MarkImageDetails;
import eu.ohim.sp.filing.domain.tm.MarkTransliterationType;
import eu.ohim.sp.filing.domain.tm.Name;
import eu.ohim.sp.filing.domain.tm.Payment;
import eu.ohim.sp.filing.domain.tm.PaymentDetails;
import eu.ohim.sp.filing.domain.tm.PaymentMethod;
import eu.ohim.sp.filing.domain.tm.PriorityDetails;
import eu.ohim.sp.filing.domain.tm.RepresentativeDetails;
import eu.ohim.sp.filing.domain.tm.SeniorityDetails;
import eu.ohim.sp.filing.domain.tm.SeniorityKind;
import eu.ohim.sp.filing.domain.tm.SignatoryDetails;
import eu.ohim.sp.filing.domain.tm.TradeMarkDetails;
import eu.ohim.sp.filing.domain.tm.TradeMarkServicesApplication;
import eu.ohim.sp.filing.domain.tm.TransformationPriorityDetails;
import eu.ohim.sp.filing.domain.tm.URI;
import eu.ohim.sp.filing.domain.tm.WIPOST3Code;
import eu.ohim.sp.filing.domain.tm.WordMarkSpecification;

public class TMeServiceApplicationConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testConvertFromClassOfTMeServiceApplicationObject() {

		TMeServiceApplication application = new TMeServiceApplication();
		application.setApplicationFilingNumber("00123452");
		application.setApplicationFilingDate(new Date());
		application.setApplicationReference("some reference");
		application.setApplicationLanguage("ES");
		application.setSecondLanguage("some second language");
		application.setReceivingOffice("CR");
		application.setRegistrationOffice("some registration office");
		application.setComment("some comment");

		// List TradeMark
        LimitedTradeMark tradeMark = fillCoreTradeMark();
		application.setTradeMarks(new ArrayList<LimitedTradeMark>());
		application.getTradeMarks().add(tradeMark);

		// List Applicant
		Applicant applicant = new Applicant();
		applicant.setLegalForm("screw");
		applicant.setNationality("ES");
		applicant.setDomicileCountry("GR");

		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry("GR");
		applicant.setIncorporationState("Attica");

		applicant.setCorrespondenceAddresses(new ArrayList<Address>());
		applicant.getCorrespondenceAddresses().add(new Address());

		applicant.setEmails(new ArrayList<Email>());
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(0)
				.setEmailAddress("test@oami.gr");
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		applicant.setPhones(new ArrayList<Phone>());
		applicant.getPhones().add(new Phone());
		applicant.getPhones().get(0).setNumber("2133542352");
		applicant.getPhones().get(0).setPhoneKind(PhoneKind.FAX);

		applicant.getCorrespondenceAddresses().get(0).setCity("London");
		applicant.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras 36");
		applicant.getCorrespondenceAddresses().get(0).setState("Attica");
		applicant.getCorrespondenceAddresses().get(0).setCountry("GR");
		applicant.getCorrespondenceAddresses().get(0).setStreetNumber("36");
		applicant.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Papas");

		applicant.setAddresses(new ArrayList<Address>());

		applicant.getAddresses().add(new Address());
		applicant.getAddresses().get(0).setCity("London Address");
		applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
		applicant.getAddresses().get(0).setState("Attica Address");
		applicant.getAddresses().get(0).setCountry("GR");
		applicant.getAddresses().get(0).setStreetNumber("36 B");

		applicant.setUrls(new ArrayList<String>());
		applicant.getUrls().add("some url");

		applicant.setName(new PersonName());

		applicant.getName().setFirstName("Christos");
		applicant.getName().setMiddleName("H");
		applicant.getName().setLastName("Papadopoulos");

		applicant.getName().setSecondLastName("secondLastName");
		applicant.getName().setOrganizationName("organizationName");

		application.setApplicants(new ArrayList<Applicant>());
		application.getApplicants().add(applicant);

		// List Representatives
		Representative representative = new Representative();
		representative.setLegalForm("screw");
		representative.setNationality("ES");
		representative.setDomicileCountry("GR");

		representative.setDomicileLocality("test");
		representative.setIncorporationCountry("GR");
		representative.setIncorporationState("Attica");

		representative.setCorrespondenceAddresses(new ArrayList<Address>());
		representative.getCorrespondenceAddresses().add(new Address());

		representative.setEmails(new ArrayList<Email>());
		representative.getEmails().add(new Email());
		representative.getEmails().get(0)
				.setEmailAddress("test@oami.gr");
		representative.getEmails().add(new Email());
		representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		representative.setPhones(new ArrayList<Phone>());
		representative.getPhones().add(new Phone());
		representative.getPhones().get(0).setNumber("2133542352");
		representative.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

		representative.getCorrespondenceAddresses().get(0).setCity("London");
		representative.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0).setState("Attica");
		representative.getCorrespondenceAddresses().get(0).setCountry("GR");
		representative.getCorrespondenceAddresses().get(0)
				.setStreetNumber("36");
		representative.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Papas");

		representative.setAddresses(new ArrayList<Address>());

		representative.getAddresses().add(new Address());
		representative.getAddresses().get(0).setCity("London Address");
		representative.getAddresses().get(0)
				.setStreet("Agias Lauras 36 Address");
		representative.getAddresses().get(0).setState("Attica Address");
		representative.getAddresses().get(0).setCountry("GR");
		representative.getAddresses().get(0).setStreetNumber("36 B");

		representative.setUrls(new ArrayList<String>());
		representative.getUrls().add("some url");
		representative.getUrls().add("some url 2");

		representative.setName(new PersonName());

		representative.getName().setFirstName("Kostas");
		representative.getName().setMiddleName("M");
		representative.getName().setLastName("Papadopoulos");

		application.setRepresentatives(new ArrayList<Representative>());
		application.getRepresentatives().add(representative);

		// List Holder
		Holder holder = new Holder();

		holder.setLegalForm("screw holdere");
		holder.setNationality("ES");
		holder.setDomicileCountry("GR");

		holder.setDomicileLocality("test holder");
		holder.setIncorporationCountry("GR");
		holder.setIncorporationState("Attica holder");

		holder.setCorrespondenceAddresses(new ArrayList<Address>());
		holder.getCorrespondenceAddresses().add(new Address());

		holder.setEmails(new ArrayList<Email>());
		holder.getEmails().add(new Email());
		holder.getEmails().get(0)
				.setEmailAddress("test.holder@oami.gr");
		holder.getEmails().add(new Email());
		holder.getEmails().get(1).setEmailAddress("kapelo.holder@oami.gr");

		holder.setPhones(new ArrayList<Phone>());
		holder.getPhones().add(new Phone());
		holder.getPhones().get(0).setNumber("2133542352 holder");
		holder.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

		holder.getCorrespondenceAddresses().get(0).setCity("London holder");
		holder.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras holder 36");
		holder.getCorrespondenceAddresses().get(0).setState("Attica holder");
		holder.getCorrespondenceAddresses().get(0).setCountry("GR");
		holder.getCorrespondenceAddresses().get(0).setStreetNumber("36 holder");
		holder.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Papas holder");

		holder.setAddresses(new ArrayList<Address>());

        holder.setKind(eu.ohim.sp.core.domain.person.PersonKind.LEGAL_ENTITY);
		holder.getAddresses().add(new Address());
		holder.getAddresses().get(0).setCity("London Address holder");
		holder.getAddresses().get(0)
				.setStreet("Agias Lauras 36 Address holder");
		holder.getAddresses().get(0).setState("Attica Address holder");
		holder.getAddresses().get(0).setCountry("GR");
		holder.getAddresses().get(0).setStreetNumber("36 B holder");

		holder.setUrls(new ArrayList<String>());
		holder.getUrls().add("some url holder");

		holder.setName(new PersonName());

		holder.getName().setFirstName("George holder");
		holder.getName().setMiddleName("M holder");
		holder.getName().setLastName("Holder");

		holder.getName().setSecondLastName("secondLastName holder");
		holder.getName().setOrganizationName("organizationName holder");

		application.setHolders(new ArrayList<Holder>());
		application.getHolders().add(holder);

		// List Assignee
		Assignee assignee = new Assignee();

		assignee.setLegalForm("screw assigneee");
		assignee.setNationality("AE");
		assignee.setDomicileCountry("AF");
        assignee.setKind(PersonKind.LEGAL_ENTITY);


		assignee.setDomicileLocality("test assignee");
		assignee.setIncorporationCountry("AI");
		assignee.setIncorporationState("Attica assignee");

		assignee.setCorrespondenceAddresses(new ArrayList<Address>());
		assignee.getCorrespondenceAddresses().add(new Address());

		assignee.setEmails(new ArrayList<Email>());
		assignee.getEmails().add(new Email());
		assignee.getEmails().get(0)
				.setEmailAddress("test.assignee@oami.gr");
		assignee.getEmails().add(new Email());
		assignee.getEmails().get(1).setEmailAddress("kapelo.assignee@oami.gr");

		assignee.setPhones(new ArrayList<Phone>());
		assignee.getPhones().add(new Phone());
		assignee.getPhones().get(0).setNumber("2133542352 assignee");
		assignee.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);
        assignee.getPhones().add(new Phone());
        assignee.getPhones().get(1).setNumber("2133542352 assignee");
        assignee.getPhones().get(1).setPhoneKind(null);

		assignee.getCorrespondenceAddresses().get(0).setCity("London assignee");
		assignee.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras assignee 36");
		assignee.getCorrespondenceAddresses().get(0)
				.setState("Attica assignee");
		assignee.getCorrespondenceAddresses().get(0).setCountry("GR");
		assignee.getCorrespondenceAddresses().get(0)
				.setStreetNumber("36 assignee");
		assignee.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Papas assignee");

		assignee.setAddresses(new ArrayList<Address>());

		assignee.getAddresses().add(new Address());
		assignee.getAddresses().get(0).setCity("London Address assigneee");
		assignee.getAddresses().get(0)
				.setStreet("Agias Lauras 36 Address assigneee");
		assignee.getAddresses().get(0).setState("Attica Address assigneee");
		assignee.getAddresses().get(0).setCountry("AU");
		assignee.getAddresses().get(0).setStreetNumber("36 B assigneee");

		assignee.setUrls(new ArrayList<String>());
		assignee.getUrls().add("some url assigneee");

		assignee.setName(new PersonName());

		assignee.getName().setFirstName("George assigneee");
		assignee.getName().setMiddleName("M assigneee");
		assignee.getName().setLastName("K assigneee");

		assignee.getName().setSecondLastName("secondLastName assigneee");
		assignee.getName().setOrganizationName("organizationName assigneee");

		application.setAssignees(new ArrayList<Assignee>());
		application.getAssignees().add(assignee);

		// List Fee
		application.setFees(new ArrayList<Fee>());
		Fee fee = new Fee();
		fee.setAmount(1234.0);

		FeeType feeType = new FeeType();
		feeType.setNameKey("Basic Fee");
		feeType.setCode("code");
		feeType.setCurrencyCode("currency code");
		feeType.setDefaultValue(1000.0);
		feeType.setDescription("description");
		fee.setFeeType(feeType);

		fee.setLegalDate(new Date());
		application.getFees().add(fee);

		// List PaymentFee
		application.setPayments(new ArrayList<PaymentFee>());

		PaymentFee payment = new PaymentFee();

		PersonName name = new PersonName();
		name.setFirstName("firstName");
		name.setLastName("lastName");
		name.setMiddleName("middleName");

		PersonRole personRole = new PersonRole();
		personRole.setName(name);

		personRole.setAddresses(new ArrayList<Address>());
		personRole.getAddresses().add(new Address());
		personRole.getAddresses().get(0).setCity("London Address");
		personRole.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
		personRole.getAddresses().get(0).setState("Attica Address");
		personRole.getAddresses().get(0).setCountry("GR");
		personRole.getAddresses().get(0).setStreetNumber("36 B");

		personRole.setUrls(new ArrayList<String>());
		personRole.getUrls().add("some url");

		personRole.setEmails(new ArrayList<Email>());
		personRole.getEmails().add(new Email());
		personRole.getEmails().get(0)
				.setEmailAddress("test@oami.gr");
		personRole.getEmails().add(new Email());
		personRole.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		personRole.setPhones(new ArrayList<Phone>());
		personRole.getPhones().add(new Phone());
		personRole.getPhones().get(0).setNumber("123456789");

		payment.setPayer(personRole);

		eu.ohim.sp.core.domain.payment.Account account = new eu.ohim.sp.core.domain.payment.Account();
		account.setAccountIdentifier("I23423423");
		account.setAccountKind("internal");

		payment.setKind(PaymentKind.CURRENT_ACCOUNT);
		payment.setAccount(account);

		payment.setReference("paymenrtReference");
		payment.setStatus(PaymentStatusCode.BANK_TRANSFER_TO_FOLLOW);

		payment.setBankTransfer(new BankTransfer());
		payment.getBankTransfer().setBankDestinationAccount(
				"bankDestinationAccount");
		payment.getBankTransfer().setBankTransferDate(new Date());
		payment.getBankTransfer().setBankTransferIdentifier(
				"bankTransferIdentifier");
		payment.getBankTransfer().setOriginBankName("originBankName");
		
		List <MatchedFee> matchedFees = new ArrayList<MatchedFee>();
		MatchedFee matchedFee = new MatchedFee();
		matchedFee.setFee(fee);
		matchedFees.add(matchedFee);
		
		Fee feeSecond = new Fee();
		feeSecond.setAmount(10.0);

		FeeType feeTypeSecond = new FeeType();
		feeTypeSecond.setNameKey("Collective Fee");
		feeTypeSecond.setCode("code 2");
		feeTypeSecond.setCurrencyCode("currency code 2");
		feeTypeSecond.setDefaultValue(12.0);
		feeTypeSecond.setDescription("description 2");
		feeSecond.setFeeType(feeTypeSecond);

		feeSecond.setLegalDate(new Date());
		application.getFees().add(feeSecond);
		
		MatchedFee matchedFeeSecond = new MatchedFee();
		matchedFeeSecond.setFee(feeSecond);
		matchedFees.add(matchedFeeSecond);
		payment.setFees(matchedFees);
		payment.setIdentifier("mercadona");
		application.getPayments().add(payment);

		// List AttachedDocument
		Document document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");
		document.setFileFormat("DOC");

		AttachedDocument attachedDocument = new AttachedDocument();
		attachedDocument.setDocument(document);
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		application.setDocuments(new ArrayList<AttachedDocument>());
		application.getDocuments().add(attachedDocument);

		// List Signatory
		Signatory signatory = new Signatory();
		signatory.setDate(new Date());
		signatory.setName("Christos");
		signatory.setPlace("here");
		signatory.setId(1231);
		signatory.setCapacity(PersonRoleKind.APPLICANT);

        signatory.setCapacityText("text");
        signatory.setAssociatedText("associatedText");
        signatory.setEmail("email@sth.com");
        signatory.setAddress(new Address());

        signatory.getAddress().setCity("London Address");
        signatory.getAddress().setStreet("Agias Lauras 36 Address");
        signatory.getAddress().setState("Attica Address");
        signatory.getAddress().setCountry("GR");
        signatory.getAddress().setStreetNumber("36 B");

        signatory.setConfirmation(Boolean.TRUE);

        tradeMark.setSignatures(new ArrayList<Signatory>());
		tradeMark.getSignatures().add(signatory);


		application.setSignatures(new ArrayList<Signatory>());
		application.getSignatures().add(signatory);

		// List warning messages
		application.setWarningMessages(new ArrayList<String>());
		application.getWarningMessages().add("some warning message");

		application.setUser("some user");

		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		dozerBeanMapper.setMappingFiles(myMappingFiles);

		TradeMarkServicesApplication dest = dozerBeanMapper.map(application,
				TradeMarkServicesApplication.class);


        assertEquals(application.getApplicationFilingNumber(),
				dest.getApplicationFilingNumber());
		assertEquals(application.getApplicationFilingDate(),
				dest.getApplicationFilingDate());
		assertEquals(application.getApplicationReference(),
				dest.getApplicationReference());
		assertEquals(application.getApplicationDate(),
				dest.getApplicationDate());
		assertEquals(application.getApplicationNumber(),
				dest.getApplicationNumber());
		assertEquals(application.getApplicationLanguage(), dest
				.getApplicationLanguage().getValue());
		assertEquals(application.getReceivingOffice(), dest
				.getReceivingOffice().value());
		assertEquals(application.getComment(), dest.getComment());

		// TradeMarks
		assertEquals(application.getTradeMarks().get(0).getApplicationNumber(),
				dest.getTradeMarkDetails().getTradeMark().get(0)
						.getApplicationNumber());
		assertEquals(application.getTradeMarks().get(0).getApplicants().get(0)
				.getName().getFirstName(), dest.getTradeMarkDetails()
				.getTradeMark().get(0).getApplicants().getApplicant().get(0)
				.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().getFirstName());
		assertEquals(application.getTradeMarks().get(0).getApplicants().get(0)
				.getEmails().get(0).getEmailAddress(), dest
				.getTradeMarkDetails().getTradeMark().get(0).getApplicants()
				.getApplicant().get(0).getAddressBook()
				.getContactInformationDetails().getEmail().get(0));
		assertEquals(application.getTradeMarks().get(0).getRepresentatives()
				.get(0).getPhones().get(0).getNumber(), dest
				.getTradeMarkDetails().getTradeMark().get(0)
				.getRepresentatives().getRepresentative().get(0)
				.getAddressBook().getContactInformationDetails().getPhone()
				.get(0).getValue());
		assertEquals(
				application.getTradeMarks().get(0).getExhibitionPriorities()
						.get(0).getExhibition().getCity(),
				dest.getTradeMarkDetails().getTradeMark().get(0)
						.getExhibitionPriorities().getExhibitionPriority()
						.get(0).getCity());

		// Applicants
		assertEquals(application.getApplicants().get(0).getLegalForm(), dest
				.getApplicantDetails().getApplicant().get(0).getLegalForm());
		assertEquals(application.getApplicants().get(0).getNationality(), dest
				.getApplicantDetails().getApplicant().get(0).getNationality());
		assertEquals(application.getApplicants().get(0).getDomicileCountry(),
				dest.getApplicantDetails().getApplicant().get(0)
						.getDomicileCountry().value());
		assertEquals(application.getApplicants().get(0).getDomicileLocality(),
				dest.getApplicantDetails().getApplicant().get(0)
						.getDomicileLocality());

		// Representatives
		assertEquals(application.getRepresentatives().get(0)
				.getIncorporationState(), dest.getRepresentativeDetails()
				.getRepresentative().get(0).getIncorporationState());
		assertEquals(application.getRepresentatives().get(0).getAddresses()
				.get(0).getCity(), dest.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getCity());
		assertEquals(application.getRepresentatives().get(0).getAddresses()
				.get(0).getCountry(), dest.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getCountry().value());
		assertEquals(application.getRepresentatives().get(0).getAddresses()
				.get(0).getState(), dest.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getState());
		assertEquals(application.getRepresentatives().get(0).getAddresses()
				.get(0).getStreet(), dest.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getStreet());
		assertEquals(application.getRepresentatives().get(0).getAddresses()
				.get(0).getStreetNumber(), dest.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getStreetNumber());

		// Holders
		assertEquals(application.getHolders().get(0).getDomicileCountry(), dest
				.getHolderDetails().getPreviousHolder().get(0)
				.getDomicileCountry().value());
		assertEquals(application.getHolders().get(0).getName().getFirstName(),
				dest.getHolderDetails().getPreviousHolder().get(0)
						.getAddressBook().getFormattedNameAddress().getName()
						.getFormattedName().getFirstName());
		assertEquals(application.getHolders().get(0).getEmails().get(0)
				.getEmailAddress(), dest.getHolderDetails().getPreviousHolder()
				.get(0).getAddressBook().getContactInformationDetails()
				.getEmail().get(0));
		assertEquals(application.getHolders().get(0).getPhones().get(0)
				.getNumber(), dest.getHolderDetails().getPreviousHolder()
				.get(0).getAddressBook().getContactInformationDetails()
				.getPhone().get(0).getValue());

		// Assignees
		assertEquals(application.getAssignees().get(0).getDomicileLocality(),
				dest.getHolderDetails().getNewHolder().get(0)
						.getDomicileLocality());
		assertEquals(application.getAssignees().get(0).getUrls().get(0), dest
				.getHolderDetails().getNewHolder().get(0).getAddressBook()
				.getContactInformationDetails().getURL().get(0));
		assertEquals(application.getAssignees().get(0).getName().getLastName(),
				dest.getHolderDetails().getNewHolder().get(0).getAddressBook()
						.getFormattedNameAddress().getName().getFormattedName()
						.getLastName());

		// Payments
		assertEquals(application.getPayments().get(0).getBankTransfer()
				.getBankTransferDate(), dest.getPaymentDetails().getPayment()
				.get(0).getPaymentMethod().getBankTransfer()
				.getBankTransferDate());

		// Documents
		assertEquals(application.getDocuments().get(0).getDocument().getName(),
				dest.getDocumentIncludedDetails().getDocumentIncluded().get(0)
						.getName());
		assertEquals(application.getDocuments().get(0).getDocumentKind(), dest
				.getDocumentIncludedDetails().getDocumentIncluded().get(0)
				.getKind().value());
		assertEquals(application.getDocuments().get(0).getDocument()
				.getFileFormat(), dest.getDocumentIncludedDetails()
				.getDocumentIncluded().get(0).getFileFormat());

		// Signatures
		assertEquals(application.getSignatures().get(0).getDate(), dest
				.getSignatoryDetails().getSignatory().get(0).getDate());
		assertEquals(application.getSignatures().get(0).getName(), dest
				.getSignatoryDetails().getSignatory().get(0).getName()
				.getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).getValue());
		assertEquals(application.getSignatures().get(0).getPlace(), dest
				.getSignatoryDetails().getSignatory().get(0).getPlace());
		assertEquals(application.getSignatures().get(0).getCapacity().value(),
				dest.getSignatoryDetails().getSignatory().get(0).getCapacity());

		// Warning messages
		assertEquals(application.getWarningMessages().get(0), dest
				.getWarningMessages().get(0));
	}

	@Test
	public void testConvertToClassOfTMeServiceApplicationObject() {

		TradeMarkServicesApplication application = new TradeMarkServicesApplication();

		application.setApplicationFilingNumber("filing number");
		application.setApplicationFilingDate(new Date());
		
		application.setComment("some comment");
		application.setApplicationLanguage(new ExtendedISOLanguageCode());
		application.getApplicationLanguage().setValue("EN");
		application.setApplicationNumber("some application number");
		application.setReceivingOffice(WIPOST3Code.AN);
		
		// Applicants
		eu.ohim.sp.filing.domain.tm.Applicant applicant = new eu.ohim.sp.filing.domain.tm.Applicant();
		applicant.setLegalForm("screw");
		applicant.setNationality("ES");
		applicant.setAddressBook(new AddressBook());
		applicant.getAddressBook().setFormattedNameAddress(new FormattedNameAddress());
		applicant.getAddressBook().getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		applicant.getAddressBook().getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());

		applicant.setDomicileCountry(ISOCountryCode.US);
		applicant.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		applicant.getComment().setValue("comment");
		
		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry(ISOCountryCode.GR);
		applicant.setIncorporationState("Attica");
		
		applicant.getCorrespondenceAddresses().add(new AddressBook());
		
		applicant.getCorrespondenceAddresses().get(0).setFormattedNameAddress(new FormattedNameAddress());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());
		
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("Limnoupoli");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("street");

		applicant.getCorrespondenceAddresses().get(0).setContactInformationDetails(new ContactInformationDetails());
		applicant.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whatever@oami.gr---------");
		applicant.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whoever@oami.gr");


		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.IS);
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36");
		
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36 Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36 B");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setPostcode("00313");
		
		applicant.getAddressBook().getFormattedNameAddress().setName(new Name());
		applicant.getAddressBook().getFormattedNameAddress().getName().setFormattedName(new FormattedName());
		applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setFirstName("Kostas");
		applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setMiddleName("P");
		applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setLastName("Papadopoulos");
		
		applicant.getAddressBook().setContactInformationDetails(new ContactInformationDetails());
		
		applicant.getAddressBook().getContactInformationDetails().getEmail().add("whatever@oami.gr");
		applicant.getAddressBook().getContactInformationDetails().getEmail().add("whoever@oami.gr");
		applicant.getAddressBook().getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.tm.Phone("3423423", eu.ohim.sp.filing.domain.tm.PhoneKind.OTHER));
		
		applicant.getAddressBook().getContactInformationDetails().getURL().add("some url");
		applicant.getAddressBook().getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.tm.Phone("3423423", eu.ohim.sp.filing.domain.tm.PhoneKind.OTHER));
		
		application.setApplicantDetails(new ApplicantDetails());
		application.getApplicantDetails().setApplicant(new ArrayList<eu.ohim.sp.filing.domain.tm.Applicant>());
		application.getApplicantDetails().getApplicant().add(applicant);
		
		// Representatives
		eu.ohim.sp.filing.domain.tm.Representative representative = new eu.ohim.sp.filing.domain.tm.Representative();
		representative.setLegalForm("screw");
		representative.setNationality("ES");
		representative.setAddressBook(new AddressBook());
		representative.getAddressBook().setFormattedNameAddress(new FormattedNameAddress());
		representative.getAddressBook().getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		representative.getAddressBook().getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());

		representative.setDomicileCountry(ISOCountryCode.US);
		representative.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		representative.getComment().setValue("comment");
		
		representative.setDomicileLocality("test");
		representative.setIncorporationCountry(ISOCountryCode.GR);
		representative.setIncorporationState("Attica");
	
		representative.setCorrespondenceAddresses(new ArrayList<AddressBook>());
		representative.getCorrespondenceAddresses().add(new AddressBook());
		representative.getCorrespondenceAddresses().get(0).setFormattedNameAddress(new FormattedNameAddress());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());
		
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("Limnoupoli");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("street");

		representative.getCorrespondenceAddresses().get(0).setContactInformationDetails(new eu.ohim.sp.filing.domain.tm.ContactInformationDetails());
		representative.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whatever@oami.gr---------");
		representative.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whoever@oami.gr");
		
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.IS);
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36");
		
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36 Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36 B");
		
		representative.getAddressBook().getFormattedNameAddress().setName(new eu.ohim.sp.filing.domain.tm.Name());
		representative.getAddressBook().getFormattedNameAddress().getName().setFormattedName(new FormattedName());
		representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setFirstName("George");
		representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setMiddleName("P");
		representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setLastName("Papadopoulos");
		
		representative.getAddressBook().setContactInformationDetails(new eu.ohim.sp.filing.domain.tm.ContactInformationDetails());
		
		representative.getAddressBook().getContactInformationDetails().getEmail().add("taska.sta.tramou.mou@oami.gr");
		representative.getAddressBook().getContactInformationDetails().getEmail().add("tesr.kapelo@oami.gr");
		representative.getAddressBook().getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.tm.Phone("4536546", eu.ohim.sp.filing.domain.tm.PhoneKind.UNDEFINED));
	
		representative.getAddressBook().getContactInformationDetails().getURL().add("some url");
		representative.getAddressBook().getContactInformationDetails().getURL().add("some url 2");
		
		application.setRepresentativeDetails(new RepresentativeDetails());
		application.getRepresentativeDetails().setRepresentative(new ArrayList<eu.ohim.sp.filing.domain.tm.Representative>());
		application.getRepresentativeDetails().getRepresentative().add(representative);

		// Holders
		application.setHolderDetails(new HolderDetailsType());
		application.getHolderDetails().setNewHolder(application.getApplicantDetails().getApplicant());
		
		// Assignees
		application.getHolderDetails().setPreviousHolder(application.getApplicantDetails().getApplicant());
		
		// TradeMarks
		eu.ohim.sp.filing.domain.tm.TradeMark tradeMark2 = fillFspTradeMark();

		TradeMarkDetails tradeMarkDetails = new TradeMarkDetails();
		tradeMarkDetails
				.setTradeMark(new ArrayList<eu.ohim.sp.filing.domain.tm.TradeMark>());
		tradeMarkDetails.getTradeMark().add(tradeMark2);
		application.setTradeMarkDetails(tradeMarkDetails);

		// Signatory
		SignatoryDetails signatoryDetails = new SignatoryDetails();
		signatoryDetails
				.setSignatory(new ArrayList<eu.ohim.sp.filing.domain.tm.Signatory>());
		signatoryDetails.getSignatory().add(new eu.ohim.sp.filing.domain.tm.Signatory());
		signatoryDetails.getSignatory().get(0).setCapacity("Applicant");
		signatoryDetails.getSignatory().get(0).setDate(new Date());
		signatoryDetails.getSignatory().get(0)
				.setConfirmation(Boolean.FALSE);
		signatoryDetails.getSignatory().get(0).setName(new Name());
		signatoryDetails.getSignatory().get(0).getName()
				.setFreeFormatName(new FreeFormatNameType());
		signatoryDetails.getSignatory().get(0).getName().getFreeFormatName()
			.setFreeFormatNameDetails(new FreeFormatNameDetailsType());
		signatoryDetails.getSignatory().get(0).getName().getFreeFormatName()
			.getFreeFormatNameDetails().setFreeFormatNameLine(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		signatoryDetails.getSignatory().get(0).getName().getFreeFormatName()
			.getFreeFormatNameDetails().getFreeFormatNameLine().add(new eu.ohim.sp.filing.domain.tm.Text("name", "en"));
		application.setSignatoryDetails(signatoryDetails);

		application.setPaymentDetails(new PaymentDetails());
		application.getPaymentDetails().setPayment(new ArrayList<Payment>());
		application.getPaymentDetails().getPayment().add(new Payment());

		// Payments
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setBankTransfer(new eu.ohim.sp.filing.domain.tm.BankTransfer());
		paymentMethod.getBankTransfer().setBankDestinationAccount(
				"bank destination");
		paymentMethod.getBankTransfer().setBankTransferDate(new Date());
		paymentMethod.getBankTransfer().setBankTransferIdentifier(
				"bank transfer identifier");
		paymentMethod.getBankTransfer().setOriginBankName("origin bank name");

		application.getPaymentDetails().getPayment().get(0)
				.setPaymentMethod(paymentMethod);

		// Documents
		eu.ohim.sp.filing.domain.tm.Document document = new eu.ohim.sp.filing.domain.tm.Document();
		document.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		document.getComment().setLanguage("EN");
		document.getComment().setValue("some value");
		document.setFileFormat("DOC");
		document.setFileName("fileName");
		document.setKind(eu.ohim.sp.filing.domain.tm.DocumentKind.APPLICATION_RECEIPT);
		document.setLanguage(ISOLanguageCode.GA);
		document.setName("some document name");
		document.setStatus(DocumentStatusCode.ATTACHED);
		document.setUri(new URI("some document uri"));
		
		application.setDocumentIncludedDetails(new DocumentIncludedDetails());
		application.getDocumentIncludedDetails().setDocumentIncluded(new ArrayList<eu.ohim.sp.filing.domain.tm.Document>());
		application.getDocumentIncludedDetails().getDocumentIncluded().add(document);
		
		// Warning messages
		application.setWarningMessages(new ArrayList<String>());
		application.getWarningMessages().add("some warning message 1");
		application.getWarningMessages().add("some warning message 2");
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		dozerBeanMapper.setMappingFiles(myMappingFiles);

		TMeServiceApplication dest = dozerBeanMapper.map(application,
				TMeServiceApplication.class);

		assertEquals(dest.getApplicationFilingNumber(),
				application.getApplicationFilingNumber());
		assertEquals(dest.getApplicationFilingDate(),
				application.getApplicationFilingDate());
		assertEquals(dest.getApplicationReference(),
				application.getApplicationReference());
		assertEquals(dest.getApplicationDate(),
				application.getApplicationDate());
		assertEquals(dest.getApplicationNumber(),
				application.getApplicationNumber());
		assertEquals(dest.getApplicationLanguage(), application
				.getApplicationLanguage().getValue());
		assertEquals(dest.getReceivingOffice(), application
				.getReceivingOffice().value());
		assertEquals(dest.getComment(), application.getComment());

		// TradeMarks
		assertEquals(dest.getTradeMarks().get(0).getApplicationNumber(),
				application.getTradeMarkDetails().getTradeMark().get(0)
						.getApplicationNumber());
		assertEquals(dest.getTradeMarks().get(0).getApplicants().get(0)
				.getName().getFirstName(), application.getTradeMarkDetails()
				.getTradeMark().get(0).getApplicants().getApplicant().get(0)
				.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().getFirstName());
		assertEquals(dest.getTradeMarks().get(0).getApplicants().get(0)
				.getEmails().get(0).getEmailAddress(), application
				.getTradeMarkDetails().getTradeMark().get(0).getApplicants()
				.getApplicant().get(0).getAddressBook()
				.getContactInformationDetails().getEmail().get(0));
		assertEquals(dest.getTradeMarks().get(0).getRepresentatives().get(0)
				.getPhones().get(0).getNumber(), application
				.getTradeMarkDetails().getTradeMark().get(0)
				.getRepresentatives().getRepresentative().get(0)
				.getAddressBook().getContactInformationDetails().getPhone()
				.get(0).getValue());
		assertEquals(
				dest.getTradeMarks().get(0).getExhibitionPriorities().get(0)
						.getExhibition().getCity(),
				application.getTradeMarkDetails().getTradeMark().get(0)
						.getExhibitionPriorities().getExhibitionPriority()
						.get(0).getCity());

		// Applicants
		assertEquals(dest.getApplicants().get(0).getLegalForm(), application
				.getApplicantDetails().getApplicant().get(0).getLegalForm());
		assertEquals(dest.getApplicants().get(0).getNationality(), application
				.getApplicantDetails().getApplicant().get(0).getNationality());
		assertEquals(dest.getApplicants().get(0).getDomicileCountry(),
				application.getApplicantDetails().getApplicant().get(0)
						.getDomicileCountry().value());
		assertEquals(dest.getApplicants().get(0).getDomicileLocality(),
				application.getApplicantDetails().getApplicant().get(0)
						.getDomicileLocality());

		// Representatives
		assertEquals(
				dest.getRepresentatives().get(0).getIncorporationState(),
				application.getRepresentativeDetails().getRepresentative()
						.get(0).getIncorporationState());
		assertEquals(dest.getRepresentatives().get(0).getAddresses().get(0)
				.getCity(), application.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getCity());
		assertEquals(dest.getRepresentatives().get(0).getAddresses().get(0)
				.getCountry(), application.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getCountry().value());
		assertEquals(dest.getRepresentatives().get(0).getAddresses().get(0)
				.getState(), application.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getState());
		assertEquals(dest.getRepresentatives().get(0).getAddresses().get(0)
				.getStreet(), application.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getStreet());
		assertEquals(dest.getRepresentatives().get(0).getAddresses().get(0)
				.getStreetNumber(), application.getRepresentativeDetails()
				.getRepresentative().get(0).getAddressBook()
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.getStreetNumber());

		// Holders
		assertEquals(dest.getHolders().get(0).getDomicileCountry(), application
				.getHolderDetails().getPreviousHolder().get(0)
				.getDomicileCountry().value());
		assertEquals(dest.getHolders().get(0).getName().getFirstName(),
				application.getHolderDetails().getPreviousHolder().get(0)
						.getAddressBook().getFormattedNameAddress().getName()
						.getFormattedName().getFirstName());
		assertEquals(dest.getHolders().get(0).getEmails().get(0)
				.getEmailAddress(), application.getHolderDetails()
				.getPreviousHolder().get(0).getAddressBook()
				.getContactInformationDetails().getEmail().get(0));
		assertEquals(dest.getHolders().get(0).getPhones().get(0).getNumber(),
				application.getHolderDetails().getPreviousHolder().get(0)
						.getAddressBook().getContactInformationDetails()
						.getPhone().get(0).getValue());

		// Assignees
		assertEquals(dest.getAssignees().get(0).getDomicileLocality(),
				application.getHolderDetails().getNewHolder().get(0)
						.getDomicileLocality());
		assertEquals(dest.getAssignees().get(0).getUrls().get(0), application
				.getHolderDetails().getNewHolder().get(0).getAddressBook()
				.getContactInformationDetails().getURL().get(0));
		assertEquals(dest.getAssignees().get(0).getName().getLastName(),
				application.getHolderDetails().getNewHolder().get(0)
						.getAddressBook().getFormattedNameAddress().getName()
						.getFormattedName().getLastName());

		// Payments
		assertEquals(dest.getPayments().get(0).getBankTransfer()
				.getBankTransferDate(), application.getPaymentDetails()
				.getPayment().get(0).getPaymentMethod().getBankTransfer()
				.getBankTransferDate());

		// Documents
		assertEquals(dest.getDocuments().get(0).getDocument().getName(),
				application.getDocumentIncludedDetails().getDocumentIncluded()
						.get(0).getName());
		assertEquals(dest.getDocuments().get(0).getDocumentKind(), application
				.getDocumentIncludedDetails().getDocumentIncluded().get(0)
				.getKind().value());
		assertEquals(dest.getDocuments().get(0).getDocument().getFileFormat(),
				application.getDocumentIncludedDetails().getDocumentIncluded()
						.get(0).getFileFormat());

		// Signatures
		assertEquals(dest.getSignatures().get(0).getDate(), application
				.getSignatoryDetails().getSignatory().get(0).getDate());
		assertEquals(dest.getSignatures().get(0).getName(), application
				.getSignatoryDetails().getSignatory().get(0).getName()
				.getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).getValue());
		assertEquals(dest.getSignatures().get(0).getPlace(), application
				.getSignatoryDetails().getSignatory().get(0).getPlace());
		assertEquals(dest.getSignatures().get(0).getCapacity().value(),
				application.getSignatoryDetails().getSignatory().get(0)
						.getCapacity());

		// Warning messages
		assertEquals(dest.getWarningMessages().get(0), application
				.getWarningMessages().get(0));
		assertEquals(dest.getWarningMessages().get(1), application
				.getWarningMessages().get(1));
	}

	@SuppressWarnings("deprecation")
	private eu.ohim.sp.core.domain.trademark.LimitedTradeMark fillCoreTradeMark() {

        LimitedTradeMark tradeMark = new LimitedTradeMark();
		tradeMark.setApplicationLanguage("en");
		tradeMark.setSecondLanguage("ES");
		tradeMark.setApplicationReference("test 123");
		Date date = new Date();

		tradeMark.setExpirationDate(date);
		tradeMark.setFilingDate(date);
		tradeMark.setFilingNumber("test");
		tradeMark.setApplicationNumber("test");
		tradeMark.setReceivingOffice("EM");
		tradeMark.setRegistrationOffice("EM");
		tradeMark.setRegistrationDate(new Date());
		tradeMark.setRegistrationNumber("R2342334567567");
		tradeMark.setTerminationDate(new Date());
		tradeMark.setComment("comment FSP");
		tradeMark.setPriorityClaimLaterIndicator(true);
		tradeMark.setExhibitionPriorityClaimLaterIndicator(true);
		tradeMark.setApplicationDate(new Date());
		tradeMark.setMarkKind(MarkFeature.WORD);
        tradeMark.setMarkRightKind(MarkKind.INDIVIDUAL);

        // Priorities
		Priority priority = new Priority();
		priority.setFilingDate(new Date());
		priority.setFilingNumber("fefef");
		priority.setFilingOffice("EM");

		Document document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");

		AttachedDocument attachedDocument = new AttachedDocument();
		attachedDocument.setDocument(document);
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
		priority.getAttachedDocuments().add(attachedDocument);
		priority.setPartialIndicator(true);

		priority.setPartialGoodsServices(new ArrayList<ClassDescription>());
		priority.getPartialGoodsServices().add(new ClassDescription());
		priority.getPartialGoodsServices().get(0).setLanguage("en");
		priority.getPartialGoodsServices().get(0).setClassNumber("42");
		priority.getPartialGoodsServices().get(0)
				.setClassificationTerms(new ArrayList<ClassificationTerm>());
		priority.getPartialGoodsServices().get(0).getClassificationTerms()
				.add(new ClassificationTerm());
		priority.getPartialGoodsServices().get(0).getClassificationTerms()
				.get(0).setTermText("whatever");

		tradeMark.setPriorities(new ArrayList<Priority>());
		tradeMark.getPriorities().add(priority);

		// Seniorities
		Seniority seniority = new Seniority();
		seniority.setFilingNumber("application number");
		seniority.setOffice("EM");
		seniority.setFilingDate(new Date());
		seniority.setRegistrationDate(new Date());
		seniority.setRegistrationNumber("R21231");
		seniority
				.setKind(eu.ohim.sp.core.domain.claim.SeniorityKind.INTERNATIONAL_TRADE_MARK);
		seniority
				.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);

		document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");

		attachedDocument = new AttachedDocument();
		attachedDocument.setDocumentKind(DocumentKind.SENIORITY_CERTIFICATE
				.value());
		attachedDocument.setDocument(document);
		seniority.setAttachedDocuments(new ArrayList<AttachedDocument>());
		seniority.getAttachedDocuments().add(attachedDocument);

		seniority.setPartialIndicator(true);
		seniority.setPartialGoodsServices(new ArrayList<ClassDescription>());
		seniority.getPartialGoodsServices().add(new ClassDescription());
		seniority.getPartialGoodsServices().get(0).setLanguage("en");
		seniority.getPartialGoodsServices().get(0).setClassNumber("42");
		seniority.getPartialGoodsServices().get(0)
				.setClassificationTerms(new ArrayList<ClassificationTerm>());
		seniority.getPartialGoodsServices().get(0).getClassificationTerms()
				.add(new ClassificationTerm());
		seniority.getPartialGoodsServices().get(0).getClassificationTerms()
				.get(0).setTermText("whatever");

		tradeMark.setSeniorities(new ArrayList<Seniority>());
		tradeMark.getSeniorities().add(seniority);

		// ExhibitionPriorities
		ExhibitionPriority exhibitionPriority = new ExhibitionPriority();
		exhibitionPriority.setExhibition(new Exhibition());
		exhibitionPriority.getExhibition().setCity("city");
		exhibitionPriority.getExhibition().setCountry("FR");
		exhibitionPriority.setDate(new Date());
		exhibitionPriority.setFirstDisplayDate(new Date());
		exhibitionPriority.getExhibition().setName("name");

		document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");

		attachedDocument = new AttachedDocument();
		attachedDocument.setDocument(document);
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		exhibitionPriority
				.setAttachedDocuments(new ArrayList<AttachedDocument>());
		exhibitionPriority.getAttachedDocuments().add(attachedDocument);

		tradeMark.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
		tradeMark.getExhibitionPriorities().add(exhibitionPriority);

		// TransformationPriorities
		TransformationPriority transformationPriority = new TransformationPriority();
		transformationPriority.setCancellationDate(new Date());
		transformationPriority.setPriorityDate(new Date());
		transformationPriority.setRegistrationDate(new Date());
		transformationPriority.setRegistrationNumber("R21312313");

		document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");

		attachedDocument = new AttachedDocument();
		attachedDocument.setDocument(document);
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		transformationPriority
				.setAttachedDocuments(new ArrayList<AttachedDocument>());
		transformationPriority.getAttachedDocuments().add(attachedDocument);

		tradeMark
				.setTransformationPriorities(new ArrayList<TransformationPriority>());
		tradeMark.getTransformationPriorities().add(transformationPriority);

		// WordSpecifications
		WordSpecification wordSpecification = new WordSpecification();
		wordSpecification.setCharacterSet("en");
		wordSpecification.setTranscriptionDetails("test whatever");
		wordSpecification.setTransliterationDetails("ere");
		wordSpecification.setWordElements("word element");

		tradeMark.setWordSpecifications(new ArrayList<WordSpecification>());
		tradeMark.getWordSpecifications().add(wordSpecification);

		wordSpecification = new WordSpecification();
		wordSpecification.setCharacterSet("en");
		wordSpecification.setTranscriptionDetails("test whatever 2");
		wordSpecification.setTransliterationDetails("ere");
		wordSpecification.setWordElements("word element");

		tradeMark.getWordSpecifications().add(wordSpecification);

		// Applicants
		Applicant applicant = new Applicant();

        applicant.setIdentifiers(new ArrayList<PersonIdentifier>());
        applicant.getIdentifiers().add(new PersonIdentifier());
        applicant.getIdentifiers().get(0).setIdentifierKindCode("VAT Number");
        applicant.getIdentifiers().get(0).setValue("value");

        applicant.setLegalForm("screw");
		applicant.setNationality("ES");
		applicant.setDomicileCountry("GR");

		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry("GR");
		applicant.setIncorporationState("Attica");

		applicant.setCorrespondenceAddresses(new ArrayList<Address>());
		applicant.getCorrespondenceAddresses().add(new Address());

		applicant.setEmails(new ArrayList<Email>());
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(0)
				.setEmailAddress("test@oami.gr");
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		applicant.setPhones(new ArrayList<Phone>());
		applicant.getPhones().add(new Phone());
		applicant.getPhones().get(0).setNumber("2133542352");
		applicant.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

		applicant.getCorrespondenceAddresses().get(0).setCity("London");
		applicant.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras 36");
		applicant.getCorrespondenceAddresses().get(0).setState("Attica");
		applicant.getCorrespondenceAddresses().get(0).setCountry("GR");
		applicant.getCorrespondenceAddresses().get(0).setStreetNumber("36");
		applicant.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Papas");

		applicant.setAddresses(new ArrayList<Address>());

		applicant.getAddresses().add(new Address());
		applicant.getAddresses().get(0).setCity("London Address");
		applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
		applicant.getAddresses().get(0).setState("Attica Address");
		applicant.getAddresses().get(0).setCountry("GR");
		applicant.getAddresses().get(0).setStreetNumber("36 B");

		applicant.setUrls(new ArrayList<String>());
		applicant.getUrls().add("some url");

		applicant.setName(new PersonName());

		applicant.getName().setFirstName("Nikolas");
		applicant.getName().setMiddleName("O");
		applicant.getName().setLastName("Papadopoulos");

		applicant.getName().setSecondLastName("secondLastName");
		applicant.getName().setOrganizationName("organizationName");

		tradeMark.setApplicants(new ArrayList<Applicant>());
		tradeMark.getApplicants().add(applicant);

		// Signatures
		Signatory signatory = new Signatory();
		signatory.setDate(new Date());
		signatory.setName("Christos");
		signatory.setPlace("here");
		signatory.setId(1231);
		signatory.setCapacity(PersonRoleKind.APPLICANT);

		tradeMark.setSignatures(new ArrayList<Signatory>());
		tradeMark.getSignatures().add(signatory);

		// ImageSpecification
		ImageSpecification imageSpecification = new ImageSpecification();
		Image representation = new Image();
		representation.setFileName("filename");
		representation.setFileFormat("JPEG");
		representation.setUri("test URI");
		representation.setDocumentId("image id");
		imageSpecification.setRepresentation(representation);

		Colour colour = new Colour();
		colour.setFormat("HEX");
		colour.setValue("D213");
		imageSpecification.setColours(new ArrayList<Colour>());
		imageSpecification.getColours().add(colour);

		colour = new Colour();
		colour.setFormat("RGB");
		colour.setValue("Black");
		imageSpecification.getColours().add(colour);

		colour = new Colour();
		colour.setValue("Black");
		colour.setFormat("PANTONE");
		imageSpecification.getColours().add(colour);

		Text colourText = new Text();
		colourText.setLanguage("en");
		colourText.setValue("colour 1");
		imageSpecification.setColourClaimedText(new ArrayList<Text>());
		imageSpecification.getColourClaimedText().add(colourText);

		tradeMark.setImageSpecifications(new ArrayList<ImageSpecification>());
		tradeMark.getImageSpecifications().add(imageSpecification);

		// SoundSpecification
		SoundSpecification soundSpecification = new SoundSpecification();
		Sound commonSound = new Sound();
		commonSound.setFileFormat(SoundFileFormat.MP3.value());
		commonSound.setFileName("filename");
		commonSound.setUri("uri");
		commonSound.setDocumentId("sound id");
		soundSpecification.setDocument(commonSound);

		tradeMark.setSoundRepresentations(new ArrayList<SoundSpecification>());
		tradeMark.getSoundRepresentations().add(soundSpecification);

		// ClassDescription
		ClassDescription classDescription = new ClassDescription();
		classDescription.setFullClassCoverageIndicator(Boolean.FALSE);
		classDescription.setLanguage("en");
		classDescription.setClassNumber("32");
		classDescription
				.setClassificationTerms(new ArrayList<ClassificationTerm>());
		classDescription.setGoodsServicesDescription("descriptions");

		ClassificationTerm classificationTerm = new ClassificationTerm();
		classificationTerm.setTermText("Test");
		classDescription.getClassificationTerms().add(classificationTerm);

		tradeMark.setClassDescriptions(new ArrayList<ClassDescription>());
		tradeMark.getClassDescriptions().add(classDescription);

		// MarkDescriptions
		tradeMark.setMarkDescriptions(new ArrayList<MarkDescription>());
		MarkDescription markDescription = new MarkDescription();
		markDescription.setLanguage("en");
		markDescription.setValue("test papapra");
		tradeMark.getMarkDescriptions().add(markDescription);

		markDescription = new MarkDescription();
		markDescription.setLanguage("ES");
		markDescription.setValue("test papapra");

		tradeMark.getMarkDescriptions().add(markDescription);

		// MarkDisclaimers
		tradeMark.setMarkDisclaimers(new ArrayList<MarkDisclaimer>());
		MarkDisclaimer markDisclaimer = new MarkDisclaimer();
		markDisclaimer.setValue("value");
		markDisclaimer.setLanguage("en");
		tradeMark.getMarkDisclaimers().add(markDisclaimer);

		markDisclaimer = new MarkDisclaimer();
		markDisclaimer.setValue("value");
		markDisclaimer.setLanguage("es");

		tradeMark.getMarkDisclaimers().add(markDisclaimer);

		// Representatives
		Representative representative = new Representative();
		representative.setLegalForm("screw");
		representative.setNationality("ES");
		representative.setDomicileCountry("GR");

		representative.setDomicileLocality("test");
		representative.setIncorporationCountry("GR");
		representative.setIncorporationState("Attica");

		representative.setCorrespondenceAddresses(new ArrayList<Address>());
		representative.getCorrespondenceAddresses().add(new Address());

		representative.setEmails(new ArrayList<Email>());
		representative.getEmails().add(new Email());
		representative.getEmails().get(0)
				.setEmailAddress("test@oami.gr");
		representative.getEmails().add(new Email());
		representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		representative.setPhones(new ArrayList<Phone>());
		representative.getPhones().add(new Phone());
		representative.getPhones().get(0).setNumber("2133542352");
		representative.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

		representative.getCorrespondenceAddresses().get(0).setCity("London");
		representative.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0).setState("Attica");
		representative.getCorrespondenceAddresses().get(0).setCountry("GR");
		representative.getCorrespondenceAddresses().get(0)
				.setStreetNumber("36");
		representative.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Papas");

		representative.setAddresses(new ArrayList<Address>());

		representative.getAddresses().add(new Address());
		representative.getAddresses().get(0).setCity("London Address");
		representative.getAddresses().get(0)
				.setStreet("Agias Lauras 36 Address");
		representative.getAddresses().get(0).setState("Attica Address");
		representative.getAddresses().get(0).setCountry("GR");
		representative.getAddresses().get(0).setStreetNumber("36 B");

		representative.setUrls(new ArrayList<String>());
		representative.getUrls().add("some url");
		representative.getUrls().add("some url 2");

		representative.setName(new PersonName());

		representative.getName().setFirstName("Georgios");
		representative.getName().setLastName("Papadakis");

		tradeMark.setRepresentatives(new ArrayList<Representative>());
		tradeMark.getRepresentatives().add(representative);
		return tradeMark;
	}

	private eu.ohim.sp.filing.domain.tm.TradeMark fillFspTradeMark() {
		eu.ohim.sp.filing.domain.tm.TradeMark tradeMark2 = new eu.ohim.sp.filing.domain.tm.TradeMark();

		tradeMark2.setApplicationLanguage(new ExtendedISOLanguageCode());
		tradeMark2.getApplicationLanguage().setValue("en");
		tradeMark2.setSecondLanguage(new ExtendedISOLanguageCode());
		tradeMark2.getSecondLanguage().setValue("e1");
		tradeMark2.setApplicationReference("test 123");
		Date date = new Date();

		tradeMark2.setExpiryDate(date);
		tradeMark2.setApplicationDate(date);
		tradeMark2.setApplicationNumber("test");
		tradeMark2.setReceivingOffice(WIPOST3Code.EM);
		tradeMark2.setRegistrationOffice(WIPOST3Code.EM);
		tradeMark2.setRegistrationDate(date);
		tradeMark2.setRegistrationNumber("R23432123132");
		tradeMark2.setTerminationDate(date);
		tradeMark2.setComment("comment FSP");
		tradeMark2.setPriorityClaimLaterIndicator(true);
		tradeMark2.setExhibitionPriorityClaimLaterIndicator(true);
		tradeMark2.setMarkFeature(eu.ohim.sp.filing.domain.tm.MarkFeature.WORD);

		// Priorities
		PriorityDetails priorityDetails = new PriorityDetails();
		priorityDetails
				.setPriority(new ArrayList<eu.ohim.sp.filing.domain.tm.Priority>());

		eu.ohim.sp.filing.domain.tm.Priority priority = new eu.ohim.sp.filing.domain.tm.Priority();
		priority.setFilingDate(new Date());
		priority.setFilingNumber("fefe");
		priority.setFilingOffice(new ExtendedWIPOST3Code());
		priority.getFilingOffice().setValue("EM");

		eu.ohim.sp.filing.domain.tm.Document document = new eu.ohim.sp.filing.domain.tm.Document();
		document.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		document.getComment().setLanguage("en");
		document.getComment().setValue("new comment");
		document.setFileName("filename");
		document.setLanguage(ISOLanguageCode.EN);
		document.setFileFormat("PDF");

		priority.setDocuments(new ArrayList<eu.ohim.sp.filing.domain.tm.Document>());
		priority.getDocuments().add(document);

		priority.setPartialIndicator(true);

		GoodsServices goodsServices = new GoodsServices();
		goodsServices.setClassDescriptionDetails(new ClassDescriptionDetails());
		goodsServices.getClassDescriptionDetails().setClassDescription(
				new ArrayList<eu.ohim.sp.filing.domain.tm.ClassDescription>());
		goodsServices.getClassDescriptionDetails().getClassDescription()
				.add(new eu.ohim.sp.filing.domain.tm.ClassDescription());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.setClassNumber(new ClassNumber());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassNumber().setValue("42");
		goodsServices
				.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.setClassificationTerms(
						new ArrayList<ClassificationTermDetails>());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().add(new ClassificationTermDetails());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0)
				.setLanguage(ISOLanguageCode.EN);
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0)
				.setClassificationTerm(new ArrayList<ClassificationTermType>());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0).getClassificationTerm()
				.add(new ClassificationTermType());
		goodsServices
				.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.getClassificationTerms()
				.get(0)
				.getClassificationTerm()
				.get(0)
				.setClassificationTermText(
						new eu.ohim.sp.filing.domain.tm.Text("text", "en"));

		priority.setPartialGoodsServices(goodsServices);
		priorityDetails.getPriority().add(priority);

		// Seniorities
		eu.ohim.sp.filing.domain.tm.Seniority seniority = new eu.ohim.sp.filing.domain.tm.Seniority();
		seniority.setFilingDate(date);
		seniority.setApplicationNumber("application number");
		seniority.setOffice(WIPOST3Code.EM);
		seniority.setRegistrationDate(date);
		seniority.setRegistrationNumber("R21231");
		seniority.setKind(new SeniorityKind());
		seniority.getKind().setValue("International trade mark");
		seniority
				.setInternationalTradeMarkCode(eu.ohim.sp.filing.domain.tm.InternationalTradeMarkCode.MADRID);

		document = new eu.ohim.sp.filing.domain.tm.Document();
		document.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		document.getComment().setLanguage("en");
		document.getComment().setValue("comment");
		document.setFileName("filename");
		document.setLanguage(ISOLanguageCode.EN);
		document.setFileFormat("PDF");
		document.setKind(eu.ohim.sp.filing.domain.tm.DocumentKind.SENIORITY_CERTIFICATE);

		seniority.setDocuments(new ArrayList<eu.ohim.sp.filing.domain.tm.Document>());
		seniority.getDocuments().add(document);

		seniority.setPartialIndicator(true);

		goodsServices = new GoodsServices();
		goodsServices.setClassDescriptionDetails(new ClassDescriptionDetails());
		goodsServices.getClassDescriptionDetails().setClassDescription(
				new ArrayList<eu.ohim.sp.filing.domain.tm.ClassDescription>());
		goodsServices.getClassDescriptionDetails().getClassDescription()
				.add(new eu.ohim.sp.filing.domain.tm.ClassDescription());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.setClassNumber(new ClassNumber());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassNumber().setValue("42");
		goodsServices
				.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.setClassificationTerms(
						new ArrayList<ClassificationTermDetails>());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().add(new ClassificationTermDetails());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0)
				.setLanguage(ISOLanguageCode.EN);
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0)
				.setClassificationTerm(new ArrayList<ClassificationTermType>());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0).getClassificationTerm()
				.add(new ClassificationTermType());
		goodsServices
				.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.getClassificationTerms()
				.get(0)
				.getClassificationTerm()
				.get(0)
				.setClassificationTermText(
						new eu.ohim.sp.filing.domain.tm.Text("text", "en"));

		seniority.setPartialGoodsServices(goodsServices);

		SeniorityDetails seniorityDetails = new SeniorityDetails();
		seniorityDetails
				.setSeniority(new ArrayList<eu.ohim.sp.filing.domain.tm.Seniority>());
		seniorityDetails.getSeniority().add(seniority);

		tradeMark2.setSeniorities(seniorityDetails);

		tradeMark2.setPriorities(priorityDetails);

		// ExhibitionPriorities
		eu.ohim.sp.filing.domain.tm.ExhibitionPriority exhibition = new eu.ohim.sp.filing.domain.tm.ExhibitionPriority();
		exhibition.setCity("city");
		exhibition.setCountry(ISOCountryCode.FR);
		exhibition.setDate(date);

		exhibition.setDocuments(new ArrayList<eu.ohim.sp.filing.domain.tm.Document>());
		document = new eu.ohim.sp.filing.domain.tm.Document();
		document.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		document.getComment().setLanguage("en");
		document.getComment().setValue("comment");
		document.setFileName("filename");
		document.setLanguage(ISOLanguageCode.EN);
		document.setFileFormat("PDF");
		document.setKind(eu.ohim.sp.filing.domain.tm.DocumentKind.PRIORITY_CERTIFICATE);
		exhibition.getDocuments().add(document);

		exhibition.setExhibitionStatusDate(date);
		exhibition.setFirstDisplayDate(date);
		exhibition.setName("name");

		exhibition.setExhibitionStatusCode(ExhibitionStatusCodeType.ACCEPTED);

		ExhibitionPriorityDetails exhibitionPriorityDetails = new ExhibitionPriorityDetails();
		exhibitionPriorityDetails
				.setExhibitionPriority(new ArrayList<eu.ohim.sp.filing.domain.tm.ExhibitionPriority>());
		exhibitionPriorityDetails.getExhibitionPriority().add(exhibition);

		tradeMark2.setExhibitionPriorities(exhibitionPriorityDetails);

		// TransformationPriorities
		eu.ohim.sp.filing.domain.tm.TransformationPriority transformation = new eu.ohim.sp.filing.domain.tm.TransformationPriority();
		transformation.setCancellationDate(new Date());
		transformation.setPriorityDate(new Date());
		transformation.setRegistrationDate(new Date());
		transformation.setRegistrationNumber("R21321");

		TransformationPriorityDetails transformationPriorityDetails = new TransformationPriorityDetails();
		transformationPriorityDetails
				.setTransformationPriority(new ArrayList<eu.ohim.sp.filing.domain.tm.TransformationPriority>());
		transformationPriorityDetails.getTransformationPriority().add(
				transformation);

		tradeMark2.setTransformationPriorities(transformationPriorityDetails);

		// WordSpecifications
		WordMarkSpecification wordSpecification = new WordMarkSpecification();
		wordSpecification.setMarkTransliteration(new MarkTransliterationType());
		wordSpecification.getMarkTransliteration().setTransliterationKind(
				"kind");
		wordSpecification.getMarkTransliteration().setValue("value");
		wordSpecification
				.setMarkVerbalElementText(new eu.ohim.sp.filing.domain.tm.Text());
		wordSpecification.getMarkVerbalElementText().setLanguage("en");
		wordSpecification.getMarkVerbalElementText().setValue("value");

		tradeMark2.setWordMarkSpecification(new ArrayList<WordMarkSpecification>());
        tradeMark2.getWordMarkSpecification().add(wordSpecification);

		// Applicants
		eu.ohim.sp.filing.domain.tm.Applicant applicant = new eu.ohim.sp.filing.domain.tm.Applicant();
		applicant.setLegalForm("screw");
		applicant.setNationality("ES");
		applicant.setAddressBook(new AddressBook());
		applicant.getAddressBook().setFormattedNameAddress(
				new FormattedNameAddress());
		applicant.getAddressBook().getFormattedNameAddress()
				.setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		applicant.getAddressBook().getFormattedNameAddress().getAddress()
				.setFormattedAddress(new FormattedAddress());

		applicant.setDomicileCountry(ISOCountryCode.US);
		applicant.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		applicant.getComment().setValue("comment");

		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry(ISOCountryCode.GR);
		applicant.setIncorporationState("Attica");

		applicant.getCorrespondenceAddresses().add(new AddressBook());

		applicant.getCorrespondenceAddresses().get(0)
				.setFormattedNameAddress(new FormattedNameAddress());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().setFormattedAddress(new FormattedAddress());

		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress().setCity("Limnoupoli");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress()
				.setCountry(ISOCountryCode.FR);
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress().setStreet("street");

		applicant.getCorrespondenceAddresses().get(0)
				.setContactInformationDetails(new ContactInformationDetails());
		applicant.getCorrespondenceAddresses().get(0)
				.getContactInformationDetails().getEmail()
				.add("whatever@oami.gr---------");
		applicant.getCorrespondenceAddresses().get(0)
				.getContactInformationDetails().getEmail()
				.add("whoever@oami.gr");

		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress().setCity("London");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress()
				.setStreet("Agias Lauras 36");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress().setState("Attica");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress()
				.setCountry(ISOCountryCode.IS);
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getAddress().getFormattedAddress().setStreetNumber("36");

		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.setName(new Name());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getName().setFreeFormatName(new FreeFormatNameType());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getName().getFreeFormatName()
				.setFreeFormatNameDetails(new FreeFormatNameDetailsType());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getName().getFreeFormatName().getFreeFormatNameDetails()
				.getFreeFormatNameLine().add(new eu.ohim.sp.filing.domain.tm.Text());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress()
				.getName().getFreeFormatName().getFreeFormatNameDetails()
				.getFreeFormatNameLine().get(0).setValue("Mr Christos Papas");

		applicant.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setCity("London Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setStreet("Agias Lauras 36 Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setState("Attica Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setCountry(ISOCountryCode.FR);
		applicant.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setStreetNumber("36 B");
		applicant.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setPostcode("00313");

		applicant.getAddressBook().getFormattedNameAddress()
				.setName(new Name());
		applicant.getAddressBook().getFormattedNameAddress().getName()
				.setFormattedName(new FormattedName());
		applicant.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().setFirstName("Rex");
		applicant.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().setMiddleName("T");
		applicant.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().setLastName("Papadopoulos");

		applicant.getAddressBook().setContactInformationDetails(
				new ContactInformationDetails());

		applicant.getAddressBook().getContactInformationDetails().getEmail()
				.add("whatever@oami.gr");
		applicant.getAddressBook().getContactInformationDetails().getEmail()
				.add("whoever@oami.gr");
		applicant
				.getAddressBook()
				.getContactInformationDetails()
				.getPhone()
				.add(new eu.ohim.sp.filing.domain.tm.Phone("3423423",
						eu.ohim.sp.filing.domain.tm.PhoneKind.OTHER));

		applicant.getAddressBook().getContactInformationDetails().getURL()
				.add("some url");
		applicant
				.getAddressBook()
				.getContactInformationDetails()
				.getPhone()
				.add(new eu.ohim.sp.filing.domain.tm.Phone("3423423",
						eu.ohim.sp.filing.domain.tm.PhoneKind.OTHER));

		ApplicantDetails applicantDetails = new ApplicantDetails();
		applicantDetails
				.setApplicant(new ArrayList<eu.ohim.sp.filing.domain.tm.Applicant>());
		applicantDetails.getApplicant().add(applicant);
		tradeMark2.setApplicants(applicantDetails);

		// Signatures
		eu.ohim.sp.filing.domain.tm.Signatory signatory = new eu.ohim.sp.filing.domain.tm.Signatory();
		signatory.setDate(new Date());
		signatory.setName(new Name());

		signatory.getName().setFreeFormatName(new FreeFormatNameType());
		signatory.getName().getFreeFormatName()
				.setFreeFormatNameDetails(new FreeFormatNameDetailsType());
		signatory.getName().getFreeFormatName().getFreeFormatNameDetails()
				.getFreeFormatNameLine().add(new eu.ohim.sp.filing.domain.tm.Text());
		signatory.getName().getFreeFormatName().getFreeFormatNameDetails()
				.getFreeFormatNameLine().get(0).setValue("Mr Christos Papas");

		signatory.setConfirmation(true);
		signatory.setCapacity("Applicant");
		signatory.setPlace("here");

		// TODO: there are not signatures

		// ImageSpecification
		MarkImageDetails markImageDetails = new MarkImageDetails();
		markImageDetails.setMarkImage(new ArrayList<MarkImage>());

		MarkImage markImage = new MarkImage();
		markImage
				.setMarkImageColourClaimedText(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		markImage.getMarkImageColourClaimedText().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		markImage.getMarkImageColourClaimedText().get(0).setLanguage("en");
		markImage.getMarkImageColourClaimedText().get(0).setValue("value");

		ColourDetailsType colourDetailsType = new ColourDetailsType();
		colourDetailsType.setColour(new ArrayList<ColourType>());
		ColourType colourType = new ColourType();
		colourType
				.setColourClaimedText(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		colourType.getColourClaimedText().add(new eu.ohim.sp.filing.domain.tm.Text());
		colourType.getColourClaimedText().get(0).setLanguage("en");
		colourType.getColourClaimedText().get(0).setValue("value");
		colourType.setColourCode(new ArrayList<ColourCodeType>());
		colourType.getColourCode().add(new ColourCodeType());
		colourType.getColourCode().get(0).setValue("colour");
		colourType.getColourCode().get(0)
				.setColourCodeFormat(ColourCodeFormatType.HEX);
		colourDetailsType.getColour().add(colourType);

		markImage.setMarkImageColourDetails(colourDetailsType);
		markImage.setMarkImageColourIndicator(true);
		markImage.setMarkImageColourMode("mode");
		markImage.setMarkImageFileFormat("JPEG");
		markImage.setMarkImageFilename("filename");
		markImage.setMarkImageIdentifier(new Identifier());
		markImage.getMarkImageIdentifier().setIdentifierKindCode("id");
		markImage.getMarkImageIdentifier().setValue("identifier");
		markImage.setMarkImageURI(new URI("some uri"));

		markImageDetails.getMarkImage().add(markImage);
		tradeMark2.setMarkImageDetails(markImageDetails);

		// ClassDescriptions
		goodsServices = new GoodsServices();
		goodsServices.setClassDescriptionDetails(new ClassDescriptionDetails());
		goodsServices.getClassDescriptionDetails().setClassDescription(
				new ArrayList<eu.ohim.sp.filing.domain.tm.ClassDescription>());
		goodsServices.getClassDescriptionDetails().getClassDescription()
				.add(new eu.ohim.sp.filing.domain.tm.ClassDescription());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.setClassNumber(new ClassNumber());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassNumber().setValue("42");
		goodsServices
				.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.setClassificationTerms(
						new ArrayList<ClassificationTermDetails>());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().add(new ClassificationTermDetails());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0)
				.setLanguage(ISOLanguageCode.EN);
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0)
				.setClassificationTerm(new ArrayList<ClassificationTermType>());
		goodsServices.getClassDescriptionDetails().getClassDescription().get(0)
				.getClassificationTerms().get(0).getClassificationTerm()
				.add(new ClassificationTermType());
		goodsServices
				.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.getClassificationTerms()
				.get(0)
				.getClassificationTerm()
				.get(0)
				.setClassificationTermText(
						new eu.ohim.sp.filing.domain.tm.Text("text", "en"));

		eu.ohim.sp.filing.domain.tm.ClassDescription classDescription = new eu.ohim.sp.filing.domain.tm.ClassDescription();
		classDescription
				.setClassificationTerms(new ArrayList<ClassificationTermDetails>());
		classDescription.getClassificationTerms().add(
				new ClassificationTermDetails());
		classDescription.getClassificationTerms().get(0)
				.setClassificationTerm(new ArrayList<ClassificationTermType>());
		classDescription.getClassificationTerms().get(0)
				.getClassificationTerm().add(new ClassificationTermType());
		classDescription.getClassificationTerms().get(0)
				.getClassificationTerm().get(0)
				.setClassificationTermText(new eu.ohim.sp.filing.domain.tm.Text());
		classDescription.getClassificationTerms().get(0)
				.getClassificationTerm().get(0).getClassificationTermText()
				.setLanguage("en");
		classDescription.getClassificationTerms().get(0)
				.getClassificationTerm().get(0).getClassificationTermText()
				.setValue("value");

		classDescription.setClassNumber(new ClassNumber("R1213"));
		classDescription.setFullClassCoverageIndicator(Boolean.FALSE);
		classDescription
				.setGoodsServicesDescription(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		classDescription.getGoodsServicesDescription().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		classDescription.getGoodsServicesDescription().get(0).setLanguage("en");
		classDescription.getGoodsServicesDescription().get(0).setValue("value");

		GoodsServicesDetails goodsServicesDetails = new GoodsServicesDetails();
		goodsServicesDetails.setGoodsServices(new ArrayList<GoodsServices>());
		goodsServicesDetails.getGoodsServices().add(new GoodsServices());
		goodsServicesDetails.getGoodsServices().get(0)
				.setClassDescriptionDetails(new ClassDescriptionDetails());
		goodsServicesDetails
				.getGoodsServices()
				.get(0)
				.getClassDescriptionDetails()
				.setClassDescription(
						new ArrayList<eu.ohim.sp.filing.domain.tm.ClassDescription>());
		goodsServicesDetails.getGoodsServices().get(0)
				.getClassDescriptionDetails().getClassDescription()
				.add(classDescription);
		tradeMark2.setGoodsServicesDetails(goodsServicesDetails);

		// MarkDescriptions
		MarkDescriptionDetails markDescriptionDetails = new MarkDescriptionDetails();
		markDescriptionDetails
				.setMarkDescription(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		markDescriptionDetails.getMarkDescription().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		markDescriptionDetails.getMarkDescription().get(0).setLanguage("en");
		markDescriptionDetails.getMarkDescription().get(0)
				.setValue("test papapra");
		markDescriptionDetails.getMarkDescription().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		markDescriptionDetails.getMarkDescription().get(1).setLanguage("ES");
		markDescriptionDetails.getMarkDescription().get(1).setValue("test 2");
		tradeMark2.setMarkDescriptions(markDescriptionDetails);

		// MarkDisclaimers
		MarkDisclaimerDetails markDisclaimerDetails = new MarkDisclaimerDetails();
		markDisclaimerDetails
				.setMarkDisclaimer(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		markDisclaimerDetails.getMarkDisclaimer().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		markDisclaimerDetails.getMarkDisclaimer().get(0).setLanguage("en");
		markDisclaimerDetails.getMarkDisclaimer().get(0).setValue("value");
		markDisclaimerDetails.getMarkDisclaimer().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		markDisclaimerDetails.getMarkDisclaimer().get(1).setLanguage("es");
		markDisclaimerDetails.getMarkDisclaimer().get(1).setValue("value 2");
		tradeMark2.setMarkDisclaimers(markDisclaimerDetails);

		// Representatives
		eu.ohim.sp.filing.domain.tm.Representative representative = new eu.ohim.sp.filing.domain.tm.Representative();
		representative.setLegalForm("screw");
		representative.setNationality("ES");
		representative.setAddressBook(new AddressBook());
		representative.getAddressBook().setFormattedNameAddress(
				new FormattedNameAddress());
		representative.getAddressBook().getFormattedNameAddress()
				.setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		representative.getAddressBook().getFormattedNameAddress().getAddress()
				.setFormattedAddress(new FormattedAddress());

		representative.setDomicileCountry(ISOCountryCode.US);
		representative.setComment(new eu.ohim.sp.filing.domain.tm.Text());
		representative.getComment().setValue("comment");

		representative.setDomicileLocality("test");
		representative.setIncorporationCountry(ISOCountryCode.GR);
		representative.setIncorporationState("Attica");

		representative.setCorrespondenceAddresses(new ArrayList<AddressBook>());
		representative.getCorrespondenceAddresses().add(new AddressBook());
		representative.getCorrespondenceAddresses().get(0)
				.setFormattedNameAddress(new FormattedNameAddress());
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress()
				.setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress()
				.setFormattedAddress(new FormattedAddress());

		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setCity("Limnoupoli");
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setCountry(ISOCountryCode.FR);
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setStreet("street");

		representative
				.getCorrespondenceAddresses()
				.get(0)
				.setContactInformationDetails(
						new eu.ohim.sp.filing.domain.tm.ContactInformationDetails());
		representative.getCorrespondenceAddresses().get(0)
				.getContactInformationDetails().getEmail()
				.add("whatever@oami.gr---------");
		representative.getCorrespondenceAddresses().get(0)
				.getContactInformationDetails().getEmail()
				.add("whoever@oami.gr");

		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setCity("London");
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setState("Attica");
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setCountry(ISOCountryCode.IS);
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getAddress().getFormattedAddress()
				.setStreetNumber("36");

		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress()
				.setName(new eu.ohim.sp.filing.domain.tm.Name());
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getName()
				.setFreeFormatName(new FreeFormatNameType());
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getName().getFreeFormatName()
				.setFreeFormatNameDetails(new FreeFormatNameDetailsType());
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getName().getFreeFormatName()
				.getFreeFormatNameDetails().getFreeFormatNameLine()
				.add(new eu.ohim.sp.filing.domain.tm.Text());
		representative.getCorrespondenceAddresses().get(0)
				.getFormattedNameAddress().getName().getFreeFormatName()
				.getFreeFormatNameDetails().getFreeFormatNameLine().get(0)
				.setValue("Mr Christos Papas");

		representative.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setCity("London Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setStreet("Agias Lauras 36 Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setState("Attica Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setCountry(ISOCountryCode.FR);
		representative.getAddressBook().getFormattedNameAddress().getAddress()
				.getFormattedAddress().setStreetNumber("36 B");

		representative.getAddressBook().getFormattedNameAddress()
				.setName(new eu.ohim.sp.filing.domain.tm.Name());
		representative.getAddressBook().getFormattedNameAddress().getName()
				.setFormattedName(new FormattedName());
		representative.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().setFirstName("Kostas");
		representative.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().setMiddleName("P");
		representative.getAddressBook().getFormattedNameAddress().getName()
				.getFormattedName().setLastName("Protonios");

		representative.getAddressBook().setContactInformationDetails(
				new eu.ohim.sp.filing.domain.tm.ContactInformationDetails());

		representative.getAddressBook().getContactInformationDetails()
				.getEmail().add("taska.sta.tramou.mou@oami.gr");
		representative.getAddressBook().getContactInformationDetails()
				.getEmail().add("tesr.kapelo@oami.gr");
		representative
				.getAddressBook()
				.getContactInformationDetails()
				.getPhone()
				.add(new eu.ohim.sp.filing.domain.tm.Phone("4536546",
						eu.ohim.sp.filing.domain.tm.PhoneKind.UNDEFINED));

		representative.getAddressBook().getContactInformationDetails().getURL()
				.add("some url");
		representative.getAddressBook().getContactInformationDetails().getURL()
				.add("some url 2");

		RepresentativeDetails representativeDetails = new RepresentativeDetails();
		representativeDetails
				.setRepresentative(new ArrayList<eu.ohim.sp.filing.domain.tm.Representative>());
		representativeDetails.getRepresentative().add(representative);
		tradeMark2.setRepresentatives(representativeDetails);
		return tradeMark2;
	}
}
