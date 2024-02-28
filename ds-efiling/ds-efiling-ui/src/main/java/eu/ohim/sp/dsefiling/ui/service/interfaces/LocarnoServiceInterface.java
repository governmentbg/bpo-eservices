package eu.ohim.sp.dsefiling.ui.service.interfaces;

import java.util.List;

import eu.ohim.sp.core.domain.design.ProductIndication;

/**
 * 
 * @author serrajo
 *
 */
public interface LocarnoServiceInterface {

	/**
	 * 
	 * @param lang
	 * @return
	 */
	List<ProductIndication> getFullClassification(String lang);
	
	/**
	 * 
	 * @param indication
	 * @param clazz
	 * @param subClass
	 * @param lang
	 * @return
	 */
	List<ProductIndication> searchProductIndication(String indication, String clazz, String subClass, String lang);
	
}
