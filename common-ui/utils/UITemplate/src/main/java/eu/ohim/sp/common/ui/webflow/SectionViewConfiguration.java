/*******************************************************************************
 * * $Id:: SectionViewConfiguration.java 113489 2013-04-22 14:59:26Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.webflow;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Field;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SectionViewConfiguration {
	
	private static final Logger logger = Logger.getLogger(SectionViewConfiguration.class);

	public enum ImportType {
		PREVIOUS_CTM, PRIORITY, SENIORITY, TRANSFORMATION
	}

	private Map<String, Sections> viewConfiguration = null;

	public SectionViewConfiguration() {
		viewConfiguration = new HashMap<String, Sections>();
	}

	public void setViewConfiguration(Map<String, Sections> viewConfiguration) {
		this.viewConfiguration = viewConfiguration;
	}

	public Map<String, Sections> getViewConfiguration() {
		return viewConfiguration;
	}

	/**
	 * Returns a list of visible sections for the specific flowModeId and the state with the specific stateId
	 * @param flowModeId the id of the flowMode that we are interested
	 * @param stateId the id of the state that we are interested
	 * @return list of visible sections according to configurations
	 */
	@Cacheable(value = "sortedSections")
	public List<Section> getSortedSections(String flowModeId, String stateId) {
		
		logger.debug("getSortedSections is called for stateId:" + stateId);
		// Iterates all the sections and add into a list the corresponding to
		// the stateId, sorting by the property order
		List<Section> sections = new ArrayList<Section>();
		Sections sectionsObject = this.getViewConfiguration().get(flowModeId);
		for (Section s : sectionsObject.getSection()) {
			if (s.getViewStateId().equals(stateId) && s.isVisible() && StringUtils.isBlank(s.getSecured()) ) {
				sections.add(s);
			} else if (s.getViewStateId().equals(stateId) && s.isVisible() && StringUtils.isNotBlank(s.getSecured())) {
				UserDetails userDetails = (SecurityContextHolder.getContext().getAuthentication() != null && 
						SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null &&
						SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails ?
						((UserDetails) SecurityContextHolder
			            .getContext().getAuthentication().getPrincipal()) :
			            	null);
				if (userDetails != null) {
					for (GrantedAuthority authority : userDetails.getAuthorities()) {
						if (s.getSecured().contains(authority.getAuthority())) {
							sections.add(s);
							break;
						}
					}
				}
				
			}
		}
		Collections.sort(sections, new SectionComparator());
		return sections;
	}

	/**
	 * Returns a list of visible sections with sub-sections for the specific flowModeId and the state with the specific stateId
	 * @param flowModeId the id of the flowMode that we are interested
	 * @param stateId the id of the state that we are interested
	 * @param parentSectionId the 
	 * @return list of visible sections according to configurations
	 */
	@Cacheable(value = "sortedSubsections")
	public List<Section> getSortedSubsections(String flowModeId, String stateId, AvailableSection parentSectionId) {
		logger.debug("getSortedSubSections is called for stateId:" + stateId);
		// Iterates all the sections and add into a list the corresponding to
		// the stateId, sorting by the property order
		List<Section> sections = new ArrayList<Section>();
		Sections sectionsObject = this.getViewConfiguration().get(flowModeId);
		for (Section s : sectionsObject.getSection()) {
			sections = getSortedSubsections(s, parentSectionId);
			if (sections.size() > 0) {
				return sections;
			}
		}
		return sections;
	}

	private List<Section> getSortedSubsections(Section section, AvailableSection parentSectionId) {
		logger.debug("private getSortedSubSections is called for section:" + section.getId());
		/*Iterates all the sections and add into a list its subsections
		corresponding to the stateId and parentSectionId, sorting by the
		property order*/
		List<Section> sections = new ArrayList<Section>();
		if (section.getId().equals(parentSectionId)) {
			for (Section s : section.getSubsection()) {
				if (s.isVisible()) {
					sections.add(s);
				}
			}
		}
		Collections.sort(sections, new SectionComparator());
		return sections;
	}

	/**
	 * 
	 * Checks if it should be displayed the navigationPath
	 * @param flowModeId the id of the flow mode that we are interested
	 * @return true if the navigation path should be displayed
	 */
	@Cacheable(value = "displayNavigationPath")
	public boolean displayNavigationPath(String flowModeId) {
		//return whether the navigation path should be displayed
		//in the current mode
		return this.getViewConfiguration().get(flowModeId).isNavigationPath();		
	}

	@Cacheable(value = "sortedViewStates")
	public List<String> getSortedViewStates(String flowModeId) {
		// Iterates all the sections and add into a list the name of the
		// view-state-id, sorting by the property view-state-order
		Sections sectionsObject = this.getViewConfiguration().get(flowModeId);
		SortedMap<Integer, String> result = new TreeMap<Integer, String>();
		for (Section s : sectionsObject.getSection()) {
			if (s.isVisible() && sectionsObject.isNavigationPath()) {
				result.put(s.getViewStateOrder(), s.getViewStateId());
			}
		}
		return new ArrayList<>(result.values());
	}

	/**
	 * Checks whether the form can be viewed by the user
	 * 
	 * @param flowModeId
	 *            the flow mode id of the form
	 * @return true if anonymous user can view the page
	 */
	public boolean isAnonymousMode(String flowModeId) {
		return (viewConfiguration.get(flowModeId) != null && viewConfiguration.get(flowModeId).isAnonymousMode());
	}

	/**
	 * Checks that if a field is "renderable" for a specific section and
	 * flowMode
	 * 
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return true if it is "renderable"
	 */
	@Cacheable(value = "renderField")
	public Boolean getRender(AvailableSection sectionId, String field, String flowModeId) {

		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (getRender(section, sectionId, field)) {
				return true;
			}
		}

		return false;
	}

	private Boolean getRender(Section section, AvailableSection sectionId, String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			if (!section.isVisible()) {
				return false;
			}
			for (Field f : section.getField()) {
				if (f.getId().equals(field) && f.isVisible()) {
					return true;
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getRender(subSection, sectionId, field)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks that if a field is required for a specific section and flowMode
	 * 
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return true if it is "renderable"
	 */
	@Cacheable(value = "requiredField")
	public Boolean getRequired(AvailableSection sectionId, String field, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (getRequired(section, sectionId, field)) {
				return true;
			}
		}
		return false;
	}

	private Boolean getRequired(Section section, AvailableSection sectionId, String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			for (Field f : section.getField()) {
				if (f.getId().equals(field) && f.isRequired()) {
					return true;
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getRequired(subSection, sectionId, field)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks that if a field is editable when it is imported for a specific
	 * section and flowMode
	 * 
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return true if it is "editable" when imported
	 */
	@Cacheable(value = "editableField")
	public Boolean getEditableImport(AvailableSection sectionId, String field, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (getEditableImport(section, sectionId, field)) {
				return true;
			}
		}
		return false;
	}

	private Boolean getEditableImport(Section section, AvailableSection sectionId, String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			if (section.isEditableImport() != null && !section.isEditableImport()) {
				return false;
			}
			for (Field f : section.getField()) {
				if (f.getId().equals(field) && (f.isEditableImport() == null || f.isEditableImport())) {
					return true;
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getEditableImport(subSection, sectionId, field)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks that if a field is fast track
	 *
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return true if it is "editable" when imported
	 */
	@Cacheable(value = "isFastTrackField")
	public Boolean isFastTrack(AvailableSection sectionId, String field, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (isFastTrack(section, sectionId, field)) {
				return true;
			}
		}
		return false;
	}

	private Boolean isFastTrack(Section section, AvailableSection sectionId, String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			if (StringUtils.isNotEmpty(field)) {
				for (Field f : section.getField()) {
					if (f.getId().equals(field) && (f.isFastTrack() != null && f.isFastTrack())) {
						return true;
					}
				}
			} else {
				return (section.isFastTrack() != null && section.isFastTrack());
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (isFastTrack(subSection, sectionId, field)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the list of fast track values for a field
	 *
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return the pattern of the formatted field
	 */
	@Cacheable(value = "fastTrackValues")
	public List<String> getFastTrackValues(AvailableSection sectionId, String field, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			List<String> values = getFastTrackValues(section, sectionId, field);
			if (values != null) {
				return values;
			}
		}
		return null;
	}

	private List<String> getFastTrackValues(Section section, AvailableSection sectionId, String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			for (Field f : section.getField()) {
				if (f.getId().equals(field)) {
					String values = f.getFastTrackValues();
					if (values != null) {
						return Arrays.asList(values.split(","));
					} else {
						return null;
					}
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				List<String> values = getFastTrackValues(subSection, sectionId, field);
				if (values != null) {
					return values;
				}
			}
		}
		return null;
	}

	/**
	 * Checks that if a section has to be validated when it is imported
	 * 
	 * @param sectionId the section
	 * @param flowModeId the flowMode on which we are
	 * @return true if it has to be validate when imported
	 */
	@Cacheable(value = "validateImportSection")
	public Boolean getValidateImportSection(AvailableSection sectionId, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (getValidateImportSection(section, sectionId)) {
				return true;
			}
		}
		return false;
	}
	
	private Boolean getValidateImportSection(Section section, AvailableSection sectionId) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			if (section.isValidateImport() != null && section.isValidateImport()) {
				return true;
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getValidateImportSection(subSection, sectionId)) {
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * Checks that if a field is usable when it is imported for a specific
	 * section and flowMode
	 * 
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return true if it is "usable" when imported
	 */
	@Cacheable(value = "usableField")
	public Boolean getUsable(AvailableSection sectionId, String field,
			String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (getUsable(section, sectionId, field)) {
				return true;
			}
		}
		return false;
	}

	private Boolean getUsable(Section section, AvailableSection sectionId,
			String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			for (Field f : section.getField()) {
				if (f.getId().equals(field) && f.isUsable()) {
					return true;
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getUsable(subSection, sectionId, field)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks that if a field is formatted for a specific section and flowMode
	 * 
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return the pattern of the formatted field
	 */
	@Cacheable(value = "formattedField")
	public String getFormatted(AvailableSection sectionId, String field, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			String f = getFormatted(section, sectionId, field);
			if (f != null) {
				return f;
			}
		}
		return null;
	}

	private String getFormatted(Section section, AvailableSection sectionId, String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			for (Field f : section.getField()) {
				if (f.getId().equals(field)) {
					return f.getFormat();
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				String format = getFormatted(subSection, sectionId, field);
				if (format != null) {
					return format;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the default value of the field in a specific section and flowMode
	 * 
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return the default value
	 */
	@Cacheable(value = "defaultValueField")
	public String getDefaultValue(AvailableSection sectionId, String field, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			String f = getDefaultValue(section, sectionId, field);
			if (f != null) {
				return f;
			}
		}

		return null;
	}

	private String getDefaultValue(Section section, AvailableSection sectionId, String field) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			for (Field f : section.getField()) {
				if (f.getId().equals(field)) {
					return f.getDefaultValue();
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				String defaultValue = getDefaultValue(subSection, sectionId, field);
				if (defaultValue != null) {
					return defaultValue;
				}
			}
		}
		return null;
	}

	/**
	 * Checks that if a section is "importable" for a specific flowMode
	 * 
	 * @param sectionId
	 *            the section on which the field appears
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @param importType
	 *            the type of import we have
	 * @return true if it is "importable" otherwise false
	 */
	@Cacheable(value = "importableSection")
	public Boolean getImportableSection(AvailableSection sectionId, String flowModeId, ImportType importType) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (getImportableSection(section, sectionId, importType)) {
				return true;
			}
		}

		return false;
	}

	private Boolean getImportableSection(Section section, AvailableSection sectionId, ImportType importType) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			if (importType == null) {
				return section.isGeneralImportable() == null || section.isGeneralImportable();
			} else {
				if (importType.equals(ImportType.PREVIOUS_CTM)) {
					return section.isPreviousCTMImportable() == null || section.isPreviousCTMImportable();
				} else if (importType.equals(ImportType.PRIORITY)) {
					return (section.isPriorityImportable() == null || section.isPriorityImportable());
				} else if (importType.equals(ImportType.SENIORITY)) {
					return (section.isSeniorityImportable() == null || section.isSeniorityImportable());
				} else if (importType.equals(ImportType.TRANSFORMATION)) {
					return (section.isTransformationImportable() == null || section.isTransformationImportable());
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getImportableSection(subSection, sectionId, importType)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks that if a field is editable when it is imported for a specific
	 * section and flowMode from the user data
	 *
	 * @param sectionId
	 *            the section on which the field appears
	 * @param field
	 *            the field on which we are interested
	 * @param flowModeId
	 *            the flowMode on which we are
	 * @return true if it is "editable" when imported
	 */
	@Cacheable(value = "selectableField")
	public Boolean getSelectable(AvailableSection sectionId, String field, String flowModeId) {
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			if (getSelectable(section, sectionId, field)) {
				return true;
			}
		}
		return false;
	}

	private Boolean getSelectable(Section section, AvailableSection sectionId, String field) {
		if(StringUtils.isEmpty(field)){
			return getSelectable(section, sectionId);
		}

		if (section.getId() != null && section.getId().equals(sectionId)) {
			if (section.isSelectable() != null && !section.isSelectable()) {
				return false;
			}
			for (Field f : section.getField()) {
				if (f.getId().equals(field) && (f.isSelectable() == null || f.isSelectable())) {
					return true;
				}
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getSelectable(subSection, sectionId, field)) {
					return true;
				}
			}
		}
		return false;
	}

	private Boolean getSelectable(Section section, AvailableSection sectionId) {
		if (section.getId() != null && section.getId().equals(sectionId)) {
			if (section.isSelectable() == null || section.isSelectable()) {
				return true;
			}
		} else {
			for (Section subSection : section.getSubsection()) {
				if (getSelectable(subSection, sectionId)) {
					return true;
				}
			}
		}
		return false;
	}

	@Cacheable(value = "importableFields")
	public List<String> getImportableFields(AvailableSection sectionId, String flowModeId, ImportType importType) {
		List<String> importableFields = new ArrayList<String>();
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			importableFields = getImportableFields(section, sectionId, importType);
			if (importableFields.size() > 0) {
				return importableFields;
			}
		}
		return importableFields;
	}

	private List<String> getImportableFields(Section section, AvailableSection sectionId, ImportType importType) {
		List<String> importableFields = new ArrayList<String>();
		if (section.getId() != null && section.getId().equals(sectionId)) {
			for (Field f : section.getField()) {
				if (importType == null && (f.isGeneralImportable() == null || f.isGeneralImportable())) {
					importableFields.add(f.getId());
				} else if (importType != null) {
					if (importType.equals(ImportType.PREVIOUS_CTM)
							&& (f.isPreviousCTMImportable() == null || f.isPreviousCTMImportable())) {
						importableFields.add(f.getId());
					} else if (importType.equals(ImportType.PRIORITY)
							&& (f.isPriorityImportable() == null || f.isPriorityImportable())) {
						importableFields.add(f.getId());
					} else if (importType.equals(ImportType.SENIORITY)
							&& (f.isSeniorityImportable() == null || f.isSeniorityImportable())) {
						importableFields.add(f.getId());
					} else if (importType.equals(ImportType.TRANSFORMATION)
							&& (f.isTransformationImportable() == null || f.isTransformationImportable())) {
						importableFields.add(f.getId());
					}
				}
			}
			return importableFields;
		} else {
			for (Section subSection : section.getSubsection()) {
				importableFields = getImportableFields(subSection, sectionId, importType);
				if (importableFields.size() > 0) {
					return importableFields;
				}
			}
		}
		return importableFields;
	}

	@Cacheable(value = "allFields")
	public List<String> getAllFields(AvailableSection sectionId, String flowModeId) {
		List<String> allFields = new ArrayList<String>();
		List<Section> l = viewConfiguration.get(flowModeId).getSection();
		for (Section section : l) {
			allFields = getAllFields(section, sectionId);
			if (allFields.size() > 0) {
				return allFields;
			}
		}
		return allFields;
	}

	private List<String> getAllFields(Section section, AvailableSection sectionId) {
		List<String> allFields = new ArrayList<String>();
		if (section.getId() != null && section.getId().equals(sectionId)) {
			for (Field f : section.getField()) {
				allFields.add(f.getId());
			}
			return allFields;
		} else {
			for (Section subSection : section.getSubsection()) {
				allFields = getAllFields(subSection, sectionId);
				if (allFields.size() > 0) {
					return allFields;
				}
			}
		}
		return allFields;
	}
}
