package eu.ohim.sp.dsefiling.ui.functions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.design.ContainsDesignsLinkForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewTypes.DesignViewType;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

/**
 * Functions for use in expression language in the jsp views.
 */
public final class ElFunctions {

	/**
	 * 
	 */
	private ElFunctions() {
		
	}
	
	/**
     * Return all design numbers linked formatted.
     * @return Numbers of linked designs.
     */
    public static String getLinkedDesignsNumbers(final ContainsDesignsLinkForm form) {
    	String designNumbers;
    	List<DesignForm> designs = form.getDesignsLinked();
    	if (CollectionUtils.isNotEmpty(designs)) {
    		List<String> toJoin = new ArrayList<String>(designs.size());
    		for (DesignForm designForm : designs) {
    			toJoin.add(designForm.getNumber().toString());
    		}
    		designNumbers = StringUtils.join(toJoin, ", ");
    	} else {
    		designNumbers = "";
    	}
    	return designNumbers;
    }
    
    /**
     * Return the label for the design view type.
     * @param designViewForm Design view form.
     * @param designViewTypes List of view types.
     * @return View type label.
     */
    public static String getLabelDesignViewType(final DesignViewForm designViewForm, final List<DesignViewType> designViewTypes) {
    	if (StringUtils.isNotEmpty(designViewForm.getType())) {
        	for (DesignViewType viewType : designViewTypes) {
        		if (StringUtils.equals(viewType.getCode(), designViewForm.getType())) {
        			return viewType.getValue();
        		}
        	}
    	}
    	return "";
    }
    
    /**
     * Return the different Locarno classifications.
     * @param designForm Design form.
     * @return Map with de different Locarno classifications.
     */
    public static Map<String, String> getDifferentLocarnoClassifications(final DesignForm designForm) {
    	LinkedHashMap<String, String> differentClassifications = new LinkedHashMap<String, String>();
    	String key;
    	String value;
    	for (LocarnoAbstractForm locarno : designForm.getLocarno()) {
    		key = locarno.getLocarnoClassFormatted();
    		if (differentClassifications.containsKey(key)) {
    			value = differentClassifications.get(key);
    			value = StringUtils.join(new String[]{value, locarno.getLocarnoIndication()}, ", ");
    		} else {
    			value = locarno.getLocarnoIndication();
    		}
    		differentClassifications.put(key, value);
    	}
    	return differentClassifications;
    }
    
    /**
     * Return true if is an application with more than one design, false in other case.
     * @param dsFlowBean Domain object.
     * @return true or false.
     */
    public static Boolean isMultipleDesignApplication(DSFlowBean dsFlowBean) {
    	return CollectionUtils.size(dsFlowBean.getDesigns()) > 1;
    }
    
    /**
     * Return true if is an application with only one design, false in other case.
     * @param dsFlowBean Domain object.
     * @return true or false.
     */
    public static Boolean isOneDesignApplication(DSFlowBean dsFlowBean) {
    	return CollectionUtils.size(dsFlowBean.getDesigns()) == 1;
    }

    /**
     * 
     * @param form
     * @return
     */
    public static Boolean isVisibleDesignSelects(final DSFlowBean dsFlowBean, final ContainsDesignsLinkForm form) {
    	return isMultipleDesignApplication(dsFlowBean) || CollectionUtils.isEmpty(form.getDesignsLinked());
    }
    
}
