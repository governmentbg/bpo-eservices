package eu.ohim.sp.dsefiling.ui.service.interfaces;

import java.util.List;
import java.util.Map;

import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;

/**
 * 
 * @author serrajo
 *
 */
public interface DSLocarnoServiceInterface {

	/**
	 * 
	 * @param lang
	 * @return
	 */
	List<LocarnoClass> getLocarnoClasses(String lang);
	
	/**
	 * 
	 * @param clazz
	 * @param lang
	 * @return
	 */
	List<LocarnoClass> getLocarnoSubclasses(String clazz, String lang);
	
	/**
	 * 
	 * @param indication
	 * @param clazz
	 * @param subClass
	 * @param lang
	 * @return
	 */
	Map<String, LocarnoClassification> getLocarnoClassifications(String indication, String clazz, String subClass, String lang);
	
}
