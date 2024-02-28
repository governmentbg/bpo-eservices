/*
 * PersonService:: RepresentativeServiceTest 10/10/13 16:47 karalch $
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
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.external.person.RepresentativeClientLocal;

/**
 * @author ionitdi
 */
public class RepresentativeServiceTest {
    @Mock
    RepresentativeClientLocal extRepresentativeServiceMock;

    @Mock
    RulesService businessRulesServiceMock;

    @InjectMocks
    RepresentativeServiceBean representativeService;

    private Sections sections;

    private static final String REPRESENTATIVESET = "representative_set";

    private Representative createTestRepresentative(String identifier) {
        Representative testRepresentative = new Representative();
        testRepresentative.setPersonNumber(identifier);

        return testRepresentative;
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
    public void testGetRepresentativeAutocomplete_nullInput_returnsNull() {
        // Arrange

        // Act
        String result = representativeService.getRepresentativeAutocomplete("tm", "EM", null, 12);

        // Assert
        assertEquals(null, result);
        verify(extRepresentativeServiceMock, never()).getRepresentativeAutocomplete("tm", "EM", null, 12);
    }

    @Test
    public void testGetRepresentativeAutocomplete_invalidMaxResults_returnsNull() {
        // Arrange

        // Act
        String result = representativeService.getRepresentativeAutocomplete("tm", "EM", "some text", 0);

        // Assert
        assertEquals(null, result);
        verify(extRepresentativeServiceMock, never()).getRepresentativeAutocomplete("tm", "EM", "some text", 0);
    }

    @Test
    public void testGetRepresentativeAutocomplete_validInput_returnsCorrectly() {
        // Arrange
        when(extRepresentativeServiceMock.getRepresentativeAutocomplete("tm", "EM", "some text", 5)).thenReturn(
                "some json result");

        // Act
        String result = representativeService.getRepresentativeAutocomplete("tm", "EM", "some text", 5);

        // Assert
        assertEquals("some json result", result);
    }

    @Test
    public void testGetRepresentativeAutocomplete_nullServiceReturn_returnNull() {
        // Arrange
        when(extRepresentativeServiceMock.getRepresentativeAutocomplete("tm", "EM", "text", 10)).thenReturn(null);

        // Act
        String result = representativeService.getRepresentativeAutocomplete("tm", "EM", "text", 10);

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testSearchRepresentative_invalidNumberOfResults_returnsNull() {
        // Arrange

        // Act
        List<Representative> result = representativeService.searchRepresentative("tm", "EM", "some id", "some name",
                "some nationality", 0);

        // Assert
        assertEquals(null, result);
        verify(extRepresentativeServiceMock, never()).searchRepresentative("tm", "EM", "some id", "some name",
                "some nationality", 0);
    }

    @Test
    public void testSearchRepresentative_validInput_returnsCorrectly() {
        // Arrange
        ArrayList<Representative> testRepresentativeList = new ArrayList<Representative>();
        testRepresentativeList.add(createTestRepresentative("some id"));

        when(
                extRepresentativeServiceMock.searchRepresentative("tm", "EM", "some id", "some name",
                        "some nationality", 22)).thenReturn(testRepresentativeList);

        // Act
        List<Representative> result = representativeService.searchRepresentative("tm", "EM", "some id", "some name",
                "some nationality", 22);

        // Assert
        assertEquals("some id", result.get(0).getPersonNumber());
    }

    @Test
    public void testSearchRepresentative_nullServiceReturn_returnsNull() {
        when(
                extRepresentativeServiceMock.searchRepresentative(eq("tm"), eq("EM"), anyString(), anyString(),
                        anyString(), anyInt())).thenReturn(null);

        List<Representative> result = representativeService.searchRepresentative("tm", "EM", "some id", "some name",
                "some nationality", 22);
        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testGetRepresentative_nullInput_returnsNull() {
        // Arrange

        // Act
        Representative result = representativeService.getRepresentative("tm", "office", null);

        // Assert
        assertEquals(null, result);
        verify(extRepresentativeServiceMock, never()).getRepresentative("tm", "office", null);
    }

    @Test
    public void testGetRepresentative_validInput_returnsCorrectly() {
        // Arrange
        Representative testRepresentative = createTestRepresentative("a test id");

        when(extRepresentativeServiceMock.getRepresentative("tm", "office", "a test id"))
                .thenReturn(testRepresentative);

        // Act
        Representative result = representativeService.getRepresentative("tm", "office", "a test id");

        // Assert
        assertEquals("a test id", result.getPersonNumber());
    }

    @Test
    public void testGetRepresentative_nullServiceResult_returnsNull() {
        // Arrange
        when(extRepresentativeServiceMock.getRepresentative(eq("tm"), anyString(), anyString())).thenReturn(null);

        // Act
        Representative result = representativeService.getRepresentative("tm", "office", "some id");

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testMatchRepresentative_invalidMaxResults_returnsNull() {
        // Arrange
        Representative givenRepresentative = new Representative();
        givenRepresentative.setPersonNumber("some id");

        // Act
        List<Representative> result = representativeService.matchRepresentative("tm", "EM", givenRepresentative, 0);

        // Assert
        assertEquals(null, result);
        verify(extRepresentativeServiceMock, never()).matchRepresentative("tm", "EM", givenRepresentative, 0);
    }

    @Test
    public void testMatchRepresentative_nullInput_returnsNull() {
        // Arrange

        // Act
        List<Representative> result = representativeService.matchRepresentative("tm", "EM", null, 5);

        // Assert
        assertEquals(null, result);
        verify(extRepresentativeServiceMock, never()).matchRepresentative("tm", "EM", null, 5);
    }

    @Test
    public void testMatchRepresentative_nullServiceReturn_returnsNull() {
        // Arrange
        when(extRepresentativeServiceMock.matchRepresentative(eq("tm"), eq("EM"), any(Representative.class), anyInt()))
                .thenReturn(null);

        // Act
        List<Representative> result = representativeService.matchRepresentative("tm", "EM", new Representative(), 5);

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testMatchRepresentative_validInput_returnsCorrectly() {
        // Arrange
        Representative givenRepresentative = new Representative();
        givenRepresentative.setPersonNumber("some test string");

        Representative retrievedRepresentative = new Representative();
        retrievedRepresentative.setPersonNumber("another test string");

        List<Representative> someListOfRepresentatives = new ArrayList<Representative>();
        someListOfRepresentatives.add(retrievedRepresentative);

        when(extRepresentativeServiceMock.matchRepresentative("tm", "EM", givenRepresentative, 15)).thenReturn(
                someListOfRepresentatives);

        // Act
        List<Representative> result = representativeService.matchRepresentative("tm", "EM", givenRepresentative, 15);

        // Assert
        assertEquals("another test string", result.get(0).getPersonNumber());
    }

    @Test
    public void testSaveRepresentative_validInput_returnsCorrectly() {

    }

    @Test
    // TODO: Define the key of sectionName and sections
    public void testValidateRepresentative_notNull() {
        Representative givenRepresentative = new Representative();
        givenRepresentative.setPersonNumber("some id");

        new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "representative_naturalperson");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(givenRepresentative);

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());

        when(businessRulesServiceMock.validate("rules_map", REPRESENTATIVESET, objects)).thenReturn(errorList);

        representativeService.validateRepresentative("rules_map", givenRepresentative, rulesInformation);
    }

    @Test
    public void testValidateRepresentative_null_stillCallsService() {
        Representative givenRepresentative = null;

        new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "representative_naturalperson");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(givenRepresentative);

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());
        errorList.getErrorList().add(new ErrorCore());
        errorList.getErrorList().get(0).setDisplayMessage("The representative is empty");

        when(businessRulesServiceMock.validate("rules_map", REPRESENTATIVESET, objects)).thenReturn(errorList);

        representativeService.validateRepresentative("rules_map", givenRepresentative, rulesInformation);
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
