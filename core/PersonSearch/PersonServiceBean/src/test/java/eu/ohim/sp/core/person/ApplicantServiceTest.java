/*
 * PersonService:: ApplicantServiceTest 10/10/13 16:47 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.external.person.ApplicantClientLocal;

/**
 * @author ionitdi
 */
public class ApplicantServiceTest {
    @Mock
    RulesService businessRulesServiceMock;

    @Mock
    ApplicantClientLocal extApplicantServiceMock;

    @InjectMocks
    ApplicantServiceBean applicantService;

    private Sections sections;

    private static final String APPLICANTSET = "applicant_set";

    private Applicant createTestApplicant(String identifier) {
        Applicant testApplicant = new Applicant();
        testApplicant.setPersonNumber(identifier);

        return testApplicant;
    }

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
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sections = convertXML2Object(readFileAsString("/xml/oneform-conf.xml"), Sections.class);
    }

    @Test
    public void testGetApplicantAutocomplete_nullInput_returnsNull() {
        // Arrange

        // Act
        String result = applicantService.getApplicantAutocomplete("tm", "EM", null, 12);

        // Assert
        assertEquals(null, result);
        verify(extApplicantServiceMock, never()).getApplicantAutocomplete("tm", "EM", null, 12);
    }

    @Test
    public void testGetApplicantAutocomplete_invalidMaxResults_returnsNull() {
        // Arrange

        // Act
        String result = applicantService.getApplicantAutocomplete("tm", "EM", "some text", 0);

        // Assert
        assertEquals(null, result);
        verify(extApplicantServiceMock, never()).getApplicantAutocomplete("tm", "EM", "some text", 0);
    }

    @Test
    public void testGetApplicantAutocomplete_validInput_returnsCorrectly() {
        // Arrange
        when(extApplicantServiceMock.getApplicantAutocomplete("tm", "EM", "some text", 5)).thenReturn(
                "some json result");

        // Act
        String result = applicantService.getApplicantAutocomplete("tm", "EM", "some text", 5);

        // Assert
        assertEquals("some json result", result);
    }

    @Test
    public void testGetApplicantAutocomplete_nullServiceReturn_returnNull() {
        // Arrange
        when(extApplicantServiceMock.getApplicantAutocomplete("tm", "EM", "text", 10)).thenReturn(null);

        // Act
        String result = applicantService.getApplicantAutocomplete("tm", "EM", "text", 10);

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testSearchApplicant_invalidNumberOfResults_returnsNull() {
        // Arrange

        // Act
        List<Applicant> result = applicantService.searchApplicant("tm", "EM", "some id", "some name",
                "some nationality", 0);

        // Assert
        assertEquals(null, result);
        verify(extApplicantServiceMock, never()).searchApplicant("tm", "EM", "some id", "some name",
                "some nationality", 0);
    }

    @Test
    public void testSearchApplicant_validInput_returnsCorrectly() {
        // Arrange
        ArrayList<Applicant> testApplicantList = new ArrayList<Applicant>();
        testApplicantList.add(createTestApplicant("some id"));

        when(extApplicantServiceMock.searchApplicant("tm", "EM", "some id", "some name", "some nationality", 22))
                .thenReturn(testApplicantList);

        // Act
        List<Applicant> result = applicantService.searchApplicant("tm", "EM", "some id", "some name",
                "some nationality", 22);

        // Assert
        assertEquals("some id", result.get(0).getPersonNumber());
    }

    @Test
    public void testSearchApplicant_nullServiceReturn_returnsNull() {
        when(
                extApplicantServiceMock.searchApplicant(eq("tm"), eq("EM"), anyString(), anyString(), anyString(),
                        anyInt())).thenReturn(null);

        List<Applicant> result = applicantService.searchApplicant("tm", "EM", "some id", "some name",
                "some nationality", 22);
        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testGetApplicant_nullInput_returnsNull() {
        // Arrange

        // Act
        Applicant result = applicantService.getApplicant("tm", "office", null);

        // Assert
        assertEquals(null, result);
        verify(extApplicantServiceMock, never()).getApplicant("tm", "office", null);
    }

    @Test
    public void testGetApplicant_validInput_returnsCorrectly() {
        // Arrange
        Applicant testApplicant = createTestApplicant("a test id");

        when(extApplicantServiceMock.getApplicant("tm", "office", "a test id")).thenReturn(testApplicant);

        // Act
        Applicant result = applicantService.getApplicant("tm", "office", "a test id");

        // Assert
        assertEquals("a test id", result.getPersonNumber());
    }

    @Test
    public void testGetApplicant_nullServiceResult_returnsNull() {
        // Arrange
        when(extApplicantServiceMock.getApplicant(eq("tm"), anyString(), anyString())).thenReturn(null);

        // Act
        Applicant result = applicantService.getApplicant("tm", "office", "some id");

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testMatchApplicant_invalidMaxResults_returnsNull() {
        // Arrange
        Applicant givenApplicant = new Applicant();
        givenApplicant.setPersonNumber("some id");

        // Act
        List<Applicant> result = applicantService.matchApplicant("tm", "EM", givenApplicant, 0);

        // Assert
        assertEquals(null, result);
        verify(extApplicantServiceMock, never()).matchApplicant("tm", "EM", givenApplicant, 0);
    }

    @Test
    public void testMatchApplicant_nullInput_returnsNull() {
        // Arrange

        // Act
        List<Applicant> result = applicantService.matchApplicant("tm", "EM", null, 5);

        // Assert
        assertEquals(null, result);
        verify(extApplicantServiceMock, never()).matchApplicant("tm", "EM", null, 5);
    }

    @Test
    public void testMatchApplicant_nullServiceReturn_returnsNull() {
        // Arrange
        when(extApplicantServiceMock.matchApplicant(eq("tm"), eq("EM"), any(Applicant.class), anyInt())).thenReturn(
                null);

        // Act
        List<Applicant> result = applicantService.matchApplicant("tm", "EM", new Applicant(), 5);

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testMatchApplicant_validInput_returnsCorrectly() {
        // Arrange
        Applicant givenApplicant = new Applicant();
        givenApplicant.setPersonNumber("some test string");

        Applicant retrievedApplicant = new Applicant();
        retrievedApplicant.setPersonNumber("another test string");

        List<Applicant> someListOfApplicants = new ArrayList<Applicant>();
        someListOfApplicants.add(retrievedApplicant);

        when(extApplicantServiceMock.matchApplicant("tm", "EM", givenApplicant, 15)).thenReturn(someListOfApplicants);

        // Act
        List<Applicant> result = applicantService.matchApplicant("tm", "EM", givenApplicant, 15);

        // Assert
        assertEquals("another test string", result.get(0).getPersonNumber());
    }

    @Test
    public void testSaveApplicant_validInput_returnsCorrectly() {

    }

    @Test
    // TODO: Define the key of sectionName and sections
    public void testValidateApplicant_notNull_callsBusinessRulesService() {
        Applicant givenApplicant = new Applicant();
        givenApplicant.setPersonNumber("some id");

        new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "representative_naturalperson");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(givenApplicant);

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());

        when(businessRulesServiceMock.validate("rules_map", APPLICANTSET, objects)).thenReturn(errorList);

        applicantService.validateApplicant("rules_map", givenApplicant, rulesInformation);

//    	if (errors != null) {
//    		assertEquals(errors.getErrorList().size(), 0);
//    	} else {
//    		Assert.fail("Errors should not be null");
//    	}
    }

    @Test
    public void testValidateApplicant_null_stillCallsService() {
        Applicant givenApplicant = null;

        new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "representative_naturalperson");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(givenApplicant);

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());
        errorList.getErrorList().add(new ErrorCore());
        errorList.getErrorList().get(0).setDisplayMessage("The applicant is empty");

        when(businessRulesServiceMock.validate("rules_map", APPLICANTSET, objects)).thenReturn(errorList);

        applicantService.validateApplicant("rules_map", givenApplicant, rulesInformation);
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

    private Section getSection(RulesInformation rulesInformation, Sections sections) {
        Section section = null;

        // Creates the iterator for the map
        if (rulesInformation.getCustomObjects() != null && !rulesInformation.getCustomObjects().isEmpty()) {
            String sectionName = (String) rulesInformation.getCustomObjects().get("sectionName");
            if (sectionName != null) {
                section = getSectionById(sections, sectionName);
            } else {
                return null;
            }
        } else {
            return null;
        }

        // Sets the section
        if (section != null) {
            return section;
        }

        // Something went wrong. The sectionName couldn't be found in the map or the section doesn't exist in the
        // sections object
        return null;
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
