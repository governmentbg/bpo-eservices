/*
 * TradeMarkService:: TradeMarkServiceTest 25/11/13 19:10 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.register;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;
import eu.ohim.sp.core.domain.opposition.LegalActVersion;
import eu.ohim.sp.core.domain.opposition.OppositionAbsoluteGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesServiceLocal;

public class TradeMarkSearchServiceBeanTest {

    @Mock
    RulesServiceLocal businessRulesServiceMock;

    @Mock
    TradeMarkSearchService extTrademarkServiceMock;

    @InjectMocks
    TradeMarkSearchServiceBean trademarkService;

    private Sections sections;

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
    public void testGetTradeMarkAutocomplete_nullInput_returnsNull() {
        // Arrange

        // Act
        String office = "";
        String text = "";
        int numberOfResults = 12;
        String result = trademarkService.getTradeMarkAutocomplete(office, text, numberOfResults);

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testGetTradeMarkAutocomplete_invalidMaxResults_returnsNull() {
        // Arrange

        // Act
        String office = "";
        String text = "";
        int numberOfResults = 0;
        String result = trademarkService.getTradeMarkAutocomplete(office, text, numberOfResults);

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testGetTradeMarkAutocomplete_validInput_returnsCorrectly() {
        // Arrange
        when(extTrademarkServiceMock.getTradeMarkAutocomplete("some office", "some text", 5)).thenReturn(
                "some json result");

        // Act
        String result = trademarkService.getTradeMarkAutocomplete("some office", "some text", 5);

        // Assert
        assertEquals("some json result", result);
    }

    @Test
    public void testGetForeignTradeMarkAutocomplete() {
        trademarkService.getForeignTradeMarkAutocomplete("office", "text", 1);
        verify(extTrademarkServiceMock, times(1)).getTradeMarkAutocomplete("office", "text", 1);
    }

    @Test
    public void testGetTradeMark() {
        trademarkService.getTradeMark("office", "text");
        verify(extTrademarkServiceMock, times(1)).getTradeMark("office", "text");
    }

    @Test
    public void testGetForeignTradeMark() {
        trademarkService.getForeignTradeMark("office", "text");
        verify(extTrademarkServiceMock, times(1)).getForeignTradeMark("office", "text");
    }

    @Test
    public void testGetPreclearanceReport() {
        trademarkService.getPreclearanceReport("office", new TradeMark());
        verify(extTrademarkServiceMock, times(1)).getPreclearanceReport(eq("office"), any(TradeMark.class));
    }

    @Test
    public void testValidateTradeMark() {
        TradeMark givenTradeMark = new TradeMark();
        givenTradeMark.setApplicants(new ArrayList<Applicant>());
        givenTradeMark.getApplicants().add(new Applicant());

        ErrorList errors = new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "representative_naturalperson");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(givenTradeMark);
        objects.add(rulesInformation);

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());

        when(businessRulesServiceMock.validate("rules_map", "trademark_set", objects)).thenReturn(errorList);

        errors = trademarkService.validateTradeMark("rules_map", givenTradeMark, rulesInformation);
        verify(businessRulesServiceMock, times(1)).validate("rules_map", "trademark_set", objects);

        assertEquals(errors.getErrorList().size(), 0);
    }

    @Test
    public void testValidateOpposition() {
        OppositionGround opposition = new OppositionAbsoluteGrounds();
        opposition.setGroundCategory(GroundCategoryKind.ABSOLUTE_GROUNDS);
        opposition.setExplanationText("explanationText");
        opposition.setLegalActVersion(new LegalActVersion());

        ErrorList errors = new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "representative_naturalperson");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(rulesInformation);
        objects.add(rulesInformation.getCustomObjects().get("sections"));

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());

        when(businessRulesServiceMock.validate(eq("tm-invalidity"), eq("opposition_set"), any(List.class))).thenReturn(
                errorList);

        errors = trademarkService.validateOpposition("tm-invalidity", opposition, rulesInformation);
        assertEquals(errors.getErrorList().size(), 0);
    }

    @Test
    public void testValidateApplicant_null_callsBusinessRulesService() {
        TradeMark givenTradeMark = new TradeMark();
        givenTradeMark.setApplicants(new ArrayList<Applicant>());
        givenTradeMark.getApplicants().add(new Applicant());
        givenTradeMark.getApplicants().get(0).setKind(null);

        ErrorList errors = new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "wordmark");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(givenTradeMark);
        objects.add(rulesInformation);

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());
        errorList.getErrorList().add(new ErrorCore());
        errorList.getErrorList().get(0).setDisplayMessage("This field is mandatory");
        errorList.getErrorList().get(0).setField("type");

        when(businessRulesServiceMock.validate("rules_map", "trademark_set", objects)).thenReturn(errorList);

        errors = trademarkService.validateTradeMark("rules_map", givenTradeMark, rulesInformation);
        assertEquals(errors.getErrorList().size(), 1);
    }

    @Test
    // TODO: Define the key to sectionName
    public void testValidateApplicant_notNull_callsBusinessRulesService() {
        TradeMark givenTradeMark = new TradeMark();
        givenTradeMark.setApplicationDate(new Date());

        ErrorList errors = new ErrorList();
        RulesInformation rulesInformation = new RulesInformation();

        Map<String, Object> customObjects = new HashMap<String, Object>();
        customObjects.put("sectionName", "wordmark");
        customObjects.put("sections", sections);
        rulesInformation.setCustomObjects(customObjects);

        Section section = getSection(rulesInformation, sections);

        List<Object> objects = new ArrayList<Object>();
        objects.add(section);
        objects.add(givenTradeMark);
        objects.add(rulesInformation);

        ErrorList errorList = new ErrorList();
        errorList.setErrorList(new ArrayList<ErrorCore>());

        when(businessRulesServiceMock.validate("rules_map", "trademark_set", objects)).thenReturn(errorList);

        errors = trademarkService.validateTradeMark("rules_map", givenTradeMark, rulesInformation);
        Assert.assertEquals(errors.getErrorList().size(), 0);
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
