/*
 *  RulesService:: SectionUtils 14/08/13 14:48 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.util;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import org.apache.log4j.Logger;

public class SectionUtil {

	private static final Logger LOGGER = Logger.getLogger(SectionUtil.class);


    protected SectionUtil() {}
	
	/**
	 * Get the section by RulesInformation
	 * @param rulesInformation
	 * @return Section
	 */
	public static Section getSectionByRulesInformation(RulesInformation rulesInformation) {
		Sections sections = null;
        Section section = null;
		
        // Checks the Rules information object and search the sections inside the custom map
        if (rulesInformation != null && rulesInformation.getCustomObjects() != null
				&& !rulesInformation.getCustomObjects().isEmpty()) {
			sections = (Sections) rulesInformation.getCustomObjects().get("sections");
		} else {
			LOGGER.debug("		> Error: couldn't get the custom map information");
			return null;
		}
        
        // Gets the subsection id
        if (sections != null) {
			section = getSection(rulesInformation, sections);
			if (section == null) {
				LOGGER.debug("		> Error: section not found.");
				return null;
			}
		} else {
			LOGGER.debug("		> Error: sections not found.");
			return null;
		}
        
        LOGGER.debug("		- Calling the rules validator");
		return section;
	}
	
	/**
     * Gets the section by applicant.
     * 
     * @param rulesInformation
     *            the rules information
     * @param sections
     *            the sections
     * @return the section by applicant
     */
	//TODO: Define the key of sectionName
	public static Section getSection(RulesInformation rulesInformation, Sections sections) {
		LOGGER.debug(">>> getSection START");

        Section section = null; 
        
        // Creates the iterator for the map
 		if (rulesInformation.getCustomObjects() != null &&
 				!rulesInformation.getCustomObjects().isEmpty()) {
 			String sectionName = (String) rulesInformation.getCustomObjects().get("sectionName");
 			if(sectionName != null){
 				section = getSectionById(sections, sectionName);
 			} else{
 				LOGGER.debug("		> Error: the section name couldn't be found");
 				return null;
 			}
 		} else {
 			LOGGER.debug("		> Error: the map is empty");
 			return null;
 		}
        
 		// Sets the section
		if (section != null) {
			LOGGER.debug("<<< getSection END");
			return section;
		}
 			
		// Something went wrong. The sectionName couldn't be found in the map or the section doesn't exist in the
		// sections object
		LOGGER.debug("   	> Error: the section couldn't be found");
		return null;
    }
    
    /**
     * Returns the section with the specified name.
     * 
     * @param sections
     *            the list of sections
     * @param id
     *            the id of the section we want
     * @return the section with the specified name, null if no section found.
     */
    public static Section getSectionById(Sections sections, String id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("   - Search:" + id);
        }
        if (sections != null) {
	        for (Section sectionIterator : sections.getSection()) {
 	            if (LOGGER.isDebugEnabled()) {
	                LOGGER.debug("   - Section:" + sectionIterator.getId().value());
	            }
	            if (sectionIterator.getId().value().equals(id)) {
	                if (LOGGER.isDebugEnabled()) {
	                    LOGGER.debug("   - Section Found:" + sectionIterator.getId().value());
	                }
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
    
    /**
     * Gets the sub section by id.
     * 
     * @param section
     *            the section
     * @param id
     *            the id
     * @return the sub section by id
     */
    public static Section getSubSectionById(Section section, String id) {
        for (Section sectionIterator : section.getSubsection()) {
            if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("      - Subsection:" + sectionIterator.getId().value());
			}
            if (sectionIterator.getId().value().equals(id)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("      - Subsection Found:" + sectionIterator.getId().value());
                }
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
