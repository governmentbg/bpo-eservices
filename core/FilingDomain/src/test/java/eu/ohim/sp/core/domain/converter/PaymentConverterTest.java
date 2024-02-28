/*
 *  FspDomain:: PaymentConverterTest 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.payment.BankTransfer;
import eu.ohim.sp.core.domain.payment.Payment;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.payment.PaymentKind;
import eu.ohim.sp.core.domain.payment.PaymentStatusCode;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.filing.domain.tm.Account;
import eu.ohim.sp.filing.domain.tm.AddressBook;
import eu.ohim.sp.filing.domain.tm.CardAccount;
import eu.ohim.sp.filing.domain.tm.ContactInformationDetails;
import eu.ohim.sp.filing.domain.tm.FormattedAddress;
import eu.ohim.sp.filing.domain.tm.FormattedName;
import eu.ohim.sp.filing.domain.tm.FormattedNameAddress;
import eu.ohim.sp.filing.domain.tm.ISOCountryCode;
import eu.ohim.sp.filing.domain.tm.Name;
import eu.ohim.sp.filing.domain.tm.PaymentKindType;
import eu.ohim.sp.filing.domain.tm.PaymentMethod;
import eu.ohim.sp.filing.domain.tm.Text;

public class PaymentConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertToFSP() {
		Payment payment = new Payment();
		
		payment.setComment("comment");
		
		BankTransfer bankTransfer = new BankTransfer();
		bankTransfer.setBankDestinationAccount("12313 123 12 234");
		bankTransfer.setBankTransferDate(new Date());
		bankTransfer.setBankTransferIdentifier("F34234234");
		bankTransfer.setOriginBankName("CAJA MURCIA");
		
		payment.setBankTransfer(bankTransfer);
		
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
		personRole.getEmails().get(0).setEmailAddress("test@oami.gr");
		personRole.getEmails().add(new Email());
		personRole.getEmails().get(1).setEmailAddress("kapelo@oami.gr");
		
		personRole.setPhones(new ArrayList<Phone>());
		personRole.getPhones().add(new Phone());
		personRole.getPhones().get(0).setNumber("123456789");
		
		payment.setPayer(personRole);
		
		eu.ohim.sp.core.domain.payment.Account account = new eu.ohim.sp.core.domain.payment.Account();
		account.setAccountIdentifier("I23423423");
		account.setAccountKind("internal");
		payment.setAccount(account);
		
		eu.ohim.sp.core.domain.payment.CardAccount cardAccount = new eu.ohim.sp.core.domain.payment.CardAccount();
		cardAccount.setCardChipApplicationIdentifier("card chip");
		cardAccount.setCardValidityStartDate(new Date());
		payment.setCardAccount(cardAccount);
		
		payment.setKind(PaymentKind.CURRENT_ACCOUNT);
		
		
		payment.setReference("paymenrtReference");
		payment.setStatus(PaymentStatusCode.BANK_TRANSFER_TO_FOLLOW);
		
		PaymentFee fee = new PaymentFee();
		fee.setCurrencyCode("EUR");
		fee.setAmount(1000.0);
		fee.setIdentifier("F001");
		fee.setReference("reference");
		//fee.setFeeUnitAmount(2);
		//fee.setFeeUnitQuantity(2);
		
		//payment.setPaymentFeeDetails(new ArrayList<PaymentFee>());
		//payment.getPaymentFeeDetails().add(fee);
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		eu.ohim.sp.filing.domain.tm.Payment dest =
			    dozerBeanMapper.map(payment, eu.ohim.sp.filing.domain.tm.Payment.class);

		assertEquals(payment.getComment(), dest.getComment().getValue());
		
		assertEquals(payment.getPayer().getName().getFirstName(), dest.getPayerName().getFormattedName().getFirstName());
		assertEquals(payment.getPayer().getName().getLastName(), dest.getPayerName().getFormattedName().getLastName());
		
		assertEquals(payment.getPayer().getUrls().get(0), dest.getPayerAddressBook().getContactInformationDetails().getURL().get(0));
		assertEquals(payment.getPayer().getEmails().get(0).getEmailAddress(), dest.getPayerAddressBook().getContactInformationDetails().getEmail().get(0));
		assertEquals(payment.getPayer().getEmails().get(1).getEmailAddress(), dest.getPayerAddressBook().getContactInformationDetails().getEmail().get(1));
		
		assertEquals(payment.getPayer().getPhones().get(0).getNumber(), dest.getPayerAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
		
		assertEquals(payment.getPayer().getAddresses().get(0).getCity(), dest.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(payment.getPayer().getAddresses().get(0).getCountry(), dest.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(payment.getPayer().getAddresses().get(0).getPostCode(), dest.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getPostcode());
		assertEquals(payment.getPayer().getAddresses().get(0).getState(), dest.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(payment.getPayer().getAddresses().get(0).getStreet(), dest.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(payment.getPayer().getAddresses().get(0).getStreetNumber(), dest.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
		
//		Not used bank transfer
		assertEquals(payment.getBankTransfer().getBankDestinationAccount(), dest.getPaymentMethod().getBankTransfer().getBankDestinationAccount());
		assertEquals(payment.getBankTransfer().getBankTransferDate(), dest.getPaymentMethod().getBankTransfer().getBankTransferDate());
		assertEquals(payment.getBankTransfer().getBankTransferIdentifier(), dest.getPaymentMethod().getBankTransfer().getBankTransferIdentifier());
		assertEquals(payment.getBankTransfer().getOriginBankName(), dest.getPaymentMethod().getBankTransfer().getOriginBankName());

		assertEquals(payment.getAccount().getAccountIdentifier(), dest.getPaymentMethod().getAccount().getAccountIdentifier());
		
		assertEquals(payment.getCardAccount().getCardChipApplicationIdentifier(), dest.getPaymentMethod().getCardAccount().getCardChipApplicationIdentifier());
		assertEquals(payment.getCardAccount().getCardValidityStartDate(), dest.getPaymentMethod().getCardAccount().getCardValidityStartDate());
		
		assertEquals(payment.getStatus().value(), dest.getPaymentStatus().value());
		
//		assertEquals(payment.getPaymentFeeDetails().iterator().next().getFeeReference(), dest.getPaymentFeeDetails().getPaymentFee().get(0).getFeeReference());
//		assertEquals(payment.getPaymentFeeDetails().iterator().next().getFeeIdentifier(), dest.getPaymentFeeDetails().getPaymentFee().get(0).getFeeIdentifier());
//		assertEquals(BigDecimal.valueOf(payment.getPaymentFeeDetails().iterator().next().getFeeAmount()), dest.getPaymentFeeDetails().getPaymentFee().get(0).getFeeAmount().getValue());
//		assertEquals(payment.getPaymentFeeDetails().iterator().next().getFeeAmount(), dest.getPaymentFeeDetails().getPaymentFee().get(0).getFeeAmount().getCurrencyCode());
		
	}
	
	@Test
	public void testConvertToCore() {
		eu.ohim.sp.filing.domain.tm.Payment payment = new eu.ohim.sp.filing.domain.tm.Payment();
		
		payment.setComment(new Text());
		payment.getComment().setLanguage("en");
		payment.getComment().setValue("value");
		
		payment.setPayerAddressBook(new AddressBook());
		payment.getPayerAddressBook().setContactInformationDetails(new ContactInformationDetails());
		payment.getPayerAddressBook().setFormattedNameAddress(new FormattedNameAddress());
		payment.getPayerAddressBook().getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		payment.getPayerAddressBook().getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());
		
		payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London Address");
		payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36 Address");
		payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica Address");
		payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36 B");
		payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setPostcode("00313");
		
		payment.getPayerAddressBook().getFormattedNameAddress().setName(new Name());
		payment.getPayerAddressBook().getFormattedNameAddress().getName().setFormattedName(new FormattedName());
		payment.getPayerAddressBook().getFormattedNameAddress().getName().getFormattedName().setFirstName("Nikos");
		payment.getPayerAddressBook().getFormattedNameAddress().getName().getFormattedName().setMiddleName("J");
		payment.getPayerAddressBook().getFormattedNameAddress().getName().getFormattedName().setLastName("Papadopoulos");
		
		payment.getPayerAddressBook().setContactInformationDetails(new ContactInformationDetails());
		
		payment.getPayerAddressBook().getContactInformationDetails().getEmail().add("whatever@oami.gr");
		payment.getPayerAddressBook().getContactInformationDetails().getEmail().add("whoever@oami.gr");
		payment.getPayerAddressBook().getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.tm.Phone("3423423", eu.ohim.sp.filing.domain.tm.PhoneKind.OTHER));
		
		payment.getPayerAddressBook().getContactInformationDetails().getURL().add("some url");
		
		payment.setPayerName(new Name());
		FormattedName formattedName = new FormattedName();
		formattedName.setFirstName("first name");
		formattedName.setLastName("last name");
		formattedName.setMiddleName("middle name");
		payment.getPayerName().setFormattedName(formattedName);

		payment.setPaymentDate(new Date());
		payment.setPaymentIdentifier("identifier");
		payment.setPaymentKind(PaymentKindType.BANK_TRANSFER);
		
		
		PaymentMethod method = new PaymentMethod();
		method.setAccount(new Account());
		method.getAccount().setAccountIdentifier("1234");
		method.setBankTransfer(new eu.ohim.sp.filing.domain.tm.BankTransfer());
		method.getBankTransfer().setBankDestinationAccount("account");
		method.getBankTransfer().setBankTransferDate(new Date());
		method.getBankTransfer().setBankTransferIdentifier("bank transfer id");
		method.getBankTransfer().setOriginBankName("origin bank name");
		method.setCardAccount(new CardAccount());
		
		payment.setPaymentMethod(method);
		payment.setPaymentStatus(eu.ohim.sp.filing.domain.tm.PaymentStatusCode.ATTACHED);
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		Payment dest = 
			    dozerBeanMapper.map(payment, Payment.class);
		
		assertEquals(dest.getComment(), payment.getComment().getValue());
		assertEquals(dest.getPayer().getName().getFirstName(), payment.getPayerName().getFormattedName().getFirstName());
		assertEquals(dest.getPayer().getName().getLastName(), payment.getPayerName().getFormattedName().getLastName());
		
		assertEquals(dest.getPayer().getUrls().get(0), payment.getPayerAddressBook().getContactInformationDetails().getURL().get(0));
		assertEquals(dest.getPayer().getEmails().get(0).getEmailAddress(), payment.getPayerAddressBook().getContactInformationDetails().getEmail().get(0));
		assertEquals(dest.getPayer().getEmails().get(1).getEmailAddress(), payment.getPayerAddressBook().getContactInformationDetails().getEmail().get(1));
		
		assertEquals(dest.getPayer().getPhones().get(0).getNumber(), payment.getPayerAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
		
		assertEquals(dest.getPayer().getAddresses().get(0).getCity(), payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(dest.getPayer().getAddresses().get(0).getCountry(), payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(dest.getPayer().getAddresses().get(0).getPostCode(), payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getPostcode());
		assertEquals(dest.getPayer().getAddresses().get(0).getState(), payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(dest.getPayer().getAddresses().get(0).getStreet(), payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(dest.getPayer().getAddresses().get(0).getStreetNumber(), payment.getPayerAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
		
//		Not used bank transfer
		assertEquals(dest.getBankTransfer().getBankDestinationAccount(), payment.getPaymentMethod().getBankTransfer().getBankDestinationAccount());
		assertEquals(dest.getBankTransfer().getBankTransferDate(), payment.getPaymentMethod().getBankTransfer().getBankTransferDate());
		assertEquals(dest.getBankTransfer().getBankTransferIdentifier(), payment.getPaymentMethod().getBankTransfer().getBankTransferIdentifier());
		assertEquals(dest.getBankTransfer().getOriginBankName(), payment.getPaymentMethod().getBankTransfer().getOriginBankName());

		assertEquals(dest.getAccount().getAccountIdentifier(), payment.getPaymentMethod().getAccount().getAccountIdentifier());
		
		assertEquals(dest.getCardAccount().getCardChipApplicationIdentifier(), payment.getPaymentMethod().getCardAccount().getCardChipApplicationIdentifier());
		assertEquals(dest.getCardAccount().getCardValidityStartDate(), payment.getPaymentMethod().getCardAccount().getCardValidityStartDate());
		
		assertEquals(dest.getStatus().value(), payment.getPaymentStatus().value());
	}

}
