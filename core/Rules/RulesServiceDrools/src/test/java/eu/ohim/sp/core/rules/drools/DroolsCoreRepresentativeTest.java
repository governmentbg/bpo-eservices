package eu.ohim.sp.core.rules.drools;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
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
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.validation.ErrorList;

public class DroolsCoreRepresentativeTest extends DroolsCore {

    @Mock
    ConfigurationService configurationService;

    private Sections sections;

    private Representative representativePerson;

    private final String _PACKAGE_ = "eu.ohim.sp.core.rules";

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
        fillBaseRepresentative();
    }

    private void initConfiguration() {

        Collection<String> drlFiles = new ArrayList<String>();
        drlFiles.add("representative.drl");

        List<String> rulesMapList = new ArrayList<String>();
        rulesMapList.add("EF_rules");

        List<String> tmRulesList = new ArrayList<String>();
        tmRulesList.add("EF_representative_list");

        List<String> tmRuleListDrls = new ArrayList<String>();
        tmRuleListDrls = new ArrayList<String>();
        tmRuleListDrls.add("representative.drl");

        when(configurationService.getXml(eq("representative.drl"), eq("eu.ohim.sp.core.rules"))).thenReturn(
                readFileAsString("/representative.drl"));

        when(configurationService.getValueList(eq("rules_map"), eq(_PACKAGE_))).thenReturn(rulesMapList);
        when(configurationService.getValueList(eq("EF_rules"), eq(_PACKAGE_))).thenReturn(tmRulesList);

        when(configurationService.getValueList(eq("EF_representative_list"), eq(_PACKAGE_))).thenReturn(tmRuleListDrls);

        initialize();
    }

    private void fillBaseRepresentative() {
        representativePerson = new Representative();

        List<PersonIdentifier> personIdentifiersList = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setIdentifierKindCode("ApplicantIdentifier");
        personIdentifier.setValue("value");
        personIdentifiersList.add(personIdentifier);
        personIdentifier = new PersonIdentifier();
        personIdentifier.setIdentifierKindCode("VAT Number");
        personIdentifier.setValue("1234");
        personIdentifiersList.add(personIdentifier);
        representativePerson.setIdentifiers(personIdentifiersList);

        representativePerson.setKind(PersonKind.NATURAL_PERSON);

        PersonName personName = new PersonName();
        personName.setCharacterSet(CharacterSet.GEORGIAN);
        personName.setFirstName("Name");
        personName.setLastName("LastName");
        personName.setMiddleName("MiddleName");
        personName.setOrganizationName("OrganizationName");
        personName.setSecondLastName("SecondLastName");
        representativePerson.setName(personName);

        representativePerson.setPersonNumber("1232132");
        representativePerson.setDateOfBirth(new java.util.Date());
        representativePerson.setNationality("en");
        representativePerson.setDomicileLocality("domicile");
        representativePerson.setDomicileCountry("domicileCountry");
        representativePerson.setLegalForm("LegalForm");
        representativePerson.setIncorporationCountry("incorportacionCountry");
        representativePerson.setIncorporationState("incorporationState");
        representativePerson.setPrivacyWaiver(true);

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
        representativePerson.setAddresses(addressList);

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
        representativePerson.setPhones(phoneList);

        List<Email> emailList = new ArrayList<Email>();
        Email email = new Email();
        email.setEmailAddress("aaaa@aaa.com");
        email.setKind("kind");
        emailList.add(email);
        representativePerson.setEmails(emailList);

        List<String> urlList = new ArrayList<String>();
        urlList.add("http://www.url.com");
        representativePerson.setUrls(urlList);

        representativePerson.setCorrespondenceAddresses(addressList);
        representativePerson.setPreferredCorrespondenceKind("preferred");
    }

//	@Test
    public void testRepresentative_Ok() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 0);

    }

//	@Test
    public void testRepresentative_BR114Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "person");

        representativePerson.setKind(null);

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR116Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.setDomicileLocality("");

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR117Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.setDomicileCountry("");

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR122Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.setEmails(null);

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR246Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.setAddresses(null);

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR128Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.setNationality(null);

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR131aEmpty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.setName(null);

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR131bEmpty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.getName().setLastName(null);

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR136Empty_SecondLastName() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.getName().setSecondLastName("");

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testRepresentative_BR132bEmpty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "representative_naturalperson");

        representativePerson.getName().setFirstName("");

        objects.add(representativePerson);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_representative_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
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

    private Section getSectionById(Sections sections, String id) {

        if (sections != null) {
            for (Section sectionIterator : sections.getSection()) {
                if (sectionIterator.getId().value().equals(id)) {
                    return sectionIterator;
                }
                if (sectionIterator.getSubsection() != null) {
                    Section section = getSubSectionById(sectionIterator, id);
                    if (section != null) {
                        return section;
                    }
                }
            }
        }

        return null;
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
