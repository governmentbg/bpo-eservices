/*******************************************************************************
 * * $Id:: SectionViewConfigurationTest.java 113489 2013-04-22 14:59:26Z karalch $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.webflow;

import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration.ImportType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.*;

import static org.junit.Assert.*;

public class SectionViewConfigurationTest{

	private SectionViewConfiguration sectionViewConfiguration;
	

	@Before
	public void setup() throws JAXBException {
		JAXBContext jc;
		jc = JAXBContext.newInstance(Sections.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		Sections sections = (Sections) unmarshaller.unmarshal(getClass().getResourceAsStream("/sections.xml"));
		
		Map<String, Sections> viewConfiguration = new HashMap<String, Sections>();
		viewConfiguration.put("flow", sections);
		sectionViewConfiguration = new SectionViewConfiguration();
		sectionViewConfiguration.setViewConfiguration(viewConfiguration);
	}

	@Test
	public void testAnonymousMode() {
		assertTrue(sectionViewConfiguration.isAnonymousMode("flow"));
	}

	@Test
	public void testSeniorityCountry() {
		assertTrue(sectionViewConfiguration.getRender(AvailableSection.SENIORITY, "country", "flow"));
		assertTrue(sectionViewConfiguration.getRequired(AvailableSection.SENIORITY, "country", "flow"));
		assertFalse(sectionViewConfiguration.getEditableImport(AvailableSection.SENIORITY, "country", "flow"));
	}
	
	@Test
	public void testPriorityFilePriorityCertificate() {
		assertFalse(sectionViewConfiguration.getRender(AvailableSection.PRIORITY, "filePriorityCertificate", "flow"));
		assertFalse(sectionViewConfiguration.getRequired(AvailableSection.PRIORITY, "filePriorityCertificate", "flow"));
		assertTrue(sectionViewConfiguration.getEditableImport(AvailableSection.PRIORITY, "filePriorityCertificate", "flow"));
	}

	@Test
	public void testNonExistentField() {
		
	}
	
	@Test 
	public void testGetSortedViewState() {
		SectionViewConfiguration sectionViewConfiguration2 = new SectionViewConfiguration();
		Map<String, Sections> viewConfiguration = new HashMap<String, Sections>();
		sectionViewConfiguration2.setViewConfiguration(viewConfiguration);
		Sections sections = new Sections();
		viewConfiguration.put("flow", sections);
		sections.setNavigationPath(true);
		//section 1
		Section section1 = new Section();
		section1.setVisible(true);
		section1.setViewStateOrder(1);
		section1.setViewStateId("one");
		//section 2
		Section section2 = new Section();
		section2.setVisible(true);
		section2.setViewStateOrder(2);
		section2.setViewStateId("two");
		
		sections.getSection().add(section1);
		sections.getSection().add(section2);
		assertArrayEquals(new String[]{"one","two"}, sectionViewConfiguration2.getSortedViewStates("flow").toArray());
		
		sections.getSection().clear();
		sections.getSection().add(section2);
		sections.getSection().add(section1);
		assertArrayEquals(new String[]{"one","two"}, sectionViewConfiguration2.getSortedViewStates("flow").toArray());
	}
	
	
	public void testDisplayNavigationPath() {
		assertFalse(sectionViewConfiguration.displayNavigationPath("flow"));
	}
	
	@Test
	public void testAllFields() {
		String[] expectedFields = {"date",
				"number",
				"country",
				"fileWrapperCopy",
				"fileWrapperTranslation",
				"filePriorityCertificate"
		};

		Set<String> fields = new HashSet<String>(Arrays.asList(expectedFields));
		for (String field : sectionViewConfiguration.getAllFields(AvailableSection.PRIORITY, "flow")) {
			assertTrue(fields.remove(field));
		}
		assertEquals(fields.size(), 0);
	}

	@Test
	public void testGetFormatted() {
		assertEquals(sectionViewConfiguration.getFormatted(AvailableSection.PRIORITY, "date", "flow"), "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$");
	}
	
	@Test
	public void testGetSortedSections() {
		String[] expectedSections = {"previousCTM",
				"claim",
				"person",
				"marktitle",
				"languages",
				"markreference",
				"typemark",
				"goodsandservices",
				"otherAttachments",
				"signature",
				"buttons1"
		};
		
		List<Section> sections = sectionViewConfiguration.getSortedSections("flow", "oneform");
		for (int i = 0; i<expectedSections.length; i++ ) {
			assertEquals(sections.get(i).getId().value(), (expectedSections[i]));
		}
	}
	
	@Test
	public void testGetSortedSubSections() {
		String[] expectedSections = {"priority",
				"seniority",
				"exhibition",
				"transformation"
		};
		
		List<Section> sections = sectionViewConfiguration.getSortedSubsections("flow", "oneform", AvailableSection.CLAIM);
		for (int i = 0; i<sections.size(); i++ ) {
			assertEquals(sections.get(i).getId().value(), (expectedSections[i]));
		}
	}
	
	@Test
	public void testImportable() {
		
		assertTrue(sectionViewConfiguration.getImportableSection(AvailableSection.SENIORITY, "flow", ImportType.PREVIOUS_CTM));
		assertTrue(sectionViewConfiguration.getImportableSection(AvailableSection.SENIORITY, "flow", ImportType.PRIORITY));
		assertFalse(sectionViewConfiguration.getImportableSection(AvailableSection.SENIORITY, "flow", ImportType.SENIORITY));
		assertTrue(sectionViewConfiguration.getImportableSection(AvailableSection.SENIORITY, "flow", ImportType.TRANSFORMATION));
		assertFalse(sectionViewConfiguration.getImportableSection(AvailableSection.SENIORITY, "flow", null));
		
		//Test Seniority
		String[] importableFields = {"country",
				"nature",
				"filingDate",
				"filingNumber"
		};
		Set<String> collectionOfImportableFields = new HashSet<String>(Arrays.asList(importableFields));
 		
		for (String str : sectionViewConfiguration.getImportableFields(AvailableSection.SENIORITY, "flow", ImportType.SENIORITY)) {
			assertTrue(collectionOfImportableFields.remove(str));
		}
		assertEquals(collectionOfImportableFields.size(), 0);
		
		//Test Priority
		importableFields = new String[] {"country",
				"nature",
				"filingDate",
				"filingNumber",
				"registrationDate",
				"registrationNumber"
		};
		
		collectionOfImportableFields = new HashSet<String>(Arrays.asList(importableFields));
		for (String str : sectionViewConfiguration.getImportableFields(AvailableSection.SENIORITY, "flow", ImportType.PRIORITY)) {
			assertTrue(collectionOfImportableFields.remove(str));
		}
		assertEquals(collectionOfImportableFields.size(), 0);
		
		//Test Import Previous CTM
		importableFields = new String[] {"country",
				"nature",
				"filingDate",
				"filingNumber",
				"registrationDate",
				"registrationNumber"
		};
		
		collectionOfImportableFields = new HashSet<String>(Arrays.asList(importableFields));
		for (String str : sectionViewConfiguration.getImportableFields(AvailableSection.SENIORITY, "flow", ImportType.PREVIOUS_CTM)) {
			assertTrue(collectionOfImportableFields.remove(str));
		}
		assertEquals(collectionOfImportableFields.size(), 0);
		
		//Test Transformation
		importableFields = new String[] {"country",
				"nature",
				"filingDate",
				"filingNumber",
				"registrationDate",
				"registrationNumber"
		};
		
		collectionOfImportableFields = new HashSet<String>(Arrays.asList(importableFields));
		for (String str : sectionViewConfiguration.getImportableFields(AvailableSection.SENIORITY, "flow", ImportType.TRANSFORMATION)) {
			assertTrue(collectionOfImportableFields.remove(str));
		}
		assertEquals(collectionOfImportableFields.size(), 0);
		
		//Test General Importable
		importableFields = new String[] {"country",
				"nature",
				"filingDate",
				"filingNumber",
				"registrationDate",
				"registrationNumber"
		};
		
		collectionOfImportableFields = new HashSet<String>(Arrays.asList(importableFields));
		for (String str : sectionViewConfiguration.getImportableFields(AvailableSection.SENIORITY, "flow", null)) {
			assertTrue(collectionOfImportableFields.remove(str));
		}
		assertEquals(collectionOfImportableFields.size(), 0);
	}
}
