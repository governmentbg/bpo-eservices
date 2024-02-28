package eu.ohim.sp.common.ui.adapter.design;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;

@Component
public class ProductIndicationSearchFactory implements UIFactory<ProductIndication, LocarnoSearchForm> {

	@Override
	public ProductIndication convertTo(LocarnoSearchForm form) {
		ProductIndication core = new ProductIndication();
		
		LocarnoClassification searchData = form.getSearchData();
		String indication = searchData.getIndication();
		String mainClass = searchData.getLocarnoClass().getClazz();
		String subClass = searchData.getLocarnoClass().getSubclass();
		
		
		ProductIndicationTerm productIndicationTerm = new ProductIndicationTerm();
		productIndicationTerm.setText(indication);
		ProductIndicationClass productIndicationClass = new ProductIndicationClass();
		productIndicationClass.setMainClass(mainClass);
		productIndicationClass.setSubClass(subClass);
		productIndicationClass.setTerms(Arrays.asList(new ProductIndicationTerm[]{productIndicationTerm}));
		core.setClasses(Arrays.asList(new ProductIndicationClass[]{productIndicationClass}));
		
		return core;
	}	

	@Override
	public LocarnoSearchForm convertFrom(@SuppressWarnings("unused") ProductIndication core) {
		return new LocarnoSearchForm(); 
	}

}
