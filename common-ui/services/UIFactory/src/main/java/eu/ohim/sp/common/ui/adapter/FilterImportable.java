/*******************************************************************************
 * * $Id:: FilterImportable.java 55890 2013-01-31 16:36:45Z soriama $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.interfaces.Importable;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration.ImportType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Component that is used to filter elements and fields according to configuration
 * 
 * @author soriama
 * 
 */
public abstract class FilterImportable<T extends FlowBean> {
    @Autowired
    private SectionViewConfiguration viewConfiguration;

    /**
     * It filters an imported form and checks if each element and each field should be imported according to
     * the sectionViewConfiguration
     * 
     * @param originalFlowBean the original form before the import
     * @param newFlowBean the imported form object
     * @param flowModeId the flow mode id in which the import takes place
     * @param importType the import type
     * @return the filtered imported data
     */
    public abstract  T filterFlowBean(T originalFlowBean, T newFlowBean, String flowModeId,
            ImportType importType) ;

    
    protected void copySingleObject(Object source, Object target, String flowModeId, AvailableSection sectionId,
            ImportType importType) throws SPException {
        PropertyUtilsBean p = new PropertyUtilsBean();
        List<String> importableFields = viewConfiguration.getImportableFields(sectionId, flowModeId, importType);
        for (String fieldName : importableFields) {
            if (p.isReadable(source, fieldName)) {
                try {
                    p.setNestedProperty(target, fieldName, p.getNestedProperty(source, fieldName));
                } catch (IllegalAccessException e) {
                    throw new SPException("Failed to set value :", e, "error.generic.filterImportable");
                } catch (InvocationTargetException e) {
                    throw new SPException("Failed to set value :", e, "error.generic.filterImportable");
                } catch (NoSuchMethodException e) {
                    throw new SPException("Failed to set value :", e, "error.generic.filterImportable");
                }
            }
        }
    }

    /**
     * Checks if a field should be imported or not
     * 
     * @param o the element that the imported data will be stored
     * @param flowModeId the flow mode id
     * @param sectionId the section on which the element appears
     * @param importType the import type
     * @return the filtered object
     */
    public Importable filterSingleObject(Importable o, String flowModeId, AvailableSection sectionId,
            ImportType importType) {
        PropertyBeanUtils p = new PropertyBeanUtils();
        List<String> importableFields = viewConfiguration.getImportableFields(sectionId, flowModeId, importType);
        List<String> allFields = viewConfiguration.getAllFields(sectionId, flowModeId);
        for (String fieldName : allFields) {
            if (!importableFields.contains(fieldName) && p.isReadable(o, fieldName)) {
                try {
                    if (p.getPropertyType(o, fieldName).equals(boolean.class)) {
                        p.setProperty(o, fieldName, false);
                    } else {
                        p.setProperty(o, fieldName, null);
                    }
                } catch (IllegalAccessException e) {
                    throw new SPException("Failed to set value :", e, "error.generic.filterImportable");
                } catch (InvocationTargetException e) {
                    throw new SPException("Failed to set value :", e, "error.generic.filterImportable");
                } catch (NoSuchMethodException e) {
                    throw new SPException("Failed to set value :", e, "error.generic.filterImportable");
                }
            }
        }
        return o;
    }

    public class PropertyBeanUtils extends PropertyUtilsBean {
        @Override
        public boolean isReadable(Object bean, String name) {
            try {
                boolean r = super.isReadable(bean, name);
                return r;
            } catch (org.apache.commons.beanutils.NestedNullException e) {
                return false;
            }
        }
    }
}
