package eu.ohim.sp.eservices.ui.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;
import eu.ohim.sp.eservices.ui.service.interfaces.DSLocarnoServiceInterface;
import eu.ohim.sp.eservices.ui.service.interfaces.LocarnoServiceInterface;

@Service(value="dsLocarnoService")
public class DSLocarnoService implements DSLocarnoServiceInterface {

    @Autowired
    private LocarnoServiceInterface locarnoService;
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable(value = "locarnoClasses")
	public List<LocarnoClass> getLocarnoClasses(String lang) {
		SortedSet<String> classes = new TreeSet<String>();
		List<ProductIndication> productIndications = locarnoService.getFullClassification(lang);
		for (ProductIndication productIndication : productIndications) {
			for (ProductIndicationClass productIndicationClass : productIndication.getClasses()) {
				classes.add(leftPadClass(productIndicationClass.getMainClass()));
			}
		}
		
		List<LocarnoClass> locarnoClasses = new ArrayList<LocarnoClass>();
		for (String clazz : classes) {
			locarnoClasses.add(new LocarnoClass(clazz, null));
		}
 		
		return locarnoClasses;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable(value = "locarnoSubclasses")
	public List<LocarnoClass> getLocarnoSubclasses(String clazz, String lang) {
		SortedSet<String> subclasses = new TreeSet<String>();
		List<ProductIndication> productIndications = locarnoService.getFullClassification(lang);
		for (ProductIndication productIndication : productIndications) {
			for (ProductIndicationClass productIndicationClass : productIndication.getClasses()) {
				if (StringUtils.equals(leftPadClass(productIndicationClass.getMainClass()), clazz)) {
					subclasses.add(leftPadClass(productIndicationClass.getSubClass()));	
				}
			}
		}
		
		List<LocarnoClass> locarnoSubclasses = new ArrayList<LocarnoClass>();
		for (String subclass : subclasses) {
			locarnoSubclasses.add(new LocarnoClass(clazz, subclass));
		}
 		
		return locarnoSubclasses;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Cacheable("locarnoClassifications")
	@Override
	public Map<String, LocarnoClassification> getLocarnoClassifications(String indication, String clazz, String subClass, String lang) {
		Map<String, LocarnoClassification> result = new LinkedHashMap<String, LocarnoClassification>();
		
		List<ProductIndication> productIndications = locarnoService.searchProductIndication(
				StringUtils.defaultIfEmpty(indication, ""), removeLeftPadClass(clazz), removeLeftPadClass(subClass), lang);
		
		for (ProductIndication productIndication : productIndications) {
			for (ProductIndicationClass productIndicationClass : productIndication.getClasses()) {
				for (ProductIndicationTerm productIndicationTerm : productIndicationClass.getTerms()) {
					result.put(productIndicationTerm.getIndprodID(), 
							new LocarnoClassification(
									productIndicationTerm.getIndprodID(),
									leftPadClass(productIndicationClass.getMainClass()),
									leftPadClass(productIndicationClass.getSubClass()),
									productIndicationTerm.getText()));
				}
			}
			
		}
		return result;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	private String leftPadClass(String clazz) {
		return StringUtils.leftPad(StringUtils.defaultIfEmpty(clazz, ""), 2, '0');
	}
	
	private String removeLeftPadClass(String clazz) {
		return StringUtils.removeStart(StringUtils.defaultIfEmpty(clazz, ""), "0");
	}
	
}
