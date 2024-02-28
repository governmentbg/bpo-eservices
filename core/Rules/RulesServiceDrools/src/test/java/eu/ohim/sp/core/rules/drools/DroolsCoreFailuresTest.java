package eu.ohim.sp.core.rules.drools;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.common.CharacterSet;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

public class DroolsCoreFailuresTest {

    @Mock
    ConfigurationService configurationService;
    @Mock
    private RulesMap rulesMap;
    @InjectMocks
    private DroolsCore droolsCore;

    private Sections sections;

    private Applicant applicantPerson;

    private final String PACKAGE = "eu.ohim.sp.core.rules";
    private final String MODULE = "dsefiling";
    private final String VALIDATE_SET = "applicant_set";
    private final String CALCULATE_SET = "calculate_set";
    private final String ERROR_SET = "error_set";
    private final String VALIDATE_DRL = "applicant.drl";
    private final String CALCULATE_DRL = "calculate.drl";
    private final String ERROR_DRL = "error.drl";
    private final String RULES_MAP = "rules_map";

    private String readFileAsString(String path) {
        String info = null;
        try {
            info = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");
        } catch (IOException e) {
            throw new SPException(e.getMessage());
        }
        return info;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        sections = convertXML2Object(readFileAsString("/xml/oneform-conf.xml"), Sections.class);

        initConfiguration();
        fillBaseApplicant();

        rulesMap = droolsCore.getRulesMap();
    }

    @Test
    public void testRuleListEmpty() {
        // Sets specific configuration
        when(configurationService.getValueList(eq(VALIDATE_SET), eq(PACKAGE + "." + MODULE))).thenReturn(
                new ArrayList<String>());

        droolsCore.initialize();
        rulesMap = droolsCore.getRulesMap();

        Assert.assertEquals(rulesMap.getRuleFilesCount(MODULE), 1);
    }

    @Test
    public void testModulesEmpty() {
        // Sets specific configuration
        when(configurationService.getValueList(eq(RULES_MAP), eq(PACKAGE))).thenReturn(new ArrayList<String>());

        droolsCore.initialize();
        rulesMap = droolsCore.getRulesMap();

        Assert.assertEquals(rulesMap.getRuleFilesCount(MODULE), 0);
    }

    @Test
    public void testErrorDrl() {
        // Sets specific configuration
        List<String> tmRulesList = new ArrayList<String>();
        tmRulesList.add(ERROR_SET);
        when(configurationService.getValueList(eq(MODULE), eq(PACKAGE + "." + MODULE))).thenReturn(tmRulesList);

        droolsCore.initialize();

        Assert.assertEquals(droolsCore.getRulesMap().getRuleFilesCount(MODULE), 0);
    }

    @Test
    public void testDrlNotExists() {
        // Sets specific configuration
        List<String> tmRulesList = new ArrayList<String>();
        tmRulesList.add(ERROR_SET);
        when(configurationService.getValueList(eq(MODULE), eq(PACKAGE + "." + MODULE))).thenReturn(tmRulesList);
        when(configurationService.getXml(eq(ERROR_DRL), eq(PACKAGE + "." + MODULE))).thenReturn(null);

        droolsCore.initialize();

        Assert.assertEquals(droolsCore.getRulesMap().getRuleFilesCount(MODULE), 0);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testEncodingException() {
        // Sets specific configuration
        List<String> tmRulesList = new ArrayList<String>();
        tmRulesList.add(ERROR_SET);
        when(configurationService.getValueList(eq(MODULE), eq(PACKAGE + "." + MODULE))).thenReturn(tmRulesList);
        when(configurationService.getXml(eq(ERROR_DRL), eq(PACKAGE + "." + MODULE))).thenThrow(
                UnsupportedEncodingException.class);

        droolsCore.initialize();

        Assert.assertEquals(droolsCore.getRulesMap().getRuleFilesCount(MODULE), 0);
    }

    private void initConfiguration() {
        List<String> rulesMapList = new ArrayList<String>();
        rulesMapList.add(MODULE);

        List<String> tmRulesList = new ArrayList<String>();
        tmRulesList.add(VALIDATE_SET);
        tmRulesList.add(CALCULATE_SET);

        List<String> tmErrorRuleListDrls = new ArrayList<String>();
        tmErrorRuleListDrls.add(ERROR_DRL);

        List<String> tmCalculateRuleListDrls = new ArrayList<String>();
        tmCalculateRuleListDrls.add(CALCULATE_DRL);

        when(configurationService.getXml(eq(VALIDATE_DRL), eq(PACKAGE + "." + MODULE))).thenReturn(
                readFileAsString("/applicant.drl"));
        when(configurationService.getXml(eq(CALCULATE_DRL), eq(PACKAGE + "." + MODULE))).thenReturn(
                readFileAsString("/calculate.drl"));
        when(configurationService.getXml(eq(ERROR_DRL), eq(PACKAGE + "." + MODULE))).thenReturn(
                readFileAsString("/error.drl"));
        when(configurationService.getValueList(eq(RULES_MAP), eq(PACKAGE))).thenReturn(rulesMapList);
        when(configurationService.getValueList(eq(MODULE), eq(PACKAGE + "." + MODULE))).thenReturn(tmRulesList);
        when(configurationService.getValueList(eq(ERROR_SET), eq(PACKAGE + "." + MODULE))).thenReturn(
                tmErrorRuleListDrls);
        when(configurationService.getValueList(eq(CALCULATE_SET), eq(PACKAGE + "." + MODULE))).thenReturn(
                tmCalculateRuleListDrls);
    }

    private void fillBaseApplicant() {
        applicantPerson = new Applicant();

        List<PersonIdentifier> personIdentifiersList = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setIdentifierKindCode("ApplicantIdentifier");
        personIdentifier.setValue("value");
        personIdentifiersList.add(personIdentifier);
        personIdentifier = new PersonIdentifier();
        personIdentifier.setIdentifierKindCode("VAT Number");
        personIdentifier.setValue("1234");
        personIdentifiersList.add(personIdentifier);
        applicantPerson.setIdentifiers(personIdentifiersList);

        applicantPerson.setKind(PersonKind.NATURAL_PERSON);

        PersonName personName = new PersonName();
        personName.setCharacterSet(CharacterSet.GEORGIAN);
        personName.setFirstName("Name");
        personName.setLastName("LastName");
        personName.setMiddleName("MiddleName");
        personName.setOrganizationName("OrganizationName");
        personName.setSecondLastName("SecondLastName");
        applicantPerson.setName(personName);

        applicantPerson.setPersonNumber("1232132");
        applicantPerson.setDateOfBirth(new java.util.Date());
        applicantPerson.setNationality("en");
        applicantPerson.setDomicileLocality("domicile");
        applicantPerson.setDomicileCountry("domicileCountry");
        applicantPerson.setLegalForm("LegalForm");
        applicantPerson.setIncorporationCountry("incorportacionCountry");
        applicantPerson.setIncorporationState("incorporationState");
        applicantPerson.setPrivacyWaiver(true);

        List<Address> addressList = new ArrayList<Address>();
        Address address = new Address();
        address.setKind("kind");
        address.setPostalName("postalName");
        address.setStreet("street");
        address.setStreetNumber("streetNumber");
        address.setCity("city");
        address.setPostCode("03440");
        address.setState("state");
        address.setCountry("country");
        addressList.add(address);
        applicantPerson.setAddresses(addressList);

        List<Phone> phoneList = new ArrayList<Phone>();
        Phone phone = new Phone();
        phone.setNumber("123123");
        phone.setInternationalPrefix("00");
        phone.setPhoneKind(PhoneKind.FAX);
        phoneList.add(phone);
        phone = new Phone();
        phone.setNumber("123123");
        phone.setInternationalPrefix("00");
        phone.setPhoneKind(PhoneKind.FIXED);
        phoneList.add(phone);
        applicantPerson.setPhones(phoneList);

        List<Email> emailList = new ArrayList<Email>();
        Email email = new Email();
        email.setEmailAddress("aaaa@aaa.com");
        email.setKind("kind");
        emailList.add(email);
        applicantPerson.setEmails(emailList);

        List<String> urlList = new ArrayList<String>();
        urlList.add("http://www.url.com");
        applicantPerson.setUrls(urlList);

        applicantPerson.setCorrespondenceAddresses(addressList);
        applicantPerson.setPreferredCorrespondenceKind("preferred");
    }

    @SuppressWarnings("unchecked")
    private <T> T convertXML2Object(String xml, Class<T> clazz) {
        try {

            StringReader sr = new StringReader(xml);
            StreamSource sc = new StreamSource(sr);
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (T) unmarshaller.unmarshal(sc);
        } catch (JAXBException jaxbException) {
            throw new SPException("Failed to convert xml", jaxbException, "error.generic");
        }
    }

    private Section getSubSectionById(Section section, String id) {
        for (Section sectionIterator : section.getSubsection()) {
            if (sectionIterator.getId().value().equals(id)) {
                return sectionIterator;
            }
            if (sectionIterator.getSubsection() != null) {
                Section sectionAux = getSubSectionById(sectionIterator, id);
                if (sectionAux != null) {
                    return sectionAux;
                }
            }
        }
        return null;
    }
}
