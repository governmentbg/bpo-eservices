package eu.ohim.sp.dsefiling.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import eu.ohim.sp.core.classification.LocarnoClassificationService;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.dsefiling.ui.service.interfaces.LocarnoServiceInterface;

/**
 * This class allows us cache the getFullClassification method.
 * @author serrajo
 *
 */
@Service(value="locarnoService")
public class LocarnoService implements LocarnoServiceInterface {

    @Autowired
    private LocarnoClassificationService locarnoClassificationService; 
    
    /**
     * 
     * @param lang
     * @return
     */
    @Override
	@Cacheable(value = "fullClassification")
	public List<ProductIndication> getFullClassification(String lang) {
		return locarnoClassificationService.getFullClassification(lang);
	}

    @Override
	@Cacheable(value = "searchProductIndication")
    public List<ProductIndication> searchProductIndication(String indication, String clazz, String subClass, String lang) {
    	return locarnoClassificationService.searchProductIndication(indication, clazz, subClass, lang);	
    }
    
}
